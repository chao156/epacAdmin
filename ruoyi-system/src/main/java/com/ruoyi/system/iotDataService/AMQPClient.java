package com.ruoyi.system.iotDataService;

import com.ruoyi.system.MsgQueue.DataMsgProducer;
import com.ruoyi.system.MsgQueue.DataMsgQueue;
import org.apache.commons.codec.binary.Base64;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.*;

@Component
public class AMQPClient implements ApplicationRunner {

    private final static Logger logger = LoggerFactory.getLogger(AMQPClient.class);
    // 指定单个进程启动的连接数
    // 单个连接消费速率有限，请参考使用限制，最大64个连接
    // 连接数和消费速率及rebalance相关，建议每500QPS增加一个连接
    private static int connectionCount = 4;
    //业务处理异步线`程池，线程池参数可以根据您的业务特点调整，或者您也可以用其他异步方式处理接收到的消息。
    private final static ExecutorService executorService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue(50000));

    public AMQPClient(){
        try {
            List<Connection> connections = new ArrayList<>();
            logger.info("---------------------AMQPClient正在启动---------------------");
            //for (int i = 0; i < connectionCount; i++) {
                long timeStamp = System.currentTimeMillis();
                //签名方法：支持hmacmd5、hmacsha1和hmacsha256。
                String signMethod = "hmacsha1";

                //userName组装方法，请参见AMQP客户端接入说明文档。   "-" + i +
                String userName = AMQPConstant.CLIENT_ID +  "|authMode=aksign"
                        + ",signMethod=" + signMethod
                        + ",timestamp=" + timeStamp
                        + ",authId=" + AMQPConstant.ACCESS_KEY
                        + ",iotInstanceId=" + AMQPConstant.IOT_INSTANCE_ID
                        + ",consumerGroupId=" + AMQPConstant.CONSUMER_GROUP_ID
                        + "|";
                //计算签名，password组装方法，请参见AMQP客户端接入说明文档。
                String signContent = "authId=" + AMQPConstant.ACCESS_KEY + "&timestamp=" + timeStamp;
                String password = doSign(signContent, AMQPConstant.ACCESS_SECRET, signMethod);
                String connectionUrl = "failover:(amqps://" + AMQPConstant.HOST + ":5671?amqp.idleTimeout=80000)"
                        + "?failover.reconnectDelay=30";

                Hashtable<String, String> hashtable = new Hashtable<>();
                hashtable.put("connectionfactory.SBCF", connectionUrl);
                hashtable.put("queue.QUEUE", "default");
                hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
                Context context = new InitialContext(hashtable);
                ConnectionFactory cf = (ConnectionFactory) context.lookup("SBCF");
                Destination queue = (Destination) context.lookup("QUEUE");
                // 创建连接。
                Connection connection = cf.createConnection(userName, password);
                connections.add(connection);

                ((JmsConnection) connection).addConnectionListener(myJmsConnectionListener);
                // 创建会话。
                // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()。
                // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）。
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                connection.start();
                // 创建Receiver连接。
                MessageConsumer consumer = session.createConsumer(queue);
                consumer.setMessageListener(messageListener);
            //}
/*
            logger.info("amqp demo is started successfully, and will exit after 60s ");

            // 结束程序运行
            //Thread.sleep(60 * 1000);
            logger.info("run shutdown");*/

            /*connections.forEach(c -> {
                try {
                    c.close();
                } catch (JMSException e) {
                    logger.error("failed to close connection", e);
                }
            });
*/
            //executorService.shutdown();
            /*if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.info("shutdown success");
            } else {
                logger.info("failed to handle messages");
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        new AMQPClient();
    }

    private static MessageListener messageListener = new MessageListener() {
        @Override
        public void onMessage(final Message message) {
            try {
                //1.收到消息之后一定要ACK。
                // 推荐做法：创建Session选择Session.AUTO_ACKNOWLEDGE，这里会自动ACK。
                // 其他做法：创建Session选择Session.CLIENT_ACKNOWLEDGE，这里一定要调message.acknowledge()来ACK。
                // message.acknowledge();
                //2.建议异步处理收到的消息，确保onMessage函数里没有耗时逻辑。
                // 如果业务处理耗时过程过长阻塞住线程，可能会影响SDK收到消息后的正常回调。
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        processMessage(message);
                    }
                });
            } catch (Exception e) {
                logger.error("submit task occurs exception ", e);
            }
        }
    };

    /**
     * 在这里处理您收到消息后的具体业务逻辑。
     */
    private static void processMessage(Message message) {
        try {
            logger.info("AMQP接收体温数据，进行处理--------放入阻塞队列中");
            byte[] body = message.getBody(byte[].class);
            if(body != null) {
                String content = new String(body);
                String topic = message.getStringProperty("topic");
                String messageId = message.getStringProperty("messageId");
                logger.info("receive message"
                        + ",\n topic = " + topic
                        + ",\n messageId = " + messageId
                        + ",\n content = " + content);
                DataMsgQueue.getInstance().add(content);
            }
        } catch (Exception e) {
            logger.error("processMessage occurs error ", e);
        }
    }

    private static JmsConnectionListener myJmsConnectionListener = new JmsConnectionListener() {
        /**
         * 连接成功建立。
         */
        @Override
        public void onConnectionEstablished(URI remoteURI) {
            logger.info("onConnectionEstablished, remoteUri:{}", remoteURI);
        }

        /**
         * 尝试过最大重试次数之后，最终连接失败。
         */
        @Override
        public void onConnectionFailure(Throwable error) {
            logger.error("onConnectionFailure, {}", error.getMessage());
        }

        /**
         * 连接中断。
         */
        @Override
        public void onConnectionInterrupted(URI remoteURI) {
            logger.info("onConnectionInterrupted, remoteUri:{}", remoteURI);
        }

        /**
         * 连接中断后又自动重连上。
         */
        @Override
        public void onConnectionRestored(URI remoteURI) {
            logger.info("onConnectionRestored, remoteUri:{}", remoteURI);
        }

        @Override
        public void onInboundMessage(JmsInboundMessageDispatch envelope) {}

        @Override
        public void onSessionClosed(Session session, Throwable cause) {}

        @Override
        public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {}

        @Override
        public void onProducerClosed(MessageProducer producer, Throwable cause) {}
    };

    /**
     * 计算签名，password组装方法，请参见AMQP客户端接入说明文档。
     */
    private static String doSign(String toSignString, String secret, String signMethod) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), signMethod);
        Mac mac = Mac.getInstance(signMethod);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(toSignString.getBytes());
        return Base64.encodeBase64String(rawHmac);
    }


}

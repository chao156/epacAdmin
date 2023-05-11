package com.ruoyi.system.MsgQueue;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class DataMsgProducer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(DataMsgProducer.class);

    private BlockingQueue<Object> queue;

    private Object data;

    public DataMsgProducer(BlockingQueue<Object> queue,Object data){
        this.queue = queue;
        this.data = data;
    };

    @Override
    public void run() {
        try {
            log.info("---------------IOT数据生产者正在生产数据------------------");
            this.queue.put(data);
        }catch (Exception e){
            log.info("------------IOT数据生产者正在生产数据发生错误-----------------");
            e.printStackTrace();
        }
    }
}

package com.ruoyi.system.iotDataService;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.iot.model.v20180120.PubRequest;
import com.aliyuncs.iot.model.v20180120.PubResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.swing.text.html.FormSubmitEvent;
import java.nio.charset.StandardCharsets;

@Component
public class DataPushToAliIot {
    public void dataPush(String userId){
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", AMQPConstant.ACCESS_KEY, AMQPConstant.ACCESS_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile); //初始化SDK客户端。
        PubRequest request = new PubRequest();
        request.setIotInstanceId(AMQPConstant.IOT_INSTANCE_ID);
        request.setProductKey(AMQPConstant.PRODUCT_KEY);
        //将用户Id作为信号发送至阿里云服务器
        request.setMessageContent(Base64.encodeBase64String(userId.getBytes()));
        request.setTopicFullName(AMQPConstant.TOPIC_FULL_NAME);
        try {
            PubResponse response = client.getAcsResponse(request);
            System.out.println(response.getSuccess());
            System.out.println(response.getCode());
            System.out.println(response.getErrorMessage());
        } catch (ServerException e)
        {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            e.printStackTrace();
        }

    }
}

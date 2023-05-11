package com.ruoyi.system.MsgQueue;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.CurrentTimeUtil;
import com.ruoyi.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class DataMsgConsumer implements ApplicationRunner {

    @Autowired
    SysUserMapper sysUserMapper;
    /**
     * 创建线程运行，一直循环，队列中数据不为空就取出
     */

    private static final Logger log = LoggerFactory.getLogger(DataMsgConsumer.class);

    //引入依赖，处理体温数据

    public void handleData(){
        try {
            while(true) {
                log.info("正在消费体温数据");
                //从队列中取出数据
                Object data = DataMsgQueue.getInstance().take();
                //将队列中的数据实例化
                String dataStr = (String) data;
                if(dataStr.length()<=9) {
                    String temperture = dataStr.substring(0, 4);
                    String userIdtemp = dataStr.substring(8);

                    //解析dataStr中的内容
                    Long userId = Long.parseLong(userIdtemp);
                    Double temp = Double.parseDouble(temperture);

                    //查询并封装user对象
                    SysUser user = sysUserMapper.selectUserById(userId);
                    if("0".equals(user.getSex())){
                        user.setSex("男");
                    }else{
                        user.setSex("女");
                    }
                    user.setTemperature(temp.toString());
                    try {
                        user.setCreateTime(CurrentTimeUtil.StringToDate(CurrentTimeUtil.getCurrentTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //将检测的体温存储到数据库中
                    sysUserMapper.insertUserTempData(user);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        handleData();
    }
}

package com.ruoyi.web.controller.epac;


import cn.hutool.extra.qrcode.QrCodeException;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.iotDataService.DataPushToAliIot;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/tempData")
public class EpacTempDataController {


    @Autowired
    ISysUserService userService;

    @Autowired
    DataPushToAliIot dataPushToAliIot;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    RedisCache redisCache;

    @GetMapping(value = "/getUserInfo/{userName}")
    public SysUser getUserInfo(@PathVariable("userName") String userName){

        Long userId = sysUserMapper.selectUserIdByUserName(userName);
        //根据用户名查询用户信息
        SysUser user = userService.selectUserById(userId);
        String userIdd = "abc"+userId.toString();
        //将用户名发送到阿里云物联网平台
        String msg = "UserID,"+userIdd;
        System.out.println("正在将数据userID上传到阿里云平台："+msg);
        dataPushToAliIot.dataPush(msg);
        return user;
    }

    @GetMapping(value = "/addQueueNumber/{areaId}")
    public int addQueueNumber(@PathVariable("areaId") String areaId){

       // synchronized (dataPushToAliIot) {
            Integer queueNum = redisCache.getCacheObject("QueueNumber_" + areaId);
            if (queueNum == null) {
                Integer queueNumber = 1;
                redisCache.setCacheObject("QueueNumber_" + areaId, queueNumber);
            }else {
                queueNum++;
                redisCache.setCacheObject("QueueNumber_" + areaId, queueNum);
            }
            List list = redisCache.getCacheList("QueueNumber");
            //缓存将有排队人数的检测点保存
            if(list != null) {
                list.add(areaId);
                redisCache.setCacheObject("QueueNumber",list);
            }else{
                list = new ArrayList();
                list.add(areaId);
                redisCache.setCacheObject("QueueNumber",list);
            }
        //}
        return redisCache.getCacheObject("QueueNumber_" + areaId);
    }

    @GetMapping(value = "/getTempData/{userName}")
    public List<SysUser> getTempData(@PathVariable("userName") String userName){
        Long userId = sysUserMapper.selectUserIdByUserName(userName);
        //查询当前用户历史体温数据
        List<SysUser> userTempData = sysUserMapper.getUserTempData(userId);
        return userTempData;
    }

    @GetMapping("/getQrImage/{userName}")
    @ResponseBody
    public void getQrImage(@PathVariable("userName") String userName, HttpServletRequest request, HttpServletResponse response){
        //
        String qrCodeContent = userName;
        try {
            QrConfig qrConfig = new QrConfig(100, 100);
            qrConfig.setBackColor(Color.green);
            QrCodeUtil.generate(qrCodeContent, qrConfig, "png", response.getOutputStream());
        } catch (QrCodeException | IOException e) {
            System.out.println("生成健康码发生错误！ {}！"+ e.getMessage());
        }
    }

    @GetMapping("/clearRedis")
    public void clearRedis(){
        redisCache.deleteObject("QueueNumber");
    }
}

package com.ruoyi.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrentTimeUtil {

    public static String  getCurrentTime(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String currentTime = dateFormat.format(calendar.getTime());
        return currentTime;
    }

    public static Date StringToDate(String time) throws ParseException {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        Date date = dateFormat.parse(time);
        return date;
    }
}

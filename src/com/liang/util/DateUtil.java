package com.liang.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liang wei
 * @description 时间处理类
 * @date 2017/6/27 18:06
 */
public class DateUtil {
    public static SimpleDateFormat formatter;
    public final static String YEAR_TO_DAY = "yyyy-MM-dd";
    public final static String YEAR_TO_SECOND = "yyyy-MM-dd hh24:mm:ss";
    public static String getDateTime(String dateTime){
        formatter = new SimpleDateFormat (dateTime);
        Date date = new Date();
        String ctime = formatter.format(date);
        return ctime;
    }
}

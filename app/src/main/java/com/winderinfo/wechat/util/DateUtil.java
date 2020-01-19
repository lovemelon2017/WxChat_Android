package com.winderinfo.wechat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * lovely_melon
 * 2020/1/17
 */
public class DateUtil {
    public static String getDateStr(Date date) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String strDate = formatter.format(date);

        return formatter.format(date);
    }

    public static String date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

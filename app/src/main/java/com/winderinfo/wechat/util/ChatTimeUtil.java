package com.winderinfo.wechat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * lovely_melon
 * 2020/1/11
 */
public class ChatTimeUtil {


    /**
     * 日期格式化
     *
     * @return
     */
    public static String getTimeString(long times) {
        Date date = new Date(times);

        //转换为日期
        long time = date.getTime();
        if (isThisYear(date)) {//今年
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            if (isToday(date)) { //今天
                Calendar calendar = Calendar.getInstance();
                String am_pm = "";
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour >= 0 && hour < 6) {
                    am_pm = "凌晨";
                } else if (hour >= 6 && hour < 12) {
                    am_pm = "早上";
                } else if (hour == 12) {
                    am_pm = "中午";
                } else if (hour > 12 && hour < 18) {
                    am_pm = "下午";
                } else if (hour >= 18) {
                    am_pm = "晚上";
                }


//                int minute = minutesAgo(time);
//                if (minute <= 1) {
//                    return "刚刚";
//                } else {
//                    return am_pm + simpleDateFormat.format(date);
//                }
                return  simpleDateFormat.format(date);

            } else {
                if (isYestYesterday(date)) {//昨天，显示昨天
                    return "昨天" + simpleDateFormat.format(date);
                } else if (isThisWeek(date)) {//本周,显示周几
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "周一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "周二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "周三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "周四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "周五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "周六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "周日";
                    }
                    return weekday + simpleDateFormat.format(date);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
                    return sdf.format(date);
                }
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        }
    }

    private static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }

    private static boolean isToday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() == now.getDate());
    }

    private static boolean isYestYesterday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() + 1 == now.getDate());
    }

    private static boolean isThisWeek(Date date) {
        Date now = new Date();
        if ((date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth())) {
            if (now.getDay() - date.getDay() < now.getDay() && now.getDate() - date.getDate() > 0 && now.getDate() - date.getDate() < 7) {
                return true;
            }
        }
        return false;
    }

    private static boolean isThisYear(Date date) {
        Date now = new Date();
        return date.getYear() == now.getYear();
    }
}

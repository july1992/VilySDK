package com.vily.service;

import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  * description :  事件工具类
 *  
 **/
public class TimeUUtiles {

    private static String TAG = "TimeUUtiles";

//    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
//    private static SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
//    private static SimpleDateFormat formatMonthDay = new SimpleDateFormat("MM月dd日");
//    private static SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
//    private static SimpleDateFormat formatMoment = new SimpleDateFormat("HH:mm");


    /**
     * 当天0 点
     */
    public static String getCurrTime(String formatType) {
        try {

            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Date date = new Date(System.currentTimeMillis());

            String currData = format.format(date);

            return currData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getCurrLongTime(String formatType) {
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        long time_long = 0;
        try {
            Date date = new Date(System.currentTimeMillis());

            String currData = format.format(date);

            time_long = format.parse(currData).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time_long;
    }

    public static Date getCurrData() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public static long string2Long(String time, String formatType) {
        SimpleDateFormat format = new SimpleDateFormat(formatType);


        try {
            long time_long = format.parse(time).getTime();
            return time_long;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String string2String(String time, String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);

            Date date = convertTime2Date(time);
            String format1 = format.format(date);

            return format1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String long2String(long longTime, String formatType) {
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        String time = format.format(longTime);
        return time;
    }

    public static long long2Long(long longTime, String formatType) {
        try {
            String s = long2String(longTime, "yyyy-MM-dd HH:mm:ss");
            long time = string2Long(s, formatType);
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 本周的第一天
     */
    public static String getFirstDayOfWeek(String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_MONTH, 0);
            cal.set(Calendar.DAY_OF_WEEK, 2);
            Date time = cal.getTime();
            return format.format(time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 本周的最后一天
     */
    public static String getLastDayOfWeek(String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
            cal.add(Calendar.DAY_OF_WEEK, 1);
            Date time = cal.getTime();
            return format.format(time);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 本月第一天
     */
    public static String getFirstDayOfMonth(String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            Date time = cal.getTime();
            return format.format(time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    /**
//     * 本年第一天
//     */
//    public static String getYearStart(String formatType) {
//        SimpleDateFormat format = new SimpleDateFormat(formatType);
//        return format.format(new Date());
//    }

//    /**
//     * 本年最后一天
//     */
//    public static String getYearEnd() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        Date currYearLast = calendar.getTime();
//        return format2.format(currYearLast) + " 23:59:59";
//    }


    /**
     * n 天前的一天, 或者 n 天后的一天
     */
    public static String getNDaysAgo(String time, int n, String formatType) {

        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Date date = convertTime2Date(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + n);
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static long getNDaysLongAgo(String time, int n) {
        try {
            Date date = convertTime2Date(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + n);

            return calendar.getTime().getTime();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getNDaysLongAgo(long time, int n) {

        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + n);

        return calendar.getTime().getTime();
    }


    /**
     * 某一天那一周的最后一天
     */
    public static String getLastDayOfSomeWeek(String time, String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Date date = convertTime2Date(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 6);
            return format.format(calendar.getTime());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 向前 或者 向后  n  个月
     */
    public static String getFirstDayOfMonth(String time, int n, String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Date date = convertTime2Date(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + n);
            return format.format(calendar.getTime());


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 向前 或者 向后  n  个月
     */
    public static String getLastDayOfMonth(String time, String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Date date = convertTime2Date(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            return format.format(calendar.getTime());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 向前 或者 向后  n  个年
     */
    public static String getFirstDayOfYear(String time, int n, String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Date date = convertTime2Date(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + n);
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取某一年的最后一刻
     */
    public static String getLastDayOfYear(String time, String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Date date = convertTime2Date(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
            calendar.set(Calendar.DAY_OF_YEAR, 0);
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDate2Time(Date date, String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Date convertTime2Date(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = format.parse(strDate, pos);
        return strtodate;
    }

    public static String getParse(String time, String formatType) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatType);
            Date date = convertTime2Date(time);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 判断某一天是周几
    public static int dayForWeek(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Calendar c = Calendar.getInstance();

            c.setTime(format.parse(pTime));

            int dayForWeek = 0;
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
            return dayForWeek;

        } catch (ParseException e) {
            e.printStackTrace();
            return 1;

        }
    }

    public static int computeDays(String createTime) {
        Log.i(TAG, "computeDays: ------:"+createTime);
        Log.i(TAG, "computeDays: ------:"+TimeUUtiles.getCurrLongTime("yyyy-MM-dd HH:mm:ss"));
        try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currData = TimeUUtiles.getCurrData();
            Date start = dfs.parse(createTime);
            long between=(currData.getTime()-start.getTime())/1000;//除以1000是为了转换成秒
            long day=between/(24*3600);


            Log.i(TAG, "computeDays: ------start:"+(currData.getTime()-start.getTime())/(24*3600));

            return (int) day;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }


    }
}

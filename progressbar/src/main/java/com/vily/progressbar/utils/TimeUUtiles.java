package com.vily.progressbar.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  * description :  事件工具类
 *  
 **/
public class TimeUUtiles {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat formatMonthDay = new SimpleDateFormat("MM月dd日");
    private static SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
    private static SimpleDateFormat formatMoment = new SimpleDateFormat("HH:mm");



    /**
     *  当天0 点
     */
    public static String getCurrZeroTime() {

        Date date = new Date(System.currentTimeMillis());

        String currData = format2.format(date)+" 00:00:00";

        return currData;
    }

    public static String getCurrTime() {

        Date date = new Date(System.currentTimeMillis());

        String currData = format.format(date);

        return currData;
    }

    public static Date getCurrData() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    /**
     * 本周的第一天
     */
    public static String getFirstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time = cal.getTime();
        return format2.format(time) + " 00:00:00";
    }

    /**
     * 本周的最后一天
     */
    public static String getLastDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        Date time = cal.getTime();
        return format2.format(time) + " 23:59:59";

    }

    /**
     * 本月第一天
     */
    public static String getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        return format2.format(time) + " 00:00:00";
    }



    /**
     * 本年第一天
     */
    public static String getYearStart() {
        return formatYear.format(new Date()) + "-01-01 00:00:00";
    }

    /**
     * 本年最后一天
     */
    public static String getYearEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date currYearLast = calendar.getTime();
        return format2.format(currYearLast) + " 23:59:59";
    }


    /**
     * n 天前的一天, 或者 n 天后的一天
     */
    public static String getNDaysAgo(String time,int n) {
        Date date = convertTime2Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + n);
        return format.format(calendar.getTime());
    }

    /**
     *  某一天那一周的最后一天
     */
    public static String getLastDayOfSomeWeek(String time) {
        Date date = convertTime2Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 6);
        return format2.format(calendar.getTime())+ " 23:59:59";
    }
    /**
     *  向前 或者 向后  n  个月
     */
    public static String getFirstDayOfMonth(String time,int n) {
        Date date = convertTime2Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + n);
        return format2.format(calendar.getTime())+" 00:00:00";
    }

    /**
     *  向前 或者 向后  n  个月
     */
    public static String getLastDayOfMonth(String time) {
        Date date = convertTime2Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH,0);
        return format2.format(calendar.getTime()) + " 23:59:59";
    }


    /**
     *  向前 或者 向后  n  个年
     */
    public static String getFirstDayOfYear(String time,int n) {
        Date date = convertTime2Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + n);
        return format2.format(calendar.getTime())+" 00:00:00";
    }

    /**
     *  获取某一年的最后一天
     */
    public static String getLastDayOfYear(String time) {
        Date date = convertTime2Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        calendar.set(Calendar.DAY_OF_YEAR,0);
        return format2.format(calendar.getTime())+" 23:59:59";
    }

    public static String convertDate2Time(Date date) {

        return format.format(date);

    }

    public static Date convertTime2Date(String strDate) {

        ParsePosition pos = new ParsePosition(0);
        Date strtodate = format.parse(strDate, pos);
        return strtodate;
    }

    public static String getYear(String time){

        Date date = convertTime2Date(time);
        return  formatYear.format(date)+"年";
    }

    public static String getMonthAndDay(String time){

        Date date = convertTime2Date(time);
        return  formatMonthDay.format(date);
    }
    public static String getMonth(String time){

        Date date = convertTime2Date(time);
        return  formatMonth.format(date)+"月";
    }

    public static String getMoment(String time){

        Date date = convertTime2Date(time);
        return  formatMoment.format(date);
    }
}

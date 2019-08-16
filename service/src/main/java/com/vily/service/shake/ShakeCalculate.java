package com.vily.service.shake;

import android.util.Log;


import com.vily.service.TimeUUtiles;

import java.util.ArrayList;
import java.util.List;

/**
 *  
 **/
public class ShakeCalculate {

    private static final String tag = "ShakeCalculate";

    private static final long second_15 = 1000*60*15;
    private static final long hour_4 = 1000*60*60*4;

    private static final int val_light = 5;
    private static final int val_deep = 3;
    private static final int val_rem = 8;

    /**
     * 计算某天的睡眠数据
     * @param list 陀螺仪数据
     * @param date 某天，格式yyyy-MM-dd
     * @return
     */
    public static SleepData calculate(List<ShakeBean> list, String date) {
        SleepData sleepData = new SleepData();
        List<SleepBean> result = new ArrayList<>();
        if(list == null || list.isEmpty() || date == null || "".equals(date)) {
            sleepData.setSleepList(result);
            return sleepData;
        }
        Log.w(tag, "before calculate list size "+list.size());
        long time_00 = TimeUUtiles.string2Long(date, "yyyy-MM-dd") - hour_4;
        long time_temp = time_00;

        long shake_time_first = list.get(0).getCreateTime();
        long shake_time_end = list.get(list.size()-1).getCreateTime();


        sleepData.setShake_time_first(shake_time_first);
        sleepData.setShake_time_end(shake_time_end);
        sleepData.setBed_time_total((shake_time_end-shake_time_first)/1000);


        long light_sleep_total = 0; //浅睡,单位秒

        long deep_sleep_total = 0; //深睡,单位秒

        long rem_sleep_total = 0; //rem,单位秒

        int start = 0;
        //判断睡眠起点
        for (int i = 0; i < 48; i ++) {

            if(i > 0) {
                time_temp += second_15;
            }

            if(shake_time_first > time_temp) {
                continue;
            }

            SleepBean bean = new SleepBean();

            long light_sleep = 0;
            long deep_sleep = 0;
            long rem_sleep = 0;

            for (int k = start; k < list.size(); k ++) {
                long l = list.get(k).getCreateTime();
                if(l < time_temp) {
                    int num = list.get(k).getShakeNum();
                    if(num <= val_deep) {
                        deep_sleep += list.get(k).getStatTime();
                    } else if(num < val_light) {
                        light_sleep += list.get(k).getStatTime();
                    } else if(num < val_rem) {
                        rem_sleep += list.get(k).getStatTime();
                    } else {
                        //no sleep
                    }
                } else {
                    start = k;
                    break;
                }

            }
            bean.setUserId(0);
            bean.setLight_sleep_time(light_sleep);
            bean.setDeep_sleep_time(deep_sleep);
            bean.setRem_time(rem_sleep);
            bean.setCreateTime(long2String(time_temp-second_15));
            result.add(bean);

            Log.w(tag, "item "+i+" light_sleep: "+light_sleep+" deep_sleep: "+deep_sleep+" rem_sleep: "+rem_sleep);

            light_sleep_total+= light_sleep;
            deep_sleep_total += deep_sleep;
            rem_sleep_total += rem_sleep;

            if(time_temp > shake_time_end) {
                break;
            }
        }
        sleepData.setLight_sleep_total(light_sleep_total);
        sleepData.setDeep_sleep_total(deep_sleep_total);
        sleepData.setRem_time_total(rem_sleep_total);
        double time = (light_sleep_total+deep_sleep_total+rem_sleep_total)/60/60;

        Log.w(tag, "total sleep time "+time);

        sleepData.setScore(ScoreCalculate.calculate(time));
        sleepData.setSleepList(result);
        Log.w(tag, "after calculate list size "+result.size());
        return sleepData;
    }

    private static long string2Long(String date) {
        return TimeUUtiles.string2Long(date, "yyyy-MM-dd HH:mm:ss");
    }

    private static String long2String(long time) {
        return TimeUUtiles.long2String(time, "yyyy-MM-dd HH:mm:ss");
    }

}

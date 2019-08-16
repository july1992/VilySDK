package com.vily.service.shake;



import com.vily.service.TimeUUtiles;

import java.util.List;

public class SleepData {


    private long bed_time_total; //床上时间,单位秒

    private long light_sleep_total; //浅睡,单位秒

    private long deep_sleep_total; //深睡,单位秒

    private long rem_time_total; //rem,单位秒

    private long shake_time_first;
    private long shake_time_end;

    private int score;

    private List<SleepBean> sleepList;

    public long getBed_time_total() {
        return bed_time_total;
    }

    public void setBed_time_total(long bed_time_total) {
        this.bed_time_total = bed_time_total;
    }

    public long getLight_sleep_total() {
        return light_sleep_total;
    }

    public void setLight_sleep_total(long light_sleep_total) {
        this.light_sleep_total = light_sleep_total;
    }

    public long getDeep_sleep_total() {
        return deep_sleep_total;
    }

    public void setDeep_sleep_total(long deep_sleep_total) {
        this.deep_sleep_total = deep_sleep_total;
    }

    public long getRem_time_total() {
        return rem_time_total;
    }

    public void setRem_time_total(long rem_time_total) {
        this.rem_time_total = rem_time_total;
    }

    public long getShake_time_first() {
        return shake_time_first;
    }

    public void setShake_time_first(long shake_time_first) {
        this.shake_time_first = shake_time_first;
    }

    public long getShake_time_end() {
        return shake_time_end;
    }

    public void setShake_time_end(long shake_time_end) {
        this.shake_time_end = shake_time_end;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<SleepBean> getSleepList() {
        return sleepList;
    }

    public void setSleepList(List<SleepBean> sleepList) {
        this.sleepList = sleepList;
    }



    @Override
    public String toString() {
        return "SleepData{" +
                "bed_time_total=" + bed_time_total +
                ", light_sleep_total=" + light_sleep_total +
                ", deep_sleep_total=" + deep_sleep_total +
                ", rem_time_total=" + rem_time_total +
                ", shake_time_first=" + TimeUUtiles.long2String(shake_time_first,"yyyy-MM-dd HH:mm:ss") +
                ", shake_time_end=" + TimeUUtiles.long2String(shake_time_end,"yyyy-MM-dd HH:mm:ss")  +
                ", score=" + score +
                '}';
    }
}

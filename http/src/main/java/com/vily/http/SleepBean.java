package com.vily.http;


import java.io.Serializable;

/**
 *  * description :  每隔15分钟数据库表
 *  
 **/
public class SleepBean   implements Serializable {

    private int id;

    private int userId;

    private int score;

    private double bed_time;

    private double rem_time;
    private double light_sleep_time;
    private double deep_sleep_time;

    private String sn;

    private String createTime;


    private int flag;  // 0 未上传

    public SleepBean() {
    }



    public SleepBean(int userId, int score, double bed_time, double rem_time, double light_sleep_time, double deep_sleep_time, String sn, String createTime) {
        this.userId = userId;
        this.score = score;
        this.bed_time = bed_time;
        this.rem_time = rem_time;
        this.light_sleep_time = light_sleep_time;
        this.deep_sleep_time = deep_sleep_time;
        this.sn = sn;
        this.createTime = createTime;
        this.flag = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getBed_time() {
        return bed_time;
    }

    public void setBed_time(double bed_time) {
        this.bed_time = bed_time;
    }



    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public double getRem_time() {
        return rem_time;
    }

    public void setRem_time(double rem_time) {
        this.rem_time = rem_time;
    }

    public double getLight_sleep_time() {
        return light_sleep_time;
    }

    public void setLight_sleep_time(double light_sleep_time) {
        this.light_sleep_time = light_sleep_time;
    }

    public double getDeep_sleep_time() {
        return deep_sleep_time;
    }

    public void setDeep_sleep_time(double deep_sleep_time) {
        this.deep_sleep_time = deep_sleep_time;
    }

    @Override
    public String toString() {
        return "SleepBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", score=" + score +
                ", bed_time=" + bed_time +
                ", rem_time=" + rem_time +
                ", light_sleep_time=" + light_sleep_time +
                ", deep_sleep_time=" + deep_sleep_time +
                ", sn='" + sn + '\'' +
                ", createTime='" + createTime + '\'' +
                ", flag=" + flag +
                '}';
    }
}

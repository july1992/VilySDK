package com.vily.service.shake;


import com.vily.service.TimeUUtiles;

import java.io.Serializable;

/**
 *  
 **/
public class ShakeBean   implements Serializable {

    private int id;

    private int userId;

    private int statTime; //统计时间

    private int shakeNum; //晃动次数

    private long createTime; //记录时间

    public ShakeBean(int userId, int shakeNum, int statTime, long createTime) {
        this.userId = userId;
        this.shakeNum = shakeNum;
        this.statTime = statTime;
        this.createTime = createTime;
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

    public int getStatTime() {
        return statTime;
    }

    public void setStatTime(int statTime) {
        this.statTime = statTime;
    }

    public int getShakeNum() {
        return shakeNum;
    }

    public void setShakeNum(int shakeNum) {
        this.shakeNum = shakeNum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

//    @Override
//    public String toString() {
//        return "ShakeBean{" +
//                "id=" + id +
//                ", userId=" + userId +
//                ", shakeNum=" + shakeNum +
//                ", createTime='" + TimeUUtiles.long2String(createTime,"yyyy-MM-dd HH:mm:ss") + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        return "ShakeBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", statTime=" + statTime +
                ", shakeNum=" + shakeNum +
                ", createTime=" + TimeUUtiles.long2String(createTime,"yyyy-MM-dd HH:mm:ss") +
                '}';
    }
}

package com.vily.litepal;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/19
 *  
 **/
public class SleepBean extends LitePalSupport {

    private int id;

    private int user_id;

    private int score;

    private String sn;

    @Column(unique = true)
    private String create_time;

    public SleepBean() {
    }

    public SleepBean( int user_id, int score, String sn, String create_time) {

        this.user_id = user_id;
        this.score = score;
        this.sn = sn;
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "SleepBean{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", score=" + score +
                ", sn='" + sn + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}

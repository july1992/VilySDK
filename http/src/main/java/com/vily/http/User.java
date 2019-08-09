package com.vily.http;

import java.util.Date;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/8
 *  
 **/
public class User {

    private String id;

    private String name;

    private String phone;

    private String icon;

    public String getId() {
        return id;
    }

    public String crateTime;


    public User() {
    }

    public User(String name, String phone, String icon, String crateTime) {
        this.name = name;
        this.phone = phone;
        this.icon = icon;
        this.crateTime = crateTime;
    }

    public User(String id, String name, String phone, String icon, String crateTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.icon = icon;
        this.crateTime = crateTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(String crateTime) {
        this.crateTime = crateTime;
    }
}

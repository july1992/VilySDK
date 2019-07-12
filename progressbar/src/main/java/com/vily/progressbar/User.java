package com.vily.progressbar;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/7/8
 *  
 **/
public class User {

    private int progress;
    private String title;

    private int color;

    public User() {
    }

    public User(int progress, String title, int color) {
        this.progress = progress;
        this.title = title;
        this.color = color;
    }

    public User(int progress, String title) {
        this.progress = progress;
        this.title = title;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

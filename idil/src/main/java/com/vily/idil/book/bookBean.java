package com.vily.idil.book;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-22
 *  
 **/
public class bookBean implements Parcelable {


    private int id;
    private String name;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(phone);

    }

    public bookBean() {
    }

    public bookBean(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    protected bookBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        phone=in.readString();
    }


    public static final Creator<bookBean> CREATOR = new Creator<bookBean>() {
        @Override
        public bookBean createFromParcel(Parcel in) {
            return new bookBean(in);
        }

        @Override
        public bookBean[] newArray(int size) {
            return new bookBean[size];
        }
    };

    @Override
    public String toString() {
        return "bookBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

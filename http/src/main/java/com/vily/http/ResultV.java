package com.vily.http;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/8
 *  
 **/
public class ResultV<T> {


    private int code;
    private String message;
    private T data;

    public ResultV() {
    }

    public ResultV(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "ResultV{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

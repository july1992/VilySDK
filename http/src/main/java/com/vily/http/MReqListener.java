package com.vily.http;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-14
 *  
 **/
public abstract class MReqListener<T> {
    private MCallBack<T> callBack;

    public MReqListener(MCallBack<T> callBack) {
        this.callBack = callBack;
    }

    public MCallBack<T> getCallBack() {
        return this.callBack;
    }

    public T onResponse_(String text) throws Exception {
        return this.onResponse(text);
    }

    public abstract T onResponse(String var1) throws Exception;
}

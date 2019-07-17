package com.vily.http;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *  * description : 接口
 *  
 **/
public interface ApiService {


    // 测试
    @GET("/wechat/home/test")
    Observable<String> test();

    // 测试
    @GET("wechat/home/testParam")
    Observable<String> testParam(@Query("phone") String phone);


    // 发送验证码
    @GET("/senbloapi/mobile/sendCode")
    Observable<String> sendCode(@Query("phone") String phone, @Query("type") int type);

    // 验证码校验
    @GET("/senbloapi/mobile/validateCode")
    Observable<String> validateCode(@Query("id") String id, @Query("code") String code);

    // 验证码校验登陆
    @GET("/senbloapi/user/loginByToken")
    Observable<String> loginByToken(@Query("phone") String phone, @Query("mobileToken") String mobileToken);

    // 注册
    @GET("/senbloapi/user/regist")
    Observable<String> regist(@Query("mobileToken") String mobileToken, @Query("phone") String phone,
                              @Query("password") String password, @Query("nickname") String nickname);

    // 设备绑定
    @GET("senbloapi/user/regist")
    Observable<String> bind(@Query("user_id") String user_id, @Query("sn") String sn);

    // 年数据
    @GET("/senbloapi/sleep/getSleepDataMonth")
    Observable<String> getSleepDataMonth(@Query("user_id") String user_id, @Query("sn") String sn,
                                         @Query("startTime") String startTime, @Query("endTime") String endTime);
}

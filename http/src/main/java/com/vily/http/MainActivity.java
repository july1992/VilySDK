package com.vily.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button mBtn_click;
    private Button mBtn_click2;

//    private String base_url="http://192.168.93.83:8080/";
//    private String base_url="http://192.168.93.113:8080/";
    private String base_url="http://106.75.122.206:8000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn_click2 = findViewById(R.id.btn_click2);
//
        mBtn_click = findViewById(R.id.btn_click);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit build = builder.build();

        final ApiService apiService = build.create(ApiService.class);


        final List<User> list=new ArrayList();
        for(int i=0;i<100;i++){
            User user=new User(""+i,"ssssad"+i,"ssss","aaaa","2018-05-15 00:00:00");
            list.add(user);
        }


        final String json = JSON.toJSONString(list);

        Log.i(TAG, "onCreate: -----:"+json);

        mBtn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ----开始网络请求");

                try {
                    String encode = URLEncoder.encode(json, "utf-8");

                    apiService.testJson(encode)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DefaultObserver<ResultV>() {
                                @Override
                                public void onNext(ResultV s) {
                                    Log.i(TAG, "onNext: ----"+s.toString());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.i(TAG, "onError: ----:"+e.getMessage());
                                    Log.i(TAG, "onError: ----:"+e.toString());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        });


        final List<SleepBean> data=new ArrayList();
        for(int i=0;i<100;i++){
            SleepBean sleepBean=new SleepBean(1000,23,44444,3333,44444,2222,"第多少条：:"+i,"2019-08-30 00:00:00");
            data.add(sleepBean);
        }


        final String json2 = JSON.toJSONString(data);
        Log.i(TAG, "onCreate: -----:"+json2);

        mBtn_click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String encode = URLEncoder.encode(json2, "utf-8");




                apiService.testBody2(100003,data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<ResultV>() {
                            @Override
                            public void onNext(ResultV s) {
                                Log.i(TAG, "onNext: ----"+s.toString());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError: ----:"+e.getMessage());
                                Log.i(TAG, "onError: ----:"+e.toString());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });


        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                apiService.advertisLogin("sms_login","17521721435")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<Object>() {
                            @Override
                            public void onNext(Object s) {
                                Log.i(TAG, "onNext: ----"+s.toString());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError: ----:"+e.getMessage());
                                Log.i(TAG, "onError: ----:"+e.toString());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

        final MCallBack<ResultV> callBack =new MCallBack<ResultV>() {
            @Override
            public void onResult(ResultV var1) {

            }
        };
        findViewById(R.id.btn_send2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("username", "17521721435");
                        params.put("type", "sms_login");
                        Log.i(TAG, "run: --------:"+params);
                        Mhttp.requestGet("http://106.75.122.206:8000/api/code/codes",params);
                    }
                }).start();

            }
        });
    }
}

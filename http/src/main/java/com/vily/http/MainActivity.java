package com.vily.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button mBtn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn_click = findViewById(R.id.btn_click);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.93.113:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit build = builder.build();

        final ApiService apiService = build.create(ApiService.class);



        mBtn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ----开始网络请求");
                apiService.testParam("14a4sdsads")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<String>() {
                            @Override
                            public void onNext(String s) {
                                Log.i(TAG, "onNext: ----");
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



    }
}

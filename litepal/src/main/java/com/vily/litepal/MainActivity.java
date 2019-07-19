package com.vily.litepal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.FluentQuery;
import org.litepal.LitePal;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn_save = findViewById(R.id.btn_save);
        Button btn_read = findViewById(R.id.btn_read);
        Button btn_clear = findViewById(R.id.btn_clear);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {

            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        }


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SleepBean sleepBean=new SleepBean();
                sleepBean.setUser_id(10003);
                sleepBean.setCreate_time("2018-09-22 22:22:22");
                sleepBean.setScore((int) (Math.random()*100));
                sleepBean.save();

//                SleepBean sleepBean2=new SleepBean();
//                sleepBean2.setUser_id(10003);
//                sleepBean2.setCreate_time("2018-08-22 12:22:22");
//                sleepBean2.setScore((int) (Math.random()*100));
//                sleepBean2.save();
//
//                SleepBean sleepBean3=new SleepBean();
//                sleepBean3.setUser_id(10003);
//                sleepBean3.setCreate_time("2018-07-22 6:22:22");
//                sleepBean3.setScore((int) (Math.random()*100));
//                sleepBean3.save();
//
//                SleepBean sleepBean4=new SleepBean();
//                sleepBean4.setUser_id(10003);
//                sleepBean4.setCreate_time("2018-06-22 9:22:22");
//                sleepBean4.setScore((int) (Math.random()*100));
//                sleepBean4.save();
            }
        });


        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SleepBean sleepBean = LitePal.find(SleepBean.class, 0);
                List<SleepBean> all = LitePal.findAll(SleepBean.class);

                if(all!=null && all.size()>0){

                    for(int i=0;i<all.size();i++){
                        Log.i(TAG, "onClick: ----:"+all.get(i).getUser_id()+"---:"+all.get(i).getScore()+"----id:"+all.get(i).getId()
                                +all.get(i).getCreate_time());
                    }
                }else{
                    Log.i(TAG, "onClick: -------空的呀");
                }

                Log.i(TAG, "onClick: ------------------------");

                List<SleepBean> sleepBeans = LitePal.where("score>? and score<?", "40", "60").find(SleepBean.class);
                if(sleepBeans!=null && sleepBeans.size()>0){

                    for(int i=0;i<sleepBeans.size();i++){
                        Log.i(TAG, "onClick: ----:"+sleepBeans.get(i).getUser_id()+"---:"+sleepBeans.get(i).getScore()+"----id:"+sleepBeans.get(i).getId()
                                +sleepBeans.get(i).getCreate_time());
                    }
                }

                Log.i(TAG, "onClick: ------------------------");

                List<SleepBean> datas = LitePal.where("create_time between ? and ?", "2018-07-13 22:22:22", "2018-08-26 22:22:22").find(SleepBean.class);
                if(datas!=null && datas.size()>0){

                    for(int i=0;i<datas.size();i++){
                        Log.i(TAG, "onClick: ----时间段:"+datas.get(i).getUser_id()+"---:"+datas.get(i).getScore()+"----id:"+datas.get(i).getId()
                                +datas.get(i).getCreate_time());
                    }
                }


            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(SleepBean.class);
            }
        });
    }
}

package com.vily.idilclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vily.idil.NewStub;
import com.vily.idil.VilyAidl;
import com.vily.idil.book.bookBean;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    protected VilyAidl addService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // 绑定service 到当前页面
        Intent intent=new Intent(VilyAidl.class.getName());
        intent.setAction("service.vily");
        intent.setPackage("com.vily.idil");
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);


        // 监听事件
        Button btn_click = findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    mNewStub.test();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                try {
                    if(addService!=null){
                        int i = addService.addNumbers(1, 30);
                        Log.i(TAG, "onClick: ---------:"+i);
                    }else{
                        Log.i(TAG, "onClick: -------service 是空:");
                    }

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(addService!=null){
                        List<bookBean> bookList = addService.getBookList();
                        for(int i=0;i<bookList.size();i++){
                            Log.i(TAG, "onClick: ------:"+bookList.get(i).toString());
                        }
                    }else{
                        Log.i(TAG, "onClick: -------service 是空:");
                    }

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            addService = VilyAidl.Stub.asInterface((IBinder) service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            addService = null;
        }
    };

    private NewStub mNewStub=new NewStub.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void test() throws RemoteException {

            Log.i(TAG, "onClick: ----------你想说啥子呀");
        }
    };
}

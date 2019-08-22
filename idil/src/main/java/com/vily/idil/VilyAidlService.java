package com.vily.idil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.vily.idil.book.bookBean;

import java.util.ArrayList;
import java.util.List;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-22
 *  
 **/
public class VilyAidlService extends Service {

    private static final String TAG = "VilyAidlService";
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final VilyAidl.Stub mBinder = new VilyAidl.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int addNumbers(int num1, int num2) throws RemoteException {
            Log.i(TAG, "addNumbers: --------:"+num1+"---:"+num2);
            return num1+num2;
        }

        @Override
        public List<bookBean> getBookList() throws RemoteException {
            List<bookBean> list=new ArrayList<>();

            list.add(new bookBean(1,"eee","rrrr"));
            list.add(new bookBean(1,"eee","rrrr"));
            list.add(new bookBean(1,"eee","rrrr"));
            list.add(new bookBean(1,"eee","rrrr"));
            return list;

        }

    };
}

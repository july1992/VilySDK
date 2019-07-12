//package com.vily.pay;
//
//import android.os.Message;
//import android.view.View;
//import android.widget.Toast;
//
//import com.alipay.sdk.app.PayTask;
//
///**
// *  * description : 
// *  * Author : Vily
// *  * Date : 2019/7/10
// *  
// **/
//public class PayUtils {
//
//    /**
//     * check whether the device has authentication alipay account.
//     * 查询终端设备是否存在支付宝认证账户
//     *
//     */
//    public void check(View v) {
//        Runnable checkRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造PayTask 对象
//                PayTask payTask = new PayTask(MainActivity.this);
//                // 调用查询接口，获取查询结果
//                boolean isExist = payTask.checkAccountIfExist();
//
//                Message msg = new Message();
//                msg.what = SDK_CHECK_FLAG;
//                msg.obj = isExist;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        Thread checkThread = new Thread(checkRunnable);
//        checkThread.start();
//
//    }
//
//
//    /**
//     * get the sdk version. 获取SDK版本号
//     *
//     */
//    public void getSDKVersion() {
//        PayTask payTask = new PayTask(this);
//        String version = payTask.getVersion();
//        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
//    }
//}

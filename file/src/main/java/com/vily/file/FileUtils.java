package com.vily.file;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019/8/9
 *  
 **/
public class FileUtils {

    private static final String TAG = "MainActivity";

    public static void write(String childDir, String name, int value) {

        try {


            File file=new File(childDir,name);
            if(!file.exists()){
                file.createNewFile();
            }

            Log.i(TAG, "write: -------:"+file.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

            bw.write(""+ value);
//            bw.write(",");   // 列换行
            bw.newLine();      // 行换行

            bw.close();
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

}

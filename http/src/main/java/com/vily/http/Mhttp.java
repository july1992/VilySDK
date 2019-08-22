package com.vily.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-08-14
 *  
 **/
public class Mhttp {


    static String requestGet(String baseUrl, HashMap<String, String> paramsMap) {
        String result = "";
        HttpURLConnection urlConn = null;

        try {
            String requestUrl = baseUrl + getParams(paramsMap, 0);
            Log.d("MHttpURLClient", "Get--->" + requestUrl);
            URL url = new URL(requestUrl);
            urlConn = (HttpURLConnection)url.openConnection();
            urlConn.setConnectTimeout(5000);
            urlConn.setReadTimeout(5000);
            urlConn.setUseCaches(true);
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            urlConn.connect();
            if (urlConn.getResponseCode() == 200) {
                result = streamToString(urlConn.getInputStream());
                Log.d("MHttpURLClient", "Get Succ，result--->\n" + result);
            } else {
                Log.e("MHttpURLClient", "Get Error " + urlConn.getResponseCode());
            }

            String var6 = result;
            return var6;
        } catch (Exception var11) {
            var11.printStackTrace();
        } catch (Error var12) {
            var12.printStackTrace();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }

        }

        return null;
    }


    static String requestPost(String baseUrl, HashMap<String, String> paramsMap) {
        Log.i("MHttpURLClient", "requestPost: ---------");
        String result = "";
        HttpURLConnection urlConn = null;

        try {
            Log.d("MHttpURLClient",  "--->" + baseUrl);
            String params = getParams(paramsMap, 1);
            byte[] data = params.getBytes();
            URL url = new URL(baseUrl);
            urlConn = (HttpURLConnection)url.openConnection();
            urlConn.setConnectTimeout(5000);
            urlConn.setReadTimeout(5000);
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestMethod("POST");
            urlConn.setInstanceFollowRedirects(true);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConn.connect();
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(data);
            dos.flush();
            dos.close();
            if (urlConn.getResponseCode() == 200) {
                result = streamToString(urlConn.getInputStream());
                Log.d("MHttpURLClient",   " Succ，result--->\n" + result);
            } else {
                Log.e("MHttpURLClient",   " Error " + urlConn.getResponseCode());
            }

            String var9 = result;
            return var9;
        } catch (Exception var14) {
            var14.printStackTrace();
        } catch (Error var15) {
            var15.printStackTrace();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }

        }

        return null;
    }



    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            boolean var3 = false;

            int len;
            while((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception var5) {
            Log.e("streamToString", var5.toString());
            return null;
        }
    }


    private static String getParams(HashMap<String, String> paramsMap, int type) {
        String params = "";
        if (paramsMap != null && !paramsMap.isEmpty()) {
            String split = "";
            if (type == 0) {
                split = "?";
            }

            try {
                StringBuilder tempParams = new StringBuilder();
                int pos = 0;

                for(Iterator var6 = paramsMap.keySet().iterator(); var6.hasNext(); ++pos) {
                    String key = (String)var6.next();
                    if (pos > 0) {
                        tempParams.append("&");
                    }

                    Log.d("MHttpURLClient", "key :" + key + "value : " + (String)paramsMap.get(key));
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode((String)paramsMap.get(key), "utf-8")));
                }

                params = split + tempParams.toString();
            } catch (UnsupportedEncodingException var8) {
                var8.printStackTrace();
            } catch (Error var9) {
                Log.e("MHttpURLClient", var9.toString());
            }

            return params;
        } else {
            return params;
        }
    }
}

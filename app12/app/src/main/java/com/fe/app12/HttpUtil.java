package com.fe.app12;


import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;


public class HttpUtil {
    public interface HttpCallbackListener{
        void onFinish(String response);
        void onError(Exception e);
    }


    /**
     * Get服务请求
     *
     * @param requestUrl
     * @return
     */
    public static String sendGet(String requestUrl){
        try{
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);

            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            connection.connect();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                responseReader.close();
                Log.d("HttpGET", buffer.toString());
                return buffer.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}


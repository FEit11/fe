package com.fe.app12;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.*;

public class ThirdActivity extends AppCompatActivity {

    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        tv_result = findViewById(R.id.tv_content1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 开启子线程
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://weather1.sina.cn/?vt=4")
                            .build();
                    Response response = client.newCall(request).execute();
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_result.setText(responseData);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
}

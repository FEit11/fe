package com.fe.app12;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class SecondActivity extends AppCompatActivity {

    EditText txt;
    Button mbt;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        txt = findViewById(R.id.txtURL);
        mbt = findViewById(R.id.button1);
        tv_result = findViewById(R.id.tv_content);
    }

    public void click(View v) {
        new Thread(new Runnable() {//新线程
            @Override
            public void run() {
                // 开启子线程
                String URL = txt.getText().toString();
                final String result = HttpUtil.sendGet(URL);
                // 回到主线程，只有主线程可以设置xml文件里面的标签
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(result);
                    }
                });
            }
        }).start();
    }
}

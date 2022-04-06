package com.gy.xmllayout;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoonView moonView = new MoonView(MainActivity.this);
        moonView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                moonView.setBitmapX(motionEvent.getX() - 100);
                moonView.setBitmapY(motionEvent.getY() - 100);
                moonView.invalidate();
                return true;
            }
        });
        RelativeLayout rl = findViewById(R.id.rl);
        rl.addView(moonView);
    }

}
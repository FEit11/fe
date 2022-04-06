package com.gy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BatteryBroadcast bb;

    ConnectivityManager mConnectivityManager;

    NetworkInfo netInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * 代码注册
         */
        // 创建广播对象
        bb = new BatteryBroadcast();
        // 创建意图对象
        IntentFilter iFilter = new IntentFilter();
        // 添加电池改变的活动
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(bb, iFilter);

        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销
        unregisterReceiver(bb);
    }

    /**
     * 监控电池的广播
     */
    public class BatteryBroadcast extends BroadcastReceiver {

        /**
         * 当电池电量发生改变时会执行此方法
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            //获取当前电量，总电量
            int level = extras.getInt(BatteryManager.EXTRA_LEVEL, 0);
            int total = extras.getInt(BatteryManager.EXTRA_SCALE, 100);

            //电池温度温度
            int temperature = extras.getInt(BatteryManager.EXTRA_TEMPERATURE);

            Log.d("温度", temperature+"");
            Log.e("电量", level+"");
            Log.wtf("电量", total+"");


            //电池状态
            int status = extras.getInt(BatteryManager.EXTRA_STATUS);
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING://充电
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING://放电
                    break;
                case BatteryManager.BATTERY_STATUS_FULL://充满
                    break;
                default:
                    break;
            }
            //电池健康程度
            int health = extras.getInt(BatteryManager.EXTRA_HEALTH);
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_GOOD://健康状态
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT://过热
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD://过冷
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE://电压过高
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 监听网络状态变化
     */
    class NetworkStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {

                    String name = netInfo.getTypeName();
                    Log.e("name=", name);

                    if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        Log.e("WiFi网络=", "WiFi网络");
                    } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                        Log.e("有线网络=", "有线网络");
                    } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        Log.e("3g网络=", "3g网络");
                    }
                } else {
                    Toast.makeText(context, "无网络", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
package com.gy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SimpleAdapter adapter;
    List<HashMap<String, String>> contactsList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //建立一个ListView用来显示读到的数据（contactList）
        ListView contactsView=(ListView)findViewById(R.id.contacts_view);
        adapter=new SimpleAdapter(this,
                contactsList,
                R.layout.item_activity,
                new String[] {"name", "phone"},
                new int[]{R.id.name, R.id.phone});
        contactsView.setAdapter(adapter);
        //检查是否有读取通讯录的权限
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_CONTACTS},1);
        }
        else{
            readContacts();
        }
        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //获得选中项的HashMap对象
                HashMap<String,String> map=(HashMap<String,String>)contactsView.getItemAtPosition(i);
                String name=map.get("name");
                String phone=map.get("phone");
                Toast.makeText(getApplicationContext(),
                        "你选择了第"+i+"个Item，name的值是："+name+"phone的值是:"+phone,
                        Toast.LENGTH_SHORT).show();
                // 跳转到发送消息页面
                //监听按钮，如果点击，就跳转
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(MainActivity.this,SendActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });
    }
    //读取通讯录
    private void readContacts(){
        Cursor cursor=null;
        try{
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    @SuppressLint("Range") String displayName= (String) cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    @SuppressLint("Range") String displayNumber=(String)cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    HashMap<String, String> tmpMap = new HashMap<>();
                    tmpMap.put("name", displayName);
                    tmpMap.put("phone", displayNumber);
                    contactsList.add(tmpMap);
                }
                //通知刷新ListView
                adapter.notifyDataSetChanged();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }
                else {
                    Toast.makeText(this,"You denied permission",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                break;
        }
    }
}

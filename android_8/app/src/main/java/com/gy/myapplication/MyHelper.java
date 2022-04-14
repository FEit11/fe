package com.gy.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(Context context) {
        super(context, "content.db", null, 1);
    }

    //数据库第一次创建的时候执行
    @Override
    public void onCreate(SQLiteDatabase db) {
        //注意自增的主键名字必须是：_id
        db.execSQL("CREATE TABLE Book (_id INTEGER PRIMARY KEY AUTOINCREMENT,author varchar(20),price varchar(20)," +
                " pages varchar(20), name varchar(20), category_id varchar(20))");
        db.execSQL("CREATE TABLE Category (_id INTEGER PRIMARY KEY AUTOINCREMENT,category_name varchar(20),category_code varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
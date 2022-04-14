package com.gy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText NameEdit;
    private EditText PasswordEdit;
    private Button login;
    private CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        NameEdit = (EditText) findViewById(R.id.edit_userName);
        PasswordEdit = (EditText) findViewById(R.id.edit_password);
        remember = (CheckBox) findViewById(R.id.remember);
        login = (Button) findViewById(R.id.login);
        boolean isRemember = preferences.getBoolean("remember_password",false);
        if (isRemember){
            String Name = preferences.getString("Name","");
            String Password = preferences.getString("Password","");
            NameEdit.setText(Name);
            PasswordEdit.setText(Password);
            remember.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = NameEdit.getText().toString();
                String Password = PasswordEdit.getText().toString();
                if (TextUtils.isEmpty(NameEdit.getText().toString()) || TextUtils.isEmpty(PasswordEdit.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
                } else
                if(Name.equals("admin")&&Password.equals("123")) {
                    editor = preferences.edit();
                    if(remember.isChecked()) {
                        editor.putBoolean("remember_password",true);
                        editor.putString("Name",Name);
                        editor.putString("Password",Password);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("Name",Name);
                    intent.putExtra("Password",Password);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"账号密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
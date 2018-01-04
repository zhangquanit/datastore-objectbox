package com.objectbox.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.objectbox.example.entity.UserInfo;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void insert(View view) {
        //保存单个对象
        UserInfo userInfo = new UserInfo();
        userInfo.username = "张三";
        userInfo.password = "12345";
        App.getBoxStore().boxFor(UserInfo.class).put(userInfo);
    }

    public void update(View view) {
    }

    public void delete(View view) {
    }

    public void query(View view) {
    }
}

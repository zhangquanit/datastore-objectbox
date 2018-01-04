package com.objectbox.example;

import android.app.Application;

import com.objectbox.example.entity.MyObjectBox;

import io.objectbox.BoxStore;

public class App extends Application {
    private static BoxStore boxStore;
    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(this).build();
    }

    public static BoxStore getBoxStore(){
        return boxStore;
    }
}

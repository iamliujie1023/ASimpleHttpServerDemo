package com.liuj.demo2020Q4;

import android.app.Application;

/**
 * @author liuj
 * @time 2020/12/29
 */
public class MyApplication extends Application {
    private static MyApplication _instance;

    public static MyApplication getInstance() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

}

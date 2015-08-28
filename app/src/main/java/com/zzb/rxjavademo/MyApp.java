package com.zzb.rxjavademo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ZZB on 2015/8/28.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}

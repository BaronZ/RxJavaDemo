package com.zzb.rxjavademo;

import android.os.Looper;

/**
 * Created by ZZB on 2015/12/31.
 */
public class Utils {
    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }
}

package com.zzb.rxjavademo.activity;

import android.os.Bundle;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by ZZB on 2016/8/11.
 */
public class LastOrDefaultActivity extends DefaultBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        println("lastOrDefault是某些操作条件全都不符合，会调用lastOrDefault里面的值 \n 如果takeUntil都不符合，就会用lastOrDefault");
        println();
        approach1();
        println();
        approach2();
    }

    private void approach1(){
        println("这个例子用 last");
        Observable.just(1, 2, 3, 4, 5).takeUntil(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                println("takeUntil integer > 6: " + integer);
                return integer > 6;
            }
        }).lastOrDefault(99, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {//这里决定是用last 或者是default, 如果true,则用last, 否则用default,
                println("lastOrDefault, last > 3: " + integer);
                return integer > 3;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                println("subscribe:" + integer);
            }
        });
    }
    private void approach2(){
        println("这个例子用 default");
        Observable.just(1, 2, 3, 4, 5).takeUntil(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                println("takeUntil integer > 6: " + integer);
                return integer > 6;
            }
        }).lastOrDefault(99, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {//这里决定是用last 或者是default, 如果true,则用last, 否则用default,
                println("lastOrDefault, last > 6: " + integer);
                return integer > 6;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                println("subscribe: " + integer);
            }
        });
    }
}

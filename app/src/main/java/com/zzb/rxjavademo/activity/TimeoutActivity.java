package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by ZZB on 2016/11/2.
 */

public class TimeoutActivity extends DefaultBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onButtonClick(View v) {
        timeoutWithoutDefault();
    }

    private void timeoutWithoutDefault() {
        println("timeout(1, TimeUnit.SECONDS)");
        println("没有default返回TimeoutException");
        Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                longRun(subscriber);
            }
        }).subscribeOn(Schedulers.io())
                .timeout(1, TimeUnit.SECONDS)
                .subscribe(s -> {
                    println("onNext:" + s);
                }, throwable -> {
                    println("error:" + throwable);
                });
    }

    private void timeoutWithDefault() {
        println("timeout(1, TimeUnit.SECONDS, Observable.just(\"timeout\")");
        println("有默认，返回默认Observable");
        Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                longRun(subscriber);
            }
        }).subscribeOn(Schedulers.io())
                .timeout(1, TimeUnit.SECONDS, Observable.just("timeout"))
                .subscribe(s -> {
                    println("onNext:" + s);
                }, throwable -> {
                    println("error:" + throwable);
                });
    }

    private void longRun(Subscriber<? super String> subscriber) {
        try {
            Thread.sleep(3000);
            subscriber.onNext("long run ended");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

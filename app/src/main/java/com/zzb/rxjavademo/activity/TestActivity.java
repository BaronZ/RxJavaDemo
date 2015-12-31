package com.zzb.rxjavademo.activity;

import android.os.Bundle;

import com.zzb.rxjavademo.R;
import com.zzb.rxjavademo.Utils;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestActivity extends BaseActivity {
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Observable.merge(disk(), memory(), net()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                println(s);
            }
        });

    }


    private Observable<String> memory() {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("memory:" + Utils.isMainThread());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    private Observable<String> disk() {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("disk:" + Utils.isMainThread());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    private Observable<String> net() {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("net:" + Utils.isMainThread());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }
}

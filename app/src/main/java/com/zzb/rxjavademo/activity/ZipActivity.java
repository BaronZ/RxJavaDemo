package com.zzb.rxjavademo.activity;

import android.os.Bundle;

import com.zzb.rxjavademo.Utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * Created by ZZB on 2016/8/23.
 */
public class ZipActivity extends DefaultBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.just(just()).delay(2, TimeUnit.SECONDS).doOnNext(this::println).flatMap( o -> zip()).subscribe();

    }
    private String just(){
        println("is main:" + Utils.isMainThread());
        return "for delay";
    }
    private Observable<Object> zip() {
        return Observable.zip(ob1(), ob2(), (Func2<String, String, Object>) (s, s2) -> s + s2);
    }

    private Observable<String> ob1() {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("ob1");
                subscriber.onCompleted();
            }
        }).doOnNext(s -> println("ob1.doOnNext:" + s));
    }

    private Observable<String> ob2() {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("ob2");
                subscriber.onCompleted();
            }
        }).doOnNext(s -> println("ob2.doOnNext:" + s));
    }
}

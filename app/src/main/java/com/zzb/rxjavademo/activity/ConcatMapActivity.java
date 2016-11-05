package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Random;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ZZB on 2016/11/5.
 */
public class ConcatMapActivity extends DefaultBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        concatMap();
//        flatMap();
    }

    private void concatMap() {
        println("concatMap是有序执行的");
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .concatMap(integer -> Observable.just(integer).observeOn(Schedulers.computation())
                        .flatMap(this::longRunTask))
                .subscribe(integer -> println("result:" + integer),
                        Throwable::printStackTrace,
                        () -> println("onCompleted"));
    }


    private void flatMap() {
        println("flatMap是无序的");
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .flatMap(integer -> Observable.just(integer)
                        .observeOn(Schedulers.computation())
                        .flatMap(this::longRunTask))
                .subscribe(integer -> println("result:" + integer),
                        Throwable::printStackTrace,
                        () -> println("onCompleted"));
    }

    @NonNull
    private Observable<? extends Integer> longRunTask(Integer i) {
        try {
            int random = new Random().nextInt(1000);
            println("index " + i + " sleep " + random);
            Thread.sleep(random);
            return Observable.just(2 * i);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }
}

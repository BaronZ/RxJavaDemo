package com.zzb.rxjavademo.activity;

import android.os.Bundle;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by ZZB on 2016/8/11.
 */
public class TakeUntilActivity extends DefaultBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        println("takeUntil 操作符会一直操作，直到操作符合一定条件，就停止不往后走");
        approach1();
        println();
        approach2();
        println();
        approach3();
    }
    private void approach1(){
        Observable.just("1", "2", "3", "4", "5").flatMap(new Func1<String, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(String s) {
                return doSomethingInTheMiddle(s);
            }
        }).takeUntil(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                println("take until:" + integer);
                return integer >= 3;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                println("subscribe:" + integer);
            }
        });
    }
    private void approach2(){
        Observable.just(1, 2, 3, 4, 5).takeUntil(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                println("take until:" + integer);
                return integer >= 3;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                println("subscribe: " + integer);
            }
        });
    }
    private void approach3(){
        Observable.from(new Integer[]{1, 2, 3, 4, 5}).takeUntil(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                println("take until:" + integer);
                return integer >= 3;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                println("subscribe: " + integer);
            }
        });
    }
    private Observable<Integer> doSomethingInTheMiddle(String s){
        println("do something in the middle:" + s);
        return Observable.just(Integer.valueOf(s));
    }
}

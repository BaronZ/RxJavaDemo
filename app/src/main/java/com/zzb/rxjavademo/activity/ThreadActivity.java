package com.zzb.rxjavademo.activity;

import android.os.Bundle;

import com.zzb.rxjavademo.R;
import com.zzb.rxjavademo.Utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 线程切换例子
 *created by ZZB at 2015/12/31 15:45
 */
public class ThreadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        println("大概解释，具体解释看代码");
        println("前面什么线程都没指定，则最后一个subscribeOn会影响前面所有的执行线程");
        println("前面有subscribeOn无observeOn，则前面的subscribeOn大于后面的subscribeOn");
        println("前面有subscribeOn有observeOn, 则前面的observeOn大于前面和后面的subscribeOn");
        test3();
    }

    private void test1(){
        //如果前面Observable没有调用，那么最后一个subscribeOn指定的线程都是前面Observable的执行线程
        //Ob.create().doOnNext().subOn(io) 则create和doOnNext都会在io执行
        Observable.create(subscriber -> {
            println("create:" + Utils.isMainThread());
            subscriber.onNext("a");
            subscriber.onCompleted();
        }).doOnNext(s -> {
            println("doOnNext:" + Utils.isMainThread());
        }).subscribeOn(Schedulers.io()).subscribe();
    }
    private void test2(){
        //如果Observable前面的Observable调用了subscribeOn，那么会影响后面的Observable的执行线程
        //Ob.create().subOn(main).doOnNext().subOn(io) 则create和doOnNext都会在main执行
        Observable.create(subscriber -> {
            println("create:" + Utils.isMainThread());
            subscriber.onNext("a");
            subscriber.onCompleted();
        }).subscribeOn(AndroidSchedulers.mainThread()).doOnNext(s -> {
            println("doOnNext:" + Utils.isMainThread());
        }).subscribeOn(Schedulers.io()).subscribe();
    }
    private void test3(){
        //如果Observable前面的Observable调用了subscribeOn,且observeOn，observeOn指定的线程会影响后面的Observable的执行线程
        //Ob.create().subOn(main).obOn(io).doOnNext().subOn(io) 则create在main,doOnNext在io执行
        Observable.create(subscriber -> {
            println("create:" + Utils.isMainThread());
            subscriber.onNext("a");
            subscriber.onCompleted();
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io()).doOnNext(s -> {
            println("doOnNext:" + Utils.isMainThread());
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}

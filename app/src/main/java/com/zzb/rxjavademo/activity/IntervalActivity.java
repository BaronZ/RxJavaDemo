package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.view.View;

import com.zzb.rxjavademo.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ZZB on 2015/8/28.
 */
public class IntervalActivity extends BaseActivity{
    private Subscription mSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        sample1();
    }
    
    /**
     * interval可以执行指定时间间隔的任务，如果次数是有限的，用take(次数)，如果要停止，则Subscription.unsubscribe
     * 效果与{@link TimerActivity#sample1()} 一样
     *@author ZZB
     *created at 2015/8/28 11:57
     */
    private void sample1(){
        //注意，这个subscription如果退出activity时，没有反注册，那么，在这个interval停止之前，会出现内存泄露
        //(比如下面的例子，运行了15秒退出，后面45秒还会执行，那么这45秒内，会内存泄露)
        println("开始interval");
        mSubscription = Observable//效果与Observable.timer(0, 1, TimeUnit.SECONDS)一样
                .interval(1, TimeUnit.SECONDS)//间隔时间一秒
                .take(60)//运行60次，即60秒内，每秒回调一次
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        println(String.format("D4 [%s] XXX COMPLETE", getCurrentTimestamp()));
                    }
                    @Override
                    public void onError(Throwable e) {
                        logE(e);
                    }

                    @Override
                    public void onNext(Long number) {
                        println(String.format("D4 [%s]     NEXT [%s]", getCurrentTimestamp(), number.toString()));
                    }
                });
    }
    public void onClick(View v){
        mSubscription.unsubscribe();//停止循环
    }
}

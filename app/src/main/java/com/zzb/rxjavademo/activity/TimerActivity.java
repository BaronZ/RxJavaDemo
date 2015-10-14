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
 * Timer操作符
 *@author ZZB
 *created at 2015/8/27 15:14
 */
public class TimerActivity extends BaseActivity implements View.OnClickListener{
    private Subscription mSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        sample1();
    }

    private void sample1(){
        /**注意内存泄露，解释见{@link IntervalActivity#sample1()}*/
        int delayStart = 1;//1秒后开始
        int interval = 2;//每隔两秒执行一次
        mSubscription = Observable.timer(delayStart, interval, TimeUnit.SECONDS)//与interval.interval(2, TimeUnit.SECONDS)效果一样
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        println(String.format("C3 [%s] XXXX COMPLETE", getCurrentTimestamp()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        logE(e);
                    }

                    @Override
                    public void onNext(Long number) {
                        println(String.format("C3 [%s]     NEXT [%d]", getCurrentTimestamp(), number));
                    }
                });
    }
    /**
     * 定时器，指定时间后执行
     *@author ZZB
     *created at 2015/8/28 14:00
     */
    private void sample(){
        println("开始timer");
        final long start = System.currentTimeMillis();
        mSubscription = Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())//使用timer要在主线程更新ui
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        println("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        println("error:" + e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        println("param:" + aLong + " elapse:" + (System.currentTimeMillis() - start));
                    }
                });
    }

    @Override
    public void onClick(View v) {
        mSubscription.unsubscribe();
    }
}

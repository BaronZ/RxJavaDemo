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
        sample();
    }
    /**
     * 定时器，指定时间后执行
     *@author ZZB
     *created at 2015/8/28 12:00
     */
    private void sample(){
        println("开始timer");
        final long start = System.currentTimeMillis();
        Observable.timer(2, TimeUnit.SECONDS)
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

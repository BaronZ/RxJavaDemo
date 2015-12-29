package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.zzb.rxjavademo.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;

public class ThrottleActivity extends BaseActivity {
    private EditText mEt;
    private Subscription mSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throttle);
        mEt = $(R.id.edit);
        Button btn = $(R.id.btn);
        rxViewThrottleFirst(btn);
        println("Throttle这个事件会有内存泄露，注册反注册");
        println();
        println("throttleFirst: 在每次事件触发后的一定时间间隔内丢弃新的事件。常用作去抖动过滤，例如按钮的点击监听器");
        println("即，只响应指定时间内的第一个动作，剩余时间做的操作不响应，可以来防止二次重复点击这样的问题");

        Observer<OnTextChangeEvent> observer = new Observer<OnTextChangeEvent>() {
            @Override
            public void onCompleted() {
                println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                printErr("onError:" + e.getMessage().toString());
            }

            @Override
            public void onNext(OnTextChangeEvent onTextChangeEvent) {
                println("onNext:" + onTextChangeEvent.text().toString());
            }
        };
//        throttleFirst(observer);
        throttleLast(observer);
    }
    /**指定时间内只能触发一次*/
    private void rxViewThrottleFirst(View v){
//        RxView.clicks(v).throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//
//                    }
//                });
        RxView.clicks(v).throttleFirst(3000, TimeUnit.MILLISECONDS).subscribe(aVoid -> {
            Log.d("test", "click");
        });
    }
    /**
     * 效果与debounce类似，但是效果没有Debounce好，可用来做快速搜索，指定时间内响应最后一个事件
     *
     */
    private void throttleLast(Observer<OnTextChangeEvent> observer){
        Observable<OnTextChangeEvent> textChangeObservable = WidgetObservable.text(mEt);
        mSubscription = textChangeObservable.throttleLast(1500, TimeUnit.MILLISECONDS) // 设置防抖间隔为 500ms
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        addSubscription(mSubscription);
    }
    //throttleFirst: 在每次事件触发后的一定时间间隔内丢弃新的事件。常用作去抖动过滤，例如按钮的点击监听器：
    //即，只响应指定时间内的第一个动作，剩余时间做的操作不响应，可以来防止二次重复点击这样的问题
    private void throttleFirst(Observer<OnTextChangeEvent> observer){
        Observable<OnTextChangeEvent> textChangeObservable = WidgetObservable.text(mEt);
        mSubscription = textChangeObservable.throttleFirst(1500, TimeUnit.MILLISECONDS) // 设置防抖间隔为 500ms
                .subscribe(observer);
        addSubscription(mSubscription);
    }
    
}

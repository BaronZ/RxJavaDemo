package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.zzb.rxjavademo.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
/**
 * Debounce操作符，指定时间内，发射多个事件，也只执行一次
 *@author ZZB
 *created at 2015/10/14 14:11
 */
public class DebounceActivity extends BaseActivity {

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce);
        EditText et = $(R.id.edit);
        Observable<OnTextChangeEvent> textChangeObservable = WidgetObservable.text(et);
        //指定时间内，发射多个事件，也只执行一次
        println("400毫秒内，再次有文字输入，不会触发afterTextChanged事件");
        println("400毫秒内，无输入，触发afterTextChanged事件");
        println("========");
        mSubscription = textChangeObservable.debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OnTextChangeEvent>() {
                    @Override
                    public void onCompleted() {
                        println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        println("onError:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(OnTextChangeEvent onTextChangeEvent) {
                        println("onNext:" + onTextChangeEvent.text().toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

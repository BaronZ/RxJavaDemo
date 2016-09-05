package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by ZZB on 2016/9/5.
 */
public class PublishSubjectActivity extends DefaultBaseActivity {
    PublishSubject<String> mPublishSubject = PublishSubject.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPublishSubject.throttleFirst(13, TimeUnit.SECONDS).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                print(s);
            }
        });
    }
    private int counter;
    @Override
    protected void onButtonClick(View v) {
        mPublishSubject.onNext("haha:" + counter++);
    }
}

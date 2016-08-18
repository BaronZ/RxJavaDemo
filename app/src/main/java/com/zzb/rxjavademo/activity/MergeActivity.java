package com.zzb.rxjavademo.activity;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by ZZB on 2016/8/17.
 */
public class MergeActivity extends DefaultBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        println("merge 所有的Observable会并行执行，并且先返回结果的，先到onNext，一个一个先后传到onNext");
        approach0();
    }

    private void approach0(){
        println("delay 2 merge delay 1");
        Observable.merge(delay(2), delay(1)).subscribe(this::println);
        Observable.just("delay 3 merge delay 4").delay(2, TimeUnit.SECONDS).subscribe(this::println);
        Observable.merge(delay(3), delay(4)).subscribe(this::println);

    }
    private Observable<String> delay(int seconds){
        return Observable.just("delay:" + seconds).delay(seconds, TimeUnit.SECONDS);
    }
}

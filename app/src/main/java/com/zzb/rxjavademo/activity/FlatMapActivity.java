package com.zzb.rxjavademo.activity;

import android.os.Bundle;

import com.zzb.rxjavademo.Data;
import com.zzb.rxjavademo.R;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

public class FlatMapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);
        println("flatMap，相当于把Observable<List<String>里面的数据遍历出来");
        println();
        approach0();
        println();
        approach1();

    }
    private void approach1(){
        Observable.just(Arrays.asList("A", "B2", "C", "D")).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                //<T> Observable<T> from(Iterable<? extends T> iterable)
                return Observable.from(strings);
            }
        }).map(new Func1<String, Data>() {
            @Override
            public Data call(String s) {
                Data data = new Data();
                data.text = s;
                return data;
            }
        }).subscribe(new Observer<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Data data) {
                println("map:" + data.text);
            }
        });
    }
    private void approach0() {
        Observable.just(Arrays.asList("A", "B2", "C", "D")).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                //<T> Observable<T> from(Iterable<? extends T> iterable)
                return Observable.from(strings);
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                println(s);
            }
        });
    }
}

package com.zzb.rxjavademo.activity;

import android.os.Bundle;

import com.zzb.rxjavademo.Data;
import com.zzb.rxjavademo.R;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class FlatMapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);
        println("flatMap，相当于把Observable<List<String>里面的数据遍历出来");
        println("flatMap，会遍历每一个Observable");
        println();
        approach0();
        println();
        approach1();
        println();
        approach2();
    }

    private void approach2() {
        println("把下面3个字符里，去掉/, 再输出数字 \n \"1/5/8\", \"1/9/11/58/16/\", \"9/15/56/49/21\"");

        Observable.just("1/5/8", "1/9/11/58/16/", "9/15/56/49/21").flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                println("flatMap.call(String s):" + s);
                return spiltSlash(s);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                println("subscribe.call(String s):" + s);
            }
        });
    }

    private Observable<String> spiltSlash(String s) {
        return Observable.from(s.split("/"));
//        return Observable.just(s.length() + "");
    }

    private void approach1() {
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
        }).subscribe(data -> {
            println("map:" + data.text);
        });
    }

    private void approach0() {
        Observable.just(Arrays.asList("A", "B2", "C", "D")).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                //<T> Observable<T> from(Iterable<? extends T> iterable)
                return Observable.from(strings);
            }
        }).subscribe(s -> {
            println(s);
        });
    }

}

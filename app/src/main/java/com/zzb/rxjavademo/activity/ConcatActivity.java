package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.view.View;

import com.zzb.rxjavademo.R;
import com.zzb.rxjavademo.Utils;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Concat操作符是，每个Observable顺序执行，前面的没完成，后面的不能执行，所以可以用这个操作符加takeFirst来做3级缓存
 * created by ZZB at 2015/12/31 10:16
 */
public class ConcatActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ConcatActivity.class.getSimpleName();
    private static String mMemory, mDisk, mNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concat);
        println("3次加载之后，就会使用内存的内容");
    }

    private void load() {
        Observable.concat(memory(), disk(), net())
                //如果没有takeFirst，每个Observable会顺序执行
                .takeFirst(s -> s != null).subscribe(s -> {
            println("获取的内容：" + s);
        });
    }

    private Observable<String> memory() {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                println("加载内存数据" + Utils.isMainThread());
                subscriber.onNext(mMemory);
                //注意要调用onCompleted，不然下一个Observable不会执行
                subscriber.onCompleted();
            }
        });
    }

    private Observable<String> disk() {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                println("加载文件数据" + Utils.isMainThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(mDisk);
                subscriber.onCompleted();
            }
        }).doOnNext(s -> {
            if (s != null) {
                //cache to disk
                println("缓存数据到文件：" + Utils.isMainThread());
                mMemory = "save data to memory:(" + s + ")";
            }
        }).subscribeOn(Schedulers.io());//这里subscribeOn(io)之后，会影响到后面的线程执行，后面的observable也会在io执行
    }

    private Observable<String> net() {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                println("加载网络数据" + Utils.isMainThread());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mNet = "data from net";
                subscriber.onNext(mNet);
                subscriber.onCompleted();
            }
        }).doOnNext(s -> {
            if (s != null) {
                //cache to disk
                println("缓存数据到文件：" + Utils.isMainThread());
                mDisk = "save data to disk:(" + s + ")";
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_load:
                load();
                break;
            case R.id.btn_clear:
                mMemory = null;
                mNet = null;
                mDisk = null;
                clearText();
                break;
        }
    }
}

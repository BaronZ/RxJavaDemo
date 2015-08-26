package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zzb.rxjavademo.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MapActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mTextView = (TextView) findViewById(R.id.tv);
        sample();
//        lambdaSample();
    }

    /**
     * 用lambda实现sample()方法
     * created at 2015/8/26 10:39
     */
    private void lambdaSample() {
        Observable.just("Hello World").map(stringResult -> stringResult.hashCode()).map(integerResult -> "Hello World的hashCode是：" + integerResult).subscribe(
                stringResult -> {
                    String displayText = "map把字符串转为整型，再从整型转为字符串，转出的结果是：\n" + stringResult;
                    mTextView.setText(displayText);
                }
        );
    }

    private void sample() {
        Observable.just("Hello world").map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.hashCode();
            }
        }).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return "Hello World的hashCode是：" + integer;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                String displayText = "map把字符串转为整型，再从整型转为字符串，转出的结果是：\n" + s;
                mTextView.setText(displayText);
            }
        });
    }

}

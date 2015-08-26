package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zzb.rxjavademo.R;

import rx.Observable;
import rx.Subscriber;

public class CreateActivity extends AppCompatActivity {
    private TextView mTextView;
    private StringBuilder displayText = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mTextView = (TextView) findViewById(R.id.tv);
        sample();
    }
    /**
     * Next: 1
     * Next: 2
     * Next: 3
     * Next: 4
     * Sequence complete.
     */
    private void sample(){
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                /**一个形式正确的有限Observable必须尝试调用观察者的onCompleted正好一次或者它的onError正好一次，而且此后不能再调用观察者的任何其它方法。*/
                try {
                    if (!observer.isUnsubscribed()) {//这里要判断subscriber是不是已经没有订阅了，避免做无谓的操作!!
                        for (int i = 1; i < 5; i++) {
                            observer.onNext(i);
                        }
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                println("Sequence complete.");
            }
        });
    }
    private void println(String text){
        displayText.append(text).append("\n");
        mTextView.setText(displayText.toString());
    }
}

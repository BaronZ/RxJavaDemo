package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zzb.library.utils.TimeUtils;
import com.zzb.rxjavademo.R;
import com.zzb.rxjavademo.Utils;

import java.util.Date;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity {

    private TextView mTvContent;
    protected StringBuffer mDisplayText = new StringBuffer();
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected <T extends View> T $(int resId) {
        return (T) findViewById(resId);
    }

    protected void setupDisplayText(TextView tv) {
        mTvContent = tv;
    }

    protected void println(){
        println("======================================");
    }
    protected void printErr(String text){
        mTvContent.post(() -> {
            println(text);
        });
    }
    protected void println(String text) {
        mDisplayText.append(text).append("\n");
        if (mTvContent == null) {
            mTvContent = (TextView) findViewById(R.id.tv_content);
        }
        if(Utils.isMainThread()){
            mTvContent.setText(mDisplayText.toString());
        }else{
            mTvContent.post(() -> mTvContent.setText(mDisplayText.toString()));
        }
    }

    protected String getCurrentTimestamp(){
        return TimeUtils.dateToTimeStamp(new Date(), "MM-dd HH:mm:ss");
    }

    protected void logE(Throwable e){
        Log.e("RxJavaDemo", "error", e);
    }

    protected void addSubscription(Subscription s){
        mCompositeSubscription.add(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }
}

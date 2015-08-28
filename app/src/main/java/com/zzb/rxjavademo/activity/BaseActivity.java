package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zzb.library.utils.TimeUtils;
import com.zzb.rxjavademo.R;

import java.util.Date;

public class BaseActivity extends AppCompatActivity {

    private TextView mTvContent;
    protected StringBuffer mDisplayText = new StringBuffer();

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

    protected void println(String text) {
        mDisplayText.append(text).append("\n");
        if (mTvContent == null) {
            mTvContent = (TextView) findViewById(R.id.tv_content);
        }
        mTvContent.setText(mDisplayText.toString());
    }

    protected String getCurrentTimestamp(){
//        com.zzb.library.utils.TimeUtils
        return TimeUtils.dateToTimeStamp(new Date(), "MM-dd HH:mm:ss");
    }



}

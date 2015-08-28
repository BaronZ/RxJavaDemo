package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    private TextView mTvContent;
    protected StringBuffer mDisplayText = new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected void setupDisplayText(TextView tv){
        mTvContent = tv;
    }
    protected void println(String text){
        mDisplayText.append(text).append("\n");
        mTvContent.setText(mDisplayText.toString());
    }


}

package com.zzb.rxjavademo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.zzb.rxjavademo.R;

/**
 * Created by ZZB on 2016/8/10.
 */
public class DefaultBaseActivity extends BaseActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mTextView = (TextView) findViewById(R.id.tv);
        setupDisplayText(mTextView);
    }
}

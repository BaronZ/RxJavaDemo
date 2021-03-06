package com.zzb.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zzb.rxjavademo.activity.ConcatActivity;
import com.zzb.rxjavademo.activity.ConcatMapActivity;
import com.zzb.rxjavademo.activity.CreateActivity;
import com.zzb.rxjavademo.activity.DebounceActivity;
import com.zzb.rxjavademo.activity.DistinctActivity;
import com.zzb.rxjavademo.activity.FlatMapActivity;
import com.zzb.rxjavademo.activity.IntervalActivity;
import com.zzb.rxjavademo.activity.LastOrDefaultActivity;
import com.zzb.rxjavademo.activity.MapActivity;
import com.zzb.rxjavademo.activity.MergeActivity;
import com.zzb.rxjavademo.activity.PublishSubjectActivity;
import com.zzb.rxjavademo.activity.TakeUntilActivity;
import com.zzb.rxjavademo.activity.TestActivity;
import com.zzb.rxjavademo.activity.ThreadActivity;
import com.zzb.rxjavademo.activity.ThrottleActivity;
import com.zzb.rxjavademo.activity.TimeoutActivity;
import com.zzb.rxjavademo.activity.TimerActivity;
import com.zzb.rxjavademo.activity.ZipActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getData());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add(TestActivity.class.getSimpleName());
        list.add(ConcatActivity.class.getSimpleName());
        list.add(ConcatMapActivity.class.getSimpleName());
        list.add(CreateActivity.class.getSimpleName());
        list.add(DebounceActivity.class.getSimpleName());
        list.add(DistinctActivity.class.getSimpleName());
        list.add(FlatMapActivity.class.getSimpleName());
        list.add(IntervalActivity.class.getSimpleName());
        list.add(LastOrDefaultActivity.class.getSimpleName());
        list.add(MapActivity.class.getSimpleName());
        list.add(MergeActivity.class.getSimpleName());
        list.add(PublishSubjectActivity.class.getSimpleName());
        list.add(TakeUntilActivity.class.getSimpleName());
//        list.add(SwitchMapActivity.class.getSimpleName());
        list.add(ThreadActivity.class.getSimpleName());
        list.add(ThrottleActivity.class.getSimpleName());
        list.add(TimeoutActivity.class.getSimpleName());
        list.add(TimerActivity.class.getSimpleName());
        list.add(ZipActivity.class.getSimpleName());
        return list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clzName = (String) parent.getAdapter().getItem(position);
        clzName = "com.zzb.rxjavademo.activity." + clzName;
        try {
            Class clz = Class.forName(clzName);
            Intent intent = new Intent(this, clz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

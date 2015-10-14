package com.zzb.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zzb.rxjavademo.activity.CreateActivity;
import com.zzb.rxjavademo.activity.DebounceActivity;
import com.zzb.rxjavademo.activity.FlatMapActivity;
import com.zzb.rxjavademo.activity.IntervalActivity;
import com.zzb.rxjavademo.activity.MapActivity;
import com.zzb.rxjavademo.activity.TimerActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView mListView;
    private StringBuffer displayText = new StringBuffer();
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
        list.add(MapActivity.class.getSimpleName());
        list.add(CreateActivity.class.getSimpleName());
        list.add(TimerActivity.class.getSimpleName());
        list.add(IntervalActivity.class.getSimpleName());
        list.add(DebounceActivity.class.getSimpleName());
        list.add(FlatMapActivity.class.getSimpleName());
//        list.add()
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

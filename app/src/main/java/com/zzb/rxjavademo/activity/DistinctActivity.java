package com.zzb.rxjavademo.activity;

import android.os.Bundle;

import rx.Observable;
/**
 * distinct 操作符
 *created by ZZB at 2016/8/10 16:52
 */
public class DistinctActivity extends DefaultBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        approach1();
        printHorizontalDivider();
        approach2();
        printHorizontalDivider();
        approach3();
    }

    private void approach1() {
        println("result of distinct int: 5 4 5 5 7");
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .map(String::length)// equals to .map(s -> s.length())
                .distinct()
                .subscribe(len -> print(len + "  "));
    }

    private void approach2() {
        println("result of distinct String: \"AA\",\"BB\",\"AA\",\"CC\",\"BB\"");
        Observable.just("AA", "BB", "AA", "CC", "BB")
                .distinct()
                .subscribe(str -> print(str + "  "));
    }
    //自定义对象需要重写equals hashcode
    private void approach3() {
        println("result of distinct custom object");
        CustomObject c1 = new CustomObject();
        CustomObject c2 = new CustomObject(1, "a");
        CustomObject c3 = new CustomObject(2, "b");
        CustomObject c4 = new CustomObject(1, "c");
        Observable.just(c1, c2, c3, c4)
                .distinct()
                .subscribe(obj -> print(obj.alias + "  "));
    }

    static class CustomObject {
        int id;
        String alias = "default";

        public CustomObject() {
        }

        public CustomObject(int id, String alias) {
            this.id = id;
            this.alias = alias;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CustomObject that = (CustomObject) o;

            return id == that.id;

        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}

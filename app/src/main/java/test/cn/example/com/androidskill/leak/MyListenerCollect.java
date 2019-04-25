package test.cn.example.com.androidskill.leak;

import android.view.View;

import java.util.WeakHashMap;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/25.
 */

public class MyListenerCollect {
    public static WeakHashMap weakHashMap = new WeakHashMap();
    public void setListeners(View view, MyView.MyListener myListener){
        weakHashMap.put(view,myListener);
    }
}

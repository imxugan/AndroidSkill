package test.cn.example.com.androidskill.java_about;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/3.
 */

public class JavaAboutActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_about);
        TextView threadPool = (TextView) findViewById(R.id.threadPool);
        threadPool.setOnClickListener(this);
        TextView callBack = (TextView) findViewById(R.id.callBack);
        callBack.setOnClickListener(this);
        TextView classLoader = (TextView) findViewById(R.id.classLoader);
        classLoader.setOnClickListener(this);
        findViewById(R.id.propertyChangeSupport).setOnClickListener(this);
        TextView annotation = (TextView) findViewById(R.id.annotation);
        annotation.setOnClickListener(this);
        findViewById(R.id.referenceQueue).setOnClickListener(this);
        findViewById(R.id.lrucache).setOnClickListener(this);
    }

    private  void testLruCache() {
        int maxSize = 16;//指定LruCache最大缓存容量是 16 Byte
        LruCache<Integer,Integer> lruCache = new LruCache<Integer, Integer>(maxSize){
            @Override
            protected int sizeOf(Integer key, Integer value) {
                return Integer.BYTES;//每个缓存的元素占用的内存大小
            }
        };
        lruCache.put(1,1);
        lruCache.put(2,2);
        lruCache.put(3,3);
        lruCache.get(1);
        lruCache.put(4,4);
        lruCache.put(5,5);
        Integer integerValue = lruCache.get(1);
        LogUtil.i(integerValue+"");
        Integer integerValue2 = lruCache.get(2);
        LogUtil.i(integerValue2+"");
    }

    private  void test2() {
//        int maxSize = 1*1024*1024;//1 MB
        int maxSize = 16;//16 Byte
        LruCache<Integer,Integer> lruCache = new LruCache<Integer, Integer>(maxSize){
            @Override
            protected int sizeOf(Integer key, Integer value) {
                return Integer.BYTES;
            }
        };
        lruCache.put(1,1);
        lruCache.put(2,2);
        lruCache.put(3,3);
        lruCache.put(4,4);
        lruCache.put(5,5);

        Integer integerValue = lruCache.get(1);
        LogUtil.i(integerValue+"");
        int size = lruCache.size();
        try {
            Field mapField = LruCache.class.getDeclaredField("map");
            mapField.setAccessible(true);
            LinkedHashMap linkedHashMap = (LinkedHashMap) mapField.get(lruCache);
            Map.Entry<Integer,Integer> entry = null;
            for (Object object:linkedHashMap.entrySet()){
                entry = (Map.Entry<Integer, Integer>) object;
                LogUtil.i(entry.getKey()+" : "+entry.getValue());
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        LogUtil.e("size=    "+size);
        for (int i = 0; i < size; i++) {
            Integer value = lruCache.get(i);
            LogUtil.i(i+"  :   "+value);
        }

    }

    private   void test(boolean accessOrder) {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, accessOrder);
        map.put(0, 0);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.get(1);
        map.get(2);
        map.put(5, 5);

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            LogUtil.i(entry.getKey() + ":" + entry.getValue());

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.classLoader:
                myStartActivity(ClassLoaderTestActivity.class,false);
                break;
            case R.id.threadPool:
                myStartActivity(ThreadPoolActivity.class,false);
                break;
            case R.id.callBack:
                myStartActivity(CallBackActivity.class,false);
                break;
            case R.id.propertyChangeSupport:
                myStartActivity(PropertyChangeSupportActivity.class,false);
                break;
            case R.id.annotation:
                myStartActivity(AnnotationActivity.class,false);
                break;
            case R.id.referenceQueue:
                myStartActivity(ReferenceQueueActivity.class,false);
                break;
            case R.id.lrucache:
                test(false);
                LogUtil.i("");
                test(true);
                test2();
                testLruCache();
                break;
        }
    }

    private void myStartActivity(Class clazz,boolean isFinish){
        Intent intent = new Intent(JavaAboutActivity.this, clazz);
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }
}

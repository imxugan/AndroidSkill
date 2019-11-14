package test.cn.example.com.androidskill.java_about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class ReferenceQueueActivity extends AppCompatActivity {
    private static final ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<>();
    private static final int _1M = 1024 * 1024;
    private Reference<byte[]> reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_queue);
        findViewById(R.id.btn_gc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThread myThread = new MyThread();
                myThread.setDaemon(true);
                myThread.start();
            }
        });
        testReferenceQueue();
    }

    private void testReferenceQueue() {
        byte[] bytes = new byte[_1M];
        reference = new WeakReference<>(bytes,referenceQueue);
    }

    private static class MyThread extends Thread{
        public void run(){
            try {
                Reference<? extends byte[]> weakReference = referenceQueue.remove(1000);
                LogUtil.i("GC前从引用队列中获取的reference "+weakReference);
            } catch (InterruptedException e) {
                e.printStackTrace();
                LogUtil.i(e.getMessage());
            }
            //手动gc
            Runtime.getRuntime().gc();
            LogUtil.i("手动gc");
            Reference<byte[]> ref = null;
            int cunt = 0;
            try {
                while ((ref = (Reference<byte[]>) referenceQueue.remove())!=null){
                    LogUtil.i("GC后  "+(cunt++)+"回收了："+ref);
                    //如果ref.get()返回的是null，表示WeakReference持有的对象确实被回收了
                    LogUtil.i("GC后 从WeakReference中获取的实际对象=  "+ref.get());
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

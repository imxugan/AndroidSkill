package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * 调试
 */
public class DebugActivity extends AppCompatActivity {
    private String name = "default";
    LinearLayout ll;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        alertName();
        testArrayList();
        ll = (LinearLayout) findViewById(R.id.ll);
        tv = (TextView) findViewById(R.id.tv);
    }

    private void testArrayList() {
        List<String> list = new ArrayList<>();
        list.add("q");
        list.add("2q");
        list.add("3q");
        list.add("4q");
        for(String s:list){
            LogUtil.i(s);
        }
    }

    public void alertName() {
        int num = 10;
        int min = Math.min(num,100);
        int result = new Random().nextInt(100);
        LogUtil.i("result="+result);
        System.out.println(min);
        debug();
    }

    public void debug() {
        this.name = "debug";
        System.out.println(name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ll.post(new Runnable() {
            @Override
            public void run() {
                int width = ll.getMeasuredWidth();
                //将px转换成dp
                width = DensityUtil.px2dip(DebugActivity.this,width);
                LogUtil.i(""+width);
                int height = ll.getMeasuredHeight();
                //将px转换成dp
                height = DensityUtil.px2dip(DebugActivity.this,height);
                LogUtil.i(""+height);

            }
        });
        tv.post(new Runnable() {
            @Override
            public void run() {
                int width = tv.getMeasuredWidth();
                //将px转换成dp
                width = DensityUtil.px2dip(DebugActivity.this,width);
                LogUtil.i("tv----width------"+width);
                int height = tv.getMeasuredHeight();
                //将px转换成dp
                height = DensityUtil.px2dip(DebugActivity.this,height);
                LogUtil.i("tv----height------"+height);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

package test.cn.example.com.androidskill.art.chapter_nine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.ServiceWorkActivity;
import test.cn.example.com.util.LogUtil;

/**
 * 四大组件工作过程
 */
public class ChapterNineActivity extends AppCompatActivity implements View.OnClickListener{

    private MyBroadcastReceiverFive myBroadcastReceiverFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charapter_nine);
        initView();
    }

    private void initView() {
        Button activity_work = (Button)findViewById(R.id.activity_work);
        activity_work.setOnClickListener(this);
        Button service_work = (Button)findViewById(R.id.service_work);
        service_work.setOnClickListener(this);
        IntentFilter intentFilter = new IntentFilter("com.android.skill");
        myBroadcastReceiverFive = new MyBroadcastReceiverFive();
        registerReceiver(myBroadcastReceiverFive,intentFilter);
        findViewById(R.id.broadcast_work_order).setOnClickListener(this);
        findViewById(R.id.broadcast_work_normal).setOnClickListener(this);
    }

    private class MyBroadcastReceiverFive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.e("动态注册  MyBroadcastReceiverFive");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_work:
//                startActivity(new Intent(ChapterNineActivity.this,LifeActivity.class));
                break;
            case R.id.service_work:
                startActivity(new Intent(ChapterNineActivity.this,ServiceWorkActivity.class));
                break;
            case R.id.broadcast_work_order:
                sendMyOrderBroadcast();
                break;
            case R.id.broadcast_work_normal:
                sendMyBroadcast();
                break;
            default:
                break;
        }
    }

    private void sendMyOrderBroadcast() {
        Intent intent=new Intent();

        //定义广播的事件类型
        intent.setAction("com.android.skill");//构建意图，设置动作。把自定义的广播发出去。
        LogUtil.e(""+intent.getComponent());
        //发送广播
        sendOrderedBroadcast(intent,null);//null为广播接收者的权限
    }

    private void sendMyBroadcast() {
        Intent intent=new Intent();
        //定义广播的事件类型
        intent.setAction("com.android.skill");//构建意图，设置动作。把自定义的广播发出去。
        LogUtil.e(""+intent.getComponent());
        //发送广播
        sendBroadcast(intent,null);//null为广播接收者的权限

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁是进行广播的注销，防止内存泄漏
        unregisterReceiver(myBroadcastReceiverFive);
    }
}

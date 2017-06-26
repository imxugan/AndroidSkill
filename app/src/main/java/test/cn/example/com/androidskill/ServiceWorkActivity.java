package test.cn.example.com.androidskill;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.service.MyService4;
import test.cn.example.com.util.LogUtil;

/**
 * service工作过程
 */
public class ServiceWorkActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_work);
        initView();
    }

    private void initView() {
        Button start = (Button)findViewById(R.id.start);
        start.setOnClickListener(this);
        Button bind = (Button)findViewById(R.id.bind);
        bind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                startService(new Intent(ServiceWorkActivity.this,MyService4.class));
                break;
            case R.id.bind:
                Intent intent = new Intent(ServiceWorkActivity.this,MyService4.class);
                bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        LogUtil.i("onServiceConnected");
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, Context.BIND_AUTO_CREATE);
                break;
            default:
                break;
        }
    }
}

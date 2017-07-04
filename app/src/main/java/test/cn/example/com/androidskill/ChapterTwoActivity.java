package test.cn.example.com.androidskill;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.constant.MyConstants;
import test.cn.example.com.androidskill.service.MyService;

/**
 * android IPC机制
 */
public class ChapterTwoActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charapter_two);
        initView();
    }

    private void initView() {
        Button messenger = (Button)findViewById(R.id.messenger);
        messenger.setOnClickListener(this);
//        Button launch_mode = (Button)findViewById(R.id.launch_mode);
//        launch_mode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.messenger:
                bindService(new Intent(ChapterTwoActivity.this, MyService.class), new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Messenger messenger = new Messenger(service);
                        Message msg = Message.obtain();
                        msg.what = MyConstants.MSG_FROM_CLINET;
                        Bundle b = new Bundle();
                        b.putString("msg_data","hello im com from charapterTwo");
                        msg.setData(b);
                        try {
                            messenger.send(msg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, Context.BIND_AUTO_CREATE);
                break;
//            case R.id.launch_mode:
//                startActivity(new Intent(ChapterTwoActivity.this,LannchModeActivity.class));
//                break;
            default:
                break;
        }
    }
}

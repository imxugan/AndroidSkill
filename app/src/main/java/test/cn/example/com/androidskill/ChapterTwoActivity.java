package test.cn.example.com.androidskill;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.constant.MyConstants;
import test.cn.example.com.androidskill.service.MyService;
import test.cn.example.com.util.LogUtil;

/**
 * android IPC机制
 */
public class ChapterTwoActivity extends AppCompatActivity implements View.OnClickListener{
    private Messenger replyMessenger = new Messenger(myHandler);

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
                Intent service = new Intent(ChapterTwoActivity.this, MyService.class);
                bindService(service, myServiceConnection, Context.BIND_AUTO_CREATE);
                break;
//            case R.id.launch_mode:
//                startActivity(new Intent(ChapterTwoActivity.this,LannchModeActivity.class));
//                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(null != myServiceConnection){
            unbindService(myServiceConnection);
        }
        super.onDestroy();
    }

    private static Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyConstants.MSG_FROM_SERVICE:
                    LogUtil.i(""+msg.getData().get(MyConstants.MSG_REPLY));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    };

    private ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Messenger messenger = new Messenger(service);
            Message msg = Message.obtain();
            msg.what = MyConstants.MSG_FROM_CLINET;
            Bundle b = new Bundle();
            b.putString(MyConstants.MSG_FROM,"hello im com from charapterTwo");
            msg.setData(b);
            msg.replyTo = replyMessenger;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}

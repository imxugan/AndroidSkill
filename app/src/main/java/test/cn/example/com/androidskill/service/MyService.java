package test.cn.example.com.androidskill.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import test.cn.example.com.androidskill.constant.MyConstants;
import test.cn.example.com.util.LogUtil;

public class MyService extends Service {
	private  MyHandler myHandler = new MyHandler();
	private static class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case MyConstants.MSG_FROM_CLINET:
					LogUtil.i(""+msg.getData().getString("msg_data"));
					break;
				default:
					super.handleMessage(msg);
					break;
			}
		}
	}

	private Messenger messenger = new Messenger(myHandler);

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		LogUtil.i("onBind");
		return messenger.getBinder();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}

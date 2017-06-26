package test.cn.example.com.androidskill.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import test.cn.example.com.util.LogUtil;

public class MyService4 extends Service {
	
	@Override
	public void onCreate() {
		super.onCreate();
		LogUtil.i("MyService4  onCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtil.i("MyService4  onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		LogUtil.i("MyService4 onBind");
		return null;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		LogUtil.i("MyService4  onUnbind");
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		LogUtil.i("MyService4  onDestroy");
		super.onDestroy();
	}

	
}

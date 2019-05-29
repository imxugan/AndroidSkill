package test.cn.example.com.androidskill.optimize.process_keep;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.Nullable;

import test.cn.example.com.androidskill.MainActivity;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.optimize.RemoteConnect;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2019/5/29.
 */

public class RemoteService extends Service {
    private MyBinder binder;
    private MyServiceConnection conn;
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(" onCreate");
        if(null == binder){
            binder = new MyBinder();
        }
        conn = new MyServiceConnection();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(" onStartCommand");
        bindService(new Intent(this,LocalService.class),conn,Service.BIND_IMPORTANT);
        setNotification("remote service");
        return START_STICKY;
    }

    private void setNotification(String text) {
        String CHANNEL_ONE_ID = "test.cn.example.com.androidskill";
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Uri mUri = Settings.System.DEFAULT_NOTIFICATION_URI;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ONE_ID, "driver", NotificationManager.IMPORTANCE_LOW);

            mChannel.setDescription("description");

            mChannel.setSound(mUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(mChannel);

            notification = new Notification.Builder(this, CHANNEL_ONE_ID)
                    .setChannelId(CHANNEL_ONE_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(text)
                    .setContentIntent(pi)
                    .build();
        } else {
            // 提升应用权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification = new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(text)
                        .setContentIntent(pi)
                        .build();
            }
        }
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        startForeground(10000, notification);
    }

    private class MyBinder extends RemoteConnect.Stub{

        @Override
        public String getProcessName() throws RemoteException {
            return "RemoteService";
        }
    }

    private class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.i(componentName.getShortClassName()+"  建立连接");
            ToastUtils.shortToast(RemoteService.this,componentName.getShortClassName()+"  建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.i(componentName.getShortClassName()+"  断开连接");
            ToastUtils.shortToast(RemoteService.this,componentName.getShortClassName()+"  断开连接");
            Intent intent = new Intent(RemoteService.this,LocalService.class);
            startService(intent);
            bindService(intent,conn,Service.BIND_IMPORTANT);
        }
    }

}

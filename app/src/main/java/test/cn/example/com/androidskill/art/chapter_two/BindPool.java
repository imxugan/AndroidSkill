package test.cn.example.com.androidskill.art.chapter_two;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.concurrent.CountDownLatch;

import test.cn.example.com.androidskill.IBindPoolInterface;
import test.cn.example.com.androidskill.service.BindPoolService;
import test.cn.example.com.util.LogUtil;

public class BindPool {
    private static final int REQUEST_CODE_PLAYMUSIC = 1;
    private static final int REQUEST_CODE_DOWNLOAD = 2;
    private final Context mContext;
    private CountDownLatch countDownLatch;
    private static BindPool instance;
    private static IBindPoolInterface iBindPoolInterface;

    private BindPool(Context context){
        mContext = context.getApplicationContext();
        connectService(mContext);
    }

    private synchronized void connectService(Context context) {
        countDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(context, BindPoolService.class);
        context.bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE);

        try {
            //这里要等待，连接上Servcie后，才能继续执行queryBinder方法，否则，queryBinder方法中的
            // iBindPoolInterface 就是 null
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  static BindPool getInstance(Context context){
        if(null == instance){
            synchronized (BindPool.class){
                if(null == instance){
                    instance = new BindPool(context);
                }
            }
        }
        return instance;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.e("servcie connected ");
            iBindPoolInterface = IBindPoolInterface.Stub.asInterface(service);
            try {
                iBindPoolInterface.asBinder().linkToDeath(mBinderPoolDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //和服务端连接成功后，就解除阻塞的线程，这样queryBinder方法才能执行
            countDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.e("service disconnect ");
        }
    };

    public void disconnectServcie(){
        if(null != mServiceConnection && null != mContext){
            mContext.unbindService(mServiceConnection);
        }
    }

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            LogUtil.e("bind die");
            iBindPoolInterface.asBinder().unlinkToDeath(mBinderPoolDeathRecipient,0);
            connectService(mContext);
        }
    };

    public IBinder queryBinder(int requestCode) throws RemoteException{
        LogUtil.e("iBindPoolInterface =  "+iBindPoolInterface);
        if(null != iBindPoolInterface){
            return iBindPoolInterface.queryBinder(requestCode);
        }
        return null;
    }


    public static  class IBindPoolImpl extends  IBindPoolInterface.Stub{

        @Override
        public IBinder queryBinder(int requestCode) throws RemoteException {
            IBinder iBinder = null;
            switch (requestCode){
                case REQUEST_CODE_PLAYMUSIC:
                    iBinder = new IPlayImpl();
                    break;
                case REQUEST_CODE_DOWNLOAD:
                    iBinder = new IDownLoadImpl();
                    break;
            }
            return iBinder;
        }
    }

}

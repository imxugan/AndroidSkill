package test.cn.example.com.androidskill.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import test.cn.example.com.androidskill.Book;
import test.cn.example.com.androidskill.IBookManager;
import test.cn.example.com.androidskill.IOnNewBookArrived;
import test.cn.example.com.androidskill.util.ProcessUtils;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/6.
 */

public class BookManagerService extends Service{
    //这里采用CopyWriteArrayList，是因为CopyWriteArrayList是支持
    //并发读写的，由于AIDL的方法是在服务端进程的Binder线程池
    //中执行的，因此当多个客户端同时连接时，会存在多个线程同问
    //访问的情况，所以需要在AIDL方法中处理线程同步的问题，CopyWriteArrayList
    //就自动同步。注意：AIDL中支持ArrayList,但是CopyWriteArrayList并不继承
    //ArrayList,那么CopyWriteArrayList为什么能正常工作呢?这是因为AIDL中所支持
    //的是抽象的List,而List只是一个接口，因此，虽然服务端返回的是CopyWriteArrayList，
    //但是binder会按照List的规范去访问数据并最终形成一个新的ArrayList返回给客户端。
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
//    private CopyOnWriteArrayList<IOnNewBookArrived> listeners = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrived> listeners = new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = ProcessUtils.getProcessName(this, Process.myPid());
        LogUtil.i("threadId ="+Thread.currentThread().getId()+"     processName=  "+processName);//主线程
        bookList.add(new Book(1,"first line code"));
        bookList.add(new Book(2,"java effective"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i("threadId ="+Thread.currentThread().getId());//主线程
        return mBinder;
    }

    private Binder mBinder = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            LogUtil.i("threadId ="+Thread.currentThread().getId());//子线程
//            SystemClock.sleep(6000);//模拟耗时操作
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);//子线程
            LogUtil.i("threadId ="+Thread.currentThread().getId()+""+book.toString());//子线程
            newBookArrived(book);
        }

        @Override
        public void registerLister(IOnNewBookArrived listener) throws RemoteException {
//            if(!listeners.contains(listener)){
//                listeners.add(listener);
//            }
            //子线程
            listeners.register(listener);//mListeners换成RemoteCallbackList后
            int num = listeners.beginBroadcast();
            LogUtil.i("threadId ="+Thread.currentThread().getId()+"---registerListener后       mListeners.size()="+num);
            listeners.finishBroadcast();
        }

        @Override
        public void unregisterLister(IOnNewBookArrived listener) throws RemoteException {
//            if(listeners.contains(listener)){
//                listeners.remove(listener);
//                LogUtil.i("unregisterLister succed");
//            }else {
//                LogUtil.i("unregisterLister failed");
//            }
//            LogUtil.i("size="+listeners.size());


            //子线程
            listeners.unregister(listener);//mListeners换成RemoteCallbackList后
            int num = listeners.beginBroadcast();
            LogUtil.i("threadId ="+Thread.currentThread().getId()+"---unregisterListener后       mListeners.size()="+num);
            listeners.finishBroadcast();
        }
    };

    private void newBookArrived(Book book) {
//        for (int i = 0; i < listeners.size(); i++) {
//            try {
//                listeners.get(i).onNewBookArrived(book);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }

        int n = listeners.beginBroadcast();
        for (int i = 0; i < n; i++) {
            IOnNewBookArrived listener = listeners.getBroadcastItem(i);
            if(listener != null){
                try {
                    listener.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        listeners.finishBroadcast();
        LogUtil.i("threadId ="+Thread.currentThread().getId());//子线程中
    }

}

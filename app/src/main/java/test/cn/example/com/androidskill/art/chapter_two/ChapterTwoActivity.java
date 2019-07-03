package test.cn.example.com.androidskill.art.chapter_two;

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

import java.util.List;

import test.cn.example.com.androidskill.Book;
import test.cn.example.com.androidskill.IBookManager;
import test.cn.example.com.androidskill.IOnNewBookArrived;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.constant.MyConstants;
import test.cn.example.com.androidskill.service.BookManagerService;
import test.cn.example.com.androidskill.service.MyService;
import test.cn.example.com.util.LogUtil;

/**
 * android IPC机制
 */
public class ChapterTwoActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean isMyServiceConnected;
    private boolean isBookServiceConnected;
    private IBookManager mIBookManager;
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
        Button btn_aidl = (Button)findViewById(R.id.btn_aidl);
        btn_aidl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.messenger:
                Intent service = new Intent(ChapterTwoActivity.this, MyService.class);
                bindService(service, myServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_aidl:
                Intent bookService = new Intent(ChapterTwoActivity.this, BookManagerService.class);
                bindService(bookService,bookServiceConnection,Context.BIND_AUTO_CREATE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(isMyServiceConnected && null != myServiceConnection){
            unbindService(myServiceConnection);
        }
        if(isBookServiceConnected ){
            //先注销掉监听
            if(null != mIBookManager){
                try {
                    mIBookManager.unregisterLister(mNewBookArrivedListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            //在解绑服务
            if(null != bookServiceConnection){
                unbindService(bookServiceConnection);
            }

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
                case MyConstants.MSG_FROM_BOOK_SERVICE:
                    Book book = (Book) msg.getData().get(MyConstants.MSG_FROM_BOOK);
                    LogUtil.i("新到的书是    "+book.toString());
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
            isMyServiceConnected = true;
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
            isMyServiceConnected = false;
        }
    };

    private ServiceConnection bookServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBookServiceConnected = true;
            mIBookManager = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient,0);
                List<Book> bookList = mIBookManager.getBookList();
                LogUtil.i("从服务端获取的书是：   "+bookList.toString());
                mIBookManager.registerLister(mNewBookArrivedListener);
                mIBookManager.addBook(new Book(3,"android skill"));
                LogUtil.i("---从服务端获取的书是：   "+mIBookManager.getBookList().toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                mIBookManager.unregisterLister(mNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isBookServiceConnected = false;
        }
    };

    private IOnNewBookArrived mNewBookArrivedListener = new IOnNewBookArrived.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            LogUtil.i("onNewBookArrived---threadId ="+Thread.currentThread().getId());
            //onNewBookArrived这个方法在客户端的线程池中执行，所以需要
            //通过handler发送消息到UI线程
            Message msg = Message.obtain();
            msg.what = MyConstants.MSG_FROM_BOOK_SERVICE;
            Bundle b = new Bundle();
            b.putParcelable(MyConstants.MSG_FROM_BOOK,book);
            msg.setData(b);
            myHandler.sendMessage(msg);
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){

        @Override
        public void binderDied() {
            LogUtil.i("binderDied");
            if(mIBookManager == null){
                return;
            }
            mIBookManager.asBinder().unlinkToDeath(mDeathRecipient,0);
            mIBookManager = null;
            //从新进行远程连接，代码略
        }
    };
}

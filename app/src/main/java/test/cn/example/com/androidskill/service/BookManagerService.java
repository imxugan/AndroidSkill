package test.cn.example.com.androidskill.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import test.cn.example.com.androidskill.Book;
import test.cn.example.com.androidskill.IBookManager;
import test.cn.example.com.androidskill.IOnNewBookArrived;

/**
 * Created by xgxg on 2017/7/6.
 */

public class BookManagerService extends Service{
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
    private ArrayList<IOnNewBookArrived> listeners = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1,"first line code"));
        bookList.add(new Book(2,"java effective"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private Binder mBinder = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
            newBookArrived(book);
        }

        @Override
        public void registerLister(IOnNewBookArrived listener) throws RemoteException {
            if(!listeners.contains(listener)){
                listeners.add(listener);
            }
        }

        @Override
        public void unregisterLister(IOnNewBookArrived listener) throws RemoteException {
            if(listeners.contains(listener)){
                listeners.remove(listener);
            }
        }
    };

    private void newBookArrived(Book book) {
        for (int i = 0; i < listeners.size(); i++) {
            try {
                listeners.get(i).onNewBookArrived(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}

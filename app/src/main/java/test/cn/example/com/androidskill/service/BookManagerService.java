package test.cn.example.com.androidskill.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import test.cn.example.com.androidskill.Book;
import test.cn.example.com.androidskill.IBookManager;

/**
 * Created by xgxg on 2017/7/6.
 */

public class BookManagerService extends Service{
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

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
        }
    };
}

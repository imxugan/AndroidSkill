// IBookManager.aidl
package test.cn.example.com.androidskill;
import java.util.List;
import test.cn.example.com.androidskill.Book;
import test.cn.example.com.androidskill.IOnNewBookArrived;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerLister(IOnNewBookArrived listener);
    void unregisterLister(IOnNewBookArrived listener);
}

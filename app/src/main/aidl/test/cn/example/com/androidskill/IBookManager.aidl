// IBookManager.aidl
package test.cn.example.com.androidskill;
import java.util.List;
import test.cn.example.com.androidskill.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}

// IOnNewBookArrived.aidl
package test.cn.example.com.androidskill;
import test.cn.example.com.androidskill.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrived {
     void onNewBookArrived(in Book book); //一定要带上in,否则会报错
}

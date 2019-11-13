package test.cn.example.com.androidskill.leak;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/24.
 */

public class LeakTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);
        CommonUtil.getInstance(this);

        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        LogUtil.i(""+heapSize);//单位是MB
        //最大分配内存获取方法2
        float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0/ (1024 * 1024));
        LogUtil.i(""+maxMemory);
//        setContentView(new MyView(this));
    }

    @Override
    public void onStop(){
        super.onStop();
        //如果想要避免内存泄漏，需要将MyListenerCollect.weakHashMap中的持有的MyView对象去除，由于MyView持有了
        //LeakTestActivity的引用，所以，去除后，LeakTestActivity就会在销毁时，被标记为垃圾对象，在gc回收时，
        //才会将其回收,这个案例中，不仅LeakTestActivity对象无法被GC回收，而且，MyView对象也无法被回收，
        //这个案例，导致内存泄漏的原因是，有与MyListenerCollect类的weakHashMap这个成员变量是静态的，导致，
        //这个变量是Gc root ,这样，当这个weakHashMap存储的元素就都是gc root 可达的，这也就表明，这个对象
        //持有的所有引用是不会被GC垃圾回收器回收的，即使手动的执行GC操作。由于weakHashMap中存放的是Myview对象，
        //所以MyView对象即使销毁，这个对象也是不会被GC回收的，MyView对象又持有LeakTestActivity的引用，导致
        //LeakTestActivity对象，即时调用了onDestroy()方法，这个LeakTestActivity对象也不会销毁，这样也造成了
        //LeakTestActivity对象无法被GC回收，这就导致了内存泄漏。

        //通过这个案例分析掌握了以下知识点：
        //1.一个应用中是有多个GC root的
        //2. 哪些对象是GC root,
        //  在java语言中，可作为GC Roots的对象包括下面几种：
        //    a).虚拟机栈(帧栈中的本地变量表)中引用的对象
        //    b).方法区中类静态属性引用的对象
        //    c).方法区中常量引用的对象
        //    d).本地方法栈中JNI(即一般说的Native方法)引用的对象

        //3.如果判断一个对象可达？
//             可达性分析算法基本思想是，通过一系列称为"GC Roots"的对象作为起始点，从这些节点开始向下搜索
//             搜索所走过的路径称为引用链(Reference Chain),当一个对象到GC Roots没有任何的引用链时，就称这个
//             对象是不可达的。
//        4.引用的分类
//              a).强引用就是指在程序代码之中普遍存在的，类似"Object obj = new Object()"这类的引用，
//                  只要强引用还存在，垃圾收集器永远不会回收掉被引用的对象。
//              b).软引用是用于描述一些还有用但并非必需的对象。对于软引用关联的对象，在系统将要发生内存
//                  溢出异常之前，将会把这些对象列进回收范围之中进行第二次回收。如果这次回收还没有足够的内存，
//                  才会抛出内存溢出异常。JDK 1.2之后，提供了SoftReference类来实现软引用。
//              c).弱引用也是用来描述非必需对象的，但是它的强度比软引用更弱一点，被弱引用关联的对象只能生
//                  存到下一次垃圾收集发生之前。当垃圾收集器工作时，无论当前内存是否足够，都会回收掉只被弱引用关联的
//                  对象。JDK 1.2之后，提供了WeakReference类来实现弱引用。
//              d).虚引用也称为幽灵引用或者幻影引用，它是一种最弱的引用关系，一个对象是否有虚引用的存在，完全不会对
//                  其生存时间构成影响，也无法通过虚引用来取得一个对象实例。为一个对象设置虚引用关联的唯一目的就是能
//                  在这个对象被垃圾收集器回收时收到一个系统通知，JDK 1.2以后，提供了PhantomReference类来实现虚引用。
//
//         5.准确获取一个对象的内存地址值   System.identityHashCode(Object)
//         6.static修饰的变量称为类变量或者静态变量，它位于方法区
//         7.WeakHashMap,特点是，当除了自身有对key引用外，此key没有其他引用，那么此map会自动丢弃此值
//        例如：int a = 1;
//        Map weakHashMap = new WeakHashMap<>();
//        weakHashMap.put(a,"hello");
//        a = null;
//        此时，weakmap里面的a会被丢弃。
//


//        java中，什么是对象的可达与不可达？
//        https://blog.csdn.net/qq_32320807/article/details/83269913
//Java中其实也有内存泄露,就是因为对象无用却可达的原因.
//        这个细分细分下来有三个
//        1. 不可用不可达------>这种情况GC会帮我们回收掉,而C++不会
//        2. 不可用可达 ------>这种情况会存在内存泄露
//        3. 可用可达 ------>正常使用
//        举个例子:
//        在这个例子中，我们循环申请Object对象，并将所申请的对象放入一个Vector中，如果我们仅仅释放引用本身，
//          那么Vector仍然引用该对象，所以这个对象对GC来说是不可回收的。因此，如果对象加入到Vector后，
//          还必须从Vector中删除，最简单的方法就是将Vector对象设置为null
//        Vector v=new Vector(10);
//        for (int i=1;i<100; i++)
//        {
//            Object o=new Object();
//            v.add(o);
//            o=null;
//        }
//此时，所有的Object对象都没有被释放，因为变量v引用这些对象。

//
//


//        MyListenerCollect.weakHashMap.clear();

        LogUtil.i(MyListenerCollect.weakHashMap.size()+"        "+System.identityHashCode(MyListenerCollect.weakHashMap));
    }
}

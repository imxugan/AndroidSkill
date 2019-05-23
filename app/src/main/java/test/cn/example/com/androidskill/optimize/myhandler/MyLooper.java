package test.cn.example.com.androidskill.optimize.myhandler;

/**
 * Created by xugan on 2019/5/21.
 */

public final class MyLooper {
    private static final ThreadLocal<MyLooper> sThreadLocal = new ThreadLocal<MyLooper>();
    MyMessageQueue myMessageQueue;
    private MyLooper(){
        myMessageQueue = new MyMessageQueue();
    }

    public static void prepare(){
        if(sThreadLocal.get() != null){
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new MyLooper());
    }

    public static MyLooper myLooper(){
        return sThreadLocal.get();
    }

    public static void loop(){
        //轮询消息
        MyLooper me = myLooper();
        if(null == me){
            throw  new RuntimeException("No Looper;Looper.prepare() wasn't called on this thread.");
        }

        MyMessageQueue queue = me.myMessageQueue;
        for(;;){
            MyMessaga myMessaga = queue.next();
            if(null == myMessaga){
                continue;
            }
            //转发给myhandler
            myMessaga.target.dispatchMessage(myMessaga);
        }
    }
}

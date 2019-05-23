package test.cn.example.com.androidskill.optimize.myhandler;

/**
 * Created by xugan on 2019/5/21.
 */

public class MyHandler {
    private final MyLooper myLooper;
    private MyMessageQueue myMessageQueue;

    public MyHandler(){
        myLooper = MyLooper.myLooper();
        this.myMessageQueue = myLooper.myMessageQueue;
    }

    public void sendMessage(MyMessaga msg){
        //向消息队列中添加消息
        msg.target = this;
        myMessageQueue.enqueueMessage(msg);
    }

    //转发消息
    public void dispatchMessage(MyMessaga messaga){
        handleMessage(messaga);
    }

    public void handleMessage(MyMessaga myMessaga){

    }
}

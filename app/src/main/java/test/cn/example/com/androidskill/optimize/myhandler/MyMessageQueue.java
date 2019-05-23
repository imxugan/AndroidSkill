package test.cn.example.com.androidskill.optimize.myhandler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xugan on 2019/5/21.
 */

public class MyMessageQueue {
    /**
     * 消息队列的设计要考虑3点
     * 1.消息队列应该要有大小的限制
     * 2.消息队列满了，子线程停止发送消息，阻塞
     * 3.消息队列为空，looper停止轮询，，阻塞
     */
    private MyMessaga[] myMessagas;
    private int putIndex;
    private int takeIndex;
    private int count;
    private Lock lock;//互斥锁
    private Condition notEmpty;
    private Condition notFull;
    public MyMessageQueue(){
        this.myMessagas = new MyMessaga[50];
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    /**
     * 加入队列
     * @param msg
     */
    public void enqueueMessage(MyMessaga msg) {
        try {
            lock.lock();
            while (count == myMessagas.length){
                //消息队列满了，则需要阻塞
                notFull.await();
            }
            myMessagas[putIndex] = msg;
            putIndex = ((++putIndex) == myMessagas.length ? 0 : putIndex);
            count++;
            //有新的消息，通知looper去轮询消息队列
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public MyMessaga next(){
        MyMessaga myMessaga = null;
        try {
            lock.lock();
            //消息队列为空，则阻塞，停止轮询
            while (0 == count){
                notEmpty.await();
            }
            myMessaga = myMessagas[takeIndex];
            myMessagas[takeIndex] = null;//重置
            takeIndex = ((++takeIndex) == myMessagas.length) ? 0 : takeIndex;
            count--;
            //使用了一个消息，通知消息生产线程可以继续生产消息了
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return myMessaga;
    }
}

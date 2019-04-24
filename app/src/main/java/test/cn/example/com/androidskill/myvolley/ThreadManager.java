package test.cn.example.com.androidskill.myvolley;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import test.cn.example.com.util.LogUtil;

/**
 * 线程池管理类以及队列类
 * Created by xugan on 2019/4/24.
 */

public class ThreadManager {
    //队列
    private LinkedBlockingDeque<Runnable> mQueue = new LinkedBlockingDeque<>();
    //创建一个线程池
    private ThreadPoolExecutor mThreadPoolExecutor;

    private DelayQueue<HttpTask> failedQueue = new DelayQueue<>();

    private ThreadManager(){
        // 防止反射入侵外部类
        if(SingletonHolder.instance != null){
            throw new IllegalStateException();
        }

        //第一个参数是核心线程数
        //第二个参数是最大线程数
        //第三个参数 空闲现线程的存活时间
        //第四个参数 时间单位
        //第五个参数 队列对象，它决定了缓存任务的排队策略
        //第六个参数是一个Handler对象，用来处理失败的线程
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                        addTask(runnable);
                    }
                });
        mThreadPoolExecutor.execute(runnable);
        mThreadPoolExecutor.execute(failedRunnable);
    }

    //将请求任务添加到队列中
    public void addTask(Runnable runnable){
        if(null == runnable){
            return;
        }
        try {
            mQueue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重试任务添加到队列
     * @param httpTask
     */
    public void addDelayTask(HttpTask httpTask){
        if(null == httpTask){
            return;
        }
        try {
            //设置重试的访问延迟时间
            httpTask.setDelayTime(3000);
            failedQueue.offer(httpTask);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 核心线程，用来执行失败了要重新请求的任务
     */
    public Runnable failedRunnable = new Runnable() {
        @Override
        public void run() {
            while(true){
                HttpTask task = null;
                try {
                    task = failedQueue.take();
                    //如果重试次数少于三次就继续
                    if(task.getCountNum()<3){
                        task.setCountNum(task.getCountNum()+1);
                        mThreadPoolExecutor.execute(task);
                        LogUtil.i("请求重试机制，请求次数"+task.getCountNum());
                    }else {
                        LogUtil.i("请求重试机制，请求超过三次，我尽力了");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 核心线程，不停的到队列中去获取任务然后执行
     */
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while(true){
                try {
                    Runnable take = mQueue.take();
                    mThreadPoolExecutor.execute(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    public static ThreadManager getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private static ThreadManager instance = new ThreadManager();
    }


}

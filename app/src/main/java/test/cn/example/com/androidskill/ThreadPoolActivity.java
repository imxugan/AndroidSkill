package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import test.cn.example.com.util.LogUtil;

/**
 * 调试
 */
public class ThreadPoolActivity extends AppCompatActivity implements View.OnClickListener {
    ScheduledExecutorService mScheduledExecutorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
//        new Thread的弊端如下：
//        a. 每次new Thread新建对象性能差。
//        b. 线程缺乏统一管理，可能无限制新建线程，相互之间竞争，及可能占用过多系统资源导致死机或oom。
//        c. 缺乏更多功能，如定时执行、定期执行、线程中断。
//        相比new Thread，Java提供的四种线程池的好处在于：
//        a. 重用存在的线程，减少对象创建、消亡的开销，性能佳。
//        b. 可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竞争，避免堵塞。
//        c. 提供定时执行、定期执行、单线程、并发数控制等功能。


//        ExecutorService的生命周期包括三种状态：运行、关闭、终止。创建后便进入运行状态，
//        当调用了shutdown（）方法时，便进入关闭状态，此时意味着ExecutorService不再接受新的任务，
//        但它还在执行已经提交了的任务，当素有已经提交了的任务执行完后，便到达终止状态。
//        如果不调用shutdown（）方法，ExecutorService会一直处在运行状态，不断接收新的任务，
//        执行新的任务，服务器端一般不需要关闭它，保持一直运行即可。
        initView();
    }

    private void initView() {
        Button cached_thread_pool = (Button) findViewById(R.id.cached_thread_pool);
        cached_thread_pool.setOnClickListener(this);
        Button fixed_thread_pool = (Button) findViewById(R.id.fixed_thread_pool);
        fixed_thread_pool.setOnClickListener(this);
        Button scheduled_thread_pool = (Button) findViewById(R.id.scheduled_thread_pool);
        scheduled_thread_pool.setOnClickListener(this);
        Button single_thread_pool = (Button) findViewById(R.id.single_thread_pool);
        single_thread_pool.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cached_thread_pool:
                testCachedThreadPool();
                break;
            case R.id.fixed_thread_pool:
                testFixedThreadPool();
                break;
            case R.id.scheduled_thread_pool:
                testScheduledThreadPool();
                break;
            case R.id.single_thread_pool:
                testSingleThreadPool();
                break;
            default:
                break;
        }
    }

    private void testSingleThreadPool() {
//创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
// 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。示例代码如下：
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        //结果依次输出，相当于顺序执行各个任务。
//现行大多数GUI程序都是单线程的。Android中单线程可用于数据库操作，文件操作，应用批量安装，
// 应用批量删除等不适合并发但可能IO阻塞性及影响UI线程响应的操作。
                        LogUtil.i("newSingleThread  index=  "+index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //关闭线程池
        executorService.shutdown();

    }

    private void testScheduledThreadPool() {
        //ScheduledExecutorService比Timer更安全，功能更强大
        //创建一个定长线程池，支持定时及周期性任务执行。延迟执行示例代码如下：
        mScheduledExecutorService = Executors.newScheduledThreadPool(5);
        mScheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                LogUtil.i("dealy 3 seconds");
            }
        },3, TimeUnit.SECONDS);//3 表示延迟3秒

//        定期执行示例代码如下：,记得关闭当前activity时，关闭线程池
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                LogUtil.i("delay 1 second and execute every 3 seconds");
            }
        }, 1, 3, TimeUnit.SECONDS);//表示1秒延时后，每3秒执行一次,注意，不是每3秒执行一次后都延时1秒
                                    //刚开始的时候延时1秒，后面每隔3秒执行一次时，不在延时1秒了

//        创建并执行一个在给定初始5秒延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的3秒延迟
        Runnable syncRunnable = new Runnable() {
            @Override
            public void run() {
                LogUtil.e(Thread.currentThread().getName());
            }
        };
        mScheduledExecutorService.scheduleWithFixedDelay(syncRunnable, 5000, 3000, TimeUnit.MILLISECONDS);
    }

    private void testFixedThreadPool() {
//        创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。示例代码如下：
//        因为线程池大小为3，每个任务输出index后sleep 5秒，所以每两秒打印3个数字。
//        定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            newFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        LogUtil.i(Thread.currentThread().getId()+"---newFixedThreadPool index=    "+index);
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //关闭线程池
        newFixedThreadPool.shutdown();
    }

    private void testCachedThreadPool() {
//创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
//线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程
        ExecutorService newCahcedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            newCahcedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    if(index%2 == 0){
                        try {
                            Thread.sleep(1000+index);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    LogUtil.i(""+index);
                }
            });
        }
        //关闭线程池
        newCahcedThreadPool.shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScheduledExecutorService.shutdownNow();
    }
}

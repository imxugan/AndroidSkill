package test.cn.example.com.androidskill.myvolley;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 将网络请求封装成任务对象，线程对象
 * Created by xugan on 2019/4/24.
 */

public class HttpTask <T> implements Runnable,Delayed{
    //请求对象
    private IHttpRequest iHttpRequest;
    //重试的延迟时间的参数
    private  long delayTime;

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime+System.currentTimeMillis();
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    //重试的次数
    private int countNum;

    public HttpTask(IHttpRequest iHttpRequest,String url,T requestData,CallBackListener callBackListener){
        this.iHttpRequest = iHttpRequest;
        this.iHttpRequest.setUrl(url);
        this.iHttpRequest.setListener(callBackListener);
        if(null != requestData){
            String dataStr = new Gson().toJson(requestData);
            try {
                this.iHttpRequest.setData(dataStr.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        try {
            iHttpRequest.execute();
        }catch (Exception e){
            ThreadManager.getInstance().addDelayTask(this);
        }

    }

    //每次重试延迟时间
    @Override
    public long getDelay(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(getDelayTime()-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed delayed) {
        return 0;
    }
}

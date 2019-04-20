package test.cn.example.com.androidskill.livedatabus;

import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xugan on 2019/4/20.
 */

public class LiveDataBus {
    //创建一个map来接收订阅者
    private Map<String,MutableLiveData<Object>> map;
    private LiveDataBus(){
        map = new HashMap<>();
    }
    private static class SingletonHolder{
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
    }

    public static LiveDataBus getInstance(){
        return SingletonHolder.DEFAULT_BUS;
    }

    //提供外部调用的方法
    public synchronized <T> MutableLiveData<T> with(String key,Class<T> type){
        if(!map.containsKey(key)){
            map.put(key,new MutableLiveData<Object>());
        }
        return (MutableLiveData<T>) map.get(key);
    }
}

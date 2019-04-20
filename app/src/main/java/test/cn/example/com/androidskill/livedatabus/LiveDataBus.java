package test.cn.example.com.androidskill.livedatabus;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    public synchronized <T> BusMutableLiveData<T> with(String key,Class<T> type){
        if(!map.containsKey(key)){
            map.put(key,new BusMutableLiveData<Object>());
        }
        return (BusMutableLiveData<T>) map.get(key);
    }

    /**
     * 重写MutableLiveData 在observe方法中进行hook
     */
    public static class BusMutableLiveData<T> extends MutableLiveData<T>{
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            super.observe(owner, observer);
            try {
                hook(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void hook(Observer<T> observer) throws Exception {
            //获取liveData类的class对象
            Class<LiveData> liveDataClazz = LiveData.class;
            //通过反射获取LiveData里面的Observer
            Field mObservers = liveDataClazz.getDeclaredField("mObservers");
            //设置成员变量可以访问
            mObservers.setAccessible(true);
            //获取这个成员变量的值，这个值是一个map
            Object objectObservers = mObservers.get(this);
            //获取这个map的class对象
            Class<?> observerClazz = objectObservers.getClass();
            //获取到observerClazz里面的get方法
            Method get = observerClazz.getDeclaredMethod("get", Object.class);
            //设置这个get方法能够暴力访问
            get.setAccessible(true);
            Object invokeEntry = get.invoke(objectObservers, observer);
            //定一个空的对象
            Object objectWrapper = null;
            if(invokeEntry instanceof Map.Entry){
                objectWrapper = ((Map.Entry)invokeEntry).getValue();
            }
            if(null == objectWrapper){
                throw new NullPointerException("Wrapper can not be null!'");
            }
            Class<?> superclass = objectWrapper.getClass().getSuperclass();
            //通过superclass获取到mLastVersion成员变量
            Field mLastVersionField = superclass.getDeclaredField("mLastVersion");
            //设置mLastVersion变量暴力访问
            mLastVersionField.setAccessible(true);
            Field mVersionFile = liveDataClazz.getDeclaredField("mVersion");
            mVersionFile.setAccessible(true);
            //获取mVerson的值
            Object mVersonValue = mVersionFile.get(this);
            //将mVerson成员变量的值赋值给mLastVersion字段
            mLastVersionField.set(objectWrapper,mVersonValue);
        }
    }
}

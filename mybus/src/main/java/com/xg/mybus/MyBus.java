package com.xg.mybus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xugan on 2019/4/18.
 */

public class MyBus {
    private Map<Object,List<MethodManager>> map;
    private Handler handler;
    private ExecutorService executorService;
    private MyBus(){
        map = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }
    private static MyBus instance = new MyBus();
    public static MyBus getInstance(){
        return instance;
    }

    /**
     * 获取到传递进来的组件里面的所有订阅方法然后放进map
     * @param object
     */
    public void register(Object object){
        List<MethodManager> methodManagerList = map.get(object);
        if(null == methodManagerList){
            methodManagerList = findAnnotationsMethod(object);
            map.put(object,methodManagerList);
        }
    }

    private List<MethodManager> findAnnotationsMethod(Object object) {
        List<MethodManager> list = new ArrayList<>();
        //获取到class对象
        Class<?> clazz = object.getClass();
        //获取到该clazz对象的所有方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for(Method method:declaredMethods){
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            //如果没有Subscribe注解，就代表这个方法不是订阅方法
            if(null == annotation){
                continue;
            }
            Type genericReturnType = method.getGenericReturnType();
            if(!genericReturnType.toString().equals("void")){
                throw new RuntimeException("方法返回值必须是void类型的");
            }
            //拿到所有接受的参数的类型
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length!=1){
                throw new RuntimeException("方法参数必须只有一个");
            }
            MethodManager methodManager = new MethodManager(parameterTypes[0],annotation.threadMode(),method);
            list.add(methodManager);
        }
        return list;
    }

    /**
     * 发布方法
     * @param data
     */
    public void post(final Object data){
        //获取到key的集合
        Set<Object> keySet = map.keySet();
        if(null != keySet){
            for(final Object obj:keySet){
                List<MethodManager> methodManagerList = map.get(obj);
                if(null != methodManagerList && methodManagerList.size()>0){
                    //遍历所有的订阅方法
                    for(final MethodManager methodManager:methodManagerList){
                        //判断订阅者接收的参数类型是否和发布的类型一致
                        if(methodManager.getType().isAssignableFrom(data.getClass())){
                            switch (methodManager.getThreadMode()){
                                case POSTING:
                                    //子-子  主-主
                                    invoke(methodManager,obj,data);
                                    break;
                                case MAIN:
                                    // 子-主  主-主
                                    //如果发送不发在主线程
                                    if(Looper.myLooper() == Looper.getMainLooper()){
                                        invoke(methodManager,obj,data);
                                    }else {
                                        //发送方在子线程,从子线程切换到主线程
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                invoke(methodManager,obj,data);
                                            }
                                        });

                                    }
                                    break;
                                case BACKGROUND:
                                    // 主-子，子-子
                                    if(Looper.myLooper() == Looper.getMainLooper()){
                                        executorService.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                invoke(methodManager,obj,data);
                                            }
                                        });
                                    }else {
                                        //发送方法在子线程
                                        invoke(methodManager,obj,data);
                                    }
                                    break;
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * 通过反射执行订阅者的方法
     * @param methodManager
     * @param obj
     * @param data
     */
    private void invoke(MethodManager methodManager, Object obj, Object data) {
        Method method = methodManager.getMethod();
        try {
            method.invoke(obj,data);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void unRegister(Object object){
        if(map.containsKey(object)){
            map.remove(object);
        }
    }
}

package test.cn.example.com.androidskill.designpattern;

import android.content.Context;

import leo.android.cglib.proxy.Enhancer;
import leo.android.cglib.proxy.MethodInterceptor;
import leo.android.cglib.proxy.MethodProxy;
import test.cn.example.com.util.LogUtil;

public class MyInterceptor implements MethodInterceptor {
    private final Context context;//目标类

    public MyInterceptor(Context context){
        this.context = context;
    }

    public Object getProxy(Class clazz){
        Enhancer enhancer = new Enhancer(context);
        enhancer.setSuperclass(clazz);
        enhancer.setInterceptor(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Object[] objects, MethodProxy methodProxy) throws Exception {
        LogUtil.i("cglib for android  动态代理 事物开始");
        Object result = methodProxy.invokeSuper(o, objects);
        LogUtil.i("cglib for android  动态代理 事物提交");
        return result;
    }
}

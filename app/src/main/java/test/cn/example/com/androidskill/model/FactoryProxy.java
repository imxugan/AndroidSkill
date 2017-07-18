package test.cn.example.com.androidskill.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/18.
 */

public class FactoryProxy {
    private Object target;
    public FactoryProxy(Object target){
        this.target = target;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),
                new InvocationHandler(){

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        LogUtil.i("工厂模式生产代理对象---开始事务");
                        Object object = method.invoke(target,args);
                        LogUtil.i("工厂模式生产代理对象---提交事务");
                        return object;
                    }
                });
    }
}

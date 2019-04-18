package com.xg.mybus;

import java.lang.reflect.Method;

/**
 * Created by xugan on 2019/4/18.
 */

public class MethodManager {
    //方法传入的参数类型
    private Class<?> type;
    //方法上面的注解参数
    private ThreadMode threadMode;
    //方法本身
    private Method method;

    public MethodManager(Class<?> type, ThreadMode threadMode, Method method) {
        this.type = type;
        this.threadMode = threadMode;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

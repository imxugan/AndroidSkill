package test.cn.example.com.androidskill.aop.aspect;

import android.os.Handler;
import android.os.Looper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by xugan on 2019/4/19.
 */
@Aspect
public class MainAspect {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Pointcut("execution(@test.cn.example.com.androidskill.aop.annotation.MainThread * * (..))")
    public void methodAnnotationsMonitored(){

    }

    @Around("methodAnnotationsMonitored()")
    public Object doMonitoredMethod(final ProceedingJoinPoint joinPoint){
        final Object[] objects = {null};
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    objects[0] = joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        return objects[0];
    }
}

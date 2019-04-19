package test.cn.example.com.androidskill.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
/**
 * Created by xugan on 2019/4/19.
 */
@Aspect
public class AsyncAspect {
    /**
     * 切入点,告诉切面，接收哪些标记的功能
     */
    @Pointcut("execution(@test.cn.example.com.androidskill.aop.annotation.AsyncThread * * (..))")
    public void methodAnnotationsMonitored(){

    }

    @Around("methodAnnotationsMonitored()")
    public Object doMonitoredMethod(final ProceedingJoinPoint joinPoint){
        final Object[] objects = {null};
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    objects[0] = joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
        return objects[0];
    }
}

package test.cn.example.com.androidskill.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/19.
 */
@Aspect
public class MonitoredAspect {
    /**
     * 切入点，告诉切面，接收哪些标记的功能
     */
    @Pointcut("execution(@test.cn.example.com.androidskill.aop.annotation.Monitored * * (..))")
    public void methodAnnotationsMonitored(){

    }

    /**
     * @Around()    性能监控，统计
     * @After()     释放资源
     * @Before()    权限验证
     * @param joinPoint
     * @return
     */
    @Around("methodAnnotationsMonitored()")
    public Object doMonitoredMethod(ProceedingJoinPoint joinPoint){
        long startTime = System.currentTimeMillis();
        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        LogUtil.i("功能的执行时间 "+(endTime-startTime)+"");
        return object;
    }
}

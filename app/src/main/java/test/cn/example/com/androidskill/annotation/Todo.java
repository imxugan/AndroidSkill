package test.cn.example.com.androidskill.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xgxg on 2017/7/13.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Todo {
    public enum Priority{LOW,MEDIUM,HIGH}
    public enum Status{STARTED,NOT_STARTED}
    String author() default "jack";
    Priority priority() default Priority.LOW;
    Status stauts() default Status.NOT_STARTED;
}

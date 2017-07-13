package test.cn.example.com.androidskill.annotation;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/13.
 */

public class AnnotationTest {
    @Todo(stauts = Todo.Status.NOT_STARTED,author = "jim",priority = Todo.Priority.HIGH)
    public void callBack() {
        LogUtil.i("老板，我的工作做完了");
    }
}

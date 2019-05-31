package test.cn.example.com.androidskill.optimize.hotfix;

import android.content.Context;

import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2019/5/30.
 */

public class MyTest {
    public void test(Context context){
        int a = 10;
        int b = 0;
        int result = a / b;
//        Toast.makeText(context,result+"",Toast.LENGTH_SHORT).show();
        ToastUtils.shortToast(context,result+"");

    }
}

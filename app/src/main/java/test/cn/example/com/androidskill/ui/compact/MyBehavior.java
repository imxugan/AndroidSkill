package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/2.
 */

public class MyBehavior extends CoordinatorLayout.Behavior<View> {
    //构造函数必须要，否则会报错
    public MyBehavior(Context context,AttributeSet attrs){
        super();
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof TextView || super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        int offset = dependency.getTop()-child.getTop();
        LogUtil.e("offset ="+offset);
        ViewCompat.offsetTopAndBottom(child,offset);
        return true;
    }
}

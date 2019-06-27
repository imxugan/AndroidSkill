package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/27.
 */

public class FabBehavior extends FloatingActionButton.Behavior {
    private boolean visible = true;
    //构造函数必须要，否则会报错
    public FabBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild, View target,
                                       int nestedScrollAxes) {
        LogUtil.i("child=      "+child);
        // 当观察的View（RecyclerView）发生滑动的开始的时候回调的
        //nestedScrollAxes:滑动关联轴， 我们现在只关心垂直的滑动。
        return nestedScrollAxes==ViewCompat.SCROLL_AXIS_VERTICAL||super.onStartNestedScroll(coordinatorLayout, child, directTargetChild,
                target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        LogUtil.i("dyConsumed=      "+dyConsumed);
        if(dyConsumed>0  && visible){
            //向上滑动,隐藏FloatingActionButton
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            ViewCompat.animate(child)
                    .translationY(child.getHeight()+layoutParams.bottomMargin)
                    .setInterpolator(new AccelerateInterpolator(3))
                    .start();
            visible = false;
        }else if(dyConsumed<0){
            //向下滑动,显示FloatingActionButton
            if(!visible){
                ViewCompat.animate(child).translationY(0).setInterpolator(new DecelerateInterpolator(3)).start();
                visible = true;
            }
        }
    }
}

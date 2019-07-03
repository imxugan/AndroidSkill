package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xugan on 2019/7/3.
 */

public class SynBehavior extends CoordinatorLayout.Behavior<View> {
    /**
     * 用于xml解析layout_Behavior属性的构造方法,如果需要Behavior支持在xml中使用,则必须有此构造方法
     * @param context
     * @param attributeSet
     */
    public SynBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
//    /**
//     * 此方法会在LayoutParams实例化后调用,或者在调用了LayoutParams.setBehavior(behavior)时调用.
//     */
//    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams params) {
//    }
//
//    /**
//     * 当Behavior脱离LayoutParams时调用,例如调用了LayoutParams.setBehavior(null).
//     * View被从View Tree中移除时不会调用此方法.
//     */
//    public void onDetachedFromLayoutParams() {
//    }
//
//    /**
//     * 接收CoordinatorLayout的触摸拦截事件,按从上到下的层级顺序分发拦截事件,
//     * 如果返回true,会在CoordinatorLayout中的onTouchEvent中调用这个View的Behavior的onTouchEvent方法.
//     *
//     * 这里的拦截应当慎重,一旦有Behavior返回true,则会导致CoordinatorLayout的所有子View触摸事件无效.
//     */
//    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
//        return false;
//    }
//
//    /**
//     * 接收CoordinatorLayout的触摸事件,
//     * 事件分发按层从上到下分发,一旦有Behavior的onTouchEvent返回true,
//     * 则此Behavior所附属的View的下面所有的CoordinatorLayout子View的Behavior都收不到onTouchEvent回调.
//     */
//    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
//        return false;
//    }
//
//    /**
//     * 获得当前Behavior附属View空间之外的阴影颜色
//     */
//    @Override
//    public int getScrimColor(@NonNull CoordinatorLayout parent, @NonNull View child) {
//        return super.getScrimColor(parent, child);
//    }
//
//    /**
//     * 获得当前Behavior附属View空间之外的阴影透明度
//     */
//    @Override
//    public float getScrimOpacity(@NonNull CoordinatorLayout parent, @NonNull View child) {
//        return super.getScrimOpacity(parent, child);
//    }
//
//    /**
//     * 是否阻止此Behavior所附属View下层的View的交互
//     */
//    public boolean blocksInteractionBelow(CoordinatorLayout parent, View child) {
//        return getScrimOpacity(parent, child) > 0.f;
//    }
//
//    /**
//     * 用于判断是否为依赖的View,一般重写该方法来获取需要联动的View
//     */
//    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        return false;
//    }
//
//    /**
//     * 当依赖的View发生改变时回调此方法,用于监听依赖View的状态
//     */
//    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        return false;
//    }
//
//    /**
//     * 当依赖的View被移除时回调此方法,用于监听依赖View的状态
//     */
//    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
//    }
//
//    /**
//     * 代替CoordinatorLayout的默认测量子View的方法,
//     * 返回true使用Behavior的测量方法来测量当前Behavior所附属View,
//     * 返回flase则使用CoordinatorLayout的默认方式
//     */
//    public boolean onMeasureChild(CoordinatorLayout parent, View child,
//                                  int parentWidthMeasureSpec, int widthUsed,
//                                  int parentHeightMeasureSpec, int heightUsed) {
//        return false;
//    }
//
//    /**
//     * 代替CoordinatorLayout的默认布局子View的方法给该Behavior的附属View布局,
//     * 返回true则使用Behavior的布局方式来给Behavior所属View布局,
//     * 返回false则使用CoordinatorLayout的默认方式
//     */
//    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
//        return false;
//    }



    //指定Behavior关注的滑动方向
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes, int type) {

        return (axes == ViewCompat.SCROLL_AXIS_VERTICAL) || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    //用来监听滑动状态，对象消费滚动距离前回调
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                  @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int scrollY = target.getScrollY();
        child.setScaleY(scrollY);
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

//    @Override
//    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
//    }


    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        NestedScrollView nestedScrollView = (NestedScrollView) child;
        nestedScrollView.fling((int) velocityY);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}

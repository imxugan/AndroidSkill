package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xgxg on 2017/9/8.
 */

public class CustomContainer extends ViewGroup {
    public CustomContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //计算所有的childView的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        //记录如果是warp_content时，设置的宽和高
        int width = 0;
        int height = 0;
        int childCount = getChildCount();
        int childWidth = 0;
        int childHeight = 0;
        MarginLayoutParams childParams = null;
        //左边两个childView的高度
        int leftChildHeight = 0;
        //右边两个childView的高度，
        int rightChildHeight = 0;
        
        //上面的两个childView的宽度
        int topChildWidth = 0;
        //下面的两个childView的宽度
        int bottomChildWidth = 0;
        
        //根据childView计算出的宽和高，以及设置的margin计算容器的宽和高，主要
        //用于容器是wrap_content的情况
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            childParams = (MarginLayoutParams) childView.getLayoutParams();
            if(i == 0 || i == 1){
                topChildWidth += childWidth + childParams.leftMargin + childParams.rightMargin;
            }

            if(i == 2 || i == 3){
                bottomChildWidth += childWidth + childParams.leftMargin + childParams.rightMargin;
            }

            if(i == 0 || i == 2){
                leftChildHeight += childHeight + childParams.topMargin + childParams.bottomMargin;
            }

            if(i == 1 || i == 3){
                rightChildHeight += childHeight + childParams.topMargin + childParams.bottomMargin;
            }
        }

        width = Math.max(topChildWidth,bottomChildWidth);
        height = Math.max(leftChildHeight,rightChildHeight);

        //如果是wrap_cotent,设置为我们计算的值，否则直接使用父容器建议的大小
        setMeasuredDimension((widthSpecMode == MeasureSpec.AT_MOST)?width:widthSpecSize,
                (heightSpecMode == MeasureSpec.AT_MOST)?height:heightSpecSize);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childWidth = 0;
        int childHeight = 0;
        MarginLayoutParams childParams = null;
        //遍历所有childView，根据其宽高以及margin进行布局
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            childParams = (MarginLayoutParams) childView.getLayoutParams();

            int childLeft = 0;
            int childTop = 0;
            int childRight = 0;
            int childBottom = 0;
            switch (i){
                case 0:
                    childLeft = childParams.leftMargin;
                    childTop = childParams.topMargin;
                    childRight = childParams.leftMargin + childWidth + childParams.rightMargin;
                    childBottom = childParams.topMargin+ childHeight + childParams.bottomMargin;
                    break;
                case 1:
//                    childLeft = childParams.leftMargin + childParams.rightMargin + childWidth ;
                    childLeft = getWidth() - childParams.leftMargin - childWidth - childParams.rightMargin;
                    childTop = childParams.topMargin;
                    childRight = getWidth() - childParams.rightMargin;
//                    childBottom = getHeight() - childParams.bottomMargin;
                    childBottom = childParams.topMargin+ childHeight + childParams.bottomMargin;
                    break;
                case 2:
                    childLeft = childParams.leftMargin;
                    childTop = getHeight() - childParams.topMargin - childHeight - childParams.bottomMargin;
                    childRight = childParams.leftMargin + childWidth + childParams.rightMargin;
                    childBottom = getHeight() - childParams.bottomMargin;
                    break;
                case 3:
                    childLeft = getWidth() - childParams.leftMargin - childWidth - childParams.rightMargin;
                    childTop = getHeight() - childParams.bottomMargin - childHeight - childParams.topMargin;
                    childRight = getWidth() - childParams.rightMargin;
                    childBottom = getHeight() - childParams.bottomMargin;
                    break;
            }
            childView.layout(childLeft,childTop,childRight,childBottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(),attrs);
    }
}

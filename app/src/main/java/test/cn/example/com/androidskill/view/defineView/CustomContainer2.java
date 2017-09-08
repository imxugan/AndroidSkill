package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/8.
 */

public class CustomContainer2 extends ViewGroup {
    public CustomContainer2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int childCount = getChildCount();
        int childTopTotalWidth = 0;
        int childBottomTotalWidth = 0;
        int childLeftTotalHeight = 0;
        int childRightTotalHeight = 0;
        View child = null;
        MarginLayoutParams childParams = null;

        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            childParams = (MarginLayoutParams) child.getLayoutParams();
            if(i == 0 || i == 1){
                childTopTotalWidth += childParams.leftMargin + childParams.rightMargin + child.getMeasuredWidth();
            }

            if(i == 2 || i == 3){
                childBottomTotalWidth += childParams.leftMargin + childParams.rightMargin + child.getMeasuredWidth();
            }

            if(i == 0 || i == 2){
                childLeftTotalHeight += childParams.topMargin + child.getMeasuredHeight() + childParams.bottomMargin;
            }

            if(i == 1 || i == 3){
                childRightTotalHeight += childParams.topMargin + child.getMeasuredHeight() + childParams.bottomMargin;
            }
        }

        int childDiseredWidth = Math.max(childTopTotalWidth,childBottomTotalWidth);
        int childDiseredHeight = Math.max(childLeftTotalHeight,childRightTotalHeight);

        setMeasuredDimension((MeasureSpec.AT_MOST == widthSpecMode)?childDiseredWidth:widthSpecSize,
                (MeasureSpec.AT_MOST == heightSpecMode)?childDiseredHeight:heightSpecSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        View child = null;
        MarginLayoutParams childParams = null;
        int childLeft = 0;
        int childTop = 0;
        int childRight = 0;
        int childBottom = 0;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            childParams = (MarginLayoutParams) child.getLayoutParams();
            if(i == 0){
                childLeft = childParams.leftMargin;
                childTop = childParams.topMargin;
                childRight = childParams.leftMargin + child.getMeasuredWidth();
                childBottom = childParams.topMargin + child.getMeasuredHeight();
            }else if(i == 1){
                LogUtil.i("child.getMeasuredWidth()="+child.getMeasuredWidth()+"---child.getWidth()="+child.getWidth());
                LogUtil.i("child.getMeasuredHeight()="+child.getMeasuredHeight()+"---child.getHeight()="+child.getHeight());
//                childLeft = getWidth() - child.getWidth() - childParams.rightMargin;
                childLeft = getWidth() - child.getMeasuredWidth() - childParams.rightMargin;
                childTop = childParams.topMargin;
                childRight = getWidth() - childParams.rightMargin;
                childBottom = childParams.topMargin + child.getMeasuredHeight();
            }else if(i == 2){
                childLeft = childParams.leftMargin;
                childTop = getHeight() - childParams.bottomMargin - child.getMeasuredHeight();
                childRight = childParams.leftMargin + child.getMeasuredWidth();
                childBottom = getHeight() - childParams.bottomMargin;
            }else if(i == 3){
                childLeft = getWidth() - childParams.rightMargin - child.getMeasuredWidth();
                childTop = getHeight() - childParams.bottomMargin - child.getMeasuredHeight();
                childRight = getWidth() - childParams.rightMargin;
                childBottom = getHeight() - childParams.bottomMargin;
            }
            child.layout(childLeft,childTop,childRight,childBottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(),attrs);
    }
}

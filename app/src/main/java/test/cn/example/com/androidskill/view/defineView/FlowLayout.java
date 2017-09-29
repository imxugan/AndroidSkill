package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xgxg on 2017/9/29.
 */

public class FlowLayout extends ViewGroup {
    private int mChildCount;
    private MarginLayoutParams lp;

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = 0;
        int heightSize = 0;
        if(widthSpecMode == MeasureSpec.AT_MOST){
            widthSize = 0;
        }else {
            widthSize = widthSpecSize;
        }
        if(heightSpecMode == MeasureSpec.AT_MOST){
            heightSize = 0;
        }else {
            heightSize = heightSpecSize;
        }
        mChildCount = getChildCount();
//        if(0 == mChildCount){
//            //当如果不包含任何的子控件，则将当前控件的宽和高设置为0
//            setMeasuredDimension(widthSize,heightSize);
//            return;
//        }
        View child = null;
        int lineWidth = 0;
        int maxLineWidth = 0;//每一行中的最宽的一行
        int lastLineHeight = 0;
        int maxLineHeight = 0;//每一行中的最高的一子控件的高
        int lineNum = 1;
        for (int i = 0; i < mChildCount; i++) {
            child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = layoutParams.leftMargin + layoutParams.rightMargin + child.getMeasuredWidth();
            int childHeight = layoutParams.topMargin + layoutParams.bottomMargin + child.getMeasuredHeight();
            if((lineWidth + childWidth) > widthSpecSize){
                lineNum++ ;
                maxLineWidth = lineWidth;
                lineWidth = childWidth;//由于是新的一行， 将当前child的宽度赋值给lineWidth

                heightSize += maxLineHeight;
                lastLineHeight = childHeight;//由于是新的一行，将当前child的高度赋值lastLineHeight
            }else{
                lineWidth +=childWidth;

                maxLineHeight = Math.max(lastLineHeight,childHeight);
                lastLineHeight = childHeight;
            }
        }
        //如果是最后一行,或者只有一行，其实只有一行的情况，也可以将这一行看作是最后一行,怎么处理？？？？？
       if(1 == lineNum){
           //只有一行
           heightSize = maxLineHeight;
       }else {
           heightSize += lastLineHeight;
       }

        //如果最后一个child出现在一个新行，则需要将这个child的宽度和前面每一行的宽度的最大值做比较，取
        //其中的较大宽度作为当前控件的最终宽度
        maxLineWidth = Math.max(maxLineWidth,lineWidth);

        //每一行中的最宽的一行作为当前view的宽，两每一行中最高的child的高相加，作为当前view的高
        setMeasuredDimension(maxLineWidth,heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = null;
        int childLeft = 0;
        int childTop = 0;
        int childRight = 0;
        int childBottom = 0;
        for (int i = 0; i < mChildCount; i++) {
            child = getChildAt(i);
            lp = (MarginLayoutParams) child.getLayoutParams();
            if(i == 0){
                childLeft = lp.leftMargin;
                childTop = lp.topMargin;
            }
            childRight = childLeft + child.getMeasuredWidth();
            childBottom = childTop + child.getMeasuredHeight();
            if(childRight <= getMeasuredWidth()){
                child.layout(childLeft,childTop,childRight,childBottom);
            }else {
                //新的一行
                childLeft = lp.leftMargin;
                childTop += child.getMeasuredHeight();
            }

            childLeft = childLeft + child.getMeasuredWidth() + lp.rightMargin;
            childTop = childTop + child.getMeasuredHeight() + lp.bottomMargin;
        }
    }
}

package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XG on 2017/10/22.
 */

public class FlowLayout2 extends ViewGroup {
    //存储所有的view,按行记录
    private List<List<View>> mAllViews = new ArrayList< List<View>>();
    //记录每行的最大高度
    private List<Integer> mLineMaxHeightList = new ArrayList<Integer>();

    public FlowLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        View child = null;
        int lineWidth = 0;
        int lineHeight = 0;
        int childWidth = 0;
        int childHeight = 0;
        int width = 0;//当layoutparams为wrap_content时，需要自己计算宽度和高度
        int height = 0;
        MarginLayoutParams layoutParams;
        int childCount = getChildCount();
        for (int i= 0;i<childCount;i++) {
            child = getChildAt(i);
            layoutParams = (MarginLayoutParams) child.getLayoutParams();
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            childWidth = child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            childHeight = child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            if((lineWidth+childWidth)>widthSpecSize){
                width = Math.max(lineWidth,childWidth);
                height+=lineHeight;
                lineWidth = childWidth;//从新开启一行,对新的行宽进行重新赋值
                lineHeight = childHeight;//从新开启一行，对新的行的高进行重新赋值
            }else {
                //将子控件的宽度累加
                lineWidth+=childWidth;
                //将当前行中最高的子view的高度作为当前行的高度
                lineHeight = Math.max(lineHeight,childHeight);
            }
            //最后一个
            if(i==(childCount-1)){
                width = Math.max(lineWidth,childWidth);
                height += childHeight;
            }
            if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
                setMeasuredDimension(width,height);
            }else if(widthSpecMode == MeasureSpec.AT_MOST){
                setMeasuredDimension(width,heightSpecSize);
            }else if(heightSpecMode == MeasureSpec.AT_MOST){
                setMeasuredDimension(widthSpecSize,height);
            }else {
                setMeasuredDimension(widthSpecSize,heightSpecSize);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineMaxHeightList.clear();
        int width = getWidth();
        int childCount = getChildCount();
        View child = null;
        int childWidth = 0;
        int childHeight = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<>();
        MarginLayoutParams lp = null;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            lp = (MarginLayoutParams) child.getLayoutParams();
            if(childWidth+lp.leftMargin+lp.rightMargin+lineWidth>width){
                //需要换行
                mLineMaxHeightList.add(lineHeight);
                mAllViews.add(lineViews);
                //将lineWidth重置
                lineWidth = 0;
                lineViews = new ArrayList<>();
            }
            lineWidth += (childWidth+lp.leftMargin+lp.rightMargin);
            lineHeight = Math.max(childHeight+lp.topMargin+lp.bottomMargin,lineHeight);
            lineViews.add(child);
        }
        //记录最后一行
        mLineMaxHeightList.add(lineHeight);
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineMaxHeightList.get(i);
            for (int j = 0; j <lineViews.size(); j++) {
                child = lineViews.get(j);
                if(View.GONE == child.getVisibility()){
                    continue;
                }
                lp = (MarginLayoutParams) child.getLayoutParams();
                int childLeft = left +lp.leftMargin;
                int childRight = childLeft + child.getMeasuredWidth() ;
                int childTop = top + lp.topMargin;
                int childBottom = childTop +child.getMeasuredHeight();
                child.layout(childLeft,childTop,childRight,childBottom);
                left += child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            }
            left = 0;
            top+=(lineHeight);
        }
    }
}

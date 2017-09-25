package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/25.
 */

public class ArcMenuViewGroup extends ViewGroup{

    private int mDefaultCount = 4;
    private int mDefaultRadius = DensityUtil.dp2Px(200);
    private int mDefaultDegree = 90;
    private double mChildBetweenDegree;
    private boolean mIsOpen = true;

    public ArcMenuViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcMenuViewGroup);
        int attr = 0;
        for (int i = 0; i < a.getIndexCount(); i++) {
            attr = a.getIndex(i);
            switch (attr){
                case R.styleable.ArcMenuViewGroup_arcMenuCount:
                    mDefaultCount = a.getInteger(attr, mDefaultCount);
                    break;
                case R.styleable.ArcMenuViewGroup_arcMenuRadius:
                    mDefaultRadius = (int) a.getDimension(attr, this.mDefaultRadius);
                    break;
                case R.styleable.ArcMenuViewGroup_degree:
                    mDefaultDegree = (int) a.getDimension(attr, this.mDefaultDegree);
                    break;
            }
        }
        a.recycle();
        mChildBetweenDegree = Math.PI/2/(mDefaultCount-2);
        LogUtil.i("mChildBetweenDegree="+mChildBetweenDegree);
//        LogUtil.i("mDefaultCount="+mDefaultCount);
        LogUtil.i("Math.sin(mChildBetweenDegree)="+Math.sin(mChildBetweenDegree));
        LogUtil.i("Math.sin(mChildBetweenDegree*2)="+Math.sin(mChildBetweenDegree*2));
//        LogUtil.i("Math.sin(30)="+Math.sin(30));
//        LogUtil.i("Math.sin(Math.PI/2/3)="+Math.sin(Math.PI/2/3));
//        LogUtil.i("Math.sin(Math.toDegrees(30))="+Math.sin(Math.toDegrees(30)));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        LogUtil.i("childCount="+childCount);
        View child = null;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int size = (widthSpecSize>heightSpecSize)?heightSpecSize:widthSpecSize;
        if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode){
            size = mDefaultRadius;
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            size = (mDefaultRadius>size)?size:mDefaultRadius;
        }else if(MeasureSpec.AT_MOST == heightSpecMode){
            size = (mDefaultRadius>size)?size:mDefaultRadius;
        }
        setMeasuredDimension(size,size);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        LogUtil.i("childCount="+childCount);
        View child = null;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            if(i == 0){
                //菜单中心点的child
                child.layout(l,t,l+child.getMeasuredWidth(),t+child.getMeasuredHeight());
                child.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(mIsOpen){

                        }else {

                        }
                        mIsOpen = !mIsOpen;
                    }
                });
            }else if(i == 1){//紧挨着x轴的child
                child.layout(l+getMeasuredWidth()-child.getMeasuredWidth(),t,l+getMeasuredWidth(),t+child.getMeasuredHeight());
            }else if(i == childCount -1){//紧挨着y轴的child
                child.layout(l,mDefaultRadius-child.getMeasuredHeight(),
                        child.getMeasuredWidth(),mDefaultRadius);
            }else {
                LogUtil.i("i="+i);
                child.layout(l+(int)(mDefaultRadius *Math.cos(mChildBetweenDegree*(i-1)))-child.getMeasuredWidth()/2,
                        t+(int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1)))-child.getMeasuredHeight()/2,
                        l+(int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1)))+child.getMeasuredWidth()/2,
                        t+(int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1)))+child.getMeasuredHeight()/2);
            }
        }
    }

//    public interface OnItemClickListener{
//        void onClick(View v);
//    }
}

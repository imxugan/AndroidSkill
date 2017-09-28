package test.cn.example.com.androidskill.view.defineView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

public class ArcMenuViewGroup extends ViewGroup implements View.OnClickListener{

    private int mDefaultCount = 4;
    private int mDefaultRadius = DensityUtil.dp2Px(200);
    private int mDefaultDegree = 90;
    private double mChildBetweenDegree;
    private boolean mIsOpen = true;
    private View mMenu;
    private OnItemClickListener mOnItemClickListener;

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
            child.setOnClickListener(this);
            if(i == 0){
                //菜单中心点的child
                mMenu = getChildAt(i);
                mMenu.layout(l,t,l+child.getMeasuredWidth(),t+child.getMeasuredHeight());
            }else if(i == 1){//紧挨着x轴的child
                LogUtil.i("i==1 时  child.getId()==="+child.getId());
                child.layout(l+mDefaultRadius-child.getMeasuredWidth()/2,t,
                        l+mDefaultRadius+child.getMeasuredWidth()/2,t+child.getMeasuredHeight());
            }  else if(i == childCount -1){//紧挨着y轴的child
                child.layout(l,t+mDefaultRadius-child.getMeasuredHeight()/2,
                        l+child.getMeasuredWidth(),t+mDefaultRadius+child.getMeasuredHeight()/2);
            }else {
                LogUtil.i("i="+i);
                child.layout(l+(int)(mDefaultRadius *Math.cos(mChildBetweenDegree*(i-1)))-child.getMeasuredWidth()/2,
                        t+(int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1)))-child.getMeasuredHeight()/2,
                        l+(int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1)))+child.getMeasuredWidth()/2,
                        t+(int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1)))+child.getMeasuredHeight()/2);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mMenu){
            LogUtil.i("mMenu is click");
            if(mIsOpen){
                playRotationAnimation(mMenu,180,0);
                close();
            }else {
                //中心控制菜单
                playRotationAnimation(mMenu,0,180);
                open();
            }
            mIsOpen = !mIsOpen;
        }else {
            mOnItemClickListener.onClick(v);
        }
    }

    private void open(){
        int childCount = getChildCount();
        View child = null;
//        LogUtil.i("mMenu.getLeft()="+mMenu.getLeft());
//        LogUtil.i("mMenu.getTop()="+mMenu.getTop());
//        LogUtil.i("(mMenu.getMeasuredWidth() / 2)="+(mMenu.getMeasuredWidth() / 2));
        float destationX = 0;
        float destationY = 0;
        for (int i = 0; i < childCount-1; i++) {
            child = getChildAt(i+1);
            AnimatorSet set = new AnimatorSet();
            if(i== 0){
                //靠近x轴的卫星
                destationX = child.getTranslationX()+mDefaultRadius - child.getMeasuredWidth()/2;
                destationY = child.getTranslationY();
            }else if(i == (childCount -2)){
                //靠近y轴的卫星
                destationX = child.getTranslationX();
                destationY = child.getTranslationY()+mDefaultRadius - child.getMeasuredHeight()/2;
            }else {
                destationX = child.getTranslationX()+(float) (mDefaultRadius * Math.cos(mChildBetweenDegree * i))-child.getMeasuredWidth()/2;
                destationY = child.getTranslationY()+(float) (mDefaultRadius * Math.sin(mChildBetweenDegree * i))-child.getMeasuredHeight()/2;
            }
            ObjectAnimator translateX = ObjectAnimator.ofFloat(child, "translationX", child.getTranslationX(), destationX);
            ObjectAnimator translateY = ObjectAnimator.ofFloat(child, "translationY", child.getTranslationY(), destationY);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(child,"alpha",0,1);
            set.playTogether(translateX,translateY,alpha);
            set.setDuration(1000).start();
            final View finalChild = child;
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    finalChild.setClickable(true);
                }
            });
        }
    }

    private void playRotationAnimation(View child,int fromDegree,int toDegree) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(child,"rotation",fromDegree,toDegree);
        rotation.setDuration(1000);
        rotation.start();
    }

    private void close(){
        int childCount = getChildCount();
        View child = null;
//        LogUtil.i("mMenu.getLeft()="+mMenu.getLeft());
//        LogUtil.i("mMenu.getTop()="+mMenu.getTop());
//        LogUtil.i("(mMenu.getMeasuredWidth() / 2)="+(mMenu.getMeasuredWidth() / 2));
        float destationX = 0;
        float destationY = 0;
        for (int i = 0; i < childCount-1; i++) {
            child = getChildAt(i+1);
            AnimatorSet set = new AnimatorSet();
            if(i == 0){
                //靠近x轴的卫星
                destationX = -(child.getTranslationX()+mDefaultRadius - child.getMeasuredWidth()/2);
                destationY = child.getTranslationY();
            }else if(i == (childCount-2)){
                //靠近y轴的卫星
                destationX = child.getTranslationX();
                destationY = -(child.getTranslationY()+mDefaultRadius - child.getMeasuredHeight()/2);

            }else {
                destationX = -(float)(child.getTranslationX()+mDefaultRadius * Math.cos(mChildBetweenDegree * i)-child.getMeasuredWidth()/2);
                destationY = -(float)(child.getTranslationY()+mDefaultRadius * Math.sin(mChildBetweenDegree * i)-child.getMeasuredHeight()/2);
            }
            ObjectAnimator translateX = ObjectAnimator.ofFloat(child, "translationX", child.getTranslationX(), destationX);
            ObjectAnimator translateY = ObjectAnimator.ofFloat(child, "translationY", child.getTranslationY(), destationY);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(child,"alpha",1,0);
            set.play(translateX).with(translateY).with(alpha);
            set.setDuration(1000);
            set.start();
            final View finalChild = child;
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    finalChild.setClickable(false);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onClick(View v);
    }




}

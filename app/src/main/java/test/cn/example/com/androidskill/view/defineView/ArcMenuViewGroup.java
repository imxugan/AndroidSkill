package test.cn.example.com.androidskill.view.defineView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/25.
 */

public class ArcMenuViewGroup extends ViewGroup implements View.OnClickListener{

    private final int mScreenWidth;
    private final int mScreenHeight;
    private int mDefaultCount = 4;
    private int mDefaultRadius = DensityUtil.dp2Px(200);
    private int mDefaultDegree = 90;
    private double mChildBetweenDegree;
    private boolean mIsOpen = true;
    private View mMenu;
    private OnItemClickListener mOnItemClickListener;
    private int mDefaultPosition;
    private final int LEFT_TOP = 0;
    private final int RIGHT_TOP = 1;
    private final int LEFT_BOTTTOM = 2;
    private final int RIGHT_BOTTOM = 3;

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
                case R.styleable.ArcMenuViewGroup_position:
                    mDefaultPosition = a.getInteger(attr, mDefaultPosition);
                    break;
            }
        }
        a.recycle();
        if(mDefaultCount>3){
            mChildBetweenDegree = Math.PI/2/(mDefaultCount-2);
        }

        LogUtil.i("mChildBetweenDegree="+mChildBetweenDegree);
//        LogUtil.i("mDefaultCount="+mDefaultCount);
        LogUtil.i("Math.sin(mChildBetweenDegree)="+Math.sin(mChildBetweenDegree));
        LogUtil.i("Math.sin(mChildBetweenDegree*2)="+Math.sin(mChildBetweenDegree*2));
//        LogUtil.i("Math.sin(30)="+Math.sin(30));
//        LogUtil.i("Math.sin(Math.PI/2/3)="+Math.sin(Math.PI/2/3));
//        LogUtil.i("Math.sin(Math.toDegrees(30))="+Math.sin(Math.toDegrees(30)));

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        LogUtil.i("mScreenWidth="+mScreenWidth);
        LogUtil.i("mScreenHeight="+mScreenHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        LogUtil.i("childCount="+childCount);
        View child = null;
        int childWidth = 0;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            childWidth = child.getMeasuredWidth();
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
        }else {
            //如果是固定大小,则要将半径从新赋值,如果不进行重新赋值，
            // 将有可能出现半径大于当前控件的宽或者高的情况,这样展示出来的控件，就会不好看了
            mDefaultRadius = size;
        }
        size += childWidth;
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
                if(mDefaultPosition == LEFT_TOP){
                    LogUtil.i("LEFT_TOP");
                    LogUtil.i("l="+l);
                    LogUtil.i("t="+t);
                    LogUtil.i("r="+r);
                    LogUtil.i("b="+b);
                    mMenu.layout(0,0,child.getMeasuredWidth(),child.getMeasuredHeight());
                }else if(mDefaultPosition == RIGHT_BOTTOM){
                    LogUtil.e("RIGHT_BOTTOM");
                    LogUtil.i("l="+l);
                    LogUtil.i("t="+t);
                    LogUtil.i("r="+r);
                    LogUtil.i("b="+b);
                    mMenu.layout(getMeasuredWidth()-child.getMeasuredWidth(),getMeasuredHeight() - mMenu.getMeasuredHeight(),
                            getMeasuredWidth(),getMeasuredHeight());
                }
            }else {
                if(mDefaultPosition == LEFT_TOP){
                    child.layout((int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1))),
                            (int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1))),
                            (int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1))+child.getMeasuredWidth()),
                            (int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1))+child.getMeasuredHeight()));
                }else if(mDefaultPosition == RIGHT_BOTTOM){
                    if(i == 1){
                        LogUtil.i("child.getId()="+child.getId()+"---qq3?");//2131558502
                        int left = getMeasuredWidth()-(int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1)))-child.getMeasuredWidth();
                        int top = getMeasuredHeight()-(int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1)))-child.getMeasuredHeight();
                        int right = getMeasuredWidth()-(int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1)));
                        int bottom = getMeasuredHeight()-(int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1)));
                        LogUtil.i("getMeasuredHeight()="+getMeasuredHeight());
                        LogUtil.i("child.getMeasuredHeight()="+child.getMeasuredHeight());
                        LogUtil.i("(int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1))="+(int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1))));
                        LogUtil.i("left="+left);
                        LogUtil.i("top="+top);
                        LogUtil.i("right="+right);
                        LogUtil.i("bottom="+bottom);

                    }
//                    LogUtil.e("RIGHT_BOTTOM---i="+i+"Math.sin(mChildBetweenDegree*(i-1))="+Math.sin(mChildBetweenDegree*(i-1)));
                    child.layout(getMeasuredWidth()-(int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1)))-child.getMeasuredWidth(),
                            getMeasuredHeight()-(int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1)))-child.getMeasuredHeight(),
                            getMeasuredWidth()-(int)(mDefaultRadius * Math.sin(mChildBetweenDegree*(i-1))),
                            getMeasuredHeight()-(int)(mDefaultRadius * Math.cos(mChildBetweenDegree*(i-1))));
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mMenu){
            LogUtil.i("mMenu is click");
            mMenu.setClickable(false);
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
            if(mDefaultPosition == LEFT_TOP){
                //            if(i== 0){
//                //靠近x轴的卫星
//                destationX = child.getTranslationX()+mDefaultRadius;
//                destationY = child.getTranslationY();
//            }else if(i == (childCount -2)){
//                //靠近y轴的卫星
//                destationX = child.getTranslationX();
//                destationY = child.getTranslationY()+mDefaultRadius;
//            }else {
//                destationX = child.getTranslationX()+(float) (mDefaultRadius * Math.cos(mChildBetweenDegree * i));
//                destationY = child.getTranslationY()+(float) (mDefaultRadius * Math.sin(mChildBetweenDegree * i));
//            }
                destationX = child.getTranslationX()+(float) (mDefaultRadius * Math.cos(mChildBetweenDegree * i));
                destationY = child.getTranslationY()+(float) (mDefaultRadius * Math.sin(mChildBetweenDegree * i));
            }else if(mDefaultPosition == RIGHT_BOTTOM){
                if(i == 0){
                    //由于执行了close动画，第一个小卫星已经从靠近Y轴的位置向下移动到了最右角，
                    // 所以这个时候获取到的child.getTranslationY()的值，就是close动画执行完后
                    //这个小卫星Y轴方向的偏移量，如果是向下移动，则这个偏移量是正值，向上移动这个偏移量的值是负值
                    //同理，如果是X轴方向的便宜量，向右移动，则这个偏移量的值是正值，向左移动，则这个偏移量的值是负值
                    LogUtil.i("RIGHT_BOTTOM---open---i="+i);
                    LogUtil.i("child.getTranslationX()="+child.getTranslationX());
                    LogUtil.i("child.getTranslationY()="+child.getTranslationY());
                }

                destationX = child.getTranslationX()-(float) (mDefaultRadius * Math.sin(mChildBetweenDegree * i));
                destationY = child.getTranslationY()-(float) (mDefaultRadius * Math.cos(mChildBetweenDegree * i));
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
                    mMenu.setClickable(true);
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
            if(mDefaultPosition == LEFT_TOP){
                //            if(i == 0){
//                //靠近x轴的卫星
//                destationX = -(child.getTranslationX()+mDefaultRadius);
//                destationY = child.getTranslationY();
//            }else if(i == (childCount-2)){
//                //靠近y轴的卫星
//                destationX = child.getTranslationX();
//                destationY = -(child.getTranslationY()+mDefaultRadius);
//
//            }else {
//                destationX = -(float)(child.getTranslationX()+mDefaultRadius * Math.cos(mChildBetweenDegree * i));
//                destationY = -(float)(child.getTranslationY()+mDefaultRadius * Math.sin(mChildBetweenDegree * i));
//            }
                destationX = -(float)(child.getTranslationX()+mDefaultRadius * Math.cos(mChildBetweenDegree * i));
                destationY = -(float)(child.getTranslationY()+mDefaultRadius * Math.sin(mChildBetweenDegree * i));
            }else if(mDefaultPosition == RIGHT_BOTTOM){
                if(i == 0){
                    LogUtil.i("RIGHT_BOTTOM---close---i="+i);
                    LogUtil.i("child.getTranslationX()="+child.getTranslationX());
                    LogUtil.i("child.getTranslationY()="+child.getTranslationY());
                }
                destationX = (float)(child.getTranslationX()+mDefaultRadius * Math.sin(mChildBetweenDegree * i));
                destationY = (float)(child.getTranslationY()+mDefaultRadius * Math.cos(mChildBetweenDegree * i));
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
                    mMenu.setClickable(true);
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

package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/17.
 */

public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //第一步，获取自身测量的宽和高
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        LogUtil.e("measuredWidth= "+measuredWidth+"       measuredHeight= "+measuredHeight);
        //由于是正方形的ImageView，所以，按照自己的算法，将自定义view的宽和高设置的相同
//        SqureImageView类中，如果注销掉以下代码
//        if(measuredWidth>measuredHeight){
//            measuredHeight = measuredWidth;
//        }else {
//            measuredWidth = measuredHeight;
//        }
//        则SqureImageView则不会等比的放大或缩小，而是在屏幕中移动，很奇葩？？？
//        Answer:在SquareImageView的background属性设置一个颜色值，就能理解这种现象了，
//        本质是，ImageView的src的内容并不是ImageView的实际宽高，
//        因为android:scaleType=“center” 保持原图的大小，显示在ImageView的中心。
//        当原图的size大于ImageView的size时，多出来的部分被截掉。
//        这时，imageview中的内容是原图大小，当动态改变Imageview的尺寸时，由于图片
//        默认居于Imageview的中心，为了适应imageview的宽度变大后，imageview的中心
//        像右侧移动，则图片也只能跟着向右侧移动，导致看到的效果是，改变imageview
//        的宽度时，图片在屏幕中移动的"假像"
        if(measuredWidth>measuredHeight){
            measuredHeight = measuredWidth;
        }else {
            measuredWidth = measuredHeight;
        }
//        LogUtil.i("measuredWidth= "+measuredWidth+"       measuredHeight= "+measuredHeight);
        //保存重新计算后的宽和高
        setMeasuredDimension(measuredWidth,measuredHeight);
    }
}

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

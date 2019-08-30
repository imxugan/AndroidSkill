package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import test.cn.example.com.util.LogUtil;

public class MyLinearLayout extends LinearLayout {

    private int specMode_width;
    private int specMode_height;

    public MyLinearLayout(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        specMode_width = MeasureSpec.getMode(widthMeasureSpec);
        int specSize_width = MeasureSpec.getSize(widthMeasureSpec);
        specMode_height = MeasureSpec.getMode(heightMeasureSpec);
        int specSize_height = MeasureSpec.getSize(heightMeasureSpec);
        LogUtil.i(specMode_width +"              "+specSize_width);
        LogUtil.i(specMode_height +"             "+specSize_height);
        LogUtil.i("MeasureSpec.AT_MOST             "+MeasureSpec.AT_MOST);
        LogUtil.i("MeasureSpec.EXACTLY             "+MeasureSpec.EXACTLY);
        LogUtil.i("MeasureSpec.UNSPECIFIED             "+MeasureSpec.UNSPECIFIED);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        LogUtil.i("l=   "+l+"   t=    "+t+"   r="+r+"     b="+b);
    }

    public int getSpecMode_width() {
        return specMode_width;
    }

    public int getSpecMode_height(){
        return specMode_height;
    }

}

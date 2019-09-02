package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.graphics.Canvas;
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
        LogUtil.i("onMeasure    getVisibility()，  "+getVisibility());
        //public static final int VISIBLE = 0x00000000;
        //public static final int INVISIBLE = 0x00000004;
        //public static final int GONE = 0x00000008;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int opacity = getBackground().getOpacity();
        LogUtil.i("l=   "+l+"   t=    "+t+"   r="+r+"     b="+b);
        LogUtil.i("onLayout   getVisibility()，  "+getVisibility()+"         opacity=    "+opacity);
        setWillNotDraw(true);//如果设置了backgroud,这里即使设置了setWillNotDraw为true也没用，draw方法还是会执行

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //只要给MyLinearLayout设置背景，则draw方法就会执行
        LogUtil.i("draw 方法执行了，  getVisibility()， "+getVisibility());
    }

    public int getSpecMode_width() {
        return specMode_width;
    }

    public int getSpecMode_height(){
        return specMode_height;
    }

}

package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class BlurMaskFilterView extends View {

    private final Bitmap bitmap;
    private final Paint mPaint;
    private BlurMaskFilter.Blur blur = null;

    public BlurMaskFilterView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BlurMaskFilterView);
        int blurMaskType = typedArray.getInt(R.styleable.BlurMaskFilterView_blurmask, 0);
        switch (blurMaskType){
            case 0:
                blur = BlurMaskFilter.Blur.INNER;
                break;
            case 1:
                blur = BlurMaskFilter.Blur.NORMAL;
                break;
            case 2:
                blur = BlurMaskFilter.Blur.OUTER;
                break;
            case 3:
                blur = BlurMaskFilter.Blur.SOLID;
                break;
        }
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.marong);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(bitmap.getWidth()+"       "+bitmap.getHeight());
//        setMeasuredDimension(bitmap.getWidth(),bitmap.getHeight());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setMaskFilter(new BlurMaskFilter(50,blur));
        canvas.drawBitmap(bitmap,30,10,mPaint);

    }
}

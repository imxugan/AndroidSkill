package test.cn.example.com.androidskill.ui.compact;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class MyGradientView2 extends View {

    private final Bitmap bitmap;
    private final int min;
    private final Paint mPaint;
    private final int ScaleX = 0;
    private final int ScaleY = 1;
    private float scaleFloat;

    public MyGradientView2(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyGradientView);
        int resourceId = typedArray.getResourceId(R.styleable.MyGradientView_src, R.drawable.avatar_contact);
        int scale = typedArray.getInt(R.styleable.MyGradientView_scale, ScaleX);
        bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        LogUtil.i("width=  "+width+"           height="+height+"        "+scale);
        if(scale == ScaleX){
            if(width>height){
                scaleFloat = width * 1.0f/height;
            }else {
                //如果宽小于高，则将宽作为view直径,不进行缩放
                scaleFloat = 1.0f;
            }
            min = width;
        }else {
            min = height;
        }
        LogUtil.i("scaleFloat=   "+scaleFloat);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //第一步，获取自身测量的宽和高
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
//        LogUtil.e("measuredWidth= "+measuredWidth+"       measuredHeight= "+measuredHeight);
        //保存重新计算后的宽和高
        setMeasuredDimension(min,min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtil.i("getWidth()="+getWidth()+"           getHeight()="+getHeight());

        //Shader.TileMode.CLAMP    拉伸最后一个像素去铺满剩下的地方
        //Shader.TileMode.MIRROR    通过镜像翻转铺满剩下的地方
        //Shader.TileMode.REPEAT    重复图片铺满整个画面

        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.MIRROR);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(bitmapShader);
        shapeDrawable.setBounds(0,0,getWidth(),getWidth());
        shapeDrawable.draw(canvas);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(min/2,min/2,min/2,mPaint);


    }
}

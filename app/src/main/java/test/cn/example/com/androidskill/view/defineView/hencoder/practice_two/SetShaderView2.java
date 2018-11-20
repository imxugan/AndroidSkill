package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * 自定义控件三部曲之绘图篇（十八）——BitmapShader与望远镜效果
 *   https://blog.csdn.net/harvic880925/article/details/52039081
 * Created by xugan on 2018/11/19.
 */

public class SetShaderView2 extends View {
    Paint mPaint;
    private Bitmap bitmap;
    /**Bitmap的宽高*/
    private int bitmapWidth, bitmapHeight;

    public SetShaderView2(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
    }

    public SetShaderView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SetShaderView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Shader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
//        canvas.translate(getWidth()/2,getHeight()/2);
//        canvas.drawCircle(0,0,getWidth()/2, mPaint);//圆形渲染图
        canvas.drawRect(100,0,getWidth(),getHeight(), mPaint);//矩形渲染图
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2, mPaint);//圆形渲染图
        canvas.drawCircle(bitmapWidth/2,bitmapHeight/2,bitmapWidth/2, mPaint);//圆形渲染图
        //自己的理解：Shader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //这行代码的意思是，将这个bitmap，按照先竖直方向按照边沿颜色进行填充，再再水平方向按照边沿颜色进行填充,
        //由于这个bitmap的四周都是不同的颜色，所以，按照Shader.TileMode进行填充，
        //当我们在将bitmapShader设置到mPaint后，如果 canvas.drawRect(0,0,getWidth(),getHeight(), mPaint);
        //画矩形，则会将整个bitmapShader画出来，后续，canvas.drawRect(100,0,getWidth(),getHeight(), mPaint);//矩形渲染图
        //只是在这个整个bitmapShader画出来的部分中，截取react部分区域，然后绘制出来，多余的部分舍弃，
        //canvas.drawCircle(bitmapWidth/2,bitmapHeight/2,bitmapWidth/2, mPaint);的原理也是一样的，也是
        //在整个bitmapShader中，截取circle大小的尺寸，然后绘制出来，相当于，在一张已经存在的封面，自己拿圆规
        //在封面上的任意地方画一个圆后，将圆以外的部分去掉，剩下的这个圆就是canvas.drawCircle(bitmapWidth/2,bitmapHeight/2,bitmapWidth/2, mPaint);
        //绘制出来的图形
    }
}

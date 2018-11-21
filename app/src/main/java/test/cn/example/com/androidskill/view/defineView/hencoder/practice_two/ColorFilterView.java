package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.IntEvaluatorActivity;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/20.
 */

public class ColorFilterView extends View {
    Paint mPaint;
    private Bitmap bitmap;
    private ColorFilter colorFilter;

    public ColorFilterView(Context context) {
        super(context);
        initPaint();
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,mPaint);
        mPaint.setColorFilter(colorFilter);
        canvas.drawBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),mPaint);
        //直接将原来的颜色中的红色移除
        colorFilter = new LightingColorFilter(0x00ffff, 0x000000);
        mPaint.setColorFilter(colorFilter);
        canvas.drawBitmap(bitmap,2*bitmap.getWidth(),0,mPaint);
        //直接将原来的颜色中的红色去除，增加蓝色部分
        colorFilter = new LightingColorFilter(0x00ffff,0x0000aa);
        mPaint.setColorFilter(colorFilter);
        canvas.drawBitmap(bitmap,3*bitmap.getWidth(),bitmap.getHeight(),mPaint);
        mPaint.setTextSize(40);
        canvas.drawText("ProterDuffColoFilter    DST_OVER",0,2*bitmap.getHeight()+40,mPaint);
        colorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DST_OVER);
        mPaint.setColorFilter(colorFilter);
        canvas.drawBitmap(bitmap,0,2*bitmap.getHeight()+60,mPaint);

    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
        int pixel = bitmap.getPixel(0, 0);
        int r = Color.red(pixel);
        int g = Color.green(pixel);
        int b = Color.blue(pixel);
        int a = Color.alpha(pixel);
        String hexR = Integer.toHexString(r);
        String hexG = Integer.toHexString(g);
        String hexB = Integer.toHexString(b);
        String hexA = Integer.toHexString(a);
        LogUtil.e(""+hexR+"     "+hexG+"     "+hexB+"       "+hexA);
        //下面是将原来的颜色中的红色移除，增加绿色部分
        colorFilter = new LightingColorFilter(0x00ffff, 0x003000);
    }
}

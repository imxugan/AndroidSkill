package test.cn.example.com.androidskill.ui.compact;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import test.cn.example.com.util.LogUtil;

public class CircleImageDrawable extends Drawable {
    private final int width;
    private Bitmap mBitmap;
    private Paint mPaint;
    private final BitmapShader bitmapShader;
    private Paint mBorderPaint;

    public CircleImageDrawable(Bitmap bitmap){
        mBitmap = bitmap;
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(bitmapShader);
        width = Math.min(bitmap.getWidth(), bitmap.getHeight());
        LogUtil.i(bitmap.getWidth()+"       "+bitmap.getHeight());
        LogUtil.i(""+width+"");
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStrokeWidth(1);
        mBorderPaint.setColor(Color.GREEN);
        mBorderPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    public void draw(@NonNull @android.support.annotation.NonNull Canvas canvas) {
        Rect bounds = getBounds();

        LogUtil.i(""+width/2+"      "+bounds.width()+"        "+bounds.height());
        canvas.drawCircle(width/2,width/2,width/2,mPaint);
        canvas.drawCircle(width/2,width/2,width/2,mBorderPaint);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
//        LogUtil.i("left="+left+"     top="+top+"     right="+right+"     bottom="+bottom);
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return width;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable @android.support.annotation.Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}

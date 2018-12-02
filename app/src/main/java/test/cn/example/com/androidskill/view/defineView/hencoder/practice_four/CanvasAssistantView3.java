package test.cn.example.com.androidskill.view.defineView.hencoder.practice_four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/30.
 */

public class CanvasAssistantView3 extends View {
    private Paint mPaint;
    private Bitmap bitmap;

    public CanvasAssistantView3(Context context) {
        super(context);
        initPaint();
    }

    public CanvasAssistantView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasAssistantView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,10,10,mPaint);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        canvas.save();
        canvas.translate(bitmapWidth+10+30,10);
        canvas.drawBitmap(bitmap,0,0,mPaint);
        canvas.restore();
        canvas.save();
        canvas.rotate(30,100,10+bitmapHeight+30);
        canvas.drawBitmap(bitmap,100,10+bitmapHeight+30,mPaint);
        canvas.restore();
        canvas.save();
        canvas.scale(2f,1f);
        //画布横向放大两倍，则drawbitmap时，x的坐标也跟着乘以2倍
        canvas.drawBitmap(bitmap,200,300,mPaint);
        canvas.restore();
        canvas.save();
        //scale(float sx , float sy, float px,float py):
        //sx 表示 横向的缩放，sy 表示纵向的缩放
        //px，表示缩放的水平位置的基点，py 表示纵向的位置的基点，表示画布以px,py为中心缩放
        canvas.scale(2f,1f,300,300);
        canvas.restore();
        canvas.save();
        //以10+bitmapWidth/2，650 为中心点进行缩放
        canvas.scale(2f,1f,10+bitmapWidth/2,650);
        canvas.drawBitmap(bitmap,10,550,mPaint);
        canvas.restore();
        Matrix matrix = canvas.getMatrix();
        LogUtil.i(""+matrix.toString());
        canvas.save();
        canvas.skew(0,-0.2f);
        canvas.drawBitmap(bitmap,10,800,mPaint);
        canvas.restore();
        canvas.save();
        canvas.skew(0.2f,0f);
        canvas.drawBitmap(bitmap,0,1000,mPaint);
        canvas.drawBitmap(bitmap,300,1000,mPaint);



    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
    }
}

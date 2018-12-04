package test.cn.example.com.androidskill.view.defineView.hencoder.practice_four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2018/12/4.
 */

public class CanvasAssistantView6 extends View {
    private Paint mPaint;
    private Bitmap bitmap;

    public CanvasAssistantView6(Context context) {
        super(context);
        initPaint();
    }

    public CanvasAssistantView6(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasAssistantView6(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerX = bitmap.getWidth()/2;
        float centerY = bitmap.getHeight()/2;
        canvas.save();
        Camera camera = new Camera();
        camera.save();
        camera.rotateX(30);//将camera沿着x，x的旋转方向是，将左手的大拇指指向正方向，四个手子的方法就是x的旋转方向
        canvas.translate(centerX,centerY);//将旋转后的投影移回原来的位置
        camera.applyToCanvas(canvas);//将旋转投影到canvas
        canvas.translate(-centerX,-centerY);//先将要绘制的内容的中心点移到原点
        camera.restore();
        canvas.drawBitmap(bitmap,0,0,mPaint);
        canvas.restore();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
    }
}

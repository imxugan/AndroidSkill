package test.cn.example.com.androidskill.view.defineView.hencoder.practice_four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2018/11/30.
 */

public class CanvasAssistantView2 extends View{
    private Paint mPaint;
    private Bitmap bitmap;
    private int left;
    private int top;

    public CanvasAssistantView2(Context context) {
        super(context);
        initPaint();
    }

    public CanvasAssistantView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasAssistantView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        Path path = new Path();
        int ovalLeft = left+bitmapWidth+75;
        int ovalTop = top+20;
        int ovalRight = ovalLeft+100;
        int ovalBottom = ovalTop+100;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path.addOval(ovalLeft,ovalTop,ovalRight,ovalBottom, Path.Direction.CCW);
            canvas.clipPath(path);
            canvas.drawBitmap(bitmap,left+bitmapWidth+30,top,mPaint);
        }
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        left = 10;
        top = 10;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
    }
}

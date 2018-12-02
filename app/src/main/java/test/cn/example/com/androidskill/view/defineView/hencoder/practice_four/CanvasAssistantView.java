package test.cn.example.com.androidskill.view.defineView.hencoder.practice_four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/30.
 */

public class CanvasAssistantView extends View {
    private Paint mPaint;
    private Bitmap bitmap;
    private int left;
    private int top;

    public CanvasAssistantView(Context context) {
        super(context);
        initPaint();
    }

    public CanvasAssistantView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasAssistantView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] values = new float[9];
        int[] location1 = new int[2];

        Matrix matrix = canvas.getMatrix();
        matrix.getValues(values);

        location1[0] = (int) values[2];
        location1[1] = (int) values[5];
        LogUtil.i("location1 = " + Arrays.toString(location1));

        int[] location2 = new int[2];
        this.getLocationOnScreen(location2);
        LogUtil.i("location2 = " + Arrays.toString(location2));


        Rect clipBounds = canvas.getClipBounds();
        LogUtil.e("最开始的 clipBounds "+clipBounds.toString());
        canvas.drawBitmap(bitmap,left,top,mPaint);
        canvas.save();
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        int right = left+bitmapWidth*2+30;
        int bottom = bitmapHeight+top-30;
        canvas.clipRect(left+bitmapWidth+30,top+30,right,bottom);
        clipBounds = canvas.getClipBounds();
        LogUtil.e("调用了canvas.clipRect()方法后的 clipBounds "+clipBounds.toString());
        canvas.drawBitmap(bitmap,left+bitmapWidth+30,top,mPaint);
        canvas.restore();
        clipBounds = canvas.getClipBounds();
        LogUtil.e("调用了canvas.restore()方法后的 clipBounds "+clipBounds.toString());
//        top = top+bitmapHeight+30;
//        canvas.drawBitmap(bitmap,left,top,mPaint);
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
        left = 10;
        top = 10;

    }
}

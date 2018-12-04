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
 * Created by xugan on 2018/12/4.
 */

public class CanvasAssistantView5 extends View {
    private Paint mPaint;
    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;

    public CanvasAssistantView5(Context context) {
        super(context);
        initPaint();
    }

    public CanvasAssistantView5(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasAssistantView5(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix1 = new Matrix();
        matrix1.reset();
        float left = 0;
        float top = 0;
        float right = bitmapWidth;
        float bottom = bitmapHeight;
        float pointsSrc[] = {left, top, right, top, right, bottom, left, bottom, };
        float pointsDst[] = {left, top , right , top + 30, right, bottom - 30, left, bottom};

//        pointCount 是采集的点的个数（个数不能大于 4，因为大于 4 个点就无法计算变换了）。
        matrix1.setPolyToPoly(pointsSrc,0,pointsDst,0,4);
        canvas.concat(matrix1);
        canvas.drawBitmap(bitmap,0,0,mPaint);

    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
    }
}

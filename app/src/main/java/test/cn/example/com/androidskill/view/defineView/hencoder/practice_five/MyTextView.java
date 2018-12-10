package test.cn.example.com.androidskill.view.defineView.hencoder.practice_five;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xugan on 2018/12/10.
 */

public class MyTextView extends TextView {
    private Paint mPaint;
    public MyTextView(Context context) {
        super(context);
        initPaint();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect = new Rect(0,0,100,100);
        canvas.drawRect(rect,mPaint);
        super.onDraw(canvas);

    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(30);
    }
}

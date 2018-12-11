package test.cn.example.com.androidskill.view.defineView.hencoder.practice_five;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by xugan on 2018/12/11.
 */

@SuppressLint("AppCompatCustomView")
public class CustomImageView extends ImageView {
    private Paint mPaint;
    public CustomImageView(Context context) {
        super(context);
        initPaint();
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        String text = "绘制的文字在onDrawForeground方法调用之前";
        Rect rect = new Rect();
        mPaint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text,10,rect.height(),mPaint);
        super.onDrawForeground(canvas);
        text = "绘制的文字在onDrawForeground方法调用之后";
        canvas.drawText(text,10,rect.height()*2,mPaint);
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.RED);
    }
}

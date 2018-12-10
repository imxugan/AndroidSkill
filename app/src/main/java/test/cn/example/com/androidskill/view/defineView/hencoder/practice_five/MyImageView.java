package test.cn.example.com.androidskill.view.defineView.hencoder.practice_five;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/10.
 */

@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView {
    private Paint mPaint;
    private Bitmap bitmap;

    public MyImageView(Context context) {
        super(context);
        initPaint();
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i("getWidth()= "+getWidth()+"       getHeight()= "+getHeight());
        Matrix imageMatrix = getImageMatrix();
        LogUtil.i(imageMatrix.toString());
        float[] matrixValues = new float[9];
        imageMatrix.getValues(matrixValues);
        float imageX = matrixValues[Matrix.MTRANS_X];
        String text = bitmap.getWidth()+"X"+bitmap.getHeight();
        TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.BLACK);
        Rect rect = new Rect();
        textPaint.getTextBounds(text,0,text.length(),rect);
        int textHeight = rect.height();
        LogUtil.i("imageX=  "+imageX+"       textHeight=  "+textHeight);
        canvas.drawText(text,imageX,textHeight,mPaint);
        canvas.drawText("当前view的宽高是"+getWidth()+"X"+getHeight(),imageX,textHeight*2,mPaint);
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(30);
        mPaint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
    }

}

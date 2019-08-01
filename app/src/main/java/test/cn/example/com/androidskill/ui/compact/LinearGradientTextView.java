package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import test.cn.example.com.util.LogUtil;

public class LinearGradientTextView extends TextView {

    private  Paint mPaint;
    private  float textWidth;
    private float translateX;
    private int gradientWidth = 20;
    private  Matrix matrix;
    private float deltaX = 20;
    private LinearGradient linearGradient;

    public LinearGradientTextView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //特别注意，这里用getPaint()，否则死活不出效果
        mPaint = getPaint();
        String text = getText().toString();
        textWidth = mPaint.measureText(text);
        if(0 != text.length()){
            gradientWidth = (int) (3*textWidth/text.length());
        }
        int[] colors = new int[]{0x22ffffff,0xffffffff,0x22ffffff};
        float[] positions = new float[]{0f,0.5f,1.0f};
        linearGradient = new LinearGradient(-gradientWidth,0,0,0,colors,positions, Shader.TileMode.CLAMP);
        matrix = new Matrix();
        mPaint.setShader(linearGradient);
        LogUtil.i("gradientWidth  "+gradientWidth+"         textWidth="+textWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        translateX +=deltaX;
        if(translateX>textWidth+1 || translateX<1){
            deltaX = - deltaX;
        }
        matrix.setTranslate(translateX,0);
        linearGradient.setLocalMatrix(matrix);
        postInvalidateDelayed(50);
    }
}

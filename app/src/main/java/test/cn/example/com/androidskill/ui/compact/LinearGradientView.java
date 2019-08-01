package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LinearGradientView extends View {
    private int[] colors = new int[]{Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN};
    private final Paint mPaint;

    public LinearGradientView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        //线性渐变
        //x0，y0,起始点
        //x1,y1 结束点
        //int[] colors,中间依次出现的颜色
        //float[] positons ,数组大小跟colors的大小一致，中间依次摆放的几个颜色，分别放在在那个位置上，
        // 里面存放的数字都是0到1之间的数
        //  Shader.TileMode  如果不够 ，边缘填充的方式
        LinearGradient linearGradient = new LinearGradient(0,0,400,400,colors,null, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawRect(0,0,500,500,mPaint);
    }
}

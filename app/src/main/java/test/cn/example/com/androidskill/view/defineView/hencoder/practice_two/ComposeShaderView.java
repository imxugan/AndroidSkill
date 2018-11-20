package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/20.
 */

public class ComposeShaderView extends View {
    private Paint mPaint;
    private Bitmap bitmap_dog_bg;
    private int bitmapWidth;
    private int bitmapHeight;
    private int width;
    private int height;
    private Shader bitmapShader;
    private Shader bitmapShader2;
    private Shader shaderCompose;
    private Bitmap bitmap_qq;

    public ComposeShaderView(Context context) {
        super(context);
        initPaint();
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap_dog_bg = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
        bitmapWidth = bitmap_dog_bg.getWidth();
        bitmapHeight = bitmap_dog_bg.getHeight();
        bitmap_qq = BitmapFactory.decodeResource(getResources(), R.drawable.qq);
        //这里获取的当前view的宽和高都是0,因为当前控件还未进行测量，所以只是初始化的值0
        width = getWidth();
        height  = getHeight();
        bitmapShader = new BitmapShader(bitmap_dog_bg, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader2 = new BitmapShader(bitmap_qq, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        //如果想知道bitmapShader，和bitmapShader2那个是SRC,可以将PorterDuff.Mode的取值分别取SRC和DST,
        //看看效果就知道哪个是SRC了,经过验证，bitmapShader2是SRC，bitmapShader是DST
        shaderCompose = new ComposeShader(bitmapShader, bitmapShader2, PorterDuff.Mode.SRC_OVER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setShader(bitmapShader);
        canvas.drawRect(0,0,getWidth(),bitmap_dog_bg.getHeight()*2,mPaint);
        mPaint.setShader(bitmapShader2);
        canvas.drawRect(0,bitmap_dog_bg.getHeight()*2+50,getWidth(),bitmap_qq.getHeight()*2+bitmap_dog_bg.getHeight()*2+50,mPaint);
        mPaint.setShader(shaderCompose);
        //硬件加速是有使用层次的，应用层，Activity层，Window层，View层
        // 在这四个层次中，应用和Activity是可以选择的，Window只能打开，View只能关闭
        //关闭硬件加速,由于关闭了硬件加速，onDraw方法被不停的调用
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        LogUtil.i(""+width+"       "+height);
        width = getWidth();
        height= getHeight();
        LogUtil.i(""+width+"       "+height);
        canvas.drawRect(0,(bitmap_dog_bg.getHeight()*2+bitmap_qq.getHeight()*2+50+50),width,height,mPaint);
    }
}

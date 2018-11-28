package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.RectF;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/26.
 */

public class PathEffectView2 extends View {
    private Paint mPaint;
    private Path mPath;
    private Bitmap bitmap;

    public PathEffectView2(Context context) {
        super(context);
        initPaint();
//        if (isInEditMode()) {
//            // Init path
//            mPath = new Path();
//            mPath.moveTo(10, 100);
//            mPath.lineTo(60, 10);
//            mPath.lineTo(120, 190);
//            mPath.lineTo(180, 40);
//            mPath.lineTo(240, 160);
//            mPath.lineTo(300, 10);
//            mPath.lineTo(360, 190);
//            mPath.lineTo(420, 40);
//            mPath.lineTo(480, 160);
//
//            // Init paint
//            mPaint = new Paint();
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setStrokeWidth(2);
//            mPaint.setColor(Color.CYAN);
//
//            Path path = new Path();
//            path.addOval(new RectF(0, 0, 10, 20), Path.Direction.CCW);
//            mPaint.setPathEffect(new PathDashPathEffect(path, 20, 0, PathDashPathEffect.Style.ROTATE));
//            return;
//        }

        // Init path
        mPath = new Path();
        mPath.moveTo(10, 100);
        mPath.lineTo(60, 10);
        mPath.lineTo(120, 190);
        mPath.lineTo(180, 40);
        mPath.lineTo(240, 160);
        mPath.lineTo(300, 10);
        mPath.lineTo(360, 190);
        mPath.lineTo(420, 40);
        mPath.lineTo(480, 160);

        // Init paint
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.CYAN);
        Path path = new Path();
        path.addOval(new RectF(0, 0, 10, 20), Path.Direction.CCW);
        mPaint.setPathEffect(new PathDashPathEffect(path, 20, 0, PathDashPathEffect.Style.ROTATE));
    }

    public PathEffectView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PathEffectView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        注意： PathEffect 在有些情况下不支持硬件加速，需要关闭硬件加速才能正常使用：
//
//        Canvas.drawLine() 和 Canvas.drawLines() 方法画直线时，setPathEffect()是不支持硬件加速的；
//
//        PathDashPathEffect 对硬件加速的支持也有问题，所以当使用 PathDashPathEffect 的时候，
//          最好也把硬件加速关了。
       canvas.drawPath(mPath,mPaint);
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(20);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText("PathDashPathEffect.Style.ROTATE",10,260,mPaint);
        Path shape = new Path();
        shape.addOval(new RectF(0, 0, 10, 20), Path.Direction.CCW);
        PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(shape,30,1, PathDashPathEffect.Style.ROTATE);
        mPaint.setPathEffect(pathDashPathEffect);
        canvas.drawRect(10,280,260,430,mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(20);
        canvas.drawText("PathDashPathEffect.Style.TRANSLATE",380,260,mPaint);
        pathDashPathEffect = new PathDashPathEffect(shape,30,1, PathDashPathEffect.Style.TRANSLATE);
        mPaint.setPathEffect(pathDashPathEffect);
        canvas.drawRect(410,280,660,430,mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(20);
        canvas.drawText("PathDashPathEffect.Style.MORPH",750,260,mPaint);
        pathDashPathEffect = new PathDashPathEffect(shape,30,1, PathDashPathEffect.Style.MORPH);
        mPaint.setPathEffect(pathDashPathEffect);
        canvas.drawRect(810,280,1010,430,mPaint);
        //PathDashPathEffect.Style.MORPH效果未演示出来


        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{20, 10, 15, 5}, 0);
        DiscretePathEffect discretePathEffect = new DiscretePathEffect(20, 5);
//        SumPathEffect 就是分别按照两种 PathEffect 分别对目标进行绘制
        SumPathEffect sumPathEffect = new SumPathEffect(dashPathEffect, discretePathEffect);
        mPaint.setPathEffect(sumPathEffect);
        Path path = new Path();
        path.moveTo(100,500);
        path.lineTo(150,450);
        path.lineTo(200,550);
        path.lineTo(280,510);
        path.lineTo(320,460);
        path.lineTo(380,520);
        canvas.drawPath(path,mPaint);
        ComposePathEffect composePathEffect = new ComposePathEffect(dashPathEffect, discretePathEffect);
        mPaint.setPathEffect(composePathEffect);
        path.reset();
        mPaint.setColor(Color.RED);
        path.moveTo(100,700);
        path.lineTo(150,650);
        path.lineTo(200,750);
        path.lineTo(280,710);
        path.lineTo(320,660);
        path.lineTo(380,720);
        canvas.drawPath(path,mPaint);

//        setShadowLayer
//        radius 是阴影的模糊范围； dx dy 是阴影的偏移量； shadowColor 是阴影的颜色。
//        注意：
//        在硬件加速开启的情况下， setShadowLayer() 只支持文字的绘制，
//        文字之外的绘制必须关闭硬件加速才能正常绘制阴影。
//        如果 shadowColor 是半透明的，阴影的透明度就使用 shadowColor 自己的透明度；
//        而如果 shadowColor 是不透明的，阴影的透明度就使用 paint 的透明度。
        mPaint.reset();
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(20);
        mPaint.setShadowLayer(10,10,1,Color.RED);
        canvas.drawText("abcdefghijklmn",10,780,mPaint);
        mPaint.setColor(Color.BLACK);
        mPaint.setShadowLayer(10,10,10,Color.GREEN);
        canvas.drawText("abcdefghijklmn",10,830,mPaint);
        mPaint.setColor(Color.RED);
        //下面是依次改变setShadowLayer方法的第一个参数的值的效果
        mPaint.setShadowLayer(1,0,10,Color.BLUE);
        canvas.drawText("abcdefghijklmn",10,880,mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setShadowLayer(2,0,10,Color.BLUE);
        canvas.drawText("abcdefghijklmn",200,880,mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setShadowLayer(4,0,10,Color.BLUE);
        canvas.drawText("abcdefghijklmn",400,880,mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setShadowLayer(8,0,10,Color.BLUE);
        canvas.drawText("abcdefghijklmn",600,880,mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setShadowLayer(10,0,10,Color.BLUE);
        canvas.drawText("abcdefghijklmn",800,880,mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        //原图
        canvas.drawBitmap(bitmap,10,900,mPaint);
//        BlurMaskFilter.Blur
//        NORMAL: 内外都模糊绘制
//        SOLID: 内部正常绘制，外部模糊
//        INNER: 内部模糊，外部不绘制
//        OUTER: 内部不绘制，外部模糊（什么鬼？）
        //使用BlurMaskFilter，需要关闭硬件加速，否则看不到效果
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        MaskFilter maskFilter = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
        mPaint.setMaskFilter(maskFilter);
        canvas.drawBitmap(bitmap,310,900,mPaint);
        maskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL);
        mPaint.setMaskFilter(maskFilter);
        canvas.drawBitmap(bitmap,710,900,mPaint);

        maskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER);
        mPaint.setMaskFilter(maskFilter);
        canvas.drawBitmap(bitmap,10,1150,mPaint);

        maskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID);
        mPaint.setMaskFilter(maskFilter);
        canvas.drawBitmap(bitmap,310,1150,mPaint);

        maskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER);
        mPaint.setMaskFilter(maskFilter);
        canvas.drawBitmap(bitmap,710,1150,mPaint);

//        构造方法 EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius)
//        的参数里， direction 是一个 3 个元素的数组，指定了光源的方向；
//        ambient 是环境光的强度，数值范围是 0 到 1；
//        specular 是炫光的系数；
//        blurRadius 是应用光线的范围
        float[] direction = new float[]{0,1,1};
        float ambient = 0.5f;
        float specular = 2f;
        float blurRadius = 5f;
        maskFilter = new EmbossMaskFilter(direction,ambient,specular,blurRadius);
        mPaint.setMaskFilter(maskFilter);
        canvas.drawBitmap(bitmap,10,1380,mPaint);

        Path dst = new Path();
        LogUtil.e(dst+"");
        //获取出绘制 Path 或文字时的实际 Path
        mPaint.getFillPath(path,dst);
        LogUtil.e(dst+"");

        mPaint.reset();
        mPaint.setAntiAlias(true);
        //设置文字的大小
        mPaint.setTextSize(20);
        canvas.drawText("abcdefg",310,1400,mPaint);

        mPaint.setUnderlineText(true);
        canvas.drawText("abcdefg",310,1420,mPaint);
        mPaint.setUnderlineText(false);
        mPaint.setFakeBoldText(true);
        canvas.drawText("abcdefg",310,1440,mPaint);



    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_bg);
    }
}

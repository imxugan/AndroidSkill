package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xugan on 2018/11/26.
 */

public class PathEffectView2 extends View {
    private Paint mPaint;
    private Path mPath;
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
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}

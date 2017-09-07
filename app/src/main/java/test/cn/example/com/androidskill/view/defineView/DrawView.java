package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import test.cn.example.com.androidskill.R;

/**
 * Created by xgxg on 2017/9/6.
 */

public class DrawView extends View {
    private Context mContext;
    private final Paint mPaint;

    public DrawView(Context context) {
        super(context);
        this.mContext = context;
        //创建画笔
        mPaint = new Paint();
        //设置画笔的颜色
        mPaint.setColor(Color.RED);
//        mPaint.setTextSize(DensityUtil.sp2px(mContext,20));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画文字
        String text = "画圆";
        canvas.drawText(text,10,20,mPaint);
        //画圆
        canvas.drawCircle(60,20,10,mPaint);
        //设置画笔的抗锯齿效果
        mPaint.setAntiAlias(true);
        canvas.drawCircle(120,20,20,mPaint);


        //画弧线
        text = "画直线,斜线";
        canvas.drawText(text,10,60,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(60,40,100,40,mPaint);
        canvas.drawLine(110,40,190,80,mPaint);

        //画笑脸弧线
        //设置空心
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(150,20,180,40);
        canvas.drawArc(oval,180,180,false,mPaint);
        oval.set(190,20,220,40);
        canvas.drawArc(oval,180,180,false,mPaint);
        oval.set(160,30,210,60);
        canvas.drawArc(oval,0,180,false,mPaint);

        //绘制矩形
        text = "画矩形:";
        canvas.drawText(text,10,80,mPaint);
        //设置灰色
        mPaint.setColor(Color.GRAY);
        //设置填满
        mPaint.setStyle(Paint.Style.FILL);
        //正方形
        canvas.drawRect(60,60,80,80,mPaint);
        //矩形
        canvas.drawRect(60,90,160,100,mPaint);

        //画椭圆和扇形
        text = "画椭圆和扇形:";
        canvas.drawText(text,10,120,mPaint);
        //设置渐变色，这个正方形的颜色是改变的
        Shader mShader = new LinearGradient(0,0,100,100,new int[]{Color.BLACK,Color.WHITE,Color.BLUE,
        Color.CYAN,Color.GREEN},null,Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
        //设置新的长方形，扫描测量
        RectF oval2 = new RectF(60,100,200,240);
        canvas.drawArc(oval2,200,130,true,mPaint);
        //画椭圆
        oval2.set(210,100,250,130);
        canvas.drawOval(oval2,mPaint);

        //画三角形
        text = "画三角形：";
        canvas.drawText(text,10,200,mPaint);
        //重置画笔
        mPaint.reset();
        Path path = new Path();
        path.moveTo(80,200);
        path.lineTo(120,250);
        path.lineTo(80,250);
        path.close();
        canvas.drawPath(path,mPaint);

        //绘制任意多边形
        mPaint.setColor(Color.GRAY);
        //设置空心
        mPaint.setStyle(Paint.Style.STROKE);
        Path path1 = new Path();
        path1.moveTo(180,200);
        path1.lineTo(200,200);
        path1.lineTo(210,210);
        path1.lineTo(200,220);
        path1.lineTo(180,220);
        path1.lineTo(170,210);
        path1.close();//封闭
        canvas.drawPath(path1,mPaint);

        //画圆角矩形
        mPaint.setStyle(Paint.Style.FILL);//填充满
        mPaint.setColor(Color.LTGRAY);
        mPaint.setAntiAlias(true);//设置抗锯齿
        text = "画圆角矩形";
        canvas.drawText(text,10,260,mPaint);
        RectF oval3 = new RectF(80,260,200,300);//设置新的长方形
        canvas.drawRoundRect(oval3,20,15,mPaint);//第二个参数是x半径，第三个参数是y半径

        //画赛贝尔曲线
        text = "赛贝尔曲线";
        canvas.drawText(text,10,310,mPaint);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.DKGRAY);
        Path path2 = new Path();
        path2.moveTo(100,320);
        path2.quadTo(150,310,170,400);
        canvas.drawPath(path2,mPaint);

        //画点
        text = "画点：";
        canvas.drawText(text,10,490,mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(3);

        canvas.drawPoint(60,490,mPaint);//画点
        canvas.drawPoints(new float[]{60,520,65,520,70,520},mPaint);

        //绘制图片
        text = "画图：";
        mPaint.reset();
        canvas.drawText(text,10,590,mPaint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bitmap,100,610,mPaint);

        text = "路径文字：";
        canvas.drawText(text,10,740,mPaint);
        Path path3 = new Path(); //定义一条路径
        path3.moveTo(80, 740); //移动到 坐标10,10
        path3.lineTo(130, 790);
        path3.lineTo(290,850);
        path3.lineTo(80, 740);
        canvas.drawTextOnPath("Android开发者", path3, 10, 10, mPaint);

        //绘制弧线区域
        text = "弧线区域,填充圆弧但包含圆心 ";
        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,10,870,mPaint);
        RectF oval4 = new RectF(230,860,260,880);
        canvas.drawArc(oval4,0,120,true,mPaint);

        text = "弧线区域，不填充圆弧，包含圆心";
        mPaint.reset();
        mPaint.setColor(Color.RED);
        canvas.drawText(text,10,900,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        RectF oval5 = new RectF(270,880,300,900);
        canvas.drawArc(oval5,0,120,true,mPaint);

        text = "弧线区域，填充圆弧，不包含圆心";
        mPaint.reset();
        mPaint.setColor(Color.GREEN);
        canvas.drawText(text,10,930,mPaint);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        RectF oval6 = new RectF(330,910,360,930);
        canvas.drawArc(oval6,0,120,false,mPaint);

        text = "弧线区域，不填充圆弧，不包含圆心";
        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        canvas.drawText(text,10,960,mPaint);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval7 = new RectF(390,940,420,960);
        canvas.drawArc(oval7,0,120,false,mPaint);

    }
}

package test.cn.example.com.androidskill.view.defineView.hencoder.practice_three;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

/**
 * Created by xugan on 2018/11/28.
 */

public class DrawTextVew extends View {
    private Paint mPaint;
    public DrawTextVew(Context context) {
        super(context);
        initPaint();
    }

    public DrawTextVew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DrawTextVew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        //canvas.drawText方法中的x,y两坐标的含义是
        //x 坐标是文字的起始位置的左边一点点的位置
        //y 坐标是表示的，文字baseline
        canvas.drawText("123abc",30,30,mPaint);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(30);
        Path path = new Path();
        path.moveTo(10,100);
        path.lineTo(90,160);
        path.lineTo(200,60);
        path.lineTo(300,130);
        path.lineTo(400,30);
        path.lineTo(500,100);
        //drawTextOnPath构造函数的中的参数
//        hOffset 为 5， vOffset 为 10，文字就会右移 5 像素和下移 10 像素。
        canvas.drawTextOnPath("123456abcdefg撒大大是多少的说法是电风扇是的发生的发生发达是打发斯蒂芬发送到发",path,10,20,mPaint);
        mPaint.setColor(Color.DKGRAY);
        canvas.drawPath(path,mPaint);

        path = new Path();
        path.moveTo(10,200);
        path.lineTo(90,260);
        path.lineTo(200,160);
        path.lineTo(300,230);
        path.lineTo(400,130);
        path.lineTo(500,200);
        mPaint.setColor(Color.RED);
        canvas.drawTextOnPath("123456abcdefg撒大大是多少的说法是电风扇是的发生的发生发达是打发斯蒂芬发送到发",path,0,0,mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(path,mPaint);

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(20);
        path = new Path();
        path.moveTo(10,300);
        path.lineTo(90,360);
        path.lineTo(200,260);
        path.lineTo(300,330);
        path.lineTo(400,230);
        path.lineTo(500,300);
        //
        PathEffect pathEffect = new CornerPathEffect(20);
        mPaint.setPathEffect(pathEffect);
        canvas.drawTextOnPath("怎么这里设置的pathEffect，作用到了前面的两文字的绘制效果上去了？？？？？",path,0,0,mPaint);
        mPaint.setColor(Color.DKGRAY);
        canvas.drawPath(path,mPaint);

        TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.BLUE);
        String text = "123abc一二三";

//        StaticLayout参数解释
//        StaticLayout(CharSequence source, int bufstart, int bufend,
//        TextPaint paint, int outerwidth,
//        Alignment align,
//        float spacingmult, float spacingadd,
//        boolean includepad,
//        TextUtils.TruncateAt ellipsize, int ellipsizedWidth)
//        1.需要分行的字符串
//        2.需要分行的字符串从第几的位置开始
//        3.需要分行的字符串到哪里结束
//        4.画笔对象
//        5.layout的宽度，字符串超出宽度时自动换行。
//        6.layout的对其方式，有ALIGN_CENTER， ALIGN_NORMAL， ALIGN_OPPOSITE 三种。
//        7.相对行间距，相对字体大小，1.5f表示行间距为1.5倍的字体高度。
//        8.在基础行距上添加多少
//        实际行间距等于这两者的和。
//        9.参数未知
//        10.从什么位置开始省略
//        11.超过多少开始省略
//        需要指出的是这个layout是默认画在Canvas的(0,0)点的，如果需要调整位置只能在draw之前移Canvas的起始坐标
//        canvas.translate(x,y);
        StaticLayout staticLayout = new StaticLayout(text+"Layout.Alignment.ALIGN_NORMAL", textPaint,1550, Layout.Alignment.ALIGN_NORMAL,1,0,true);
        //canvas.translate(10, 10);//把当前画布的原点移到(10,10),后面的操作都以(10,10)作为参照点，默认原点为(0,0)
        canvas.translate(10,380);
        staticLayout.draw(canvas);
        staticLayout = new StaticLayout(text+"Layout.Alignment.ALIGN_CENTER",textPaint,1550, Layout.Alignment.ALIGN_CENTER,1,0,true);
        canvas.translate(0,30);
        staticLayout.draw(canvas);
        textPaint.setColor(Color.RED);
        staticLayout = new StaticLayout(text+"Layout.Alignment.ALIGN_NORMAL",textPaint,1550, Layout.Alignment.ALIGN_NORMAL,1,0,true);
        canvas.translate(0,30);
        staticLayout.draw(canvas);
        staticLayout = new StaticLayout(text+"Layout.Alignment.ALIGN_OPPOSITE",textPaint,1550, Layout.Alignment.ALIGN_OPPOSITE,1,0,true);
        canvas.translate(0,30);
        staticLayout.draw(canvas);
        staticLayout = new StaticLayout(text+"Layout.Alignment.ALIGN_NORMAL",textPaint,1520, Layout.Alignment.ALIGN_NORMAL,1,0,true);
        canvas.translate(0,30);
        staticLayout.draw(canvas);

        textPaint.setTextSize(50);
        staticLayout = new StaticLayout(text,textPaint,500, Layout.Alignment.ALIGN_NORMAL,1,0,true);
        canvas.translate(0,30);
        staticLayout.draw(canvas);

        mPaint.setTextSize(30);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(text+"Typeface.DEFAULT_BOLD",10,80,mPaint);
        mPaint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(text+"Typeface.DEFAULT",10,130,mPaint);
        mPaint.setTypeface(Typeface.SERIF);
        canvas.drawText(text+"Typeface.SERIF",10,180,mPaint);
        mPaint.setTypeface(Typeface.MONOSPACE);
        canvas.drawText(text+"Typeface.MONOSPACE",10,220,mPaint);
        mPaint.setTypeface(Typeface.SANS_SERIF);
        canvas.drawText(text+"Typeface.SANS_SERIF",10,270,mPaint);
        //将设置的typeface还原为默认的效果
        mPaint.setTypeface(Typeface.DEFAULT);
        //是否使用伪粗体
        mPaint.setFakeBoldText(true);
        canvas.drawText(text+"设置了伪粗体 setFakeBoldText",10,320,mPaint);
        //将设置的伪粗体效果还原
        mPaint.setFakeBoldText(false);
        //是否加删除线
        mPaint.setStrikeThruText(true);
        canvas.drawText(text+"使用了删除线的效果 setStrikeThruText(true)",10,370,mPaint);
        //将设置的删除线还原
        mPaint.setStrikeThruText(false);
        //设置下划线
        mPaint.setUnderlineText(true);
        canvas.drawText(text+"设置下划线效果 setUnderline()",10,420,mPaint);
        mPaint.setUnderlineText(false);
        //设置文字倾斜度效果
        mPaint.setTextSkewX(1.5f);
        canvas.drawText(text+"设置文字倾斜度 setTextSkewX()",10,470,mPaint);
        mPaint.setTextSkewX(-0.5f);
        canvas.drawText(text+"设置文字倾斜度 setTextSkewX()",10,520,mPaint);
        mPaint.setTextSkewX(0f);
        canvas.drawText(text+"设置文字倾斜度 setTextSkewX()",10,570,mPaint);
        //设置文字横向缩放
        mPaint.setTextScaleX(1.5f);
        canvas.drawText(text+"设置文字横向缩放效果 setTextScaleX()",10,620,mPaint);
        mPaint.setTextScaleX(1.0f);
        canvas.drawText(text+"设置文字横向缩放效果 setTextScaleX()",10,670,mPaint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPaint.setLetterSpacing(0.5f);
        }
        canvas.drawText(text+"设置字符的间距,怎么前面的文字的间距也变了？？？",10,720,mPaint);
        //setFontFeatureSettings用css的font-feature-settings方式设置文字
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text+"Paint.Align.LEFT",10,770,mPaint);
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text+"Paint.Align.CENTER",10,820,mPaint);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(text+"Paint.Align.RIGHT",10,870,mPaint);
        mPaint.setTextAlign(Paint.Align.LEFT);

        //设置setLocale,根据系统区域设置字体
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mPaint.setTextLocale(Locale.TAIWAN);
        }
        canvas.drawText(text+"Locale.TAIWAN",10,920,mPaint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mPaint.setTextLocale(Locale.CHINA);
        }
        canvas.drawText(text+"Locale.CHINA",10,970,mPaint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mPaint.setTextLocale(Locale.JAPAN);
        }
        canvas.drawText(text+"Locale.JAPAN",10,1020,mPaint);


    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
}

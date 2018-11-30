package test.cn.example.com.androidskill.view.defineView.hencoder.practice_three;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/29.
 */

public class DrawTextView2 extends View {
    private Paint mPaint;
    private int fontSpacing;

    public DrawTextView2(Context context) {
        super(context);
        initPaint();
    }

    public DrawTextView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DrawTextView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(30);
        String text = "aJj一意№すâщ";
        float textWidth = mPaint.measureText(text);
        int baseline = 30;
        canvas.drawText(text,10,30,mPaint);
        fontSpacing = (int)mPaint.getFontSpacing();
        LogUtil.e(""+fontSpacing);
        baseline = 30+fontSpacing;
        canvas.drawText(text,10,baseline,mPaint);
        baseline = 30+ fontSpacing *2;
        canvas.drawText(text,10,baseline,mPaint);

        mPaint.setTextSize(120);
        baseline = 30+ fontSpacing *7;
        canvas.drawText(text,10,baseline,mPaint);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;//top的值是baseline上方的一个纵坐标减去baseline后的结果，所以top的值是负的
        float ascent = fontMetrics.ascent;//ascent的值是baseline上方的一个纵坐标减去baseline后的结果，所以，ascent的值是负的
        float descent = fontMetrics.descent;//descent的值是baseline下方的一个纵坐标减去baseline后的结果，所以descent的值是正的
        float bottom = fontMetrics.bottom;//bottyom的值是baseline下方的一个纵坐标减去baseline后的结果，所以，bottom的值是正的s
        float leading = fontMetrics.leading;//这个值是第一个行的bottom和第二行的top的距离
        LogUtil.e("baseline="+baseline+"   top=  "+top+"   ascent="+ascent+"   descent="+descent+"   bottom="+bottom);
        LogUtil.e("leading="+leading);
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(0,baseline,getWidth()-textWidth+30,baseline,mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawLine(0,baseline+top,getWidth()-textWidth,baseline+top,mPaint);
        mPaint.setColor(Color.parseColor("#9932cd"));
        canvas.drawLine(0,baseline+ascent,getWidth()-textWidth+50,baseline+ascent,mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(0,baseline+descent,getWidth()-textWidth+60,baseline+descent,mPaint);
        mPaint.setColor(Color.parseColor("#7f00ff"));
        canvas.drawLine(0,baseline+bottom,getWidth()-textWidth,baseline+bottom,mPaint);
//        ascent 和 descent 这两个值还可以通过 Paint.ascent() 和 Paint.descent() 来快捷获取
        LogUtil.e("ascent=  "+mPaint.ascent()+"    descent=  "+mPaint.descent());

        LogUtil.e("mPaint.getFontSpacing()*4=  "+fontSpacing *4+"     baseline=  "+baseline);
        mPaint.setTextSize(30);
        baseline = fontSpacing *4+baseline;
        LogUtil.e("     baseline=  "+baseline);
        //获取文字的显示范围
        Rect rect = new Rect();
        mPaint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text+" 的文字宽度是 "+(rect.right-rect.left),0,baseline,mPaint);
        baseline = baseline + fontSpacing*2;
        canvas.drawText(text+" 的文字宽度是(通过paint.measureText()方式计算的文字宽度) "+mPaint.measureText(text),0,baseline,mPaint);
        baseline = baseline + fontSpacing*2;
        float[] floats = new float[1];
        mPaint.getTextWidths(text,0,1,floats);
        canvas.drawText(text+"中第一个字符的宽度  "+floats[0],10,baseline,mPaint);
        floats = new float[1];
        mPaint.getTextWidths(text,2,3,floats);
        baseline = baseline+fontSpacing*2;
        canvas.drawText(text+"中第3个字符的宽度是 "+floats[0],10,baseline,mPaint);
        floats = new float[1];
        mPaint.getTextWidths(text,3,4,floats);
        baseline = baseline + fontSpacing*2;
        canvas.drawText(text+"中第4个字符的宽度  "+floats[0],10,baseline,mPaint);
        floats = new float[5];
        mPaint.getTextWidths(text,0,5,floats);
        baseline = baseline+fontSpacing*2;
        canvas.drawText(text+"中前5个字符的总宽度是"+(floats[0]+floats[1]+floats[2]+floats[3]+floats[4]),10,baseline,mPaint);

        float[] measuredWidth = {0};
        int textCount = mPaint.breakText(text, 0, text.length(), true, 100, measuredWidth);
        baseline = baseline+ fontSpacing*2;
        canvas.drawText(text+"通过paint.breakText()方法获取宽度为100的文字的个数 "+textCount,10,baseline,mPaint);

        baseline = baseline+fontSpacing*3;
        canvas.drawText(text,10,baseline,mPaint);
        //计算文字的光标位置
//        getRunAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset)
        //start  end  是文字的起始坐标和结束坐标
        //contextStart contextEnd  是上下文的起始和结束坐标
        //isRt1 是文字的方向
        //offset 是字数的偏移量，即第几个字符处的光标
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPaint.setColor(Color.RED);
            String textTemp = "Hello world \uD83C\uDDE8\uD83C\uDDF3";
            baseline = baseline+fontSpacing*2;
            canvas.drawText(textTemp,10,baseline,mPaint);
            int length = textTemp.length();
            float runAdvance = mPaint.getRunAdvance(textTemp, 0, length, 0, length, true, length-1);
            LogUtil.e("光标的位置是  "+runAdvance);
            canvas.drawLine(runAdvance,baseline-fontSpacing*2,runAdvance,baseline+fontSpacing*2,mPaint);
            runAdvance = mPaint.getRunAdvance(textTemp, 0, length, 0, length, true, length-2);
            canvas.drawLine(runAdvance,baseline-fontSpacing*2,runAdvance,baseline+fontSpacing*2,mPaint);
            runAdvance = mPaint.getRunAdvance(textTemp, 0, length, 0, length, true, length-5);
            canvas.drawLine(runAdvance,baseline-fontSpacing*2,runAdvance,baseline+fontSpacing*2,mPaint);

            String tempText2 = "getOffsetForAdvance()这个方法也未弄清楚";
            baseline = baseline+fontSpacing*4;
            canvas.drawText(tempText2,10,baseline,mPaint);
            int offsetForAdvance = mPaint.getOffsetForAdvance(tempText2, 0, tempText2.length(), 0, tempText2.length(), true, tempText2.length());
            LogUtil.e("offsetForAdvance=  "+offsetForAdvance);
            mPaint.setColor(Color.GREEN);
            canvas.drawLine(offsetForAdvance,baseline-fontSpacing,offsetForAdvance,baseline+fontSpacing,mPaint);

            //检查指定的字符串中是否是一个单独的字形 (glyph）
            boolean get = mPaint.hasGlyph("get");
            LogUtil.e("get=   "+get);
            String a = "a";
            baseline = baseline+fontSpacing*2;
            canvas.drawText(a,10,baseline,mPaint);
            boolean hasGlyph = mPaint.hasGlyph(a);
            LogUtil.e("hasGlyph=   "+hasGlyph);
            String ab = "ab";
            baseline = baseline+fontSpacing*2;
            canvas.drawText(ab,10,baseline,mPaint);
            boolean hasGlyph1 = mPaint.hasGlyph(ab);
            LogUtil.e("hasGlyph1=   "+hasGlyph1);
        }


    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
}

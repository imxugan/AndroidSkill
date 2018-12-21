package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Random;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/20.
 */

public class CommentView2 extends View {
    private Paint mPaint;
    private StringBuffer sb_change = new StringBuffer();
    private StringBuffer sb_change_old = new StringBuffer();
    private StringBuffer sb_holder = new StringBuffer();
    private Rect rect = new Rect();
    private int oldNumber;
    private String text;
    private String textNumber="";
    private ValueAnimator valueAnimator;
    private int animatedValue;
    private boolean isEnd = false;
    private float xOffset;
    private Random random = new Random();
    private TextPaint textPaint;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.oldNumber = this.number;
        this.number = number;
        textNumber = textNumber(number);
        valueAnimator.start();
    }

    private int number;//点赞数量

    private String textNumber(int number){
        String result = "";
        if(number<=0){
            result = "0";
        }else if(0<number && number<=9){
            result = getChangedNumberText(1,oldNumber,number);
        }else if(number>9 && number<100){
            result = getChangedNumberText(2,oldNumber,number);
        }else if(number>=100 && number<=999 && oldNumber<=999){
            result = getChangedNumberText(3,oldNumber,number);
        }else {
            sb_holder.setLength(0);
            result = "999+";
            xOffset = 0;
            text = "";
            sb_holder.append("");
        }
        mPaint.getTextBounds(result,0,result.length(),rect);
//        for (int i = 0; i <=10; i++) {
//            mPaint.getTextBounds(""+i,0,(""+i).length(),rect);
//            LogUtil.e("i= "+i+"     rect.width()= "+rect.width());
//        }
        valueAnimator.setIntValues(rect.height());
        LogUtil.e("textNumber时  "+rect.height());
        return result;
    }

    private String getChangedNumberText(int arrLength , int oldNumber,int number){
        sb_change.setLength(0);
        sb_change_old.setLength(0);
        sb_holder.setLength(0);
        switch (arrLength){
            case 1:
                sb_change.append(""+number);
                sb_change_old.append(""+oldNumber);
                sb_holder.append("");
                break;
            case 2:
                int old_shi_2 = oldNumber / 10;
                int shi_2 = number / 10;
                int old_ge_2 = oldNumber % 10;
                int ge_2 = number % 10;
                if(old_shi_2 != shi_2){
                    sb_change.append(shi_2);
                    sb_change_old.append(old_shi_2);
                }else {
                    sb_holder.append(shi_2);
                }

                if(old_ge_2 != ge_2){
                    sb_change.append(ge_2);
                    sb_change_old.append(old_ge_2);
                }else {
                    sb_holder.append(ge_2);
                }
                break;
            case 3:
                int old_bai_3 = oldNumber/100;
                int bai_3 = number/100;
                int old_shi_3 = oldNumber/10%10;
                int shi_3 = number/10%10;
                int old_ge_3 = oldNumber%100%10;
                int ge_3= number%100%10;
                if(old_bai_3 != bai_3){
                    sb_change.append(bai_3);
                    sb_change_old.append(old_bai_3);
                }else {
                    sb_holder.append(bai_3);
                }

                if(old_shi_3 != shi_3){
                    sb_change.append(shi_3);
                    sb_change_old.append(old_shi_3);
                }else {
                    sb_holder.append(shi_3);
                }

                if(old_ge_3 != ge_3){
                    sb_change.append(ge_3);
                    sb_change_old.append(old_ge_3);
                }else {
                    sb_holder.append(ge_3);
                }
                break;
        }
        //获取字符串中每个字符的宽度，并把结果填入参数 widths
        String s = sb_holder.toString();
        float[] widths = new float[s.length()];
        mPaint.getTextWidths(sb_holder.toString(),widths);
        for (int i = 0; i < widths.length; i++) {
            LogUtil.e("第"+i+"个字符的宽度  "+widths[i]);
        }

        xOffset = mPaint.measureText(sb_holder.toString());
        LogUtil.e("xOffset="+xOffset+"  "+sb_holder.toString()+"   "+sb_change.toString());
        return sb_change.toString();
    }

    public CommentView2(Context context) {
        super(context);
        initPaint();
    }

    public CommentView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CommentView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setTextSize(50);
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.GRAY);
        textPaint.setStrokeWidth(5);
        sb_holder.append(oldNumber+"");
        valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(100);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                animatedValue = (int) valueAnimator.getAnimatedValue();
                LogUtil.e(""+fraction+"    "+ animatedValue);
                invalidate();
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isEnd = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isEnd = true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.e(""+getMeasuredWidth()+"     "+getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtil.e(""+number);
//        canvas.drawText(number+"",100,150,mPaint);
        canvas.drawText(number+"",200,100,mPaint);

        text = sb_change.toString();
        canvas.drawText(sb_holder.toString(),100,100,mPaint);
        if(rect.height()!=0 && animatedValue<rect.height()/2){
            if(random.nextBoolean()){
                canvas.drawCircle(100+random.nextInt(rect.width()),100-random.nextInt(rect.height()),50,textPaint);
            }else {
                canvas.drawCircle(100+random.nextInt(rect.width()),100+random.nextInt(rect.height()),50,textPaint);
            }

        }

        canvas.save();
        if(number>=oldNumber){
//            canvas.translate(100+xOffset,100-rect.height()+animatedValue);
            canvas.translate(100+xOffset,100-animatedValue);
        }else {
//            canvas.translate(100+xOffset,100+rect.height()-animatedValue);
            canvas.translate(100+xOffset,100+animatedValue);
        }

        if(!isEnd){
            canvas.drawText(sb_change_old.toString(),0,0,mPaint);
        }else {
            canvas.drawText("",0,0,mPaint);
        }
        canvas.restore();
        canvas.save();
        if(number>=oldNumber){
            canvas.translate(100+xOffset,100+rect.height()-animatedValue);
        }else {
            canvas.translate(100+xOffset,100-rect.height()+animatedValue);
        }
        canvas.drawText(sb_change.toString(),0,0,mPaint);
        canvas.restore();
    }
}

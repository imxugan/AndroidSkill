package test.cn.example.com.androidskill.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by xgxg on 2017/8/28.
 */

public abstract class Particle {
    protected float x;
    protected float y;
    protected int color;

    public Particle(int color,float x,float y){
        this.color = color;
        this.x = x;
        this.y = y;
    }

    protected abstract void draw(Canvas canvas, Paint paint);

    protected abstract void caculate(float factor);

    public void advance(Canvas canvas,Paint paint,float factor){
        caculate(factor);
        draw(canvas,paint);
    }
}

package test.cn.example.com.androidskill.model.explode.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import test.cn.example.com.androidskill.model.explode.Particle;
import test.cn.example.com.util.DensityUtil;

/**
 * 向上爆炸粒子
 * Created by xgxg on 2017/8/29.
 */

public class ExplodeParticle extends Particle {
    public float radius = 8;
    protected final float END_VALUE = 1.4f;
    public float alpha;
    public int clolr;
    public float x;
    public float y;
    public float baseX;
    public float baseY;
    public float baseRadius;
    public float top;
    public float bottom;
    public float mag;
    public float neg;
    public float life;
    public float overflow;
    public ExplodeParticle(int color, float x, float y) {
        super(color, x, y);
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int)(Color.alpha(color) * alpha));//这样透明色就不是黑色了
        canvas.drawCircle(x,y,radius,paint);

    }

    @Override
    protected void caculate(float factor) {
        float f = 0f;
        float normalization = factor / END_VALUE;
        if(normalization < life || normalization >(1f- overflow)){
            alpha = 0f;
            return;
        }

        normalization = (normalization - life) / (1f - life - overflow);
        float f2 = normalization * END_VALUE ;
        if(normalization>=0.7f){
            f = (normalization - 0.7f) / 0.3f;
        }
        alpha = 1f - f;
        f = bottom * 2;
        x = baseX + f;
        y = (float)(baseY - this.neg * Math.pow(f,2.0)) - f * mag;
        radius = DensityUtil.dp2Px(2) + (baseRadius - DensityUtil.dp2Px(2))* f2;

    }
}

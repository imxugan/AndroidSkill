package test.cn.example.com.androidskill.model.explode.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

import test.cn.example.com.androidskill.inter.explode.ParticleFactory;
import test.cn.example.com.androidskill.model.explode.Particle;
import test.cn.example.com.androidskill.model.explode.particle.ExplodeParticle;
import test.cn.example.com.util.DensityUtil;

/**
 * Created by xgxg on 2017/8/29.
 */

public class ExplodeParticleFacory extends ParticleFactory {
    protected static final int PART_WH = 8;//默认小球的宽高
    protected static final  float X = DensityUtil.dp2Px(5);
    public static final float Y = DensityUtil.dp2Px(20);
    protected static final float V = DensityUtil.dp2Px(2);
    protected static final float W = DensityUtil.dp2Px(1);
    private static final float END_VALUE = 1.4f;
    private Rect mRect;
    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect rect) {
        mRect = new Rect(rect);
        int w = rect.width();
        int h = rect.height();
        Random random = new Random();
        int partW_Count = w / PART_WH;//横向个数(列)
        int partH_Count = h / PART_WH;//纵向个数(行)

        int bitmap_part_w = bitmap.getWidth() / partW_Count;//每个小圆点的宽
        int bitmap_part_h = bitmap.getHeight() / partH_Count;//每个小圆点的高

        Particle[][] particles = new Particle[partH_Count][partW_Count];
        for (int row = 0; row < partH_Count; row++) {
            for (int column = 0; column < partW_Count; column++) {
                //每个粒子在当前所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w,row * bitmap_part_h);
//                float x = rect.left + PART_WH * column;
//                float y = rect.top + PART_WH * row;
                particles[row][column] = generateParticle(color,random);

            }

        }
        return particles;
    }

    private Particle generateParticle(int color, Random random) {
        ExplodeParticle particle = new ExplodeParticle(color,0,0);
        particle.clolr = color;
        particle.radius = V;
        if(random.nextFloat() < 0.2f){
            particle.baseRadius = V + ((X - V) * random.nextFloat());
        }else {
            particle.baseRadius = W +(V - W) * random.nextFloat();
        }

        float nextFloat = random.nextFloat();
        particle.top = mRect.height() * (0.18f * random.nextFloat()) + 0.2f;
        particle.top = nextFloat < 0.2f ? particle.top : particle.top +(particle.top * 0.2f) * random.nextFloat();

        particle.bottom = mRect.height() * (random.nextFloat()- 0.5f) * 1.8f;
        float f = nextFloat < 0.2f? particle.bottom:nextFloat<0.8f?particle.bottom * 0.6f:particle.bottom * 0.3f;
        particle.bottom = f;
        particle.mag = 4.0f * particle.top / particle.bottom;
        particle.neg = (-particle.mag)/particle.bottom;
        f = mRect.centerX() + (Y * ( random.nextFloat() - 0.5f));
        particle.baseY = f;
        particle.x = f;
        particle.life = END_VALUE / 10 * random.nextFloat();
        particle.overflow = 0.4f * random.nextFloat();
        particle.alpha = 1f;
        return particle;
    }
}

package test.cn.example.com.androidskill.model.explode.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;

import test.cn.example.com.androidskill.inter.explode.ParticleFactory;
import test.cn.example.com.androidskill.model.explode.Particle;
import test.cn.example.com.androidskill.model.explode.particle.FlyawayParticle;

/**
 * Created by xgxg on 2017/8/29.
 */

public class FlyawayFactory extends ParticleFactory {
    public static final int PART_WH = 8;//默认小球高度
    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect rect) {
        int w = rect.width();
        int h = rect.height();
        int partW_Count = w / PART_WH;//列
        int partH_Count = h / PART_WH;//行

        int bitmap_part_w = bitmap.getWidth() / partW_Count;
        int bitmap_part_h = bitmap.getHeight() / partH_Count;

        Particle[][] particles = new Particle[partH_Count][partW_Count];
        for (int row = 0; row < partH_Count; row++) {
            for (int column = 0; column < partW_Count; column++) {
                int color = bitmap.getPixel(row * bitmap_part_h,column * bitmap_part_w);
                float x = rect.left + PART_WH * column;
                float y = rect.top + PART_WH * row;
                particles[row][column] = new FlyawayParticle(color,x,y,rect);
            }
        }

        return particles;
    }
}

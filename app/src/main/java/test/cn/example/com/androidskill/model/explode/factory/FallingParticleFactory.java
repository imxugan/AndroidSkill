package test.cn.example.com.androidskill.model.explode.factory;

import android.graphics.Bitmap;
import android.graphics.Rect;

import test.cn.example.com.androidskill.inter.explode.ParticleFactory;
import test.cn.example.com.androidskill.model.explode.particle.FallingParticle;
import test.cn.example.com.androidskill.model.explode.Particle;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/28.
 */

public class FallingParticleFactory extends ParticleFactory {
    public static final int PART_WH = 8;//小球默认宽高
    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect rect) {
        int w = rect.width();//场景宽
        int h = rect.height();//场景高
        LogUtil.i("w="+w+"---h="+h);

        int partW_count = w / PART_WH;//横向的个数(相当于列)
        int partH_count = h / PART_WH;//竖向的个数(相当于行)

        int bitmap_part_w = bitmap.getWidth()/partW_count;
        int bitmap_part_h = bitmap.getHeight() / partH_count;
        Particle[][] particles = new Particle[partH_count][partW_count];
        for (int row = 0; row < partH_count; row++) {
            for (int column = 0; column < partW_count; column++) {
                //获取当前粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w,row * bitmap_part_h);
                float x = rect.left + FallingParticleFactory.PART_WH * column;
                float y = rect.top + FallingParticleFactory.PART_WH  * row;
                particles[row][column] = new FallingParticle(color,x,y,rect);
            }
        }
        return particles;
    }
}

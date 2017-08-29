package test.cn.example.com.androidskill.inter.explode;

import android.graphics.Bitmap;
import android.graphics.Rect;

import test.cn.example.com.androidskill.model.explode.Particle;

/**
 * Created by xgxg on 2017/8/28.
 */

public abstract class ParticleFactory {
    public abstract Particle[][] generateParticles(Bitmap bitmap,Rect rect);
}

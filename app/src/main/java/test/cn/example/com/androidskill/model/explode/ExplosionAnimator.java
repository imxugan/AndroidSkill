package test.cn.example.com.androidskill.model.explode;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import test.cn.example.com.androidskill.inter.ParticleFactory;

/**
 * Created by xgxg on 2017/8/28.
 */

public class ExplosionAnimator extends ValueAnimator {
    public static final int DEFAULT_DURATION = 0x400;

    private Particle[][] particles;

    private Paint paint;

    private View container;

    public ExplosionAnimator(View view , Bitmap bitmap, Rect rect,ParticleFactory particleFactory){
        this.paint = new Paint();
        this.container = view;
        setFloatValues(0.0f,1.0f);
        setDuration(DEFAULT_DURATION);
        particles = particleFactory.generateParticles(bitmap,rect);
    }

    public void draw(Canvas canvas){
        if(!isStarted()){
            return;
        }

        for (Particle[] particle:particles) {
            for (Particle p:particle){
                p.advance(canvas,paint,(float)getAnimatedValue());
            }
        }

        container.invalidate();
    }

    @Override
    public void start() {
        super.start();
        container.invalidate();
    }
}

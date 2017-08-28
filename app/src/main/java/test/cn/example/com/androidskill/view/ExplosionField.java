package test.cn.example.com.androidskill.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import test.cn.example.com.androidskill.inter.ParticleFactory;
import test.cn.example.com.androidskill.model.ExplosionAnimator;
import test.cn.example.com.util.BitmapUtils;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/28.
 */

public class ExplosionField extends View {
    private ArrayList<ExplosionAnimator> explosionAnimators;
    private HashMap<View,ExplosionAnimator> explosionAnimatorHashMap;
    private OnClickListener onClickListener;
    private ParticleFactory particleFactory;
    public ExplosionField(Context context, ParticleFactory particleFactory) {
        super(context);
        init(particleFactory);
    }


    public ExplosionField(Context context,AttributeSet attrs,ParticleFactory particleFactory){
        super(context,attrs);
        init(particleFactory);
    }

    private void init(ParticleFactory particleFactory) {
        explosionAnimators = new ArrayList<>();
        explosionAnimatorHashMap = new HashMap<>();
        this.particleFactory = particleFactory;
        att2Activity((Activity)getContext());
    }

    private void att2Activity(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );

        rootView.addView(this,layoutParams);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ExplosionAnimator animator:explosionAnimators){
            animator.draw(canvas);
        }
    }

    public void explode(final View view){
        if(explosionAnimatorHashMap.get(view) != null && explosionAnimatorHashMap.get(view).isStarted()){
            return;
        }

        if(view.getVisibility() != View.VISIBLE || view.getAlpha() == 0){
            return;
        }

        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);//获取view相对于整个屏幕的坐标
        int contentTop = ((ViewGroup)getParent()).getTop();
        Rect frame = new Rect();
        ((Activity)getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        LogUtil.i("statusBarHeight======="+statusBarHeight+"---contentTop="+contentTop);
        rect.offset(0,-contentTop - statusBarHeight);//去掉状态栏高度和标题栏高度,如果不去掉，则会导致爆炸的位置不在原图片的位置
        if(rect.width() == 0 || rect.height() ==0){
            return;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            Random random = new Random();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((random.nextFloat()-0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((random.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                explode(view,rect);
            }
        });
        animator.start();
    }

    private void explode(final View view, Rect rect) {
        final ExplosionAnimator animator = new ExplosionAnimator(this, BitmapUtils.createBitmapFromView(view),
                rect,particleFactory);
        explosionAnimators.add(animator);
        explosionAnimatorHashMap.put(view,animator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //缩小，透明动画
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束时，从动画集合中移除
                view.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(150).start();

                explosionAnimators.remove(animator);
                explosionAnimatorHashMap.remove(view);
            }
        });
        animator.start();
    }

    public void addListener(View view){
        if(view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0;i<count;i++){
                addListener(viewGroup.getChildAt(i));
            }
        }else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }
    }

    private OnClickListener getOnClickListener(){
        if(null == onClickListener){
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExplosionField.this.explode(v);
                }
            };
        }
        return onClickListener;
    }
}

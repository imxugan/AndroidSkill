package test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/16.
 */

public class HencoderPracticeSevenFragment extends Fragment implements View.OnClickListener {
    private int mIndex;
    private CircleView circleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        switch (mIndex){
            case 0:
                final View ll = inflater.inflate(R.layout.fragment_hencode_practice_seven_animation_argb_evaluator, container, false);
                LogUtil.i("ll.getWidth()="+ll.getWidth()+"     ll.getMeasuredWidth()="+ll.getMeasuredWidth());
                ll.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e("ll.getWidth()="+ll.getWidth()+"     ll.getMeasuredWidth()="+ll.getMeasuredWidth());
                    }
                });
                circleView = (CircleView) ll.findViewById(R.id.circleView);
                ll.findViewById(R.id.btn_reset).setOnClickListener(this);
                ll.findViewById(R.id.btn_start).setOnClickListener(this);
                ll.findViewById(R.id.btn_start2).setOnClickListener(this);
                ll.findViewById(R.id.btn_start3).setOnClickListener(this);
                ll.findViewById(R.id.btn_start4).setOnClickListener(this);
                ll.findViewById(R.id.btn_start5).setOnClickListener(this);
                ll.findViewById(R.id.btn_start6).setOnClickListener(this);
                ll.findViewById(R.id.btn_start7).setOnClickListener(this);
                ll.findViewById(R.id.btn_start8).setOnClickListener(this);
                ll.findViewById(R.id.btn_start9).setOnClickListener(this);
                root.addView(ll);
                break;
        }
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.btn_reset:
                //将颜色重置
                circleView.setColor(Color.RED);
                //将位置重置
                circleView.setPoint(new float[]{100,100});

                circleView.setScaleX(1);
                circleView.setScaleY(1);
                circleView.setRotationX(0);
                break;
            case R.id.btn_start:
                LogUtil.i("(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)="+(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP));
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofArgb(circleView,"color",0xffff0000,0xff00ff00);
                    objectAnimator.setDuration(3000);
                    objectAnimator.setInterpolator(new LinearInterpolator());
                    objectAnimator.start();
                }
            case R.id.btn_start2:
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(circleView, "color", 0xffff0000, 0xff00ff00);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                objectAnimator.setDuration(3000);
                objectAnimator.setInterpolator(new LinearInterpolator());
                objectAnimator.start();
                break;
            case R.id.btn_start3:
                ObjectAnimator objectAnimator_hsvEvaluator = ObjectAnimator.ofObject(circleView, "color", new HsvTypeEvaluator(), 0xffff0000, 0xff00ff00);
                objectAnimator_hsvEvaluator.setDuration(3000);
                objectAnimator_hsvEvaluator.setInterpolator(new LinearInterpolator());
                objectAnimator_hsvEvaluator.start();
                break;
            case R.id.btn_start4:
                int width = circleView.getWidth();
                int height = circleView.getHeight();
                LogUtil.i(""+width+"    "+height);
                int radius = 100;
                ObjectAnimator objectAnimator_pointEvaluator = ObjectAnimator.ofObject(circleView, "point", new PointEvaluator(), new float[]{100, 100}, new float[]{width - radius, height - radius});
                objectAnimator_pointEvaluator.setDuration(3000);
                objectAnimator_pointEvaluator.setInterpolator(new LinearInterpolator());
                objectAnimator_pointEvaluator.start();
                break;
            case R.id.btn_start5:
                int circleViewWidth = circleView.getWidth();
                int circleViewHeight = circleView.getHeight();
                LogUtil.i(""+circleViewWidth+"    "+circleViewHeight);
                int circleRidus = 100;
                PropertyValuesHolder color_holder = PropertyValuesHolder.ofObject("color",new HsvTypeEvaluator(),0xffff0000,0xff00ff00);
                PropertyValuesHolder point_holder = PropertyValuesHolder.ofObject("point", new PointEvaluator(), new float[]{100, 100}, new float[]{circleViewWidth - circleRidus, circleViewHeight - circleRidus});
                ObjectAnimator objectAnimator_propertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(circleView, color_holder, point_holder);
                objectAnimator_propertyValuesHolder.setDuration(3000);
                objectAnimator_propertyValuesHolder.setInterpolator(new LinearInterpolator());
                objectAnimator_propertyValuesHolder.start();
                break;
            case R.id.btn_start6:
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(circleView, "scaleX", 1,0.5f,1);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(circleView, "scaleY", 1, 0.5f, 1);
                ObjectAnimator rotationX = ObjectAnimator.ofFloat(circleView, "rotationX", 60);
                animatorSet.play(scaleX).before(scaleY).before(rotationX);
                animatorSet.setDuration(3000);
                animatorSet.setInterpolator(new LinearInterpolator());
                animatorSet.start();
                break;
            case R.id.btn_start7:
                AnimatorSet animatorSet1 = new AnimatorSet();
                ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(circleView, "scaleX", 1,0.5f,1);
                ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(circleView, "scaleY", 1, 0.5f, 1);
                ObjectAnimator rotationX1 = ObjectAnimator.ofFloat(circleView, "rotationX", 60);
                animatorSet1.play(scaleX1).after(scaleY1).after(rotationX1);
                animatorSet1.setDuration(3000);
                animatorSet1.setInterpolator(new LinearInterpolator());
                animatorSet1.start();
                break;
            case R.id.btn_start8:
                AnimatorSet animatorSet2 = new AnimatorSet();
                ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(circleView, "scaleX", 1, 0.5f, 1);
                ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(circleView, "scaleY", 1, 0.5f, 1);
                ObjectAnimator rotationX2 = ObjectAnimator.ofFloat(circleView, "rotationX", 60);
                animatorSet2.playSequentially(scaleX2,scaleY2,rotationX2);
                animatorSet2.setDuration(3000);
                animatorSet2.setInterpolator(new LinearInterpolator());
                animatorSet2.start();
                break;
            case R.id.btn_start9:
                AnimatorSet animatorSet3 = new AnimatorSet();
                ObjectAnimator scaleX3 = ObjectAnimator.ofFloat(circleView, "scaleX", 1, 0.5f, 1);
                ObjectAnimator scaleY3 = ObjectAnimator.ofFloat(circleView, "scaleY", 1, 0.5f, 1);
                ObjectAnimator rotationX3 = ObjectAnimator.ofFloat(circleView, "rotationX", 60);
                animatorSet3.playTogether(scaleX3,scaleY3,rotationX3);
                animatorSet3.setDuration(3000);
                animatorSet3.setInterpolator(new LinearInterpolator());
                animatorSet3.start();
                break;
        }
    }
}

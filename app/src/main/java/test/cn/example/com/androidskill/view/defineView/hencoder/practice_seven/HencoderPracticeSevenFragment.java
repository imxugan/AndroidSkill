package test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
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
                View ll = inflater.inflate(R.layout.fragment_hencode_practice_seven_animation_argb_evaluator, container, false);
                circleView = (CircleView) ll.findViewById(R.id.circleView);
                ll.findViewById(R.id.btn_reset).setOnClickListener(this);
                ll.findViewById(R.id.btn_start).setOnClickListener(this);
                ll.findViewById(R.id.btn_start2).setOnClickListener(this);
                ll.findViewById(R.id.btn_start3).setOnClickListener(this);
                ll.findViewById(R.id.btn_start4).setOnClickListener(this);
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
        }
    }
}

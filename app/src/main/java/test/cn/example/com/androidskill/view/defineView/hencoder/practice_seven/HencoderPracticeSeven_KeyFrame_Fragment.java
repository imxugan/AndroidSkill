package test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven;

import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/16.
 */
public class HencoderPracticeSeven_KeyFrame_Fragment extends Fragment implements View.OnClickListener {
    private int mIndex;
    private CustomProgressView customProgressView;
    private Button btn_start2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        final View ll = inflater.inflate(R.layout.fragment_hencode_practice_seven_animation_key_frame, container, false);
        customProgressView = (CustomProgressView) ll.findViewById(R.id.progressView);
        ll.findViewById(R.id.btn_reset).setOnClickListener(this);
        ll.findViewById(R.id.btn_start).setOnClickListener(this);
        btn_start2 = (Button) ll.findViewById(R.id.btn_start2);
        btn_start2.setOnClickListener(this);
        root.addView(ll);
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.btn_reset:
                //将进度重置
                customProgressView.setProgress(0);
                break;
            case R.id.btn_start:
                Keyframe keyframe = Keyframe.ofFloat(0,0);
                Keyframe keyframe1 = Keyframe.ofFloat(0.5f,100);
                Keyframe keyframe2 = Keyframe.ofFloat(1,80);
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("progress", keyframe, keyframe1, keyframe2);
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(customProgressView, propertyValuesHolder);
                objectAnimator.setDuration(3000);
                objectAnimator.setInterpolator(new LinearInterpolator());
                objectAnimator.start();
                break;
            case R.id.btn_start2:
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
                valueAnimator.setDuration(1000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        /**
                         * 通过这样一个监听事件，我们就可以获取
                         * 到ValueAnimator每一步所产生的值。
                         *
                         * 通过调用getAnimatedValue()获取到每个时间因子所产生的Value。
                         * */
                        Integer animatedValue = (Integer) valueAnimator.getAnimatedValue();
                        float fraction = valueAnimator.getAnimatedFraction();
                        LogUtil.i(Thread.currentThread().getId()+"   animatedValue  "+animatedValue+"   fraction  "+fraction);
                        //这里btn_start2.setText(animatiedValue+"");这里animatedValue后面一定要带上""，否则会报错，
                        //毕竟animatedValue是Integer类型不是String类型
                        btn_start2.setText(animatedValue+"");
                    }
                });
                valueAnimator.start();
                break;
        }
    }
}

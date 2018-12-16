package test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
public class HencoderPracticeSeven_KeyFrame_Fragment extends Fragment implements View.OnClickListener {
    private int mIndex;
    private CustomProgressView customProgressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        final View ll = inflater.inflate(R.layout.fragment_hencode_practice_seven_animation_key_frame, container, false);
        customProgressView = (CustomProgressView) ll.findViewById(R.id.progressView);
        ll.findViewById(R.id.btn_reset).setOnClickListener(this);
        ll.findViewById(R.id.btn_start).setOnClickListener(this);
        ll.findViewById(R.id.btn_start2).setOnClickListener(this);
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
        }
    }
}

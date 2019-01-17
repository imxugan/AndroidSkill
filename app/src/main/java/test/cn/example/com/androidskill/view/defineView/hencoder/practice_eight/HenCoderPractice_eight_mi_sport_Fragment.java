package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/1/17.
 */

public class HenCoderPractice_eight_mi_sport_Fragment extends Fragment implements View.OnClickListener {

    private ViewPropertyAnimator animate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color, container, false);
        inflater.inflate(R.layout.fragment_hencoder_practice_eight_mi_sport, root, true);
        final MiSportView miSportView = (MiSportView) root.findViewById(R.id.miSportView);
        root.findViewById(R.id.btn_start).setOnClickListener(this);
        root.findViewById(R.id.btn_reset).setOnClickListener(this);
        animate = miSportView.animate();
        animate.rotation(720);
        animate.setDuration(3000);
        animate.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                miSportView.setConnected(true);
            }
        });
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                LogUtil.i("btn_start");
                animate.rotation(720);
                animate.setDuration(3000);
                break;
            case R.id.btn_reset:
                animate.rotation(0);
                break;
        }
    }
}

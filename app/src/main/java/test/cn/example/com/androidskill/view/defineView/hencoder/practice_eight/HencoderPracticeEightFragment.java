package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

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
import android.widget.SeekBar;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.util.DensityUtil;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven.CircleView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven.HsvTypeEvaluator;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_seven.PointEvaluator;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/16.
 */

public class HencoderPracticeEightFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private int mIndex;
    private SquareImageView squreImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        switch (mIndex){
            case 0:
                final View ll = inflater.inflate(R.layout.fragment_hencode_practice_eight_on_measure, container, false);
                squreImageView = (SquareImageView) ll.findViewById(R.id.squreImageView);
                SeekBar seekBar = (SeekBar) ll.findViewById(R.id.seekBar);
                seekBar.setOnSeekBarChangeListener(this);
                root.addView(ll);
                break;
        }
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) squreImageView.getLayoutParams();
        switch (seekBar.getId()){
            case R.id.seekBar:
                LogUtil.e(""+progress);
                layoutParams.width = DensityUtil.dp2px(getActivity(),progress);
                break;
        }
        squreImageView.setLayoutParams(layoutParams);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

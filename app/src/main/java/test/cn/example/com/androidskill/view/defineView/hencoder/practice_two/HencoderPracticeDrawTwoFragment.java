package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawArcView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawCircleView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawColorView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawHistoryView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawLineView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawOvalView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawPathView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawPieView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawPointView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawRectView;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/12.
 */

public class HencoderPracticeDrawTwoFragment extends Fragment {
    private int mIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        switch (mIndex){
            case 0:
                SetColorView setColorView = new SetColorView(getActivity());
                root.addView(setColorView);
                break;
            case 1:
                SetShaderView setShaderView = new SetShaderView(getActivity());
                root.addView(setShaderView);
                break;
            case 2:
                SetShaderView2 setShaderView2 = new SetShaderView2(getActivity());
                root.addView(setShaderView2);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
        }
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }
}

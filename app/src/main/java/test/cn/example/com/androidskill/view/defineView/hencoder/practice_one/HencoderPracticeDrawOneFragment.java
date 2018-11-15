package test.cn.example.com.androidskill.view.defineView.hencoder.practice_one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawColorView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawCircleView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawRectView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawPointView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawOvalView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawLineView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawArcView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawHistoryView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawPathView;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_one.DrawPieView;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/12.
 */

public class HencoderPracticeDrawOneFragment extends Fragment {
    private int mIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        switch (mIndex){
            case 0:
                DrawColorView drawColorView = new DrawColorView(getActivity());
                root.addView(drawColorView);
                break;
            case 1:
                DrawCircleView drawCircleView = new DrawCircleView(getActivity());
                root.addView(drawCircleView);
                break;
            case 2:
                DrawRectView drawRectView = new DrawRectView(getActivity());
                root.addView(drawRectView);
                break;
            case 3:
                DrawPointView drawPointView = new DrawPointView(getActivity());
                root.addView(drawPointView);
                break;
            case 4:
                DrawOvalView drawOvalView = new DrawOvalView(getActivity());
                root.addView(drawOvalView);
                break;
            case 5:
                DrawLineView customview = new DrawLineView(getActivity());
                root.addView(customview);
                break;
            case 6:
                DrawArcView drawArcView = new DrawArcView(getActivity());
                root.addView(drawArcView);
                break;
            case 7:
                DrawPathView drawPathView = new DrawPathView(getActivity());
                root.addView(drawPathView);
                break;
            case 8:
                DrawHistoryView drawHistoryView = new DrawHistoryView(getActivity());
                root.addView(drawHistoryView);
                break;
            case 9:
                DrawPieView drawPieView = new DrawPieView(getActivity());
                root.addView(drawPieView);
                break;
        }
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }
}

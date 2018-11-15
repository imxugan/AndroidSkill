package test.cn.example.com.androidskill.view.defineView.hencoder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.defineView.CustomView;
import test.cn.example.com.androidskill.view.defineView.hencoder.customview.CustomView0;
import test.cn.example.com.androidskill.view.defineView.hencoder.customview.CustomView1;
import test.cn.example.com.androidskill.view.defineView.hencoder.customview.CustomView2;
import test.cn.example.com.androidskill.view.defineView.hencoder.customview.CustomView3;
import test.cn.example.com.androidskill.view.defineView.hencoder.customview.CustomView4;
import test.cn.example.com.androidskill.view.defineView.hencoder.customview.CustomView5;
import test.cn.example.com.androidskill.view.defineView.hencoder.customview.CustomView6;
import test.cn.example.com.androidskill.view.defineView.hencoder.customview.DrawPathView;
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
                CustomView0 customView0 = new CustomView0(getActivity());
                root.addView(customView0);
                break;
            case 1:
                CustomView1 customView1 = new CustomView1(getActivity());
                root.addView(customView1);
                break;
            case 2:
                CustomView2 customView2 = new CustomView2(getActivity());
                root.addView(customView2);
                break;
            case 3:
                CustomView3 customView3 = new CustomView3(getActivity());
                root.addView(customView3);
                break;
            case 4:
                CustomView4 customView4 = new CustomView4(getActivity());
                root.addView(customView4);
                break;
            case 5:
                CustomView5 customview5 = new CustomView5(getActivity());
                root.addView(customview5);
                break;
            case 6:
                CustomView6 customView6 = new CustomView6(getActivity());
                root.addView(customView6);
                break;
            case 7:
                DrawPathView drawPathView = new DrawPathView(getActivity());
                root.addView(drawPathView);
                break;
        }
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }
}

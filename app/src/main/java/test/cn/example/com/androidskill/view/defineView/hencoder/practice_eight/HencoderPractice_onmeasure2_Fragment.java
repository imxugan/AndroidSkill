package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/19.
 */

public class HencoderPractice_onmeasure2_Fragment extends Fragment{
    private int mIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_eight_onmeasure2,container,false);
        MyView myView = new MyView(getActivity());
        ViewGroup.LayoutParams layoutParams = myView.getLayoutParams();
        if(null != layoutParams){
            LogUtil.e(""+layoutParams.width+"   "+layoutParams.height);
        }else {
            LogUtil.e(""+layoutParams);
        }

        root.addView(myView);
        root.addView(ll);

        layoutParams = myView.getLayoutParams();
        if(null != layoutParams){
            LogUtil.e("root.addView(myView)后   "+layoutParams.width+"   "+layoutParams.height);
        }else {
            LogUtil.e("root.addView(myView)后   "+layoutParams);
        }
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

}

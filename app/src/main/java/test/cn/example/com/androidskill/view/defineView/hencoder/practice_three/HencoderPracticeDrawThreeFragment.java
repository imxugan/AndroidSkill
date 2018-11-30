package test.cn.example.com.androidskill.view.defineView.hencoder.practice_three;

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
 * Created by xugan on 2018/11/12.
 */

public class HencoderPracticeDrawThreeFragment extends Fragment {
    private int mIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        switch (mIndex){
            case 0:
                DrawTextVew drawTextVew = new DrawTextVew(getActivity());
                root.addView(drawTextVew);
                break;
            case 1:
                DrawTextView2 drawTextView2 = new DrawTextView2(getActivity());
                root.addView(drawTextView2);
                break;
        }
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }
}

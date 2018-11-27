package test.cn.example.com.androidskill.view.defineView.hencoder.practice_two;

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
                ComposeShaderView composeShaderView = new ComposeShaderView(getActivity());
                root.addView(composeShaderView);
                break;
            case 4:
                ColorFilterView colorFilterView = new ColorFilterView(getActivity());
                root.addView(colorFilterView);
                break;
            case 5:
                XfermodeView xfermodeView = new XfermodeView(getActivity());
                root.addView(xfermodeView);
                break;
            case 6:
                SetStokeXXView setStokeXXView = new SetStokeXXView(getActivity());
                root.addView(setStokeXXView);
                break;
            case 7:
                PathEffectView pathEffectView = new PathEffectView(getActivity());
                root.addView(pathEffectView);
                break;
            case 8:
                PathEffectView2 pathEffectView2 = new PathEffectView2(getActivity());
                root.addView(pathEffectView2);
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

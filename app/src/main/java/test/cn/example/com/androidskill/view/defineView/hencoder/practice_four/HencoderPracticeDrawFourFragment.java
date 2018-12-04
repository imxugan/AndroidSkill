package test.cn.example.com.androidskill.view.defineView.hencoder.practice_four;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_three.DrawTextVew;
import test.cn.example.com.androidskill.view.defineView.hencoder.practice_three.DrawTextView2;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/11/12.
 */

public class HencoderPracticeDrawFourFragment extends Fragment {
    private int mIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("mIndex="+mIndex);
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);
        switch (mIndex){
            case 0:
                CanvasAssistantView canvasAssistantView = new CanvasAssistantView(getActivity());
                root.addView(canvasAssistantView);
                break;
            case 1:
                CanvasAssistantView2 canvasAssistantView2 = new CanvasAssistantView2(getActivity());
                root.addView(canvasAssistantView2);
                break;
            case 2:
                CanvasAssistantView3 canvasAssistantView3 = new CanvasAssistantView3(getActivity());
                root.addView(canvasAssistantView3);
                break;
            case 3:
                CanvasAssistantView4 canvasAssistantView4 = new CanvasAssistantView4(getActivity());
                root.addView(canvasAssistantView4);
                break;
            case 4:
                CanvasAssistantView5 canvasAssistantView5 = new CanvasAssistantView5(getActivity());
                root.addView(canvasAssistantView5);
                break;
            case 5:
                CanvasAssistantView6 canvasAssistantView6 = new CanvasAssistantView6(getActivity());
                root.addView(canvasAssistantView6);
                break;
        }
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }
}

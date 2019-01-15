package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/12/21.
 */

public class HencoderPractice_eight_rule_Fragment extends Fragment implements View.OnClickListener {
    private int mIndex;
    private RuleView ruleView;
    private RuleView2 ruleView2;
    private int count = 0,count2 = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_eight_rule,container,false);
        ruleView = (RuleView) ll.findViewById(R.id.ruleView);
        ll.findViewById(R.id.btn_add).setOnClickListener(this);
        ll.findViewById(R.id.btn_decrease).setOnClickListener(this);
        ruleView2 = (RuleView2) ll.findViewById(R.id.ruleView2);
        ll.findViewById(R.id.btn_add2).setOnClickListener(this);
        ll.findViewById(R.id.btn_decrease2).setOnClickListener(this);
        root.addView(ll);
        return root;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                count+=1;
                ruleView.setOffset(count);
                break;
            case R.id.btn_decrease:
                count-=1;
                ruleView.setOffset(count);
                break;
            case R.id.btn_add2:
                count2+=10;
                ruleView2.setOffset(count2);
                break;
            case R.id.btn_decrease2:
                count2-=5;
                ruleView2.setOffset(count2);
                break;
        }
    }
}

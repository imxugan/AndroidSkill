package test.cn.example.com.androidskill.view.defineView.hencoder.practice_eight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2018/12/19.
 */

public class HencoderPractice_eight_comment_Fragment extends Fragment implements View.OnClickListener {
    private int mIndex;
    private CommentView commentView;
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_one_draw_color,container,false);

        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_hencode_practice_eight_comment,container,false);
        commentView = (CommentView) ll.findViewById(R.id.commentView);
        ll.findViewById(R.id.btn_add).setOnClickListener(this);
        ll.findViewById(R.id.btn_decrease).setOnClickListener(this);
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
                count++;
                commentView.setNumber(count);
                break;
            case R.id.btn_decrease:
                if(count<=0){
                    count = 0;
                }else {
                    count--;
                }
                commentView.setNumber(count);
                break;
        }
    }
}

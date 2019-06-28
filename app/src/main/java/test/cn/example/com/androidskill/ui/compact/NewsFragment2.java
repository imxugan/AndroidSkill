package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/27.
 */

public class NewsFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e(""+container);
        String data = getArguments().getString("data");
        View view = inflater.inflate(R.layout.item_coordinatorlayout_fragment, container, false);
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText(data);
        return view;
    }
}

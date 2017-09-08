package test.cn.example.com.androidskill.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.cn.example.com.androidskill.R;

/**
 * Created by xgxg on 2017/9/8.
 */

public class CustomContainerFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_custom_container_2,container,false);
        return layout;
    }
}

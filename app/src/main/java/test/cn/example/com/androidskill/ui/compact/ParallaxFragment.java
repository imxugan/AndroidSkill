package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xugan on 2019/7/8.
 */

public class ParallaxFragment extends Fragment {
    private List<View> viewlist = new ArrayList<View>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout_id = getArguments().getInt("layout_id");
        ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(inflater,getActivity(),this);
        return parallaxLayoutInflater.inflate(layout_id,null);
    }

    public List<View> getViews() {
        return viewlist;
    }
}

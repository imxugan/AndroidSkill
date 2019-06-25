package test.cn.example.com.androidskill.ui.compact;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by xugan on 2019/6/25.
 */

public class NewsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String title = bundle.getString("data");
        TextView textView = new TextView(getContext());
        textView.setText(title);
        textView.setBackgroundColor(Color.argb((int)Math.random()*255,(int)Math.random()*255,(int)Math.random()*255,(int)Math.random()*255));
        return textView;
    }
}

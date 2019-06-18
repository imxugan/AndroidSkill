package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/17.
 */

public class WraperRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wraper_recyclerview);
        WraperRecyclerView wraperRecyclerView = findViewById(R.id.recyclerView);
        List data = new ArrayList();
        for(int i=0;i<15;i++){
            data.add("item  "+i);
        }
        TextView header = new TextView(this);
        header.setText("heaer view");
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(layoutParams);
        wraperRecyclerView.addHeaderView(header);

        TextView footer = new TextView(this);
        footer.setText("footer viwe");
        footer.setLayoutParams(layoutParams);
        wraperRecyclerView.addFooterView(footer);

        wraperRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        wraperRecyclerView.setAdapter(new MyRecyclerAdapter(this,data));

    }}

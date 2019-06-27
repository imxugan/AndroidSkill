package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/27.
 */

public class CoordinatorLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerview = findViewById(R.id.recyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = new ArrayList<>();
        for(int i=0;i<20;i++){
            data.add("item  "+i);
        }
        recyclerview.setAdapter(new MyRecyclerAdapter3(this,data));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "开启加速包吗？", Snackbar.LENGTH_SHORT);
                snackbar.setAction("确定",new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(),"666",Toast.LENGTH_SHORT).show();
                    }
                });
                snackbar.show();
            }
        });
    }
}

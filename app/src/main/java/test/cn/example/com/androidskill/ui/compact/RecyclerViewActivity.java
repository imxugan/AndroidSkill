package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/12.
 */

public class RecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private DividerGridViewItemDecoraton dividerGridViewItemDecoraton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        findViewById(R.id.btn_horizontal).setOnClickListener(this);
        findViewById(R.id.btn_vertical).setOnClickListener(this);
        findViewById(R.id.btn_grid).setOnClickListener(this);
        findViewById(R.id.btn_staggederedGrid).setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this,3);
        dividerGridViewItemDecoraton = new DividerGridViewItemDecoraton(this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(i+"");
        }

        myRecyclerAdapter = new MyRecyclerAdapter(this,data);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_horizontal:
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(myRecyclerAdapter);
                break;
            case R.id.btn_vertical:
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(myRecyclerAdapter);
                break;
            case R.id.btn_grid:
                recyclerView.removeItemDecoration(dividerGridViewItemDecoraton);
                recyclerView.addItemDecoration(dividerGridViewItemDecoraton);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(myRecyclerAdapter);
                break;
            case R.id.btn_staggederedGrid:
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                recyclerView.setAdapter(myRecyclerAdapter);
                break;
        }
    }
}

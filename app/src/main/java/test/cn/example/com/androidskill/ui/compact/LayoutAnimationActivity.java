package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/6/21.
 */

public class LayoutAnimationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutanimator);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            data.add(i+"abc");
        }
        MyRecyclerAdapter2 myRecyclerAdapter = new MyRecyclerAdapter2(this,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerAdapter);
        ILayoutAnimationController.setLayoutAnimation(recyclerView,R.anim.left_enter,0.8f,ILayoutAnimationController.IndexAlgorithm.INDEXSIMPLEPENDULUM);
    }
}

package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/27.
 */

public class FloatingActionButtonHideShowActivity extends AppCompatActivity implements IScrollListener{

    private ImageButton fab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_show_hide);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("标题栏");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = new ArrayList<>();
        for(int i=0;i<20;i++){
            data.add("item  "+i);
        }
        recyclerView.setAdapter(new MyRecyclerAdapter3(this,data));
        recyclerView.addOnScrollListener(new FabOnScrollListener(this));
        fab = findViewById(R.id.fab);
    }

    @Override
    public void show() {
        LogUtil.i("show");
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    }

    @Override
    public void hide() {
        LogUtil.i("hide");
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(3));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        fab.animate().translationY(fab.getHeight()+layoutParams.bottomMargin).setInterpolator(new AccelerateInterpolator(3));
    }

}

package test.cn.example.com.androidskill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import test.cn.example.com.androidskill.model.loadingIndicator.BallBeatIndicator;
import test.cn.example.com.androidskill.model.loadingIndicator.BallPulseIndicator;
import test.cn.example.com.androidskill.model.loadingIndicator.Indicator;
import test.cn.example.com.androidskill.model.loadingIndicator.IndicatorHolder;

/**
 * 各种loading效果
 * https://github.com/81813780/AVLoadingIndicatorView
 * Created by xgxg on 2017/8/30.
 */
public class LoadingIndicatorActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_indicator);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new RecyclerView.Adapter<IndicatorHolder>(){

            @Override
            public IndicatorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.item_indicator,parent,false);
                return new IndicatorHolder(view);
            }

            @Override
            public void onBindViewHolder(IndicatorHolder holder, final int position) {

                holder.indicatorView.setIndicator(INDICATORS_OBJECT[position]);
                holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoadingIndicatorActivity.this,IndicatorActivity.class);
                        intent.putExtra("indicator",INDICATORS_STRING[position]);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return INDICATORS_STRING.length;
            }
        });

    }

    private static final String[] INDICATORS_STRING = new String[]{
            "BallPulseIndicator",
            "BallBeatIndicator"
    };

    private static final Indicator[] INDICATORS_OBJECT = new Indicator[]{
            new BallPulseIndicator(),
            new BallBeatIndicator()
    };

}

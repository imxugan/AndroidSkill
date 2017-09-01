package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.model.loadingIndicator.BallPulseIndicator;
import test.cn.example.com.androidskill.view.loadingIndicator.LoadingIndicatorView;

/**
 * 各种loading效果
 * https://github.com/81813780/AVLoadingIndicatorView
 * Created by xgxg on 2017/8/30.
 */
public class IndicatorActivity extends AppCompatActivity {
    private LoadingIndicatorView loadingIndicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        initView();
    }

    private void initView() {
        loadingIndicatorView = (LoadingIndicatorView) findViewById(R.id.loadingIndicatorView);
        loadingIndicatorView.setIndicator(new BallPulseIndicator());
//        loadingIndicatorView.setIndicator("BallBeatIndicator");//通过这种方式，如果BallBeatIndicator和LoadingIndicatorView
        //不在同一个包下，会出现BallBeatIndicator这个类无法找到异常
    }

    public void hideClick(View view) {
        loadingIndicatorView.hide();
        // or avi.smoothToHide();
    }

    public void showClick(View view) {
        loadingIndicatorView.show();
        // or avi.smoothToShow();
    }
}

package test.cn.example.com.androidskill.screenAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

public class ScreenAdapterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_adapter);
        DisplayMetrics metrics = DensityUtil.getDisplayMetrics(this);
        LogUtil.i("density= "+metrics.density);
        LogUtil.i("densityDpi= "+metrics.densityDpi);
        LogUtil.i(metrics.widthPixels+"         "+metrics.heightPixels);
        final ImageView iv_1 = findViewById(R.id.iv_1);
        final ImageView iv_2 = findViewById(R.id.iv_2);
        iv_1.post(new Runnable() {
            @Override
            public void run() {
                int width = iv_1.getWidth();
                int height = iv_1.getHeight();
                LogUtil.i("iv_1     "+width+"       "+height);
            }
        });
        iv_2.post(new Runnable() {
            @Override
            public void run() {
                int width = iv_2.getWidth();
                int height = iv_2.getHeight();
                LogUtil.i("iv_2     "+width+"       "+height);
            }
        });

    }
}

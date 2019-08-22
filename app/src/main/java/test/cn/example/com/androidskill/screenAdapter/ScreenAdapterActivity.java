package test.cn.example.com.androidskill.screenAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

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
    }
}

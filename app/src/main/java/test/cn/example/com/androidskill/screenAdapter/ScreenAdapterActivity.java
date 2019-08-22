package test.cn.example.com.androidskill.screenAdapter;

import android.util.DisplayMetrics;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

public class ScreenAdapterActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_adapter;
    }

    @Override
    public int getMyDefinedEmptyLayoutId() {
        return 0;
    }

    @Override
    public int getMyDefinedErrorLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        DisplayMetrics metrics = DensityUtil.getDisplayMetrics(this);
        LogUtil.i("density= "+metrics.density);
        LogUtil.i("densityDpi= "+metrics.densityDpi);
        LogUtil.i(metrics.widthPixels+"         "+metrics.heightPixels);
    }
}

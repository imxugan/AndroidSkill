package test.cn.example.com.androidskill.ui.compact;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import test.cn.example.com.androidskill.R;

public class BaseActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
    }

    // 设置app字体不随系统字体设置改变
    @Override
    public Resources getResources() {
        Resources mResources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        mResources.updateConfiguration(config, mResources.getDisplayMetrics());
        return mResources;
    }

}
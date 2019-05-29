package test.cn.example.com.androidskill.optimize.service_keep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/5/28.
 */

public class KeepLiveActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("onCreate");
        Window window = getWindow();
        window.setGravity(Gravity.LEFT|Gravity.TOP);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.height = 1;
        attributes.width = 1;
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);
        KeepLiveActivityManager.getInstance(this).setKeepLiveActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("onDestroy");
    }
}

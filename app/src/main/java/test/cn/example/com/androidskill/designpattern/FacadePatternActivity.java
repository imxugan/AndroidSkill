package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.Mobile;
import test.cn.example.com.util.LogUtil;

/**
 * 外观(门面)模式
 * Created by xgxg on 2017/7/19.
 */

public class FacadePatternActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facade_pattern);
        Mobile mobile = new Mobile();
        LogUtil.i("测试手机通话功能");
        mobile.call();
        LogUtil.i("测试手机视频聊天功能");
        mobile.videoChat();
        LogUtil.i("测试手机拍照功能");
        mobile.takePicture();
    }

}

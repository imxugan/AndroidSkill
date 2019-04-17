package test.cn.example.com.androidskill.changeSkin;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/10.
 */

public class ChangeSkinActivity extends Activity implements View.OnClickListener {

    private SkinFactory skinFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SkinManager.getInstance().setContext(this);
        skinFactory = new SkinFactory();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        layoutInflater.setFactory2(skinFactory);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_skin);
//        LogUtil.i(Environment.getExternalStorageDirectory().getAbsolutePath());
        findViewById(R.id.btn_change_skin).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void changeSkin(){
        File file = new File(Environment.getExternalStorageDirectory(),"skin.apk");
        SkinManager.getInstance().loadSkinApk(file.getAbsolutePath());
//        LogUtil.i(file.getAbsolutePath());
        skinFactory.apply();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change_skin:
                changeSkin();
                break;
        }
    }
}

package test.cn.example.com.androidskill.optimize.hotfix;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2019/5/30.
 */

public class HotFixActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix);
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_fix).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test:
                MyTest myTest = new MyTest();
                myTest.test(HotFixActivity.this);
                break;
            case R.id.btn_fix:
                fixBug();
                break;
        }
    }

    private void fixBug() {
        //目录：  /data/data/packagename/odex
        File fileDir = getDir(MyConstant.DEX_DIR, Context.MODE_PRIVATE);
        String name = "classes2.dex";
        String filePath = fileDir.getAbsolutePath()+File.separator+name;
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        //将下载到sd的修复了bug的class.dex文件搬到file所在的目录下
        FileInputStream fis = null;
        FileOutputStream fos = null;
        String fixDexPath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+name;
        try {
            fis = new FileInputStream(fixDexPath);
            fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while((len=fis.read(buf))!=-1){
                fos.write(buf,0,len);
                fos.flush();
            }

            File f = new File(filePath);
            if(f.exists()){
                LogUtil.i(f.getAbsolutePath());
                ToastUtils.shortToast(HotFixActivity.this,"文件从sd卡搬运到app内部存储目录成功");
            }

            //热修复
            FixDexUtils.loafFixedDex(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != fos){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

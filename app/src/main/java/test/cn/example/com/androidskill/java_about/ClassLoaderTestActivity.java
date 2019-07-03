package test.cn.example.com.androidskill.java_about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/8/10.
 * classLoader的工作原理以及相关的知识点
 */
public class ClassLoaderTestActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classloader);
        initView();
    }



    private void initView() {
        Button classLoader = (Button)findViewById(R.id.classLoader);
        classLoader.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.classLoader:
                classLoaderTest();
                break;
            default:
                break;
        }
    }

    private void classLoaderTest() {
        ClassLoader c = getClassLoader();
        if(null != c){
            LogUtil.i(c.toString());//打印结果：  dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/test.cn.example.com.androidskill-2.apk"],nativeLibraryDirectories=[/data/app-lib/test.cn.example.com.androidskill-2, /vendor/lib, /data/cust/lib, /data/datalib, /system/lib]]]
            while(null != c.getParent()){
                c = c.getParent();
                LogUtil.i(c.toString());//打印结果：java.lang.BootClassLoader@41b8c330
            }
        }
        ClassLoader classLoader = String.class.getClassLoader();
        LogUtil.i(classLoader.toString());//打印结果：java.lang.BootClassLoader@41b8c330
        ClassLoader int_classLoader =  int.class.getClassLoader();
        LogUtil.i("int_classLoader=====  "+int_classLoader);
        ClassLoader person_classLoader = Person.class.getClassLoader();
        LogUtil.i(person_classLoader.toString());

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        LogUtil.i(systemClassLoader.toString());
        ClassLoader systemClassLoader_parent = ClassLoader.getSystemClassLoader().getParent();
        LogUtil.i(systemClassLoader_parent.toString());
    }

    class Person {}

}

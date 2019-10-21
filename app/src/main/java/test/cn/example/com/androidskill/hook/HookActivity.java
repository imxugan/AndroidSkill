package test.cn.example.com.androidskill.hook;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.designpattern.ProxyPatternActivity;
import test.cn.example.com.androidskill.optimize.hotfix.FixDexUtils2;
import test.cn.example.com.androidskill.optimize.hotfix.MyConstant;
import test.cn.example.com.util.LogUtil;

public class HookActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);

        try {
            //将插件dex合并到DexPathList类的dexElements这个数组中
            copyDexFileToInnerPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        try {
//            HookHelper.hookHandler();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

    }


    private void copyDexFileToInnerPath() throws FileNotFoundException, ClassNotFoundException {
        //模拟下载插件dex文件
        File dir = getDir(MyConstant.DEX_DIR, Context.MODE_PRIVATE);
        LogUtil.i(""+dir);
        String name = "classes3.dex";
        String filePath = dir.getAbsolutePath()+File.separator+name;
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        LogUtil.i(Environment.getExternalStorageDirectory().getAbsolutePath());
        File fixFile = new File(Environment.getExternalStorageDirectory(),name);
        if(null != fixFile){
            LogUtil.i(""+fixFile.getAbsolutePath());
            if(!fixFile.exists()){
                throw new FileNotFoundException(name+"is not exists");
            }
        }

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fixFile));
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            int len = 0;
            byte[] buffer = new byte[1024];

            while (-1!=(len = bis.read(buffer))){
                bos.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != bos){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != bis){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(file.exists()){
            LogUtil.i("文件复制成功       "+file.getAbsolutePath());
        }

        mergeFixDexFile(dir);
    }

    private void mergeFixDexFile(File dir) throws ClassNotFoundException {
        //遍历app内部的存储了修复了bug的dex文件
        File[] files = dir.listFiles();
        File optDirFile = getDir("opt_dex", Context.MODE_PRIVATE);
        if(optDirFile.exists()){
            optDirFile.delete();
        }
        optDirFile.mkdirs();
        LogUtil.i("修复的dex文件的解压路径    "+optDirFile.getAbsolutePath());
        Class<?> baseDexClassLoaderClazz = Class.forName("dalvik.system.BaseDexClassLoader");
        for(File f:files){
            if(f.getName().startsWith("classes") && f.getName().endsWith(".dex")){
                //2.获取DexClassLoader的实例，获取
                try {
                    DexClassLoader dexClassLoader = new DexClassLoader(f.getAbsolutePath(),optDirFile.getAbsolutePath(),"",getClassLoader());
                    Object dexPathList = FixDexUtils2.getObject(baseDexClassLoaderClazz, "pathList",dexClassLoader);

                    PathClassLoader pathClassLoader = (PathClassLoader) getClassLoader();
                    Object pathList = FixDexUtils2.getObject(baseDexClassLoaderClazz, "pathList", pathClassLoader);

                    Class<?> dexPathListClazz = Class.forName("dalvik.system.DexPathList");
                    Object dexElements = FixDexUtils2.getObject(dexPathListClazz, "dexElements", dexPathList);
                    Object pathDexElements = FixDexUtils2.getObject(dexPathListClazz, "dexElements", pathList);

                    int i = Array.getLength(dexElements);
                    int j = Array.getLength(pathDexElements);
                    Object element = Array.get(pathDexElements, 0);
                    Object newElements = Array.newInstance(element.getClass(), i + j);
                    for(int k=0;k<(i+j);k++){
                        if(k<i){
                            Array.set(newElements,k,Array.get(dexElements,k));
                        }else {
                            Array.set(newElements,k,Array.get(pathDexElements,k-i));
                        }
                    }
                    //合并完成后，将新的elements赋值给DexPathList的成员变量dexElements
                    Field dexElementsField = FixDexUtils2.getField(dexPathListClazz, "dexElements");
                    dexElementsField.setAccessible(true);
                    dexElementsField.set(pathList,newElements);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
                try {
                    HookHelper.hookActivityThreadInstrumentation();
                    Intent intent = new Intent(HookActivity.this, ProxyPatternActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_2:
                try {
                    HookHelper.hookActivityInstrumentation(HookActivity.this);

                    startActivity(new Intent(HookActivity.this, ProxyPatternActivity.class));

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.tv_3:
                //演示hook  AMS 时，记得将MyApplication的attachBaseContext方法中的hookActivityThreadInstrumentation();
                //方法注销
                try {
                    //建议放到Application类的attachBaseContext方法中，更好
                    HookHelper.hookAMS();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Intent intent_plugin = new Intent(this, PlugActivity.class);
                intent_plugin.putExtra("data","first plugin test");
                startActivity(intent_plugin);
                break;
            case R.id.tv_4:
                //演示hook  Instrumentation 时，如果MyApplication的attachBaseContext方法中的
                // hookActivityThreadInstrumentation();被注销，记得打开
                Intent intent_plugin_2 = new Intent(this, PlugActivity.class);
                intent_plugin_2.putExtra("data","first plugin test");
                startActivity(intent_plugin_2);
                break;
            case R.id.tv_5:
                Intent intent = new Intent();
                try {
                    Class<?> plugServiceClazz = Class.forName(HookHelper.PACKAGENAME + ".hook.service.PlugService");
                    LogUtil.i(""+plugServiceClazz);
                    Service service = (Service) plugServiceClazz.newInstance();
                    service.onCreate();
                    intent.setClass(this, plugServiceClazz);
                    startService(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }


                break;
        }
    }
}

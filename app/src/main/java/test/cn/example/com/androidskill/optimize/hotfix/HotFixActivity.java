package test.cn.example.com.androidskill.optimize.hotfix;

import android.content.Context;
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
import test.cn.example.com.androidskill.hook.RefInvokeUtils;
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
        findViewById(R.id.btn_fix2).setOnClickListener(this);
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
            case R.id.btn_fix2:
                try {
                    fixBug2();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void fixBug2() throws FileNotFoundException, ClassNotFoundException {
        File dir = getDir(MyConstant.DEX_DIR, Context.MODE_PRIVATE);
        LogUtil.i(""+dir);
        String name = "classes2.dex";
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
                    Object dexPathList = RefInvokeUtils.getObject(baseDexClassLoaderClazz, "pathList",dexClassLoader);

                    PathClassLoader pathClassLoader = (PathClassLoader) getClassLoader();
                    Object pathList = RefInvokeUtils.getObject(baseDexClassLoaderClazz, "pathList", pathClassLoader);

                    Class<?> dexPathListClazz = Class.forName("dalvik.system.DexPathList");
                    Object dexElements = RefInvokeUtils.getObject(dexPathListClazz, "dexElements", dexPathList);
                    Object pathDexElements = RefInvokeUtils.getObject(dexPathListClazz, "dexElements", pathList);

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
                    Field dexElementsField = RefInvokeUtils.getField(dexPathListClazz, "dexElements");
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

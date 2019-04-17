package test.cn.example.com.androidskill.changeSkin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Method;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/16.
 */

public class SkinManager {
    private SkinManager(){}
    private static SkinManager instance = new SkinManager();
    //资源对象
    private Resources resources;
    //上下文
    private Context context;

    private String skinPackage;
    public static SkinManager getInstance(){
        return instance;
    }

    public void loadSkinApk(String path){
        try {
            //获取到皮肤APK的包名
            PackageManager packageManager = context.getPackageManager();
            skinPackage = packageManager.getPackageArchiveInfo(path,packageManager.GET_ACTIVITIES).packageName;
            //获取皮肤APK的资源文件
            AssetManager assetManager = AssetManager.class.newInstance();
            //通过反射获取到AssetManager对象的addAssetPath的方法
            Method method = assetManager.getClass().getMethod("addAssetPath",String.class);
            //执行方法
            method.invoke(assetManager,path);
            //获取外置APP资源对象
            resources = new Resources(assetManager,context.getResources().getDisplayMetrics(),context.getResources().getConfiguration());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Drawable getDrawable(int resId){
        LogUtil.e(resId+"");//宿主apk中需要换肤的资源id
        if(null == resources){
            return ContextCompat.getDrawable(context,resId);
        }
//        LogUtil.e(context.getResources().getResourceName(resId));//test.cn.example.com.androidskill:drawable/bg
        String resType = context.getResources().getResourceTypeName(resId);
        String resName = context.getResources().getResourceEntryName(resId);
        int skinId = resources.getIdentifier(resName,resType,skinPackage);
        //LogUtil.i(""+skinId);//换肤apk中的资源的id
        if(skinId == 0){
            return ContextCompat.getDrawable(context,resId);
        }
        return resources.getDrawable(skinId);
    }

    public void setContext(Context context){
        this.context = context;
    }

    public int getColor(int id){
        if(null == resources){
            return id;
        }
        String resType = context.getResources().getResourceTypeName(id);
        String realName = context.getResources().getResourceEntryName(id);
        //拿到skinId
        int skinId = resources.getIdentifier(realName,resType,skinPackage);
        return resources.getColor(skinId);
    }

}

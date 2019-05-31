package test.cn.example.com.androidskill.optimize.hotfix;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/5/30.
 */

public class FixDexUtils {
    private static HashSet<File> loadedDexSet = new HashSet<>();

    static {
        loadedDexSet.clear();
    }

    public static void loafFixedDex(Context context) {
        if(null == context){
            return;
        }
        //遍历所有的修复的dex
        File dir = context.getDir(MyConstant.DEX_DIR, Context.MODE_PRIVATE);
        File[] listFiles = dir.listFiles();
        for (File file : listFiles) {
            if(file.getName().startsWith("classes") && file.getName().endsWith(".dex")){
                LogUtil.i(file.getName());
                loadedDexSet.add(file);
            }
        }

        doDexInject(context,dir,loadedDexSet);
    }

    private static void doDexInject(Context context, File dir, HashSet<File> loadedDexSet) {
        //1.加载应用程序的dex
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

        //2.加载指定的修复的dex文件
        String optimizePath = dir.getAbsolutePath()+File.separator+"opt_dex";

        File opt_file = new File(optimizePath);
        if(!opt_file.exists()){
            opt_file.mkdirs();
        }
        String optimizedDirectory = opt_file.getAbsolutePath();
        for (File dexFile : loadedDexSet) {
            //String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent
            DexClassLoader dexClassLoader = new DexClassLoader(dexFile.getAbsolutePath(),optimizedDirectory,null,pathClassLoader);
            //3.合并
            try {
                Object dexObject = getPathList(dexClassLoader);
                Object pathObject = getPathList(pathClassLoader);
                Object dexElements = getDexElements(dexObject);//补丁dexElements
                Object pathDexElements = getDexElements(pathObject);//需要被修复的dexElements
                Object combineArray = combineArray(dexElements, pathDexElements);
                LogUtil.i(dexObject+"");
                LogUtil.i(pathObject+"");
                LogUtil.i(dexElements+"");
                LogUtil.i(pathDexElements+"");
                LogUtil.i(combineArray+"");
                //将合并后的dexElements赋值给PathList类中的dexElements这个字段
                setField(pathObject,pathObject.getClass(),"dexElements",combineArray);
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
                e.printStackTrace();
            }
        }


    }

    private static void setField(Object obj,Class<?> aClass,String fieldName,Object value) throws Exception {
        Field field = aClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj,value);
    }

    private static Object getPathList(Object baseDexClassLoader) throws Exception {
       return getFiled(baseDexClassLoader,Class.forName("dalvik.system.BaseDexClassLoader"),"pathList");
    }

    private static Object getFiled(Object baseDexClassLoader, Class<?> aClass, String fieldName) throws Exception {
        Field field = aClass.getDeclaredField(fieldName);//注意这里必须是getDeclaredField
        field.setAccessible(true);
        return field.get(baseDexClassLoader);
    }

    private static Object getDexElements(Object obj) throws Exception {
        return getFiled(obj,obj.getClass(),"dexElements");
    }

    /**
     * 合并两个数组
     * @param arrayLhs  左边的数组
     * @param arrayRhs  右边的数组
     * @return
     */
    private static Object combineArray(Object arrayLhs,Object arrayRhs){
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i+ Array.getLength(arrayRhs);//合并后的数组的长度
        Object result = Array.newInstance(localClass,j);
        for (int k = 0; k < j; ++k) {
            if(k<i){
                Array.set(result,k,Array.get(arrayLhs,k));
            }else {
                Array.set(result,k,Array.get(arrayRhs,k-i));
            }
        }
        return result;
    }
}

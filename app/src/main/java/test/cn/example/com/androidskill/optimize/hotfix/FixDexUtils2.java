package test.cn.example.com.androidskill.optimize.hotfix;

import java.lang.reflect.Field;

public class FixDexUtils2 {
    private FixDexUtils2(){}
    public static Field getField(Class clazz,String fieldName) throws NoSuchFieldException {
        return clazz.getDeclaredField(fieldName);
    }

    public static Object getObject(Class clazz,String fieldName,Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(clazz, fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    public static void setObject(Class clazz,String fieldName,Object obj,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(clazz, fieldName);
        field.setAccessible(true);
        field.set(obj,value);
    }
}

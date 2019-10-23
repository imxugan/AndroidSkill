package test.cn.example.com.androidskill.hook;

import java.lang.reflect.Field;

public class RefInvokeUtils {
    private RefInvokeUtils(){}
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

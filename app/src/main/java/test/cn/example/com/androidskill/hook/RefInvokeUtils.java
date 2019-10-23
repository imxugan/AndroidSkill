package test.cn.example.com.androidskill.hook;

import java.lang.reflect.Field;

public class RefInvokeUtils {
    private RefInvokeUtils(){}
    public static Field getField(Class clazz,String fieldName) throws NoSuchFieldException {
        return clazz.getDeclaredField(fieldName);
    }

    public static Field getField(String className,String fieldName){
        try {
            Class<?> clazz = Class.forName(className);
            Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getObject(String className,String fieldName,Object obj){
        Field field = getField(className, fieldName);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
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

    public static void setObject(String className,String fieldName,Object obj,Object value){
        Field field = getField(className, fieldName);
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

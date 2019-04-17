package test.cn.example.com.androidskill.changeSkin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.util.LogUtil;

/**
 * 换肤工厂类
 * Created by xugan on 2019/4/10.
 */

public class SkinFactory implements LayoutInflater.Factory2 {
    //收集所有需要换肤的控件
    private List<SkinView> viewList = new ArrayList<SkinView>();
    //所有控件的前缀
    private static final String[] prxfixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };
    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {
//        LogUtil.i(name);
        View view = null;
        try {
            //通过反射获取控件的class对象
            Class clazz = context.getClassLoader().loadClass(name);
            //获取构造方法
            Constructor<? extends View> constructor = clazz.getConstructor(new Class[]{Context.class,AttributeSet.class});
            view = constructor.newInstance(context,attributeSet);
        }catch (Exception e){
                e.printStackTrace();
        }
        return view;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        LogUtil.i("name = " + name);
//        int n = attrs.getAttributeCount();
//        for (int i = 0; i < n; i++) {
//            LogUtil.e(attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
//        }
        View view = null;
        //自定义控件和系统控件
        if(name.contains(".")){
            view = onCreateView(name,context,attrs);
        }else {
//            LogUtil.i("name = " + name);
            for (String prx : prxfixList){
                view = onCreateView(prx+name,context,attrs);
                if(null != view){
                    break;
                }
            }
        }
        //如果控件不为空，则收集需要换肤的控件
        if(null != view){
            parseView(view,name,attrs);
        }
        return view;
    }

    private void parseView(View view,String name,AttributeSet attrs) {
        List<SkinItem> items = new ArrayList<>();
        for(int i=0;i<attrs.getAttributeCount();i++){
            //获取属性名
            String attrName = attrs.getAttributeName(i);
            //获取属性值，获取到的是16进制的ID
            String attributeValue = attrs.getAttributeValue(i);
            if(attrName.contains("background") || attrName.contains("textColor") || attrName.contains("src")){
                //获取ID
                int id = Integer.parseInt(attributeValue.substring(1));
                //根据ID获取属性类型
                String typeName = view.getResources().getResourceTypeName(id);
                //根据ID获取属性名称
                String entryName = view.getResources().getResourceEntryName(id);
                LogUtil.i(attrName+"      "+id+"      "+typeName+"       "+entryName);
                SkinItem skinItem = new SkinItem(attrName,id,entryName,typeName);
                items.add(skinItem);
            }
        }

        if(null != items && items.size()>0){
            SkinView sk = new SkinView(view,items);
            viewList.add(sk);
            sk.apply();
        }
    }

    /**
     * 整个app换肤
     */
    public void apply(){
        LogUtil.i("viewList.size()  "+viewList.size());
        for(SkinView skinView:viewList){
            skinView.apply();
        }
    }
}

package test.cn.example.com.androidskill.changeSkin;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * 对单个需要换肤的控件进行封装
 * Created by xugan on 2019/4/16.
 */

public class SkinView {
    private View view;
    //控件的所有属性对象集合
    private List<SkinItem> list;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public SkinView(View view, List<SkinItem> list) {
        this.view = view;
        this.list = list;
    }

    //换肤开关
    public void apply(){
        for(SkinItem skinItem:list){
            if("background".equals(skinItem.getName())){
                if("color".equals(skinItem.getTypeName())){
                    //通过ID 从皮肤APK总获取到同一个资源的属性ID
                    view.setBackgroundColor(SkinManager.getInstance().getColor(skinItem.getResId()));
                }else if("drawable".equals(skinItem.getTypeName()) || "mipmap".equals(skinItem.getTypeName())){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                        view.setBackground(SkinManager.getInstance().getDrawable(skinItem.getResId()));
                    }else {
                        view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(skinItem.getResId()));
                    }
                }
            }else if("textColor".equals(skinItem.getName())){
                if(view instanceof TextView){
                    ((TextView)view).setTextColor(SkinManager.getInstance().getColor(skinItem.getResId()));
                }else if(view instanceof Button){
                    ((Button)view).setTextColor(SkinManager.getInstance().getColor(skinItem.getResId()));
                }
            }

        }
    }
}

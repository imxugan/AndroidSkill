package test.cn.example.com.androidskill.leak;

import android.content.Context;
import android.view.View;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/25.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
        init();
    }

    private void init() {
        MyListenerCollect collect = new MyListenerCollect();
        collect.setListeners(this,myListener);
    }

    public interface MyListener{
        public abstract void callBack();
    }

    public MyListener myListener = new MyListener(){
        public void callBack(){
            LogUtil.i("被调用");
        }
    };
}

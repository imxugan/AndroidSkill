package test.cn.example.com.androidskill.view;

import android.view.View;

/**
 * Created by xgxg on 2017/6/28.
 * 一个view的包装类，用于扩展一下设置view的宽度的方法，
 * view本身的getWidth方法是获取最大或者最小宽度。
 */

public class ViewWrapper {
    private View mTarget;
    public ViewWrapper(View target){
        this.mTarget = target;
    }
    public int getWidth(){
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width){
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}

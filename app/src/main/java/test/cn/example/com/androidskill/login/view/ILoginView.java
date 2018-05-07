package test.cn.example.com.androidskill.login.view;

import test.cn.example.com.androidskill.base.IBaseView;

/**
 * Created by xgxg on 2017/7/17.
 * 处理登录视图逻辑
 */

public interface ILoginView extends IBaseView{
    void upDateCluaseState(boolean state);

    void clearText();

    void onLoginResult(boolean result,int code);

    void onSetProgressBarVisibility(int visibility);

    void showShortToast(String s);
}

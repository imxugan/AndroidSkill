package test.cn.example.com.androidskill.login.presenter;

import test.cn.example.com.androidskill.base.IBasePresenter;

/**
 * Created by xgxg on 2017/7/17.
 */

public interface ILoginPresenter extends IBasePresenter{
    void checkCluaseState(boolean state);
    void clear();
    void login(String name,String psw);
}

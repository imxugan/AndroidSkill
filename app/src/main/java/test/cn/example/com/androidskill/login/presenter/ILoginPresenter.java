package test.cn.example.com.androidskill.login.presenter;

/**
 * Created by xgxg on 2017/7/17.
 */

public interface ILoginPresenter {
    void clear();
    void login(String name,String psw);
    void setProgressBarVisibility(int visibility);
}

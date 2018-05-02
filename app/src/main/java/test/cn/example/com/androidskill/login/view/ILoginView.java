package test.cn.example.com.androidskill.login.view;

/**
 * Created by xgxg on 2017/7/17.
 */

public interface ILoginView {
    public abstract void upDateCluaseState(boolean state);

    public abstract void clearText();

    public abstract void onLoginResult(boolean result,int code);

    public abstract void onSetProgressBarVisibility(int visibility);

    void showShortToast(String s);
}

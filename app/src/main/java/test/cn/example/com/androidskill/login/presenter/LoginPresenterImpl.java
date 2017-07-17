package test.cn.example.com.androidskill.login.presenter;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import test.cn.example.com.androidskill.login.model.IUser;
import test.cn.example.com.androidskill.login.model.UserModel;
import test.cn.example.com.androidskill.login.view.ILoginView;

/**
 * Created by xgxg on 2017/7/17.
 */

public class LoginPresenterImpl implements ILoginPresenter {
    ILoginView iLoginView;
    IUser user;
    Handler handler;
    public LoginPresenterImpl(ILoginView iLoginView){
        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    private void initUser() {
        user = new UserModel("mvp","mvp");
    }

    @Override
    public void clear() {
        iLoginView.clearText();
    }

    @Override
    public void login(String name, String psw) {
        boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name,psw);
        if(code != 1){
            isLoginSuccess = false;
        }
        final boolean result = isLoginSuccess;
        setProgressBarVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result,code);
            }
        },3000);

    }

    @Override
    public void setProgressBarVisibility(int visibility) {
            iLoginView.onSetProgressBarVisibility(visibility);
    }
}

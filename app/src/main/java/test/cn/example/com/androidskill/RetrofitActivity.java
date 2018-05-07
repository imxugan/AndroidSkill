package test.cn.example.com.androidskill;

import android.view.View;

import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.retrofit.presenter.GetUserInfoPresenterImpl;
import test.cn.example.com.androidskill.retrofit.presenter.IGetUserInfoPresenter;
import test.cn.example.com.androidskill.retrofit.view.IGetUserInfoView;

/**
 * Created by xugan on 2018/5/4.
 */

public class RetrofitActivity extends BaseActivity implements View.OnClickListener,IGetUserInfoView{

    private IGetUserInfoPresenter iGetUserInfoPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_retrofit;
    }

    @Override
    public void initView() {
        iGetUserInfoPresenter = new GetUserInfoPresenterImpl(this);
        findViewById(R.id.btn_retrofit_test).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_retrofit_test:
                iGetUserInfoPresenter.getUserInfo("20751339");
                break;
            default:
                break;
        }
    }

}

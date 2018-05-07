package test.cn.example.com.androidskill.retrofit.presenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.cn.example.com.androidskill.base.BaseBean;
import test.cn.example.com.androidskill.base.IBaseView;
import test.cn.example.com.androidskill.retrofit.model.User;
import test.cn.example.com.androidskill.retrofit.request.GetUserInfoRequest;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/5/4.
 */

public class GetUserInfoPresenterImpl implements IGetUserInfoPresenter {
    private final IBaseView iBaseView;

    public GetUserInfoPresenterImpl(IBaseView iBaseView){
        this.iBaseView = iBaseView;
    }
    @Override
    public void getUserInfo(String id) {
        Call<BaseBean<User>> call = GetUserInfoRequest.instance.getUserInfoCall(id);
        call.request();
        //发送网络请求(异步)
        call.enqueue(new Callback<BaseBean<User>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<BaseBean<User>> call, Response<BaseBean<User>> response) {
                //请求处理,输出结果
                BaseBean<User> userBaseBean = response.body();
                LogUtil.i("msg="+userBaseBean.msg+"---code="+userBaseBean.code);
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<BaseBean<User>> call, Throwable throwable) {
                LogUtil.i("连接失败----Thread="+Thread.currentThread().getName());
                iBaseView.onErrorResultView();
            }
        });

    }

    @Override
    public void setProgressBarVisibility(int visibility) {
    }
}

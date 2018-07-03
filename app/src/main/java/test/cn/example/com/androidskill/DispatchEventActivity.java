package test.cn.example.com.androidskill;

import android.view.MotionEvent;
import android.view.View;

import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.retrofit.presenter.GetUserInfoPresenterImpl;
import test.cn.example.com.androidskill.retrofit.presenter.IGetUserInfoPresenter;
import test.cn.example.com.androidskill.retrofit.view.IGetUserInfoView;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/5/4.
 */

public class DispatchEventActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private View rl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dispatch_event;
    }

    @Override
    public int getMyDefinedEmptyLayoutId() {
        return 0;
    }

    @Override
    public int getMyDefinedErrorLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        rl = findViewById(R.id.rl);
        LogUtil.i("rl.isEnabled()======"+rl.isEnabled());
        rl.setOnClickListener(this);
        rl.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl:
                LogUtil.i("onclick");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtil.i("ontouch   ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i("ontouch   ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.i("ontouch   ACTION_UP");
                break;
            default:
                break;
        }
        return false;
    }
}

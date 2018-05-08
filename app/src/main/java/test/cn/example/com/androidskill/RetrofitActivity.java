package test.cn.example.com.androidskill;

import android.view.View;
import android.view.ViewParent;

import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.retrofit.presenter.GetUserInfoPresenterImpl;
import test.cn.example.com.androidskill.retrofit.presenter.IGetUserInfoPresenter;
import test.cn.example.com.androidskill.retrofit.view.IGetUserInfoView;
import test.cn.example.com.util.LogUtil;

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
    public int getMyDefinedEmptyLayoutId() {
        return 0;
    }

    @Override
    public int getMyDefinedErrorLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        iGetUserInfoPresenter = new GetUserInfoPresenterImpl(this);
        findViewById(R.id.btn_retrofit_test).setOnClickListener(this);
        final View parent = (View) currentSubRootView.getParent();
        LogUtil.i(""+parent);
        //当baseActivity中的采用这种
        // currentSubRootView = inflater.inflate(getLayoutId(), root, true);
        // 方式添加currentSubRootView到父布局中时，parent的值是
        //android.support.v7.widget.ContentFrameLayout{29bf99f V.E...... ........ 0,0-1080,1740 #1020002 android:id/content}
        //这种方式导致的后果是，如果currentSubRootView设置为INVISIBLE或者GONE时，整个页面都看不到了


        //当baseActivity中的采用这种
//        currentSubRootView = inflater.inflate(getLayoutId(), root, false);
//        root.addView(currentSubRootView);
        // 方式添加currentSubRootView到父布局中时，parent的值是
        //android.widget.FrameLayout{d16593f V.E...... ......I. 0,0-0,0 #7f0d0067 app:id/root}
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

    @Override
    protected void tryAgain() {
        int random = (int)(Math.random() * 10);
        if(random>=0 && random<=2){
            onErrorResultView();
        }else if(random>=3 && random<=5){
            onEmptyResultView();
        }else {
            onSuccessResultView();
        }
        currentSubRootView.post(
                new Runnable() {
                    @Override
                    public void run() {
                        int width = currentSubRootView.getWidth();
                        int height = currentSubRootView.getHeight();
                        LogUtil.i("width="+width+"---height="+height);
                    }
                }
		);

    }
}

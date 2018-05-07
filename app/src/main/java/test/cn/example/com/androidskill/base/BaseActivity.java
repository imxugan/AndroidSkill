package test.cn.example.com.androidskill.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/5/4.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView{
    protected View currentSubRootView;
    private View view_empty,view_error;
    private int emptyLayoutId;
    private int errorLayoutId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        emptyLayoutId = getMyDefinedEmptyLayoutId();
        if(0== emptyLayoutId){
            emptyLayoutId = R.layout.common_empty;
        }
        errorLayoutId = getMyDefinedErrorLayoutId();
        if(0== errorLayoutId){
            errorLayoutId = R.layout.common_error;
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        currentSubRootView = inflater.inflate(getLayoutId(), root, true);
        initView();
    }

    public abstract int getLayoutId();

    public abstract int getMyDefinedEmptyLayoutId();

    public abstract int getMyDefinedErrorLayoutId();

    public abstract void initView();

    @Override
    public void onSuccessResultView() {

    }

    @Override
    public void onEmptyResultView() {
        if(null == view_empty){
            ViewStub viewstub_empty = (ViewStub) findViewById(R.id.viewstub_empty);
            viewstub_empty.setLayoutResource(emptyLayoutId);
            view_empty = viewstub_empty.inflate();
            LogUtil.i(view_empty.getVisibility()+"======="+View.VISIBLE);
        }else {
            view_empty.setVisibility(View.VISIBLE);
        }

        if(null !=view_error){
            view_error.setVisibility(View.GONE);
        }

        currentSubRootView.setVisibility(View.GONE);
    }

    @Override
    public void onErrorResultView() {
        if(null != view_empty){
            view_empty.setVisibility(View.GONE);
        }
        if(null == view_error){
            ViewStub viewstub_error = ((ViewStub) findViewById(R.id.viewstub_error));
            viewstub_error.setLayoutResource(errorLayoutId);
            view_error = viewstub_error.inflate();
            LogUtil.i(view_error.getVisibility()+"======="+View.VISIBLE+"---parent="+view_error.getParent());
            LogUtil.i("height="+view_error.getHeight()+"-------width="+view_error.getWidth());
        }else {
            view_error.setVisibility(View.VISIBLE);
        }
        currentSubRootView.setVisibility(View.GONE);
    }
}

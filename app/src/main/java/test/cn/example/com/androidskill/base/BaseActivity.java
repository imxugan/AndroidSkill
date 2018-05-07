package test.cn.example.com.androidskill.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2018/5/4.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView{

    protected ViewStub viewstub_empty;
    protected ViewStub viewstub_error;
    protected View currentSubRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        viewstub_empty = (ViewStub) findViewById(R.id.viewstub_empty);
        viewstub_empty.inflate();
        viewstub_error = (ViewStub) findViewById(R.id.viewstub_error);
        viewstub_error.inflate();
        LayoutInflater inflater = LayoutInflater.from(this);
        currentSubRootView = inflater.inflate(getLayoutId(), root, true);
        initView();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    @Override
    public void onSuccessResult() {

    }

    @Override
    public void onEmptyResult() {
        currentSubRootView.setVisibility(View.GONE);
        viewstub_empty.setVisibility(View.VISIBLE);
        viewstub_error.setVisibility(View.GONE);
    }

    @Override
    public void onErrorResult() {

    }
}

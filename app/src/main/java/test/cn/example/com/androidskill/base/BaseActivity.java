package test.cn.example.com.androidskill.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;

import test.cn.example.com.androidskill.R;

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
        setContentView(R.layout.activity_base_common);
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
        //这种方式导致的后果是，如果currentSubRootView设置为INVISIBLE或者GONE时，整个页面都看不到了
//        currentSubRootView = inflater.inflate(getLayoutId(), root, true);
        currentSubRootView = inflater.inflate(getLayoutId(), root, false);
        root.addView(currentSubRootView);
        initView();
    }

    public abstract int getLayoutId();

    public abstract int getMyDefinedEmptyLayoutId();

    public abstract int getMyDefinedErrorLayoutId();

    public abstract void initView();

    @Override
    public void onSuccessResultView() {
        if(null != view_empty){
           view_empty.setVisibility(View.GONE);
        }

        if(null !=view_error){
            view_error.setVisibility(View.GONE);
        }

        currentSubRootView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmptyResultView() {
        if(null == view_empty){
            ViewStub viewstub_empty = (ViewStub) findViewById(R.id.viewstub_empty);
            viewstub_empty.setLayoutResource(emptyLayoutId);
            view_empty = viewstub_empty.inflate();
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
            Button btn_try_aggin = (Button) view_error.findViewById(R.id.btn_try_again);
            if(null != btn_try_aggin){
                btn_try_aggin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tryAgain();
                    }
                });
            }
        }else {
            view_error.setVisibility(View.VISIBLE);
        }
        currentSubRootView.setVisibility(View.GONE);
    }

    protected void tryAgain(){}
}

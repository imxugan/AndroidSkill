package test.cn.example.com.androidskill;


import android.view.View;
import android.widget.TextView;

import test.cn.example.com.androidskill.base.BaseActivity;

/**
 * Created by xugan on 2018/5/4.
 */

public class ViewStubActivity extends BaseActivity {
    /**
     * 给ViewStub直接设置background是无效的
     */

    @Override
    public int getLayoutId() {
        return R.layout.activity_viewstub;
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
        TextView tv_random = (TextView) findViewById(R.id.tv_random);
        int random = (int)(Math.random() * 10);
        tv_random.setText(random+"");
        if(random>=5){
            onErrorResultView();
        }else {
            onEmptyResultView();
        }
        currentSubRootView.setVisibility(View.VISIBLE);
    }
}

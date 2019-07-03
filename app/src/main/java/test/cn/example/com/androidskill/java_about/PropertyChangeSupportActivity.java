package test.cn.example.com.androidskill.java_about;

import android.view.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.base.BaseActivity;
import test.cn.example.com.androidskill.model.SomeBean;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2018/8/1.
 */

public class PropertyChangeSupportActivity extends BaseActivity implements PropertyChangeListener{

    private SomeBean someBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_property_change_support;
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
        someBean = new SomeBean();
        someBean.addPropertyChangeListener(this);
        someBean.setName("李四");
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                someBean.setName("李不是");
                someBean.setAge(190);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        LogUtil.i("PropertyName "+propertyChangeEvent.getPropertyName());
        LogUtil.i("OldValue "+propertyChangeEvent.getOldValue());
        LogUtil.i("NewValue "+propertyChangeEvent.getNewValue());
        LogUtil.i("Source   "+propertyChangeEvent.getSource());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        someBean.removePropertyChangeListener(this);
    }
}

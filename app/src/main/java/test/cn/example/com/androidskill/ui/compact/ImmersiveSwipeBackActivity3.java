package test.cn.example.com.androidskill.ui.compact;

import com.androidskill.base.view.GestureDetectorActivity;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/24.
 */

public class ImmersiveSwipeBackActivity3 extends GestureDetectorActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_immersive_swipeback3;
    }

    @Override
    public void initView() {
        int color = getResources().getColor(R.color.c_ddff90);
        setOrChangeStatusNavigatonState(this,color);
    }
}

package test.cn.example.com.androidskill.ui.compact;

import android.content.Intent;
import android.view.View;

import com.androidskill.base.view.BaseActitivy;
import com.androidskill.base.view.GestureDetectorActivity;

import test.cn.example.com.androidskill.R;

/**
 * Created by xugan on 2019/7/24.
 */

public class ImmersiveSwipeBackActivity extends GestureDetectorActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_immersive_swipeback;
    }

    @Override
    public void initView() {
        int color = getResources().getColor(R.color.colorAccent);
        setOrChangeStatusNavigatonState(this,color);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImmersiveSwipeBackActivity.this,ImmersiveSwipeBackActivity2.class));
            }
        });
    }
}

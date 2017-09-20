package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import test.cn.example.com.androidskill.view.defineView.GestureLockViewGroup;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/19.
 */

public class GestureLockViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock_view);
        GestureLockViewGroup gestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.gestureLockViewGroup);
        gestureLockViewGroup.setOnGestureLockViewSelectedListener(new GestureLockViewGroup.OnGestureLockViewSelectedListener() {
            @Override
            public void onSingleSelected(int id) {
                LogUtil.i("id====="+id);
            }

            @Override
            public void isMatch(boolean result) {
                LogUtil.i("是否解锁====="+result);
            }

            @Override
            public void restMatchTimes(int tryTimes) {
                LogUtil.i("剩余的尝试次数====="+tryTimes);
                if(tryTimes >= 0){
                    Toast.makeText(GestureLockViewActivity.this,"剩余的尝试次数====="+tryTimes,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

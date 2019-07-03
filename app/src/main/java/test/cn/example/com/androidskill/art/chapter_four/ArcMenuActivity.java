package test.cn.example.com.androidskill.art.chapter_four;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.defineView.ArcMenuViewGroup;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/25.
 */

public class ArcMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_menu);
        ArcMenuViewGroup arcMenu = (ArcMenuViewGroup) findViewById(R.id.arcMenu);
        arcMenu.setOnItemClickListener(new ArcMenuViewGroup.OnItemClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.qq:
                        showToast("qq");
                        break;
                    case R.id.qq_music:
                        showToast("qq_music");
                        break;
                    case R.id.test_ic_img_user_default:
                        showToast("test_ic_img_user_default");
                        break;
                    case R.id.wb:
                        showToast("wb");
                        break;
                    case R.id.vx:
                        showToast("vx");
                        break;
                }
            }
        });

        ArcMenuViewGroup arcMenu3 = (ArcMenuViewGroup) findViewById(R.id.arcMenu_3);
        ImageView qq_3 = (ImageView) arcMenu3.findViewById(R.id.qq_3);
        LogUtil.i("qq_3.getId()="+qq_3.getId());
    }

    private void showToast(String msg){
        Toast.makeText(ArcMenuActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

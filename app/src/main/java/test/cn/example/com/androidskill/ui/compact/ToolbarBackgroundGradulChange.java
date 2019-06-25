package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import test.cn.example.com.androidskill.R;

/**
 * Toolbar背景渐变，配合scrollview使用
 * Created by xugan on 2019/6/25.
 */

public class ToolbarBackgroundGradulChange extends AppCompatActivity implements ITranslunceListenter{
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        clipToPadding 如果为false意思是，view内部的padding区也可以显示view。
//        clipChildren 如果为false意思是，布局内部的子view即使出了布局的边界，也可以显示出子view界面。
        setContentView(R.layout.activity_toolbar_background_gradul_change);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Android去除ToolBar中左侧的标题,toolbar左侧的标题，其实是ActionBar的标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MyScrollView myScrollView = findViewById(R.id.myScrollView);
        myScrollView.setmTranslunceListener(this);
    }

    @Override
    public void translunceChangee(float ratio) {
        toolbar.setAlpha(ratio);
    }
}

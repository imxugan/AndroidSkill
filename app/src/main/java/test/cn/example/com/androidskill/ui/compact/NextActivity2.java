package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;

public class NextActivity2 extends CompatStatusBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_next);
        int color = 0xfff2005f;
        toolbar.setBackgroundColor(color);
        setImmersiveStatusBar(true, color);

        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("页面3");

    }
}

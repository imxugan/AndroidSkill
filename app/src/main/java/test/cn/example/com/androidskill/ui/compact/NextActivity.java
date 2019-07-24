package test.cn.example.com.androidskill.ui.compact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import test.cn.example.com.androidskill.R;

public class NextActivity extends CompatStatusBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_next);
        int color = 0xffffffff;
        toolbar.setBackgroundColor(color);
        setImmersiveStatusBar(true, color);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NextActivity.this,NextActivity2.class));
            }
        });
    }
}

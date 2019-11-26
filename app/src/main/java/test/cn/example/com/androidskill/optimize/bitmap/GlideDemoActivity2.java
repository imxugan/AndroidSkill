package test.cn.example.com.androidskill.optimize.bitmap;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class GlideDemoActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv,iv_1,iv_2,iv_3,iv_4,iv_5;
    private Button btn_2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_demo2);
        findViewById(R.id.btn).setOnClickListener(this);
        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        iv = findViewById(R.id.iv);
        iv_1 = findViewById(R.id.iv_1);
        iv_2 = findViewById(R.id.iv_2);
        iv_3 = findViewById(R.id.iv_3);
        iv_4 = findViewById(R.id.iv_4);
        iv_5 = findViewById(R.id.iv_5);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                LogUtil.i(""+this);
                String url = "https://up.sc.enterdesk.com/edpic/22/07/6a/22076abeaf3109f12194502ea001fa58.jpg";
                Glide.with(this)
                        .load(url)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(new MySimpleTarget());
                break;
            case R.id.btn_2:
                String url2 = "https://up.sc.enterdesk.com/edpic/da/67/7f/da677f15b9860e8dd0465264f9f8359f.jpg";
                Glide.with(this)
                        .load(url2)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(new MyViewTarget(btn_2));
                break;
            case R.id.btn_3:

                break;
            case R.id.btn_4:

                break;
            case R.id.btn_5:

                break;
            case R.id.btn_6:

                break;
        }
    }

    class MySimpleTarget extends SimpleTarget<GlideDrawable>{

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            LogUtil.i(""+resource);
            iv.setImageDrawable(resource);
        }
    }

    class MySimpleTarget2 extends SimpleTarget<Bitmap>{

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            iv_1.setImageBitmap(resource);
        }
    }

    class MyViewTarget extends ViewTarget<Button, GlideDrawable>{

        public MyViewTarget(Button view) {
            super(view);
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            Button button = getView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackground(resource);
            }
        }
    }
}

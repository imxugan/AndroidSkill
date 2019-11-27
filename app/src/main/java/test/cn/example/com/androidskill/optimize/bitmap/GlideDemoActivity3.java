package test.cn.example.com.androidskill.optimize.bitmap;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

public class GlideDemoActivity3 extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private Button btn_2;
    private String url3 = "https://up.sc.enterdesk.com/edpic/48/50/9a/48509af259f37cd8b9371c124f26f508.jpg";
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    private LinearLayout ll_root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_demo3);
        ll_root = findViewById(R.id.ll_root);
        findViewById(R.id.btn).setOnClickListener(this);
        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);

        iv = findViewById(R.id.iv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                reset();
                LogUtil.i(""+this);
                String url = "https://up.sc.enterdesk.com/edpic/cb/ac/4d/cbac4dcdce2cb63a018c6f3aab2147ea.jpg";
                Glide.with(this)
                        .load(url)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv);
                break;
            case R.id.btn_2:

                break;
            case R.id.btn_3:
                Glide.with(this).load(url3)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                ToastUtils.shortToast(GlideDemoActivity3.this,"图片预加载失败");
                                return false;//注意，这里返回false，Target的onLoadFailed()方法才能执行
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                ToastUtils.shortToast(GlideDemoActivity3.this,"图片预加载成功");
                                return false;//注意，这里返回false，Target的onResourceReady方法才能执行
                            }
                        })
                        .preload();
                break;
            case R.id.btn_4:

                break;
            case R.id.btn_5:

                break;
            case R.id.btn_6:

                break;

        }
    }

    private void reset() {
        ll_root.removeView(iv);
        iv = new ImageView(this);
        iv.setLayoutParams(layoutParams);
        ll_root.addView(iv);
    }

}

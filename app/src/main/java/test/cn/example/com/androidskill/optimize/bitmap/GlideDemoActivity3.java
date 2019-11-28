package test.cn.example.com.androidskill.optimize.bitmap;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

public class GlideDemoActivity3 extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private Button btn_2;
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
                reset();
                String url2 = "https://up.sc.enterdesk.com/edpic/36/70/be/3670be204147632115a7f272ff190aa6.jpg";
                Glide.with(this).load(url2)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                LogUtil.i(""+((e!=null)?e.getMessage():""));
                                LogUtil.i("model=   "+model);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                LogUtil.i(model);
                                return false;
                            }
                        })
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(iv);
                break;
            case R.id.btn_3:
                reset();
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

//                String url3 = "https://up.sc.enterdesk.com/edpic/80/59/8e/80598e308a20df77cfcb70163cfd977a.jpg";
                final String url3 = "https://up.sc.enterdesk.com/edpic/fb/6a/4c/fb6a4c2e7c044a22d785a18a01b8b6d1.jpg";
                MyProgressInterceptor.addListener(url3, new MyProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        progressDialog.setProgress(progress);
                    }
                });
                Glide.with(this).load(url3)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                ToastUtils.shortToast(GlideDemoActivity3.this,"图片加载失败");
                                return false;//注意，这里返回false，Target的onLoadFailed()方法才能执行
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                ToastUtils.shortToast(GlideDemoActivity3.this,"图片加载成功");
                                return false;//注意，这里返回false，Target的onResourceReady方法才能执行
                            }
                        })
                        .into(new GlideDrawableImageViewTarget(iv){
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                progressDialog.show();
                            }

                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                                progressDialog.dismiss();
                                MyProgressInterceptor.removeListener(url3);

                            }
                        });
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

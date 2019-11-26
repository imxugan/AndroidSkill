package test.cn.example.com.androidskill.optimize.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import test.cn.example.com.androidskill.MyApplication;
import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

public class GlideDemoActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv,iv_1,iv_2,iv_3,iv_4,iv_5;
    private Button btn_2;
    private String url3;

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
                url3 = "https://up.sc.enterdesk.com/edpic/48/50/9a/48509af259f37cd8b9371c124f26f508.jpg";
                Glide.with(this).load(url3)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                ToastUtils.shortToast(GlideDemoActivity2.this,"图片预加载失败");
                                return false;//注意，这里返回false，Target的onLoadFailed()方法才能执行
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                ToastUtils.shortToast(GlideDemoActivity2.this,"图片预加载成功");
                                return false;//注意，这里返回false，Target的onResourceReady方法才能执行
                            }
                        })
                        .preload();
                break;
            case R.id.btn_4:
                Glide.with(this).load(url3)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(iv_2);
                break;
            case R.id.btn_5:
                new Thread(new MyRunnable()).start();
                break;
            case R.id.btn_6:
                String url5 = "https://www.dadijh.com/uploads/allimg/181015/5-1Q01521091D32.jpg";
                Glide.with(this).load(url5).downloadOnly(new MyFutureTarget());
                break;
        }
    }

    static class MyRunnable implements Runnable{
        @Override
        public void run() {
            final String url4 = "https://up.sc.enterdesk.com/edpic/43/e3/e9/43e3e9b90e43a44150fcde59b85c0ec9.png";
            FutureTarget<File> fileFutureTarget = Glide.with(MyApplication.getInstance()).load(url4).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            File file = null;
            try {
                //fileFutureTarget.get()这个方法是阻塞线程的，所以要在子线程中调用
                file = fileFutureTarget.get();
                if(null != file){
                    LogUtil.i(file.getAbsolutePath());
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class MyFutureTarget implements FutureTarget<File> {

        @Override
        public void clear() {

        }

        @Override
        public void onLoadStarted(Drawable placeholder) {

        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
            LogUtil.i(resource.getAbsolutePath());
        }

        @Override
        public void onLoadCleared(Drawable placeholder) {

        }

        @Override
        public void getSize(SizeReadyCallback cb) {
            //这个方法也需要自己实现
            cb.onSizeReady(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL);
        }

        @Override
        public void setRequest(Request request) {

        }

        @Override
        public Request getRequest() {
            return null;
        }

        @Override
        public void onStart() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public File get() throws ExecutionException, InterruptedException {
            return null;
        }

        @Override
        public File get(long timeout, @NonNull TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
            return null;
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

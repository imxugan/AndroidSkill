package test.cn.example.com.androidskill.optimize.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
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
    private String url3 = "https://up.sc.enterdesk.com/edpic/48/50/9a/48509af259f37cd8b9371c124f26f508.jpg";
    private String cropUrl = "https://up.sc.enterdesk.com/edpic/ad/52/78/ad5278b2b0f1b4c83c786e82165631ce.jpg";
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    private LinearLayout ll_root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_demo2);
        ll_root = findViewById(R.id.ll_root);
        findViewById(R.id.btn).setOnClickListener(this);
        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_10).setOnClickListener(this);
        findViewById(R.id.btn_11).setOnClickListener(this);
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
                reset();
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
            case R.id.btn_7:
                //原始地址是 https://up.sc.enterdesk.com/edpic/19/2a/90/192a907b722a2a65343e74bba9da5fde.jpg
                //故意在地址后面加了一个token
                String urlToken = "https://up.sc.enterdesk.com/edpic/19/2a/90/192a907b722a2a65343e74bba9da5fde.jpg?token=12456";
                MyGlideUrl myGlideUrl = new MyGlideUrl(urlToken);
                LogUtil.i(myGlideUrl.getCacheKey());
                Glide.with(this).load(myGlideUrl).into(iv_3);
                break;
            case R.id.btn_8:
                reset();
                LogUtil.i("iv.getScaleType()=   "+iv.getScaleType());
                Glide.with(this).load(cropUrl)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .fitCenter()
                        .into(iv);
                break;
            case R.id.btn_9:
                reset();
                LogUtil.i("iv.getScaleType()=   "+iv.getScaleType());
                Glide.with(this).load(cropUrl)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerCrop()
                        .into(iv);
                break;
            case R.id.btn_10:
                reset();
                Glide.with(this).load(cropUrl)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                LogUtil.i("图片加载失败  "+((e!=null)?e.getMessage():""));
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                LogUtil.i("图片加载成功");
                                return false;
                            }
                        })
                        .transform(new RoundCornerCrop(this,100,0))
//                        .bitmapTransform(new CircleCrop(this))
                        .into(iv);
                break;
            case R.id.btn_11:
                reset();
                Glide.with(this).load(cropUrl)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                LogUtil.i("图片加载失败  "+((e!=null)?e.getMessage():""));
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                LogUtil.i("图片加载成功");
                                return false;
                            }
                        })
                        .bitmapTransform(new CircleCrop(this))
                        .into(iv);
                break;
        }
    }

    private void reset() {
        ll_root.removeView(iv);
        iv = new ImageView(this);
        iv.setLayoutParams(layoutParams);
        ll_root.addView(iv);
    }

    public class CircleCrop extends BitmapTransformation {

        public CircleCrop(Context context) {
            super(context);
        }

        public CircleCrop(BitmapPool bitmapPool) {
            super(bitmapPool);
        }

        @Override
        public String getId() {
            return "test.cn.example.com.androidskill.optimize.bitmap.GlideDemoActivity2.CircleCrop";
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            int diameter = Math.min(toTransform.getWidth(), toTransform.getHeight());

            final Bitmap toReuse = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
            final Bitmap result;
            if (toReuse != null) {
                result = toReuse;
            } else {
                result = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
            }

            int dx = (toTransform.getWidth() - diameter) / 2;
            int dy = (toTransform.getHeight() - diameter) / 2;
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP,
                    BitmapShader.TileMode.CLAMP);
            if (dx != 0 || dy != 0) {
                Matrix matrix = new Matrix();
                matrix.setTranslate(-dx, -dy);
                shader.setLocalMatrix(matrix);
            }
            paint.setShader(shader);
            paint.setAntiAlias(true);
            float radius = diameter / 2f;
            canvas.drawCircle(radius, radius, radius, paint);

            if (toReuse != null && !pool.put(toReuse)) {
                toReuse.recycle();
            }
            return result;
        }
    }

    class RoundCornerCrop extends BitmapTransformation {
        private int radius;
        private int margin;

        public RoundCornerCrop(Context context) {
            super(context);
        }

        public RoundCornerCrop(Context context,int radius,int margin){
            this(context);
            this.radius = radius;
            this.margin = margin;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            LogUtil.i(""+toTransform);
            int width = toTransform.getWidth();
            int height = toTransform.getHeight();
            Bitmap bitmap = pool.get(width,height,Bitmap.Config.ARGB_8888);
            if(null == bitmap){
                bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            }
            LogUtil.i(""+bitmap);
            bitmap.setHasAlpha(true);
            bitmap.setDensity(toTransform.getDensity());
            Canvas canvas = new Canvas(bitmap);
            Paint paint  = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            float right = width - margin;
            float bottom = height - margin;
            canvas.drawRoundRect(new RectF(margin,margin,right,bottom),radius,radius,paint);

            return bitmap;
        }

        @Override
        public String getId() {
            return "test.cn.example.com.androidskill.optimize.bitmap.GlideDemoActivity2.RoundCornerCrop";
        }
    }

    class MyGlideUrl extends GlideUrl{

        private final String mUrl;

        public MyGlideUrl(String url) {
            super(url);
            mUrl = url;
        }

        @Override
        public String getCacheKey() {
            return handleUrl();
        }

        private String handleUrl() {
            String tokenParam = "";
            int tokenKeyIndex = mUrl.indexOf("?token=") >= 0 ? mUrl.indexOf("?token=") : mUrl.indexOf("&token=");
            if (tokenKeyIndex != -1) {
                int nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1);
                if (nextAndIndex != -1) {
                    tokenParam = mUrl.substring(tokenKeyIndex + 1, nextAndIndex + 1);
                } else {
                    tokenParam = mUrl.substring(tokenKeyIndex);
                }
            }
            LogUtil.i(tokenParam);
            return mUrl.replace(tokenParam,"");
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

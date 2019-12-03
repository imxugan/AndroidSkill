package test.cn.example.com.androidskill.optimize.bitmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class BitmapOptimizeActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    private LinearLayout ll_root;
    private ImageView iv,iv_2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_optimize);
        ll_root = findViewById(R.id.ll_root);
        iv_2 = findViewById(R.id.iv_2);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);


    }


    private void caculteBitmapSize(Bitmap.Config bimapConfig){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = bimapConfig;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        int byteCount = bitmap.getByteCount();//单位是byte
        LogUtil.i("width=   "+bitmap.getWidth()+"   height="+bitmap.getHeight()+"      byteCount=   "+byteCount);
    }

    private void caculateBitmapSize(int inSampleSize){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        int byteCount = bitmap.getByteCount();
        LogUtil.i("width=   "+bitmap.getWidth()+"   height="+bitmap.getHeight()+"   byteCount="+byteCount);
    }

    private void caculateBitmapSize(){
        reset();
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeResource(getResources(),R.drawable.test_meitu,options);
        int srceenDensity = getSrceenDensity();
        LogUtil.i("屏幕的像素密度    "+srceenDensity+"     options.outWidth="+options.outWidth);
        LogUtil.i("options.inDensity="+options.inDensity+"      options.inTargetDensity="+options.inTargetDensity+"     options.inScaled="+options.inScaled);
        options.inScaled = true;
//        options.inSampleSize = caculateInSampleSize(30,30);
        options.inDensity = options.outWidth;
        options.inTargetDensity = 30*options.inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_meitu, options);
        LogUtil.i("赋值后  options.inDensity="+options.inDensity+"      options.inTargetDensity="+options.inTargetDensity+"     options.inScaled="+options.inScaled);
        LogUtil.i("width=   "+bitmap.getWidth()+"   height="+bitmap.getHeight()+"   byteCount="+bitmap.getByteCount());
        iv.setImageBitmap(bitmap);

        Drawable drawable = iv_2.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bp = bitmapDrawable.getBitmap();
        LogUtil.i("width=   "+bp.getWidth()+"   height="+bp.getHeight()+"   byteCount="+bp.getByteCount());
    }

    private void caculateBitmapSize2(){
        reset();
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeResource(getResources(),R.drawable.test_meitu,options);
        int srceenDensity = getSrceenDensity();
        LogUtil.i("屏幕的像素密度    "+srceenDensity);
        LogUtil.i("默认值  options.inDensity="+options.inDensity+"      options.inTargetDensity="+options.inTargetDensity+"     options.inScaled="+options.inScaled);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_meitu, options);
        LogUtil.i("width=   "+bitmap.getWidth()+"   height="+bitmap.getHeight()+"   byteCount="+bitmap.getByteCount());
        iv.setImageBitmap(bitmap);

        Drawable drawable = iv_2.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bp = bitmapDrawable.getBitmap();
        LogUtil.i("width=   "+bp.getWidth()+"   height="+bp.getHeight()+"   byteCount="+bp.getByteCount());


    }

    private int getSrceenDensity(){
        //屏幕的density可由此代码获得,这个就是inTargetDensity
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        int inTargetDensity = metrics.densityDpi;
        return inTargetDensity;
    }


    private int caculateInSampleSize(int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher,options);
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if(width>reqWidth || height>reqHeight){
            int halfWidth = width/2;
            int halfHeight = height/2;
            while ((halfWidth/inSampleSize)>=reqWidth && (halfHeight/inSampleSize)>=reqHeight){
                inSampleSize *=2;
//            LogUtil.i("while    inSampleSize="+inSampleSize);
            }
        }
        LogUtil.i("inSampleSize=    "+inSampleSize);
        return inSampleSize;
    }

    private void reset(){
        ll_root.removeView(iv);
        iv = new ImageView(this);
        iv.setLayoutParams(layoutParams);
        ll_root.addView(iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                caculteBitmapSize(Bitmap.Config.ARGB_8888);
                caculteBitmapSize(Bitmap.Config.ARGB_4444);
                caculteBitmapSize(Bitmap.Config.RGB_565);
                caculteBitmapSize(Bitmap.Config.ALPHA_8);
                break;
            case R.id.btn_2:
                caculateBitmapSize(caculateInSampleSize(100,100));
                caculateBitmapSize(caculateInSampleSize(80,80));
                caculateBitmapSize(caculateInSampleSize(50,50));
                caculateBitmapSize(caculateInSampleSize(30,30));
                break;
            case R.id.btn_8:
                LogUtil.i("========================");
                caculateBitmapSize();
                break;
            case R.id.btn_9:
                LogUtil.i("========================");
                caculateBitmapSize2();
                break;
            case R.id.btn_3:
                startActivity(new Intent(this, TestMyImageLoaderActivity.class));
                break;
            case R.id.btn_4:
                startActivity(new Intent(this,FrescoDemoActivity.class));
                break;
            case R.id.btn_5:
                startActivity(new Intent(this,GlideDemoActivity.class));
                break;
            case R.id.btn_6:
                startActivity(new Intent(this,GlideDemoActivity2.class));
                break;
            case R.id.btn_7:
                startActivity(new Intent(this,GlideDemoActivity3.class));
                break;
        }
    }
}

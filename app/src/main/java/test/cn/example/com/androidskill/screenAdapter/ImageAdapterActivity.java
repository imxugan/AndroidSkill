package test.cn.example.com.androidskill.screenAdapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

public class ImageAdapterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_adapter);
        DisplayMetrics metrics = DensityUtil.getDisplayMetrics(this);
        LogUtil.i(metrics.toString());
        LogUtil.i("density= "+metrics.density);
        LogUtil.i("densityDpi= "+metrics.densityDpi);
        LogUtil.e(getResources().getDisplayMetrics().density+"");
        LogUtil.i(metrics.widthPixels+"         "+metrics.heightPixels);
        final ImageView iv_1 = findViewById(R.id.iv_1);
        final ImageView iv_2 = findViewById(R.id.iv_2);
        printBitmapSize(iv_1);
        iv_1.post(new Runnable() {
            @Override
            public void run() {
                int width = iv_1.getWidth();
                int height = iv_1.getHeight();
                LogUtil.i("iv_1     "+width+"       "+height);
            }
        });
        iv_2.post(new Runnable() {
            @Override
            public void run() {
                int width = iv_2.getWidth();
                int height = iv_2.getHeight();
                LogUtil.i("iv_2     "+width+"       "+height);
            }
        });

        Bitmap bitmap = getImageFromAssetsFile(this, "test_meitu.png");
        final ImageView iv_4 = findViewById(R.id.iv_4);
        iv_4.setImageBitmap(bitmap);
        iv_4.post(new Runnable() {
            @Override
            public void run() {
                int width = iv_4.getWidth();
                int height = iv_4.getHeight();
                LogUtil.i("iv_4     "+width+"       "+height);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) iv_4.getDrawable();
            }
        });
        printBitmapWidthHeight();

    }

    private void printBitmapWidthHeight() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            InputStream inputStream = getAssets().open("test_meitu.png");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream,new Rect(),options);
            LogUtil.i(""+bitmap+"       图片原始wdith="+options.outWidth+"       图片原始height="+options.outHeight);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i(e.toString());
        }

    }

    private void printBitmapSize(ImageView imageView){
        Drawable drawable = imageView.getDrawable();
        if(null != drawable){
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            LogUtil.i("图片大小  "+bitmap.getByteCount());
        }
    }

    private Bitmap getImageFromAssetsFile(Context context, String fileName){
        AssetManager assetManager = context.getResources().getAssets();
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i(e.toString());
        }finally {
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}

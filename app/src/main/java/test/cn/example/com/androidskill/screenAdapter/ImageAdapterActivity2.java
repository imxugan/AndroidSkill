package test.cn.example.com.androidskill.screenAdapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

public class ImageAdapterActivity2 extends AppCompatActivity {

    private ImageView iv;
    private boolean tinyPng = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = DensityUtil.getDisplayMetrics(this);
        LogUtil.i(metrics.toString());
        LogUtil.i("density= "+metrics.density);
        LogUtil.i("densityDpi= "+metrics.densityDpi);
        LogUtil.i(metrics.widthPixels+"         "+metrics.heightPixels);
        setContentView(R.layout.activity_image_adapter_2);
        iv = findViewById(R.id.iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resId = tinyPng?R.drawable.splash12_meitu_tiny:R.drawable.splash12_meitu;
                iv.setImageDrawable(getResources().getDrawable(resId));
                tinyPng = !tinyPng;

                BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                int byteCount = bitmap.getByteCount();
                //tinyPng对图片进行压缩后，只是改变了文件的存储格式，减少文件大小，但是
                //正在加载到内存中后，占用的内存大小只和图片的宽高，以及位图格式
                //所以这里显示的byteCount是不变的。
                LogUtil.i("采用tinyPng压缩后的图片的大小   byteCount="+byteCount);


            }
        });
    }
}

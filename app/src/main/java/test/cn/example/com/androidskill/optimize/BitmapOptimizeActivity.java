package test.cn.example.com.androidskill.optimize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class BitmapOptimizeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_optimize);
        findViewById(R.id.btn_1).setOnClickListener(this);


    }


    private void caculteBitmapSize(Bitmap.Config bimapConfig){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = bimapConfig;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        int byteCount = bitmap.getByteCount();//单位是byte
        LogUtil.i("width=   "+bitmap.getWidth()+"   height="+bitmap.getHeight()+"      byteCount=   "+byteCount);
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
        }
    }
}

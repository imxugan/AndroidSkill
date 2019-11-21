package test.cn.example.com.androidskill.optimize.bitmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

public class GlideDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv,iv_1,iv_2,iv_3,iv_4,iv_5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_demo);
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
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
//                String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
                String url = "http://file06.16sucai.com/2016/0905/afb96842ee1a6c49e19b11a779867bb0.jpg";
                Glide.with(this)
                        .load(url)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv);
                break;
            case R.id.btn_2:
                //图片地址指向的是一个动图
//                String gifUrl = "http://p1.pstatp.com/large/166200019850062839d3";
                String gifUrl = "https://upfile.asqql.com/2009pasdfasdfic2009s305985-ts/2019-5/2019571884813490.gif";
                Glide.with(this)
                        .load(gifUrl)
                        .asGif()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv_1);
                break;
            case R.id.btn_3:
                //图片地址指向的是一个静图
                String gifUrl2 = "https://tse2.mm.bing.net/th?id=OIP.HJq6te9c9yOIKfWVx7RljgHaFF&pid=Api&rs=1";
                Glide.with(this)
                        .load(gifUrl2)
                        .asGif()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv_2);
                break;
            case R.id.btn_4:
                //图片地址指向的是一个静图
                String url3 = "http://5b0988e595225.cdn.sohucs.com/images/20171021/c343adc0f7ee419c9626066e1de9d39f.jpeg";
                Glide.with(this)
                        .load(url3)
                        .asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv_3);
                break;
            case R.id.btn_5:
                //图片地址指向的是一个动图
                String url4 = "https://upfile.asqql.com/2009pasdfasdfic2009s305985-ts/2019-5/2019571884813490.gif";
                Glide.with(this)
                        .load(url4)
                        .asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv_4);
                break;
            case R.id.btn_6:
                String url5 = "https://pic.52112.com/180420/180420_32/J9xjxe1jIg_small.jpg";
                Glide.with(this)
                        .load(url5)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .override(DensityUtil.dip2px(this,500), DensityUtil.dip2px(this,100))
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_5);
                break;
        }
    }
}

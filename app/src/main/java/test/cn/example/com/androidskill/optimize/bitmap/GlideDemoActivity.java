package test.cn.example.com.androidskill.optimize.bitmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

public class GlideDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private LinearLayout ll_root;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

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
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);

        ll_root = findViewById(R.id.ll_root);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                reset();
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
                reset();
                //图片地址指向的是一个动图
//                String gifUrl = "http://p1.pstatp.com/large/166200019850062839d3";
                String gifUrl = "https://upfile.asqql.com/2009pasdfasdfic2009s305985-ts/2019-5/2019571884813490.gif";
                Glide.with(this)
                        .load(gifUrl)
                        .asGif()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv);
                break;
            case R.id.btn_3:
                reset();
                //图片地址指向的是一个静图
                String gifUrl2 = "https://tse2.mm.bing.net/th?id=OIP.HJq6te9c9yOIKfWVx7RljgHaFF&pid=Api&rs=1";
                Glide.with(this)
                        .load(gifUrl2)
                        .asGif()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv);
                break;
            case R.id.btn_4:
                reset();
                //图片地址指向的是一个静图
                String url3 = "http://5b0988e595225.cdn.sohucs.com/images/20171021/c343adc0f7ee419c9626066e1de9d39f.jpeg";
                Glide.with(this)
                        .load(url3)
                        .asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv);
                break;
            case R.id.btn_5:
                reset();
                //图片地址指向的是一个动图
                String url4 = "https://upfile.asqql.com/2009pasdfasdfic2009s305985-ts/2019-5/2019571884813490.gif";
                Glide.with(this)
                        .load(url4)
                        .asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .into(iv);
                break;
            case R.id.btn_6:
                reset();
                String url5 = "https://pic.52112.com/180420/180420_32/J9xjxe1jIg_small.jpg";
                Glide.with(this)
                        .load(url5)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.msg_status_send_error)
                        .override(DensityUtil.dip2px(this,500), DensityUtil.dip2px(this,100))
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv);
                break;
            case R.id.btn_7:
                //注意仔细观察左上角，有个很小的缩略图闪现出来后，就立刻显示转换后的尺寸
                //传入0.1f，表示显示的是原图的十分之一
                reset();
                String url6 = "https://up.sc.enterdesk.com/edpic/cf/3e/84/cf3e8415383e065b0fe8938f73bedf70.jpg";
                Glide.with(this)
                        .load(url6)
                        .error(R.drawable.msg_status_send_error)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .thumbnail(0.1f)
                        .into(iv);
                break;
            case R.id.btn_8:
                reset();
                String thumbnailUrl = "https://up.sc.enterdesk.com/edpic/c1/2a/ef/c12aef2b3ce6fbccc770a3524e8fe87f.jpg";
                DrawableRequestBuilder requestBuilder = Glide.with(this).load(thumbnailUrl);
                String url7 = "https://up.sc.enterdesk.com/edpic/cf/3e/84/cf3e8415383e065b0fe8938f73bedf70.jpg";
                Glide.with(this)
                        .load(url7)
                        .error(R.drawable.msg_status_send_error)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .thumbnail(requestBuilder)
                        .into(iv);
                break;
            case R.id.btn_9:
                reset();
                String url8 = "https://up.sc.enterdesk.com/edpic/cf/3e/84/cf3e8415383e065b0fe8938f73bedf70.jpg";
                Glide.with(this)
                        .load(url8)
                        .error(R.drawable.msg_status_send_error)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(iv);
                break;
        }
    }

    private void reset(){
        ll_root.removeView(iv);
        iv = new ImageView(this);
        iv.setLayoutParams(params);
        ll_root.addView(iv);
    }
}

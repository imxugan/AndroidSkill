package test.cn.example.com.androidskill.ui.compact;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/24.
 */

public class PaletteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
        ImageView iv = findViewById(R.id.iv);
        final TextView tv_title = findViewById(R.id.tv_title);
        final TextView tv_1 = findViewById(R.id.tv_1);
        final TextView tv_2 = findViewById(R.id.tv_2);
        final TextView tv_3 = findViewById(R.id.tv_3);
        final TextView tv_4 = findViewById(R.id.tv_4);
        final TextView tv_5 = findViewById(R.id.tv_5);
//        final TextView tv_6 = findViewById(R.id.tv_6);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                //暗，柔和的颜色
                int darkMutedColor = palette.getDarkMutedColor(Color.RED);
                //亮，柔和的颜色
                int lightMutedColor = palette.getLightMutedColor(Color.RED);
                //暗，鲜艳的颜色
                int darkVibrantColor = palette.getDarkVibrantColor(Color.RED);
                //亮，鲜艳的颜色
                int lightVibrantColor = palette.getLightVibrantColor(Color.RED);
                //获取莫衷特性颜色的样品
                Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                LogUtil.i(swatch+"");
                //swatch的值有可能是null，这里要进行非null判断
                if(null != swatch){
                    //主色调，谷歌推荐的图片整体的颜色rgb的混合色
                    int rgb = swatch.getRgb();
                    //谷歌推荐的作为标题的严(有一定和图片对比度度颜色)
                    int titleTextColor = swatch.getTitleTextColor();
                    //颜色向量
                    float[] hsl = swatch.getHsl();
                    //分析该颜色在图片中所占的像素多少值
                    int population = swatch.getPopulation();

                    tv_title.setTextColor(titleTextColor);
                    tv_title.setBackgroundColor(generateBackGroundColor(0.6f,rgb));

                    tv_5.setTextColor(rgb);
                    tv_5.setText("rgb");
                }

                tv_1.setTextColor(darkMutedColor);
                tv_1.setText("darkMutedColor");

                tv_2.setTextColor(lightMutedColor);
                tv_2.setText("lightMutedColor");

                tv_3.setTextColor(darkVibrantColor);
                tv_3.setText("darkVibrantColor");

                tv_4.setTextColor(lightVibrantColor);
                tv_4.setText("lightVibrantColor");
            }
        });

    }

    private int generateBackGroundColor(float percent, int rgb) {
        int bule = rgb & 0xff;//Color.blue(rgb);
        int green = rgb >>8 & 0xff; //Color.green(rgb);
        int red = rgb >> 16 & 0xff; //Color.red(rgb);
        int alpha = rgb>>>24;
        alpha = Math.round(percent * alpha);
        return Color.argb(alpha,red,green,bule);
    }
}

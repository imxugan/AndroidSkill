package test.cn.example.com.androidskill.myimageloader;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by xugan on 2019/4/23.
 */

public class Image implements Serializable{
    private String url;
    private Bitmap bitmap;

    public Image(String url, Bitmap bitmap) {
        this.url = url;
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

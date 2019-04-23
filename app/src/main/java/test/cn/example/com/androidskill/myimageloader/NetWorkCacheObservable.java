package test.cn.example.com.androidskill.myimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by xugan on 2019/4/23.
 */

public class NetWorkCacheObservable extends CacheObservable {
    @Override
    public Image getDataFromCache(String url) {
        Image image = null;
        Bitmap bitmap = downLoadImage(url);
        if(null != bitmap){
            image = new Image(url,bitmap);
        }
        return image;
    }

    @Override
    public void putDataToCache(Image image) {

    }

    private Bitmap downLoadImage(String url){
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try{
            URLConnection urlConnection = new URL(url).openConnection();
            inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }catch (IOException e){
            e.printStackTrace();
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

package test.cn.example.com.androidskill.myimageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by xugan on 2019/4/23.
 */

public class MemoryCacheObaervable extends CacheObservable {
    private int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);//kb

    private int cacheSize = maxMemory/8;

    LruCache<String,Bitmap> mLruCache = new LruCache<String,Bitmap>(cacheSize){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes()*value.getHeight()/1024;
        }
    };

    @Override
    public Image getDataFromCache(String url) {
        Bitmap bitmap = mLruCache.get(url);
        Image image = null;
        if(null != bitmap){
            image = new Image(url,bitmap);
        }
        return image;
    }

    @Override
    public void putDataToCache(Image image) {
        mLruCache.put(image.getUrl(),image.getBitmap());
    }
}

package test.cn.example.com.androidskill.myimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xugan on 2019/4/23.
 */

public class DiskCacheObservable extends CacheObservable {
    private Context mContext;
    private static final String CACHEPATH = "image_cache";
    //DiskLruCache中对于图片的最大缓存值
    private long maxSize = 20 * 1024* 1024;
    private DiskLruCache diskLruCache;

    public DiskCacheObservable(Context context){
        this.mContext = context;
        initDiskLruCache();
    }

    @Override
    public Image getDataFromCache(String url) {
        Image image = null;
        Bitmap bitmap = getDataFromDiskLruCache(url);
        if(null != bitmap){
            image = new Image(url,bitmap);
        }
        return image;
    }

    @Override
    public void putDataToCache(final Image image) {
        Observable.create(new ObservableOnSubscribe<Image>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Image> emitter) throws Exception {
                putDataToDiskLruCache(image);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    private void initDiskLruCache() {
        File diskCacheDir = DiskCacheUtil.getDiskCacheDir(mContext, CACHEPATH);
        if(!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        int appVersionCode = DiskCacheUtil.getAppVersionCode(mContext);
        try {
            diskLruCache = DiskLruCache.open(diskCacheDir, appVersionCode, 1, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Bitmap getDataFromDiskLruCache(String url){
        if(null == diskLruCache){
            return null;
        }
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        DiskLruCache.Snapshot snapshot = null;
        try{
            //生成图片url对应的key
            String md5String = DiskCacheUtil.getMd5String(url);
            //查找key对应的缓存
            snapshot = diskLruCache.get(md5String);
            if(null != snapshot){
                fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }
            //将缓存数据解析成Bitmap对象
            Bitmap bitmap = null;
            if(null !=fileDescriptor){
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            }
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null == fileDescriptor && null != fileInputStream){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void putDataToDiskLruCache(Image img){
        //获取到要缓存的图片的图片对应的唯一key
        String md5String = DiskCacheUtil.getMd5String(img.getUrl());
        try {
            //获取到DisLruCache的editor
            DiskLruCache.Editor editor = diskLruCache.edit(md5String);
            if(null != editor){
                //从editor中获取OutputStream
                OutputStream outputStream = editor.newOutputStream(0);
                //下载网络图片并保存至DiskLruCache图片缓存
                boolean isSucceddful = downLoad(img.getUrl(),outputStream);
                if(isSucceddful){
                    editor.commit();
                }else {
                    editor.abort();
                }
                diskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean downLoad(String url, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            URL urlTemp = new URL(url);
            urlConnection = (HttpURLConnection) urlTemp.openConnection();
            bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream(),8*1024);
            bufferedOutputStream = new BufferedOutputStream(outputStream,8*1024);
            int b;
            while((b=bufferedInputStream.read())!=-1){
                bufferedOutputStream.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != urlConnection){
                urlConnection.disconnect();
            }

            if(null !=bufferedOutputStream){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != bufferedInputStream){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}

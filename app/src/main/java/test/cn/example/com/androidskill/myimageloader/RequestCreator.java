package test.cn.example.com.androidskill.myimageloader;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/23.
 */

public class RequestCreator {
    private MemoryCacheObaervable memoryCacheObaervable;
    private DiskCacheObservable diskCacheObservable;
    private NetWorkCacheObservable netWorkCacheObservable;

    public RequestCreator(Context context) {
        memoryCacheObaervable = new MemoryCacheObaervable();
        diskCacheObservable = new DiskCacheObservable(context);
        netWorkCacheObservable = new NetWorkCacheObservable();
    }

    public Observable<Image> getImageFromMemory(String url) {
        return memoryCacheObaervable.getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(@NonNull Image image) throws Exception {
                        return null != image;
                    }
                }).doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Exception {
                        LogUtil.i("pic from memeory");
                    }
                });
    }

    public Observable<Image> getImageFromDisk(String url) {
        return diskCacheObservable.getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(@NonNull Image image) throws Exception {
                        return null != image;
                    }
                }).doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Exception {
                        LogUtil.i("pic from disk");
                        //缓存到内存
                        memoryCacheObaervable.putDataToCache(image);
                    }
                });
    }

    public Observable<Image> getImageFromNetWork(String url) {
        return netWorkCacheObservable.getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(@NonNull Image image) throws Exception {
                        return null != image;
                    }
                })
                .doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Exception {
                        LogUtil.i("pic from network    "+image.getUrl());
                        //将图片缓存到本地文件
                        diskCacheObservable.putDataToCache(image);
                        //将图片缓存到内存
                        memoryCacheObaervable.putDataToCache(image);
                    }
                });
    }
}

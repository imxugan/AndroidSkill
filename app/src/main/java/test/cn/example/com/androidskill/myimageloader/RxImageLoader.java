package test.cn.example.com.androidskill.myimageloader;

import android.content.Context;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/4/23.
 */

public class RxImageLoader {
    private String mUrl;
    private RequestCreator requestCreator;

    private RxImageLoader(Builder builder) {
        requestCreator = new RequestCreator(builder.mContext);
    }

    private static RxImageLoader instance;

    public static RxImageLoader with(Context context) {
        if (null == instance) {
            synchronized (RxImageLoader.class) {
                if (null == instance) {
                    instance = new Builder(context).build();
                }
            }
        }
        return instance;
    }

    public RxImageLoader load(String url) {
        this.mUrl = url;
        return instance;
    }

    public void into(final ImageView imageView) {
        Observable.concat(requestCreator.getImageFromMemory(mUrl),
                requestCreator.getImageFromDisk(mUrl),
                requestCreator.getImageFromNetWork(mUrl))
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(@NonNull Image image) throws Exception {
                        return image != null;
                    }
                })
                .firstElement()
                .subscribe(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Exception {
                        imageView.setImageBitmap(image.getBitmap());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.i(throwable.getMessage());
                    }
                });
    }

    private static class Builder {
        private Context mContext;

        public Builder(Context context) {
            mContext = context;
        }

        public RxImageLoader build() {
            return new RxImageLoader(this);
        }
    }
}

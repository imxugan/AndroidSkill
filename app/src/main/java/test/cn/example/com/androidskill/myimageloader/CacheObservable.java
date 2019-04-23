package test.cn.example.com.androidskill.myimageloader;

import com.jakewharton.rxbinding3.widget.AdapterViewNothingSelectionEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xugan on 2019/4/23.
 */

public abstract class CacheObservable {

    public Observable<Image> getImage(final String url){
        return Observable.create(new ObservableOnSubscribe<Image>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Image> emitter) throws Exception {
                if(!emitter.isDisposed()){
                    Image image = getDataFromCache(url);
                    if(null != image){
                        emitter.onNext(image);
                    }
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public abstract Image getDataFromCache(String url);

    public abstract void putDataToCache(Image image);
}

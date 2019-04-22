package com.xywy.im.gallery.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.xywy.im.R;
import com.xywy.im.gallery.GalleryImage;
import com.xywy.im.gallery.tool.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by hillwind
 */
public class GalleryAdapter extends PagerAdapter {

    private OnItemClickListener listener;
    private final Context context;

    private List<GalleryImage> mPhotos = new ArrayList<GalleryImage>();

    public GalleryAdapter(Context context, List<GalleryImage> photos) {
        this.context = context;
        if (photos != null) {
            mPhotos = photos;
        }
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(final ViewGroup container, final int position) {
        final PhotoView photoView = new PhotoView(container.getContext());

        final String path = mPhotos.get(position).path;
        if (path.contains(":/")) {
            Picasso.with(context).load(path).into(photoView);
        } else {
            Picasso.with(context).load(new File(path)).into(photoView);
        }
        container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(container, v, position);
                }
            }
        });
        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (listener != null) {
                    listener.onItemClick(container, view, position);
                }
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog = PhotoActionPopup.createDialog(context, new PhotoActionPopup.Listener() {
                    @Override
                    public void onSaveToPhone() {
                        saveImageToPhone(photoView, path);
                    }
                });
                dialog.show();
                return false;
            }
        });
        return photoView;
    }

    private void saveImageToPhone(final PhotoView photoView, final String path) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                Bitmap bitmap = ((BitmapDrawable) photoView.getDrawable()).getBitmap();
                try {
                    String desImagePath = ImageUtils.savePNGImage(context, path, bitmap);
                    emitter.onNext(desImagePath);
                    emitter.onComplete();
                } catch (IOException e) {
                    emitter.onError(e);
                }
            }

        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(context, R.string.gallery_image_save_failed, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(context, context.getString(R.string.gallery_image_saved_to) + s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ViewGroup container, View view, int position);
    }

}

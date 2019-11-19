package test.cn.example.com.androidskill.optimize;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

public class ImageResizer {

    public ImageResizer(){}

    public Bitmap decodeSampleBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId,options);
        options.inSampleSize = caculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    private int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if(0 == reqWidth || 0 == reqHeight){
            return  1;
        }
        int width = options.outWidth;
        int height = options.outHeight;
        int sampleSize = 1;
        if(width>reqWidth || height>reqHeight){
            final int halfWidth = width/2;
            final int halfHeight = height/2;
            while ((halfWidth/sampleSize)>reqWidth && (halfHeight/sampleSize)>reqHeight){
                sampleSize *=2;
            }
        }
        return sampleSize;
    }

    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);
        options.inSampleSize = caculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }


}

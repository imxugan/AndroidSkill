package com.xywy.im.tools.downImg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xywy.im.activity.PeerMessageActivity;
import com.xywy.im.db.DBManager;
import com.xywy.im.db.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2018/9/6.
 */

public class SDFileHelper {
    private Context context;

    private SDFileHelper(Context context){
        this.context = context;
    }
    private static SDFileHelper instance = null;
    public static SDFileHelper getInstance(Context context){
        if(null == context){
            throw new RuntimeException("context can not be null");
        }
        if(null == instance){
            synchronized (SDFileHelper.class){
                if(null == instance){
                    instance = new SDFileHelper(context);
                }
            }
        }
        return instance;
    }
//    public void savePicture(final String fileName,String url){
//        Picasso.with(context).load(url).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                saveToSD(fileName,bitmap);
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//                LogUtil.i("图片加载失败");
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });
//    }


    public void savePicture(final Message message){
        String content = message.getContent();
        final String fileName = message.getMsgId()+".png";
        try {
            final JSONObject jsonObject = new JSONObject(content);
            final String img_url = jsonObject.getString("content");
            Picasso.with(context).load(img_url).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    String absoluteFilePath = saveToSD(fileName, bitmap);
                    try {
                        jsonObject.put("filePath",absoluteFilePath);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    message.setContent(jsonObject.toString());
                    DBManager.getInstance().upateMessage(message);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    LogUtil.i("图片加载失败");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
       
    }

    private String saveToSD(String fileName, Bitmap bitmap) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String fileDir = Environment.getExternalStorageDirectory().getPath()+"/xywy_im_img/";
            File dir = new File(fileDir);
            if(!dir.exists()){
                dir.mkdirs();
            }
            fileName = fileDir + fileName;
            FileOutputStream  fos = null;
            try {
                fos = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            ToastUtils.shortToast(context,"SD卡不存在或者不可读写");
        }
        return fileName;
    }

    public void deleteDir(File dir){
        if(null != dir){
            if(dir.isFile()){
                dir.delete();
                return;
            }

            if(dir.isDirectory()){
                File[] childFiles = dir.listFiles();
                if(null != childFiles || childFiles.length == 0){
                    dir.delete();
                    return;
                }

                for (int i = 0; i < childFiles.length; i++) {
                    deleteDir(childFiles[i]);
                }
                dir.delete();
            }
        }
    }
}

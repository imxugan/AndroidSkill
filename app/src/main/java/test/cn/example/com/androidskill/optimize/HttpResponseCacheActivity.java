package test.cn.example.com.androidskill.optimize;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/5/16.
 */

public class HttpResponseCacheActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private ProgressDialog progressDialog;
    private  Bitmap bitmap = null;
    private Button btn_cache;
    private boolean openCache = true;
    private File cacheDir;
    private long cacheSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpresponsecache);
        cacheDir = new File(getCacheDir(),"http");
        cacheSize = 10*1024*1024;// 10 MiB
        findViewById(R.id.btn_request).setOnClickListener(this);
        btn_cache = findViewById(R.id.btn_cache);
        btn_cache.setOnClickListener(this);
        iv = findViewById(R.id.iv);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_request:
                netWorkRequest();
                break;
            case R.id.btn_cache:
                if(openCache){
                    try {
                        HttpResponseCache.install(cacheDir, cacheSize);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    HttpResponseCache installed = HttpResponseCache.getInstalled();
                    if(null != installed){
                        try {
                            installed.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                openCache = !openCache;
                btn_cache.setText(openCache?"关闭缓存":"打开缓存");
                break;
        }
    }

    private void netWorkRequest() {
        String imageUrl = "http://pic31.nipic.com/20130724/5252423_104848296000_2.jpg";
        new AsyncTask<String,Void,Bitmap>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Bitmap doInBackground(String... params) {

                LogUtil.i(params[0]);
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                        InputStream inputStream = httpURLConnection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap a) {
                super.onPostExecute(a);
                progressDialog.dismiss();
                iv.setImageBitmap(a);

            }
        }.execute(imageUrl);
    }
}

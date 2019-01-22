package com.xywy.test.myretrofit;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.xywy.test.util.MyRetrofit;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashHandler.getInstance().init(this);
        setContentView(R.layout.activity_main);
        requestPermission();
        findViewById(R.id.btn_retrofit2_test1).setOnClickListener(this);
        findViewById(R.id.btn_retrofit2_test2).setOnClickListener(this);
        findViewById(R.id.btn_retrofit2_test3).setOnClickListener(this);
        findViewById(R.id.btn_retrofit2_test4).setOnClickListener(this);
        findViewById(R.id.btn_retrofit2_test5).setOnClickListener(this);
        findViewById(R.id.btn_retrofit2_test6).setOnClickListener(this);
        findViewById(R.id.btn_retrofit2_test7).setOnClickListener(this);
        findViewById(R.id.btn_retrofit2_test8).setOnClickListener(this);
        findViewById(R.id.btn_retrofit2_test9).setOnClickListener(this);
        findViewById(R.id.btn_retrofit2_test10).setOnClickListener(this);
        retrofit = MyRetrofit.getInstance().getRetrofit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_retrofit2_test1:
                CommonApi commonApi = retrofit.create(CommonApi.class);
                Call<ResponseBody> baiduData = commonApi.getBaiduData();
                baiduData.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i("MainActivity",response.code()+"  "+response.message());
                        ResponseBody responseBody = response.body();
                        LogUtils.i(""+responseBody.toString());
                        InputStream inputStream = responseBody.byteStream();//获取输入流
                        try {
                            byte[] bytes = responseBody.bytes();//获取字节数组
                            String str = responseBody.string();//获取字符串数据
                            LogUtils.i(""+str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("MainActivity",t.getMessage());
                    }
                });
                break;
            case R.id.btn_retrofit2_test2:
                MyRetrofit.getInstance().getRetrofit().create(CommonApi.class).getBaiduAAAData().enqueue(new Callback<BaseData>() {
                    @Override
                    public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                        LogUtils.i(""+response.body().code+"     "+response.body().message);
                    }

                    @Override
                    public void onFailure(Call<BaseData> call, Throwable t) {

                    }
                });
                break;
            case R.id.btn_retrofit2_test3:
                MyRetrofit.getInstance().getRetrofit().create(CommonApi.class).getBaiduBBBData("jack").enqueue(new Callback<BaseData>() {
                    @Override
                    public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                        LogUtils.i(""+response.body().message+"     "+response.body().code);
                    }

                    @Override
                    public void onFailure(Call<BaseData> call, Throwable t) {

                    }
                });
                break;
            case R.id.btn_retrofit2_test4:
                MyRetrofit.getInstance().getRetrofit().create(CommonApi.class).getBaiduCCCData("12345").enqueue(new Callback<BaseData>() {
                    @Override
                    public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                        LogUtils.i(""+response.body().code+"    "+response.body().message);
                    }

                    @Override
                    public void onFailure(Call<BaseData> call, Throwable t) {

                    }
                });
                break;
            case R.id.btn_retrofit2_test5:
                Map map = new HashMap<String,String>();
                map.put("age",20+"");
                map.put("sex",1+"");
                MyRetrofit.getInstance().getRetrofit().create(CommonApi.class).getBaiduDDDData(map).enqueue(new Callback<BaseData>() {
                    @Override
                    public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                        LogUtils.i(""+response.body().message);
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
                break;
            case R.id.btn_retrofit2_test6:
                MyRetrofit.getInstance().getRetrofit().create(CommonApi.class).postBaiduEEEData("my reason is what why").enqueue(new Callback<BaseData>() {
                    @Override
                    public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                        LogUtils.i(""+response.body().message);
                    }

                    @Override
                    public void onFailure(Call<BaseData> call, Throwable t) {

                    }
                });
                break;
            case R.id.btn_retrofit2_test8:
                Map postMap = new HashMap<String,String>();
                postMap.put("age",20);
                postMap.put("sex",1);
                postMap.put("reason","不知道什么原因");
                MyRetrofit.getInstance().getRetrofit().create(CommonApi.class).psotBaiduGGGData(postMap).enqueue(new Callback<BaseData>() {
                    @Override
                    public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                        LogUtils.i(""+response.body().message);
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
                break;
            case R.id.btn_retrofit2_test7:
                User u = new User();
                u.age = 20;
                u.sex =1;
                u.userId = 123457;
                u.userName = "张三";
                String postInfo = new Gson().toJson(u);
//                final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postInfo);
                final RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),postInfo);
                MyRetrofit.getInstance().getRetrofit().create(CommonApi.class).postBaiduFFFData(requestBody).enqueue(new Callback<BaseData>() {
                    @Override
                    public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                        LogUtils.i(""+response.body().message);
                    }

                    @Override
                    public void onFailure(Call<BaseData> call, Throwable t) {

                    }
                });
                break;
            case R.id.btn_retrofit2_test9:
                //post请求参数是对象时，底层其实还是将对象转换成了json字符串进行传递
                User user = new User();
                user.userId = 123345;
                user.userName = "Jack ma";
                user.age = 48;
                user.sex = 0;
                MyRetrofit.getInstance().getRetrofit().create(CommonApi.class).postBaiduHHHData(user).enqueue(new Callback<BaseData>() {
                    @Override
                    public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                        LogUtils.i(""+response.body().message);
                    }

                    @Override
                    public void onFailure(Call<BaseData> call, Throwable t) {

                    }
                });
                break;
            case R.id.btn_retrofit2_test10:
                User user1 = new User();
                user1.age = 11;
                user1.sex = 0;
                user1.userName = "pony ma";
                user1.userId = 111111;
                RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),new Gson().toJson(user1));
                MyRetrofit.getInstance().getRetrofit().create(CommonApi.class).postBaiduIIIData(requestBody1).enqueue(new Callback<BaseData>() {
                    @Override
                    public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                        LogUtils.i(""+response.body().message);
                    }

                    @Override
                    public void onFailure(Call<BaseData> call, Throwable t) {

                    }
                });

                break;
        }
    }

    private void requestPermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean isAllGranted = checkPermissionAllGranted(permissions);
        if(isAllGranted){
            LogUtils.i("授权了外部存储空间写的权限，可以进行向sd卡写数据了");
            return;
        }

        //向用户发起授权请求
        ActivityCompat.requestPermissions(MainActivity.this,permissions,PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            boolean isAllGranted = true;
            for (int grandResult:grantResults) {
                if(grandResult != PackageManager.PERMISSION_GRANTED){
                    isAllGranted = false;
                    break;
                }
            }

            if(isAllGranted){
                LogUtils.i("已经授权了，可以去向外部sd卡写数据了e");
            }else {
                //提示用户去授权
                openAppDetails();
            }
        }
    }

    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("备份通讯录需要访问 “通讯录” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission: permissions) {
            if(ContextCompat.checkSelfPermission(MainActivity.this,permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;

    }
}

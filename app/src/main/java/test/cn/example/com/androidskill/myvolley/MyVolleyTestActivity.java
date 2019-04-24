package test.cn.example.com.androidskill.myvolley;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.myvolley.IJsonDataListener;
import test.cn.example.com.androidskill.myvolley.MyVolley;
import test.cn.example.com.androidskill.myvolley.RequestBean;
import test.cn.example.com.androidskill.myvolley.ResponseBean;
import test.cn.example.com.util.LogUtil;

public class MyVolleyTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myvolley);
        long maxMemory = Runtime.getRuntime().maxMemory()/1024/1024;
        LogUtil.i(maxMemory+"");
        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testMyVolley();
            }
        });
    }

    private void testMyVolley() {
        String url = "http://test.volley.com/site/notice";
        RequestBean requestBean = new RequestBean("1","abc");
        MyVolley.sendRequest(url, requestBean, ResponseBean.class, new IJsonDataListener<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean object) {
                if(null != object){
                    LogUtil.i(object.code+"          "+object.msg);
                }
            }

            @Override
            public void onFailed() {

            }
        });

    }

}

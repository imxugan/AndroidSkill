package test.cn.example.com.androidskill.java_about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.model.Employee;
import test.cn.example.com.androidskill.model.Manager;

/**
 * Created by xgxg on 2017/6/13.
 */

public class CallBackActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
        new Manager(new Employee());
    }
}

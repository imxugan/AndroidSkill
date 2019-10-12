package test.cn.example.com.androidskill.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.inter.CameraInter;
import test.cn.example.com.androidskill.inter.IUserDao;
import test.cn.example.com.androidskill.login.model.IUser;
import test.cn.example.com.androidskill.model.FactoryProxy;
import test.cn.example.com.androidskill.model.UserDao;
import test.cn.example.com.androidskill.model.UserDaoProxy;
import test.cn.example.com.util.LogUtil;

/**
 * 代理模式
 * Created by xgxg on 2017/7/18.
 */

public class ProxyPatternActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_pattern);
        initView();

    }

    private void initView() {
        Button proxy_static = (Button) findViewById(R.id.proxy_static);
        proxy_static.setOnClickListener(this);
        Button proxy_dynamic = (Button) findViewById(R.id.proxy_dynamic);
        proxy_dynamic.setOnClickListener(this);
        Button proxy_dynamic_factory = (Button) findViewById(R.id.proxy_dynamic_factory);
        proxy_dynamic_factory.setOnClickListener(this);
        findViewById(R.id.proxy_dynamic_cglib_for_android).setOnClickListener(this);
    }

    private class DynamicUserDaoProxy implements InvocationHandler{
        private Object realSubject;
        public DynamicUserDaoProxy(IUserDao subject){
            this.realSubject = subject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            LogUtil.i("动态代理---开始事务");
            method.invoke(realSubject,args);
            LogUtil.i("动态代理---提交事务");
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.proxy_static:
                //新建真实对象
                IUserDao userDao = new UserDao();
                //通过真实主题对象构造一个代理对象
                IUserDao userDaoProxy = new UserDaoProxy(userDao);
                userDaoProxy.save();
                break;
            case R.id.proxy_dynamic:
                //动态代理
                IUserDao realUserDao = new UserDao();
                InvocationHandler h = new DynamicUserDaoProxy(realUserDao);
                Class<?>[] interfaces = realUserDao.getClass().getInterfaces();
                if(null != interfaces){
                    for (int i = 0; i < interfaces.length; i++) {
                        LogUtil.i(""+interfaces[i]);
                    }
                }

                IUserDao dynamicProxy = (IUserDao) Proxy.newProxyInstance(h.getClass().getClassLoader(),realUserDao.getClass().getInterfaces(),h);
                dynamicProxy.save();

                CameraInter dynamicProxy1 = (CameraInter) Proxy.newProxyInstance(h.getClass().getClassLoader(),realUserDao.getClass().getInterfaces(),h);
                dynamicProxy1.open();


                break;
            case R.id.proxy_dynamic_factory:
                //使用工厂模式生产动态代理对象
                //这种方式封装的更好，降低了耦合
                IUserDao realUserDao2 = new UserDao();
                LogUtil.i("realUserDao2.getClass()="+realUserDao2.getClass());
                FactoryProxy factoryProxy = new FactoryProxy(realUserDao2);
                IUserDao proxy = (IUserDao) factoryProxy.getProxy();
                LogUtil.i("proxy.getClass()="+proxy.getClass());
                proxy.save();
                break;
            case R.id.proxy_dynamic_cglib_for_android:
                // https://github.com/leo-ouyang/CGLib-for-Android
                UserDao proxy1 = (UserDao) new MyInterceptor(this).getProxy(UserDao.class);
                LogUtil.i(""+proxy1);
                proxy1.save();
                break;
            default:
                break;
        }
    }
}

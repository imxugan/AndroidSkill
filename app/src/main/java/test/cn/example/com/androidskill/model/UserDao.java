package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.CameraInter;
import test.cn.example.com.androidskill.inter.IUserDao;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/18.
 */

public class UserDao implements IUserDao , CameraInter {
    @Override
    public void save() {
        LogUtil.i("保存数据");
    }

    @Override
    public void open() {
        LogUtil.i("open");
    }

    @Override
    public void takePhoto() {

    }

    @Override
    public void close() {

    }
}

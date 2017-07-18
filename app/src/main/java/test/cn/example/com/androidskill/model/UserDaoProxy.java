package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.IUserDao;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/18.
 */

public class UserDaoProxy implements IUserDao {
    private IUserDao userDao;
    public UserDaoProxy(IUserDao userDao){
        this.userDao = userDao;
    }
    @Override
    public void save() {
        LogUtil.e("开始事务");
        userDao.save();
        LogUtil.i("提交事务");
    }
}

package test.cn.example.com.androidskill.model;

import test.cn.example.com.androidskill.inter.IUserDao;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/7/18.
 */

public class UserDao implements IUserDao {
    @Override
    public void save() {
        LogUtil.i("保存数据");
    }
}

package test.cn.example.com.androidskill.login.model;

import android.text.TextUtils;

/**
 * Created by xgxg on 2017/7/17.
 */

public class UserModel implements IUser{
    public String name,psw;
    public UserModel(String name,String psw){
        this.name = name;
        this.psw = psw;
    }
    @Override
    public int checkUserValidity(String name, String psw) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(psw)){
            return 0;
        }
        return 1;
    }
}

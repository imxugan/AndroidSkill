package test.cn.example.com.androidskill.leak;

import android.content.Context;

/**
 * Created by xugan on 2019/4/24.
 */

public class CommonUtil {
    private static Context mContext;
    private static CommonUtil instance;
    private CommonUtil(Context context){
        this.mContext = context;
    }

    public static CommonUtil getInstance(Context context){
        if(null == instance){
            synchronized (CommonUtil.class){
                if(null == instance){
                    instance = new CommonUtil(context);
                }
            }
        }
        return instance;
    }
}

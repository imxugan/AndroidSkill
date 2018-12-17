package test.cn.example.com.androidskill.util;

import android.content.Context;

/**
 * Created by xugan on 2018/12/17.
 */

public class DensityUtil {
    public static int dp2px(Context context, float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public static int px2dp(Context context,float pxValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
}

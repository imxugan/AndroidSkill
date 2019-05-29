package test.cn.example.com.androidskill.optimize.service_keep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * Created by xugan on 2019/5/28.
 */

public class KeepLiveActivityManager {
    private Context mContext;
    private WeakReference<Activity> activityWeakReference ;

    private KeepLiveActivityManager(Context applicationContext){
        mContext = applicationContext;
    }
    private static KeepLiveActivityManager instance = null;
    public static KeepLiveActivityManager getInstance(Context context){
        if(null == instance){
            synchronized (KeepLiveActivityManager.class){
                if(null == instance){
                    instance = new KeepLiveActivityManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void setKeepLiveActivity(Activity activity){
        activityWeakReference = new WeakReference<Activity>(activity);
    }


    public void startKeepLiveActivity(Context context) {
        Intent intent = new Intent(context,KeepLiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void finishKeepLiveActivity(){
        if(null != activityWeakReference && null != activityWeakReference.get()){
            Activity activity = activityWeakReference.get();
            activity.finish();
        }
    }
}

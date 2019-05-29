package test.cn.example.com.androidskill.optimize.process_keep;

import android.app.ActivityManager;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;

import java.util.List;

import test.cn.example.com.util.LogUtil;
import test.cn.example.com.util.ToastUtils;

/**
 * Created by xugan on 2019/5/29.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobHandleService extends JobService {
    private int kJobId = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("JobHandleService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("JobHandleService   onStartCommand");
        scheduleJob(getJobInfo());
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        boolean isLocalServiceWork = isServiceWork(this,"test.cn.example.com.androidskill.optimize.process_keep.LocalService");
        boolean isRemoteServiceWork = isServiceWork(this,"test.cn.example.com.androidskill.optimize.process_keep.RemoteService");
        if(!isLocalServiceWork || !isRemoteServiceWork){
            startService(new Intent(this,LocalService.class));
            startService(new Intent(this,RemoteService.class));
            ToastUtils.shortToast(this,"process start");
        }
        return true;
    }



    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        LogUtil.i("onStopJob");
        scheduleJob(getJobInfo());
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("onDestroy");
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param context
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    private boolean isServiceWork(Context context, String serviceName) {
        boolean isWork = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
        if(runningServices.size()<=0){
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            String name = runningServices.get(i).service.getClassName().toString();
            if(name.equals(serviceName)){
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    private void scheduleJob(JobInfo jobInfo) {
        LogUtil.i("JobHandleService   scheduleJob");
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(jobInfo);
    }

    private JobInfo getJobInfo() {
        JobInfo.Builder builder = new JobInfo.Builder(kJobId++,new ComponentName(this,JobHandleService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        builder.setPeriodic(10);//间隔时间(周期)，实际开发中，要设置很大，否则会很耗电
        return builder.build();
    }


}

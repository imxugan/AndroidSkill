package test.cn.example.com.androidskill.art.chapter_eight;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

public class FloatingService extends Service {
    private Button btn;
    private WindowManager windowManager;
    private int downX;
    private int downY;

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createWindow(this);
        return super.onStartCommand(intent, flags, startId);
    }

    private void createWindow(Context context){
        //6.0以上系统
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            btn = new Button(context);
            btn.setText("悬浮窗");
            btn.setGravity(Gravity.CENTER);
            btn.setBackground(getResources().getDrawable(R.color.red));

            final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
                //8.0以上的系统
//                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                //这里如果指定window级别是应用级的，
                // 那么就会报Unable to add window -- token null is not valid; is your activity running? 这个异常
//                layoutParams.type =1; //这里如果指定window级别是应用级的，那么就需要
            }else {
//                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                //这里如果指定window级别是应用级的，
                // 那么就会报Unable to add window -- token null is not valid; is your activity running? 这个异常
//                layoutParams.type =1;
            }
            //这里falgs 如果是仅仅设置成FLAG_NOT_TOUCH_MODAL，那么按返回键，onDestory方法不会回调
            layoutParams.flags =  FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL;
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.height = 300;

            layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            layoutParams.x = 300;
            layoutParams.y = 300;
            windowManager.addView(btn,layoutParams);
            //获取当前窗口可视区域大小
            Rect rect = new Rect();
            btn.getWindowVisibleDisplayFrame(rect);
            LogUtil.i("rect.width()=${rect.width()}      rect.height()= ${rect.height()}");


            btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            downX = (int) event.getRawX();
                            downY = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int moveX = (int) event.getRawX();
                            int moveY = (int) event.getRawY();
                            layoutParams.x += moveX - downX;
                            layoutParams.y += moveY - downY;
                            windowManager.updateViewLayout(btn,layoutParams);
                            downX = moveX;
                            downY = moveY;
                            break;
                        case MotionEvent.ACTION_UP:
                            if(event.getRawX() == downX && event.getRawY() == downY){
                                LogUtil.e("单击");
                                makeActivityFromBackgroundToForeground();
                            }
                            break;
                    }
                    return false;
                }
            });
        }
    }

    private boolean isRunningForeground(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : runningAppProcesses) {
            if(appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return appProcessInfo.processName.equals(getApplicationInfo().processName);
            }
        }
        return false;
    }

    private void makeActivityFromBackgroundToForeground(){
        if(!isRunningForeground()){
            ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo runningTaskInfo:runningTasks){
                if(runningTaskInfo.topActivity.getPackageName().equals(getPackageName())){
                    LogUtil.e("后台回到前台,怎么偶尔又不生效????");
                    activityManager.moveTaskToFront(runningTaskInfo.id, ActivityManager.MOVE_TASK_WITH_HOME);
                    return;
                }
            }
            LogUtil.e("重新启动");
            //如果没有找到运行的task，用户结束了task或者被系统回收了，则需要重新启动WindowActivity
            Intent intent = new Intent(this,WindowActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != windowManager && null != btn){
            windowManager.removeViewImmediate(btn);
        }
    }
}

package test.cn.example.com.androidskill.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Trace;

import test.cn.example.com.androidskill.optimize.hotfix.FixDexUtils2;
import test.cn.example.com.util.LogUtil;

public class HCallBack implements Handler.Callback {
    public static final int LAUNCH_ACTIVITY         = 100;
    public static final String PACKAGENAME = "test.cn.example.com.androidskill";
    public static final String BACKUP_CLASSNAME = PACKAGENAME+".hook.BackUpActivity";
    private final Handler mHandler;

    public HCallBack(Handler handler){
        this.mHandler = handler;
    }
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LAUNCH_ACTIVITY: {
                Object obj = msg.obj;
                LogUtil.e(obj+"");
                try {
                    Intent intent = (Intent) FixDexUtils2.getObject(obj.getClass(), "intent", obj);
                    if(BACKUP_CLASSNAME.equals(intent.getComponent().getClassName())){
                        Intent realIntent = intent.getParcelableExtra(HookHelper.PLUG_INTENT);
//                        FixDexUtils2.setObject(obj.getClass(),"intent",obj,realIntent);
                        intent.setComponent(realIntent.getComponent());
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
        mHandler.handleMessage(msg);
        return true;
    }
}

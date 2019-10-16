package test.cn.example.com.androidskill.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import test.cn.example.com.androidskill.optimize.hotfix.FixDexUtils;
import test.cn.example.com.androidskill.optimize.hotfix.FixDexUtils2;
import test.cn.example.com.util.LogUtil;

public class InstrumentationProxy extends Instrumentation {
    private final Instrumentation mInstrumentation;

    public InstrumentationProxy(Instrumentation instrumentation){
        this.mInstrumentation = instrumentation;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        LogUtil.i("调用了代理类的execStartActivity");
        String className = intent.getComponent().getClassName();
        if(HookHelper.PLUGCLASSNAME.equals(className)){
            Intent newIntent = new Intent();
            newIntent.setClassName(HookHelper.packageName,HookHelper.BACKUPCLASSNAME);
            intent.putExtra(HookHelper.PLUG_INTENT,newIntent);
            intent.setComponent(newIntent.getComponent());
        }
        try {
            Method execStartActivityMethod = mInstrumentation.getClass().getDeclaredMethod("execStartActivity", Context.class, IBinder.class, IBinder.class,
                    Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivityMethod.setAccessible(true);
            return (ActivityResult) execStartActivityMethod.invoke(mInstrumentation, who, contextThread, token, target, intent, requestCode, options);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Activity newActivity(ClassLoader cl, String className,
                                Intent intent) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Intent parcelableExtra = intent.getParcelableExtra(HookHelper.PLUG_INTENT);
        String intentName = parcelableExtra.getComponent().getClassName();
        if(!TextUtils.isEmpty(intentName)){
            return super.newActivity(cl,intentName,intent);
        }
        return super.newActivity(cl,className,intent);
    }
}

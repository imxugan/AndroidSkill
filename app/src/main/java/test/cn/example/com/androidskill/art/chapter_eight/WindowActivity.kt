package test.cn.example.com.androidskill.art.chapter_eight

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil
import test.cn.example.com.util.ToastUtils

class WindowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        //1.首先要确定是要显示系统窗口还是应用窗口，如果是要显示系统窗口则要获取权限，如果用户未授权，则会报错
        if(checkWindowPermission(this)){
            startService(Intent(this,FloatingService::class.java))
        }
    }

    private fun checkWindowPermission(context:Activity): Boolean {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(!Settings.canDrawOverlays(context)){
                //还未授权，需要跳转到权限设置页面
                ToastUtils.longToast(context,"当前无权限，请授权")
                context.startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+context.packageName)),0)
                return false
            }
            return true
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(0 ==requestCode){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    //授权失败
                    ToastUtils.shortToast(this,"授权失败")
                }else{
                    //授权成功
                    ToastUtils.shortToast(this,"授权成功")
                    startService(Intent(this,FloatingService::class.java))
                }
            }
        }
    }

    override fun onContentChanged() {
        super.onContentChanged()
        LogUtil.i("onContentChanged")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i("onDestroy")
        stopService(Intent(this,FloatingService::class.java))
    }
}

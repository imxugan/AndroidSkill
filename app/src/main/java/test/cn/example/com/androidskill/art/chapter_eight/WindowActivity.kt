package test.cn.example.com.androidskill.art.chapter_eight

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.view.WindowManager.LayoutParams.*
import android.widget.Button
import test.cn.example.com.androidskill.R
import test.cn.example.com.util.LogUtil
import test.cn.example.com.util.ToastUtils

class WindowActivity : AppCompatActivity() {
    private var btn:Button ? = null
    private var mWindowManager:WindowManager ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        var windowManager:WindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager = windowManager
        //1.首先要确定是要显示系统窗口还是应用窗口，如果是要显示系统窗口则要获取权限，如果用户未授权，则会报错
        if(checkWindowPermission(this)){
            createWindow(this)
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

    @SuppressLint("ClickableViewAccessibility")
    fun createWindow(context: Context){
        //6.0以上系统
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            btn = Button(context)
            btn!!.text = "悬浮窗"
            btn!!.gravity = Gravity.CENTER
            btn!!.background = resources.getDrawable(R.color.red)

            var layoutParams = WindowManager.LayoutParams()
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
                //8.0以上的系统
                    layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//                layoutParams.type =1
            }else {
                    layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
//                layoutParams.type =1
            }
            //这里falgs 如果是仅仅设置成FLAG_NOT_TOUCH_MODAL，那么按返回键，onDestory方法不会回调
            layoutParams.flags =  FLAG_NOT_FOCUSABLE or FLAG_NOT_TOUCH_MODAL
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.height = 300

            layoutParams.gravity = Gravity.LEFT or Gravity.TOP
            layoutParams.x = 300
            layoutParams.y = 300
            mWindowManager?.addView(btn,layoutParams)
            //获取当前窗口可视区域大小
            val rect = Rect()
            btn!!.getWindowVisibleDisplayFrame(rect)
            LogUtil.i("rect.width()=${rect.width()}      rect.height()= ${rect.height()}")

            var downX = 0
            var downY = 0
            btn!!.setOnTouchListener(object:View.OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                    when(event!!.action){
                        MotionEvent.ACTION_DOWN -> {
                            downX = event.rawX.toInt()
                            downY = event.rawY.toInt()
                        }
                        MotionEvent.ACTION_MOVE ->  {
                            var moveX = event.rawX.toInt()
                            var moveY = event.rawY.toInt()
                            var offsetX:Int = (moveX - downX)
                            var offsetY:Int = (moveY - downY)
                            downX = moveX
                            downY = moveY
                            LogUtil.i("offsetX = ${offsetX}     offsetY = ${offsetY}")
                            layoutParams.x =layoutParams.x + offsetX
                            layoutParams.y =layoutParams.y + offsetY
                            mWindowManager?.updateViewLayout(btn,layoutParams)
                        }
                    }
                    return true
                }
            })
        }

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
                    createWindow(this)
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
        if(null != btn){
            mWindowManager?.removeView(btn)
        }
    }
}

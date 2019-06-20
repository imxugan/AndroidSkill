package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/20.
 */

public class SnackBarActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanckbar);
        findViewById(R.id.btn_toast).setOnClickListener(this);
        findViewById(R.id.btn_snackbar).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_toast:
                myToast("自定义toast");
                break;
            case R.id.btn_snackbar:
                final Snackbar snackbar = Snackbar.make(v, "是否开启加速模式", Snackbar.LENGTH_SHORT);
                snackbar.setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myToast("自定义吐司");
                    }
                });
                snackbar.setCallback(new Snackbar.Callback(){
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        LogUtil.i("sanckbar 消失了");
                    }

                    @Override
                    public void onShown(Snackbar sb) {
                        super.onShown(sb);
                        LogUtil.i("sanckbar 显示出来了");
                    }
                });
                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                snackbar.show();
                break;
        }
    }
//    public static Toast makeText(@NonNull Context context, @Nullable Looper looper,
//                                 @NonNull CharSequence text, @Duration int duration) {
//        Toast result = new Toast(context, looper);
//
//        LayoutInflater inflate = (LayoutInflater)
//                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflate.inflate(com.android.internal.R.layout.transient_notification, null);
//        TextView tv = (TextView)v.findViewById(com.android.internal.R.id.message);
//        tv.setText(text);
//
//        result.mNextView = v;
//        result.mDuration = duration;
//
//        return result;
//    }

    private void myToasInThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Toast toast = new Toast(SnackBarActivity.this);
                LayoutInflater layoutInflateService = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = layoutInflateService.inflate(R.layout.toast_layout, null);
                TextView tv = v.findViewById(R.id.tv);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(v);
                toast.show();
            }
        }).start();
    }
    private void myToast(String text){
        Toast toast = new Toast(SnackBarActivity.this);
        LayoutInflater layoutInflateService = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflateService.inflate(R.layout.toast_layout, null);
        TextView tv = v.findViewById(R.id.tv);
        tv.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(v);
        toast.show();

    }
}

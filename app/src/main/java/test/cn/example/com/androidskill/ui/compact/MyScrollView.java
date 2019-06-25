package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ScrollView;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/25.
 */

public class MyScrollView extends ScrollView {

    private final int screenHeight;
    private ITranslunceListenter mTranslunceListener;
    public void setmTranslunceListener(ITranslunceListenter listener){
        this.mTranslunceListener = listener;
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager service = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = service.getDefaultDisplay();
        defaultDisplay.getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollY = getScrollY();
        float changeOffset = screenHeight / 3f;
        if(scrollY<=changeOffset){
            if(null != mTranslunceListener){
                LogUtil.i(screenHeight+"        "+scrollY/changeOffset);
                mTranslunceListener.translunceChangee(1-scrollY/changeOffset);
            }
        }
    }
}

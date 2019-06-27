package test.cn.example.com.androidskill.ui.compact;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/27.
 */

public class FabOnScrollListener extends RecyclerView.OnScrollListener {
    private static final int MINMOVEDISTANCE = 20;
    private static int distance;
    private IScrollListener mListener;
    private boolean visible = true;

    public FabOnScrollListener(IScrollListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dy 分 正 负，代表了 移动的增量
        //向下移动，dy 是正数，向上移动，dy是负数
        LogUtil.i("dy=      "+dy+"          "+visible);
        if(distance>MINMOVEDISTANCE ){
            LogUtil.i("向上移动的距离超过了20px       "+distance);
            distance = 0;
            if(null != mListener && visible){
                mListener.hide();
                visible = false;
            }
        }else if(distance<-MINMOVEDISTANCE){
            LogUtil.e("向下移动的距离超过了20px       "+distance);
            distance = 0;
            if(null != mListener  && !visible){
                mListener.show();
                visible = true;
            }
        }

        distance +=dy;
    }
}

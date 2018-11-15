package test.cn.example.com.androidskill.view.defineView.hencoder.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import test.cn.example.com.util.DensityUtil;

/**
 * Created by xugan on 2018/11/13.
 */

public class DrawColorView extends View {
    private int mDefaultWidth = DensityUtil.dp2Px(150);
    private int mDefaultHeight = DensityUtil.dp2Px(150);
    private Context mContext;
    public DrawColorView(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mDefaultWidth,mDefaultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
    }
}

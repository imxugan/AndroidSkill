package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/1.
 */

public class CustomTitleView extends View {
    private final int defaultWidth = DensityUtil.dp2Px(150);
    private final int defaultHeight = DensityUtil.dp2Px(150);
    public CustomTitleView(Context context) {
        this(context,null);
        LogUtil.i("CustomTitleView(Context context)");
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context,attrs,0);
//        LogUtil.i("CustomTitleView(Context context, AttributeSet attrs)");
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView);
//        int count = array.getIndexCount();
//        for (int i =0;i<count;i++){
//            int arr = array.getIndex(i);
//            switch (arr){
//                case R.styleable.CustomTitleView_titleText:
//                    String titleText = array.getString(arr);
//                    LogUtil.i("titleText="+titleText);
//                    break;
//                case R.styleable.CustomTitleView_titleTextColor:
//                    String titleTextColor = array.getString(arr);
//                    LogUtil.i("titleTextColor="+titleTextColor);
//                    break;
//                case R.styleable.CustomTitleView_titleTextSize:
//                    String titleTextSize = array.getString(arr);
//                    LogUtil.i("titleTextSize="+titleTextSize);
//                    break;
//            }
//            array.recycle();

//        }
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i =0;i<count;i++){
            int arr = array.getIndex(i);
            LogUtil.e("arr="+arr);
            switch (arr){
                case R.styleable.CustomTitleView_titleText:
                    String titleText = array.getString(arr);
                    LogUtil.i("titleText="+titleText);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    String titleTextColor = array.getString(arr);
                    LogUtil.i("titleTextColor="+titleTextColor);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    String titleTextSize = array.getString(arr);
                    LogUtil.i("titleTextSize="+titleTextSize);
                    break;
                default:
                    String s = array.getString(arr);
                    LogUtil.i(""+s);
                    break;
            }
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if(MeasureSpec.AT_MOST == widthSpecMode &&
                MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            setMeasuredDimension(defaultWidth,heightSpecSize);
        }else if(MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(widthSpecSize,defaultHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i("onDraw");
    }
}

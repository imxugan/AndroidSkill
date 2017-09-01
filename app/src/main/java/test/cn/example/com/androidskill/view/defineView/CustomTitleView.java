package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/1.
 */

public class CustomTitleView extends View {
    public CustomTitleView(Context context) {
        super(context);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context,attrs,0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i =0;i<count;i++){
            int arr = array.getIndex(i);
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
            }
            array.recycle();

        }
    }

}

package test.cn.example.com.androidskill.view.defineView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.DensityUtil;
import test.cn.example.com.util.LogUtil;

import static test.cn.example.com.androidskill.R.styleable.CustomView_attr1;
import static test.cn.example.com.androidskill.R.styleable.CustomView_attr2;
import static test.cn.example.com.androidskill.R.styleable.CustomView_attr3;
import static test.cn.example.com.androidskill.R.styleable.CustomView_attr4;
import static test.cn.example.com.androidskill.R.styleable.CustomView_attr5;
import static test.cn.example.com.androidskill.R.styleable.CustomView_attr6;

/**
 * Created by xgxg on 2017/9/4.
 */

public class CustomView extends View {
    private final int DEFAULTWIDTH = DensityUtil.dp2Px(100);
    private final int DEFAULTHEIGHT = DensityUtil.dp2Px(100);
    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context,attrs, R.attr.customViewStyle);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomView,0,0);
//        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomView,defStyleAttr,0);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomView,defStyleAttr,R.style.default_custom_view_style);
        int attrCount = a.getIndexCount();
        for (int i = 0; i < attrCount; i++) {
            LogUtil.i("AttributeName="+attrs.getAttributeName(i));
            // <test.cn.example.com.androidskill.view.defineView.CustomView
//            android:layout_width="wrap_content"
//            android:layout_height="wrap_content"
//            app:attr1="attr1 from xml"
//            app:attr2="attr2 from xml"
//            style="@style/xml_style"
//            android:background="#ff0">

//            09-06 18:10:22.880 5338-5338/test.cn.example.com.androidskill I/MY_LOG: CustomView.java::43::<init>-->>AttributeName=background
//            09-06 18:10:22.880 5338-5338/test.cn.example.com.androidskill I/MY_LOG: CustomView.java::43::<init>-->>AttributeName=layout_width
//            09-06 18:10:22.890 5338-5338/test.cn.example.com.androidskill I/MY_LOG: CustomView.java::43::<init>-->>AttributeName=layout_height
//            09-06 18:10:22.890 5338-5338/test.cn.example.com.androidskill I/MY_LOG: CustomView.java::43::<init>-->>AttributeName=attr1
//            09-06 18:10:22.890 5338-5338/test.cn.example.com.androidskill I/MY_LOG: CustomView.java::43::<init>-->>AttributeName=attr2
//            09-06 18:10:22.890 5338-5338/test.cn.example.com.androidskill I/MY_LOG: CustomView.java::43::<init>-->>AttributeName=style
        }

        LogUtil.i("CustomView_attr1="+a.getString(CustomView_attr1));
        LogUtil.i("CustomView_attr2="+a.getString(CustomView_attr2));
        LogUtil.i("CustomView_attr3="+a.getString(CustomView_attr3));
        LogUtil.i("CustomView_attr4="+a.getString(CustomView_attr4));
        LogUtil.i("CustomView_attr5="+a.getString(CustomView_attr5));
        LogUtil.i("CustomView_attr6="+a.getString(CustomView_attr6));

        //CustomView_attr1=attr1 from xml
        //CustomView_attr2=attr2 from xml
        //CustomView_attr3=attr3 from xml style
        //CustomView_attr4=attr4 from custom title view style
        //CustomView_attr5=null
        //CustomView_attr6=null
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if(MeasureSpec.AT_MOST == widthSpecMode && MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,DEFAULTHEIGHT);
        }else if(MeasureSpec.AT_MOST == widthSpecMode){
            setMeasuredDimension(DEFAULTWIDTH,heightSpecSize);
        }else if(MeasureSpec.AT_MOST == heightSpecMode){
            setMeasuredDimension(widthSpecSize,DEFAULTHEIGHT);
        }
    }
}

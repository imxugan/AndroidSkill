package test.cn.example.com.androidskill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import test.cn.example.com.util.DensityUtil;

import static test.cn.example.com.androidskill.R.id.scrollImageViewToLeft;

/**
 * Scroll相关的知识点
 * Created by xgxg on 2017/9/15.
 */

public class ScrollActivity extends AppCompatActivity {
    private final String TAG = "ScrollActivity";
    private ImageView mScrollImageViewToLeft;
    private ImageView mScrollImageViewToRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        initView();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView scrollImageView = (ImageView) findViewById(R.id.scrollImageView);
        textView.setText("getScrollX="+scrollImageView.getScrollX()+"---getScrollY="+scrollImageView.getScrollY());
        Log.i(TAG,"getX()="+scrollImageView.getX()+"---getY()="+scrollImageView.getY());


        mScrollImageViewToRight = (ImageView) findViewById(R.id.scrollImageViewToRight);
        mScrollImageViewToRight.scrollTo(DensityUtil.dp2Px(100),0);
        TextView textViewToRight = (TextView) findViewById(R.id.textViewToRight);
        textViewToRight.setText("getScrollX="+ mScrollImageViewToRight.getScrollX()+"---getScrollY="+ mScrollImageViewToRight.getScrollY());
        Log.i(TAG,"getX()="+mScrollImageViewToRight.getX()+"---getY()="+mScrollImageViewToRight.getY());
        Log.i(TAG,"getLeft()="+mScrollImageViewToRight.getLeft()+"---getTop()="+mScrollImageViewToRight.getTop());


        mScrollImageViewToLeft = (ImageView) findViewById(scrollImageViewToLeft);
        mScrollImageViewToLeft.scrollTo(DensityUtil.dp2Px(-100),0);
        TextView textViewToLeft = (TextView) findViewById(R.id.textViewToLeft);
        textViewToLeft.setText("getScrollX="+ mScrollImageViewToLeft.getScrollX()+"---getScrollY="+ mScrollImageViewToLeft.getScrollY());

    }

    @Override
    protected void onStart() {
        super.onStart();
        mScrollImageViewToRight.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"mScrollImageViewToRight.getWidth()="+mScrollImageViewToRight.getWidth());
            }
        });

        mScrollImageViewToLeft.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"mScrollImageViewToLeft.getWidth()="+mScrollImageViewToLeft.getWidth());
                Log.i(TAG,"getX()="+mScrollImageViewToLeft.getX()+"---getY()="+mScrollImageViewToLeft.getY());
                Log.i(TAG,"getLeft()="+mScrollImageViewToLeft.getLeft()+"---getTop()="+mScrollImageViewToLeft.getTop());
                Log.i(TAG,"getPaddingLeft()="+mScrollImageViewToLeft.getPaddingLeft()+"---getPaddingTop()="+mScrollImageViewToLeft.getPaddingTop());

                int[] location = new int[2];
                mScrollImageViewToLeft.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Log.i(TAG,"x:"+x+"y:"+y);
            }
        });
    }
}

package test.cn.example.com.androidskill.ui.compact;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

public class GestureDetectorActivity2 extends AppCompatActivity {

    private TextView tv;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector_2);
//        子线程创建GestureDetector的几种方式
//        final Handler handler = new Handler();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //方式一：
////                Looper.prepare();
////                gestureDetector = new GestureDetector(GestureDetectorActivity2.this,gestureListener);
////                gestureDetector.setOnDoubleTapListener(onDoubleTapListener);
////                Looper.loop();
//
//                //方式二：
////                Handler handler = new Handler(Looper.getMainLooper());
////                gestureDetector = new GestureDetector(GestureDetectorActivity2.this,gestureListener,handler);
////                gestureDetector.setOnDoubleTapListener(onDoubleTapListener);
//
//                // 方式三：
//                gestureDetector = new GestureDetector(GestureDetectorActivity2.this,gestureListener,handler);
//                gestureDetector.setOnDoubleTapListener(onDoubleTapListener);
//            }
//        }).start();

//        gestureDetector = new GestureDetector(GestureDetectorActivity2.this,gestureListener);
//        gestureDetector.setOnDoubleTapListener(onDoubleTapListener);




//        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
//            @Override
//            public boolean onDown(MotionEvent e) {
//                LogUtil.i("super.onDown(e)=  "+super.onDown(e));
//                return super.onDown(e);
//            }
//
//            @Override
//            public void onShowPress(MotionEvent e) {
//                super.onShowPress(e);
//                LogUtil.i("onShowPress");
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                LogUtil.i("super.onSingleTapUp(e)=  "+super.onSingleTapUp(e));
//                return super.onSingleTapUp(e);
//            }
//
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                LogUtil.i("super.onSingleTapConfirmed(e)=  "+super.onSingleTapConfirmed(e));
//                return super.onSingleTapConfirmed(e);
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                LogUtil.i("super.onDoubleTap(e)=  "+super.onDoubleTap(e));
//                return super.onDoubleTap(e);
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                LogUtil.i("super.onDoubleTapEvent(e)=  "+super.onDoubleTapEvent(e));
//                return super.onDoubleTapEvent(e);
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//                super.onLongPress(e);
//                LogUtil.i("onLongPress");
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                LogUtil.i("super.onScroll(e1, e2, distanceX, distanceY)=  "+super.onScroll(e1, e2, distanceX, distanceY));
//                return super.onScroll(e1, e2, distanceX, distanceY);
//            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                LogUtil.i("super.onFling(e1, e2, velocityX, velocityY)=  "+super.onFling(e1, e2, velocityX, velocityY));
//                return super.onFling(e1, e2, velocityX, velocityY);
//            }
//        });
//        tv = findViewById(R.id.tv);
//        //为何要设置下面三项？
//        tv.setFocusable(true);
//        tv.setClickable(true);
//        tv.setLongClickable(true);
//
//        tv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                gestureDetector.onTouchEvent(event);
//                return false;
//            }
//        });
    }

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            LogUtil.i(Thread.currentThread().getName()+"      onDown");
            tv.setTextColor(getResources().getColor(R.color.accent));
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            LogUtil.i(Thread.currentThread().getName()+"      onShowPress");
            tv.setTextColor(getResources().getColor(R.color.black));
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //Tap:轻敲
            //SingleTapUp : 一次轻敲抬起
            LogUtil.i(Thread.currentThread().getName()+"      onSingleTapUp");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            LogUtil.i(Thread.currentThread().getName()+"      onScroll");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            LogUtil.i(Thread.currentThread().getName()+"      onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            LogUtil.i(Thread.currentThread().getName()+"      onFling");
            LogUtil.i("velocityX="+velocityX+"        velocityY="+velocityY);
            return false;
        }
    };

    private GestureDetector.OnDoubleTapListener onDoubleTapListener = new GestureDetector.OnDoubleTapListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            LogUtil.i(Thread.currentThread().getName()+"      onSingleTapConfirmed");
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            LogUtil.i(Thread.currentThread().getName()+"      onDoubleTap");
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            LogUtil.i(Thread.currentThread().getName()+"      onDoubleTapEvent");
            return false;
        }
    };

}

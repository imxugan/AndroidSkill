package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/14.
 */
public class QQListView2 extends ListView {

    private final LayoutInflater mInflater;
    private DeleteItemListener mDeleteItemListener;
    private final PopupWindow mPopupWindow;
    private float mDownX;
    private float mMoveX;
    private float mDownY;
    private float mMoveY;
    private int mCurrentPos;
    private View mCurrentView;
    private int[] mLocation;
    private final int mScaledTouchSlop;
    private boolean mIsSliding;

    public QQListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.delete_btn, null);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        View mDeleteButton = view.findViewById(R.id.item_btn_delete);
        mDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteItemListener.deleteItem(mCurrentPos);
                if (null != mPopupWindow) {
                    mPopupWindow.dismiss();
                }
            }
        });
//        PopupWindow的相关函数
//        （1）、构造函数：
//        [java] view plain copy
//        //方法一：
//        public PopupWindow (Context context)
//        //方法二：
//        public PopupWindow(View contentView)
//        //方法三：
//        public PopupWindow(View contentView, int width, int height)
//        //方法四：
//        public PopupWindow(View contentView, int width, int height, boolean focusable)
//        首要注意：看这里有四个构造函数，但要生成一个PopupWindow最基本的三个条件是一定要设置的：View contentView，int width, int height ；少任意一个就不可能弹出来PopupWindow！！！！
//        所以，如果使用方法一来构造PopupWindow，那完整的构造代码应该是这样的：
//        [java] view plain copy
//        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popuplayout, null);
//        PopupWindwo popWnd = PopupWindow (context);
//        popWnd.setContentView(contentView);
//        popWnd.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                LogUtil.i("downX="+ev.getX()+"---downY="+ev.getY());
//                mCurrentPos = pointToPosition((int) ev.getX(), (int) ev.getY());
//                LogUtil.i("mCurrentPosition="+mCurrentPos);
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
        boolean result = super.dispatchTouchEvent(ev);
        LogUtil.i("result=" + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null != mPopupWindow && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                mDownX = x;
                mDownY = y;

                mCurrentPos = pointToPosition((int) mDownX, (int) mDownY);
//                LogUtil.i("---mCurrentPos="+mCurrentPos);
                if(mCurrentPos == INVALID_POSITION){
                    return false;//当mCurrentPos为无效的位置时，需要返回false,这样，后面的
                                //move和up事件就都不会执行，这样，就不会出现空指针的异常
                }
                mCurrentView = getChildAt(mCurrentPos - getFirstVisiblePosition());
//                LogUtil.i(""+((TextView)mCurrentView).getText()+"---mCurrentPos="+mCurrentPos);
                mLocation = new int[2];
                mCurrentView.getLocationOnScreen(mLocation);

//                LogUtil.i("mCurrentView===="+mCurrentView+"---mCurrentPosition="+mCurrentPosition
//                        +"---getFirstVisiblePosition()="+getFirstVisiblePosition());
//                LogUtil.i("mLocation[0]===="+mLocation[0]+"---mLocation[1]="+mLocation[1]);
//                LogUtil.i("mCurrentView.getWidth()===="+mCurrentView.getWidth()+"---mCurrentView.getHeight()="+mCurrentView.getHeight());
            case MotionEvent.ACTION_MOVE:
                mMoveX = x;
                mMoveY = y;
                if (mMoveX < mDownX) {
                    //向左滑动
                    if (Math.abs(mMoveX - mDownX) > Math.abs(mDownY - mMoveY)) {
                        //横向移动的距离大于纵向移动的距离
                        if (Math.abs(mMoveX - mDownX) > mScaledTouchSlop) {
                            mIsSliding = true;
                        } else {
                            mIsSliding = false;
                        }
                        if (null != mPopupWindow && mIsSliding) {
                            mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT | Gravity.TOP,
                                    mLocation[0] + mCurrentView.getWidth(),
                                    mLocation[1]);
                        }
                    } else {
                        mIsSliding = false;
                    }
                } else {
                    //手指向右滑动，并且横向移动的距离大于纵向移动的距离，让popuwindow消失
                    if (Math.abs(mMoveX - mDownX) > Math.abs(mMoveY - mDownY)) {
                        if (Math.abs(mMoveX - mDownX) > mScaledTouchSlop) {
                            mIsSliding = true;
                        } else {
                            mIsSliding = false;
                        }
                        if (null != mPopupWindow && mPopupWindow.isShowing()) {
                            mPopupWindow.dismiss();
                        }
                    } else {
                        mIsSliding = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.i("mIsSliding:" + mIsSliding);
                if (mIsSliding) {
                    return true;//当listview的条目处于滑行状态，这里不返回super.onTouchEvent(ev),这样
                    //listview的条目点击事件就不会执行，因为通过源码返现，点击事件是在
                    //super.onTouchEvent(ev)方法内部执行的，如果不执行super.onTouchEvent(ev)
                    //则，listview的条目点击事件就无法响应。所以这里不返回super.onTouchEvent(ev)
                    //这里返回true或者false都可以，返回true,则表示listview自己处理这个up事件,返回
                    //false，表示listview自己不处理这个up事件
                } else {
                    return super.onTouchEvent(ev);
                }
        }
        return super.onTouchEvent(ev);
    }

    public interface DeleteItemListener {
        void deleteItem(int position);
    }

    public void setDeleteItemListener(DeleteItemListener listener) {
        this.mDeleteItemListener = listener;
    }
}

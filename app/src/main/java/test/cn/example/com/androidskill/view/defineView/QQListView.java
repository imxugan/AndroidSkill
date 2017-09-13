package test.cn.example.com.androidskill.view.defineView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xgxg on 2017/9/12.
 */

public class QQListView extends ListView {

    private final LayoutInflater mInflater;
    private final int mScaledTouchSlop;
    private final PopupWindow mPopupWindow;
    private final int mPopupWindowHeight;
    private final int mPopupWindowWidth;
    private int xDown,yDown;
    private int mCurrentViewPosition;
    private View mCurrentView;
    private int yMove;
    private int xMove;
    private boolean isSliding;
    private final Button mDeleteButton;
    private DeleteButtonListener mListener;

    public QQListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        View view = mInflater.inflate(R.layout.delete_btn, null);
        mDeleteButton = (Button) view.findViewById(R.id.item_btn_delete);
        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //先调用下measure,否则拿不到宽和高
        mPopupWindow.getContentView().measure(0,0);
        mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
        mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        LogUtil.i("dispatchTouchEvent ---ev.getAction()="+ev.getAction());
        int action = ev.getAction();
        int x = (int)ev.getX();
        int y = (int)ev.getY();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;
//                LogUtil.i("mPopupWindow======="+mPopupWindow);
                if(null != mPopupWindow && mPopupWindow.isShowing()){
                    mPopupWindow.dismiss();
                }
                //获取当前手指按下的位置
                mCurrentViewPosition = pointToPosition(xDown, yDown);
//                LogUtil.i("mCurrentViewPosition====="+mCurrentViewPosition+"---mPopupWindow======="+mPopupWindow
//                        +"---mPopupWindow.isShowing()="+mPopupWindow.isShowing());
                //获取当前手指按下时的item
                View view = getChildAt(mCurrentViewPosition - getFirstVisiblePosition());
                mCurrentView = view;
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;
                if(xMove<xDown &&Math.abs(dx)>mScaledTouchSlop && Math.abs(dy)<mScaledTouchSlop){
                    isSliding = true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        LogUtil.i("onTouchEvent ---ev.getAction()="+ev.getAction()+"---isSliding="+isSliding);
        if(isSliding){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(null != mCurrentView){
                        int[] location = new int[2];
                        //获取当前item的位置x与y
//                    LogUtil.i("mCurrentView======"+mCurrentView);
                        mCurrentView.getLocationOnScreen(location);
                        mPopupWindow.update();
                        mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT|Gravity.TOP,
                                location[0] + mCurrentView.getWidth(),
                                location[1]+ mCurrentView.getHeight()/2 - mPopupWindowHeight/2);
                        //设置删除按钮的回调
                        mDeleteButton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(null != mListener){
                                    mListener.onDelete(mCurrentViewPosition);
                                    //测试代码
//                                    test();
                                    mPopupWindow.dismiss();
                                }
                            }
                        });
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    isSliding = false;
                    //响应滑动期间屏幕item的点击事件，避免发生冲突
                    return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void test() {
        int childCount = getChildCount();
        TextView textView = (TextView) mCurrentView;
                                LogUtil.e("childCount======="+childCount+"---mCurrentViewPosition="+mCurrentViewPosition);
        for (int i = 0; i < childCount; i++) {
            TextView childAt = (TextView) getChildAt(i);
                                    LogUtil.e("mCurrentView="+textView.getText()+"---childAt="+childAt.getText());
        }
    }

    public void setDeleteButtonListener(DeleteButtonListener listener){
        mListener = listener;
    }


    public interface DeleteButtonListener{
        void onDelete(int position);
    }

}

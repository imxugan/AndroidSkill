package test.cn.example.com.androidskill.view.defineView.hencoder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.cn.example.com.androidskill.R;

public class TitleIndicator extends LinearLayout {
    private static final String TAG = "TitleIndicator";
    /**
     * 标题
     */
    private List<String> mLables = new ArrayList<>();
    /**
     * 存放标题的集合
     */
    private List<CheckedTextView> mCheckedList = new ArrayList<CheckedTextView>();
    /**
     * 存放标题的下划线集合
     */
    private List<View> mLineList = new ArrayList<View>();

    /**
     * 存放标题的集合
     */
    private List<TextView> mRedList = new ArrayList<TextView>();
    /**
     * 存放View的集合
     */
    private List<View> mViewList = new ArrayList<View>();
    /**
     * 屏幕的宽度
     */
    private int mScreenWidth;
    /**
     * 每个item的大小
     */
    private int mItemWidth;
    /**
     * 分割线
     */
    private View mLine;

    private String type;

    private int isFrom;
    // 回调接口
    private OnTitleIndicatorListener mTabListener;

    public TitleIndicator(Context context, List<String> mLables) {
        super(context);
        this.mLables = mLables;
        init(context);
    }

    public TitleIndicator(Context context, List<String> mLables, int isFrom) {
        super(context);
        this.mLables = mLables;
        this.isFrom = isFrom;
        init(context);
    }

    public TitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("NewApi")
    public TitleIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        this.setBackgroundColor(getResources().getColor(R.color.white));

        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mItemWidth = mScreenWidth / mLables.size();

        // 标题layout
        LinearLayout titleLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;

        LayoutParams titleParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater inflater = LayoutInflater.from(context);

        int size = mLables.size();
        for (int i = 0; i < size; i++) {
            final int index = i;
            View view = inflater.inflate(R.layout.title_indicator_item, null);
            CheckedTextView itemName = (CheckedTextView) view
                    .findViewById(R.id.item_name);
            View item_line = view.findViewById(R.id.item_line);
            String nowText = mLables.get(i);

            itemName.setText(nowText);

            titleLayout.addView(view, params);
            itemName.setTag(index);
            item_line.setTag(index);

            mCheckedList.add(itemName);
            mLineList.add(item_line);
            mViewList.add(view);

            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != mTabListener) {
                        mTabListener.onIndicatorSelected(index);
                    }
                }
            });

            // 初始化菜单栏

            if (i == 0) {
                itemName.setChecked(true);
                itemName.setTextColor(getResources().getColor(
                        R.color.c_00c8aa));
                item_line.setBackgroundColor(getResources().getColor(
                        R.color.c_00c8aa));
            } else {
                itemName.setChecked(false);
                itemName.setTextColor(getResources()
                        .getColor(R.color.c_666));
                item_line.setBackgroundColor(getResources().getColor(
                        R.color.c_e8e8e8));
            }
        }

        this.addView(titleLayout, titleParams);

    }

    /**
     * 设置红点是否显示，以及动态更新
     */
    public void setRedPointShow(String tag, int num) {
        int size = mRedList.size();
        TextView text = null;
        if (tag.equals("asktome")) {
            text = mRedList.get(size - 2);
        }

        if (tag.equals("edit")) {
            text = mRedList.get(0);
        }

        if (tag.equals("zhuiwen")) {
            text = mRedList.get(size - 1);
        }

        RelativeLayout layout = (RelativeLayout) text.getParent();
        if (num == 0) {
            Log.i(TAG, "ooNUM==" + num);
            layout.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "NUM==" + num);
            layout.setVisibility(View.VISIBLE);
            text.setText("" + num);
        }

    }

    /**
     * 设置底部导航中图片显示状态和字体颜色
     */
    public void setTabsDisplay(Context context, int index) {
        int size = mCheckedList.size();
        for (int i = 0; i < size; i++) {
            CheckedTextView checkedTextView = mCheckedList.get(i);
            View item_line = mLineList.get(i);
            if ((Integer) (checkedTextView.getTag()) == index) {
                checkedTextView.setChecked(true);
                checkedTextView.setTextColor(getResources().getColor(R.color.c_00c8aa));
                item_line.setBackgroundColor(getResources().getColor(R.color.c_00c8aa));
            } else {
                checkedTextView.setChecked(false);
                checkedTextView.setTextColor(getResources().getColor(R.color.c_666));
                item_line.setBackgroundColor(getResources().getColor(R.color.c_e8e8e8));
            }
        }
    }

    public void setOnTitleIndicatorListener(OnTitleIndicatorListener listener) {
        this.mTabListener = listener;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public interface OnTitleIndicatorListener {
        void onIndicatorSelected(int index);
    }

}

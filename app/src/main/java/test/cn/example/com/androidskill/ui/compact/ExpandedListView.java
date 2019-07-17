package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by xugan on 2019/7/17.
 */

public class ExpandedListView extends ListView {
    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

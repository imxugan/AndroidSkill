package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/6/12.
 */

public class DividerGridViewItemDecoraton extends RecyclerView.ItemDecoration {
    private final Context mContext;
    private final Drawable mDivider;
    private int[] arrts = new int[]{
            android.R.attr.listDivider
    };

    public DividerGridViewItemDecoraton(Context context){
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(arrts);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.onDraw(c, parent, state);
        LogUtil.e(parent.getChildCount()+"");
        drawHorizontal(c,parent);
        drawVertical(c,parent);

    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        //绘制垂直方向的分割线
        int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight()+layoutParams.rightMargin;
            int top = child.getTop()-layoutParams.topMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int bottom = child.getBottom()+layoutParams.bottomMargin;
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        //绘制水平方向的分割线
        int count = parent.getChildCount();
        for(int i=0;i<count;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft()-params.leftMargin;
            int top = child.getBottom()+params.bottomMargin;
            int right = child.getRight()+params.rightMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
//        super.getItemOffsets(outRect, itemPosition, parent);
        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();
        if(isLastColumn(itemPosition,parent)){
            right = 0;
        }

        if(isLastRow(itemPosition,parent)){
            bottom = 0;
        }
        LogUtil.i(itemPosition+"        "+right+"       "+bottom);
        outRect.set(0,0,right,bottom);
    }

    //最后一列
    private boolean isLastColumn(int itemPosition,RecyclerView parent){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            int spanCount = getSpanCount(parent);
            if((itemPosition+1)%spanCount==0){
                return true;
            }
        }
        return false;

    }

    private boolean isLastRow(int itemPosition,RecyclerView parent){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            int spanCount = getSpanCount(parent);
            int itemCount = parent.getAdapter().getItemCount();
            int rowCount = itemCount%spanCount;
            if(0==rowCount || rowCount<spanCount){
                return true;
            }
        }
        return false;
    }

    private int getSpanCount(RecyclerView parent){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            return lm.getSpanCount();
        }
        return 0;
    }
}

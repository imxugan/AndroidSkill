package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.FitWindowsLinearLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xugan on 2019/6/13.
 */

public class DivierItemDecoration extends RecyclerView.ItemDecoration {
    private int mOrientation;
    private final Context mContext;
    private int[] attrs = new int[]{
            android.R.attr.listDivider
    };
    private final Drawable mDivider;

    public DivierItemDecoration(Context context, int orientation){
        if(orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL){
            throw new IllegalStateException("no oritentation");
        }
        setOrientation(orientation);
        this.mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    public void setOrientation(int orientation){
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(mOrientation == LinearLayoutManager.HORIZONTAL){
            drawVerticalDivider(c,parent);
        }else {
            drawHorizontalDivider(c,parent);
        }
    }

    private void drawVerticalDivider(Canvas c,RecyclerView parent){
        int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            int left = view.getRight()+layoutParams.rightMargin;
            int top = view.getTop()-layoutParams.topMargin;
            int right = left+mDivider.getIntrinsicWidth();
            int bottom = view.getBottom()+layoutParams.bottomMargin;
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontalDivider(Canvas c,RecyclerView parent){
        int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            int left = view.getLeft()-layoutParams.leftMargin;
            int top = view.getBottom()+layoutParams.bottomMargin;
            int right = view.getRight()+layoutParams.rightMargin;
            int bottom = top+mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(mOrientation == LinearLayoutManager.VERTICAL){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else {
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }
    }
}

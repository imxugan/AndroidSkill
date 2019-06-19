package test.cn.example.com.androidskill.ui.compact;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import test.cn.example.com.util.LogUtil;


/**
 * Created by xugan on 2019/6/18.
 */

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback{
    private final ItemMoveListener mItemMoveListener;

    public MyItemTouchHelperCallback(ItemMoveListener itemMoveListener) {
        this.mItemMoveListener = itemMoveListener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP; //注意这里是 | 运算
        int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        //是否允许长按的拖拽效果
        return true;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder srcHolder, @NonNull RecyclerView.ViewHolder targetHolder) {
        //当来回拖拽时回调这个方法
        if(srcHolder.getItemViewType() != targetHolder.getItemViewType()){
            //如果不是相同类型的条目则不进行拖拽移动
            return false;
        }
        boolean result = mItemMoveListener.onItemMove(srcHolder.getAdapterPosition(), targetHolder.getAdapterPosition());
        return result;//返回ture，才起作用
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        LogUtil.e(viewHolder.getAdapterPosition()+"             "+direction);
        //侧滑时，回调
        if(direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT){
            //向左或者向右侧滑时
            mItemMoveListener.onItemRemove(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //选择的条目，改变背景颜色
        if(ItemTouchHelper.ACTION_STATE_IDLE != actionState){
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(android.R.color.holo_blue_light));
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        LogUtil.i(""+viewHolder.getAdapterPosition());
        //恢复选中的条目的背景颜色
        viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(android.R.color.transparent));
        //恢复之前设置的各个动画效果，防止，出现条目复用时，出现空白的条目的bug
        viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleX(1);
        viewHolder.itemView.setScaleY(1);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        LogUtil.i(viewHolder.getAdapterPosition()+"     dX="+dX+"      dY="+dY+"        "+viewHolder.itemView.getWidth());
        //dX 横向移动的增量,负数，向左，正数 向右,范围 0---view.getWidth()
        //dY 纵向移动的增量
        float alpha = 1 - (Math.abs(dX)) / viewHolder.itemView.getWidth();
        if(ItemTouchHelper.ACTION_STATE_SWIPE==actionState){
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);

            if(Math.abs(dX)<=viewHolder.itemView.getWidth()/2){
                viewHolder.itemView.setTranslationX(dX);
            }else {
                viewHolder.itemView.setTranslationX(-viewHolder.itemView.getWidth()/2);
            }
        }
    }
}

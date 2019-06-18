package test.cn.example.com.androidskill.ui.compact;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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
        //侧滑时，回调
        if(direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT){
            //向左或者向右侧滑时
            mItemMoveListener.onItemRemove(viewHolder.getAdapterPosition());
        }

    }
}

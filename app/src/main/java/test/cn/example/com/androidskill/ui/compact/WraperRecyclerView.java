package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by xugan on 2019/6/17.
 */

public class WraperRecyclerView extends RecyclerView {
    private ArrayList<View> mHeaderViewInfos = new ArrayList<View>();
    private ArrayList<View> mFootViewInfos = new ArrayList<View>();
    private Adapter mAdapter;
    public WraperRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void addHeaderView(View v){
        mHeaderViewInfos.add(v);
        if(mAdapter!=null){
            if(!(mAdapter instanceof HeaderViewRecyclerAdapter)){
                mAdapter = new HeaderViewRecyclerAdapter(mHeaderViewInfos,mFootViewInfos,mAdapter);
            }
        }
    }

    public void addFooterView(View v){
        mFootViewInfos.add(v);
        if(mAdapter!=null){
            if(!(mAdapter instanceof HeaderViewRecyclerAdapter)){
                mAdapter = new HeaderViewRecyclerAdapter(mHeaderViewInfos,mFootViewInfos,mAdapter);
            }
        }
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if(mHeaderViewInfos.size()>0 || mFootViewInfos.size()>0){
            mAdapter = new HeaderViewRecyclerAdapter(mHeaderViewInfos,mFootViewInfos,adapter);
        }else {
            mAdapter = adapter;
        }
        super.setAdapter(mAdapter);//这里记得是mAdapter，不是adapter,坑了半个小时
    }
}

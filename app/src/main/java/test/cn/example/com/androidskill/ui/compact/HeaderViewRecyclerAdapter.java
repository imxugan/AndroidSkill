package test.cn.example.com.androidskill.ui.compact;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xugan on 2019/6/17.
 */

public class HeaderViewRecyclerAdapter extends RecyclerView.Adapter {
    private final int TYPE_HEADER = -2;
    private final int TYPE_BODY = 1;
    private final int TYPE_FOOTER = -1;
    private List<View> mHeaderViewInfos = new ArrayList<View>();
    private List<View> mFooterViewInfos = new ArrayList<View>();
    private RecyclerView.Adapter mAdapter;

    public HeaderViewRecyclerAdapter(List<View> headerViewInfos, List<View> footerViewInfos, RecyclerView.Adapter adapter){
        if(null == headerViewInfos){
            mHeaderViewInfos = new ArrayList<>();
        }else {
            mHeaderViewInfos = headerViewInfos;
        }

        if(null == footerViewInfos){
            mFooterViewInfos = new ArrayList<>();
        }else {
            mFooterViewInfos = footerViewInfos;
        }

        this.mAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        int headersCount = getHeaderCount();
        if(position<headersCount){
            return TYPE_HEADER;
        }
        int adjPosition = position - headersCount;
        int adapterCount = 0;
        if(null != mAdapter){
            adapterCount = mAdapter.getItemCount();
            if(adjPosition<adapterCount){
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        return TYPE_FOOTER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(TYPE_HEADER == viewType){
            return new HeaderViewHoder(mHeaderViewInfos.get(0));
        }else if(TYPE_FOOTER == viewType){
            return new HeaderViewHoder(mFooterViewInfos.get(0));
        }else {
           return mAdapter.onCreateViewHolder(viewGroup,viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int headerCount = getHeaderCount();
        if(position<headerCount){
            //header 什么都不做
            return;
        }

        int adjPositon = position - headerCount;
        int adapterCount = 0;
        if(null != mAdapter){
            adapterCount = mAdapter.getItemCount();
            if(adjPositon<adapterCount){
                mAdapter.onBindViewHolder(viewHolder,adjPositon);//这里要传adjPositon，否则会报角标越界异常
                return;
            }
        }

        //footer 也是什么都不做
    }

    private int getHeaderCount() {
        return mHeaderViewInfos.size();
    }

    private int getFooterCount(){
        return mFooterViewInfos.size();
    }

    @Override
    public int getItemCount() {
        if(null == mAdapter){
            return getHeaderCount()+getFooterCount();
        }else {
            return getHeaderCount()+getFooterCount()+mAdapter.getItemCount();
        }

    }
}

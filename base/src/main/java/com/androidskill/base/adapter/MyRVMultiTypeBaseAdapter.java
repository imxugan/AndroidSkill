package com.androidskill.base.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MyRVMultiTypeBaseAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context context;

    public MyRVMultiTypeBaseAdapter(Context context) {
        super(context, null);
        this.mDatas = new ArrayList<>();
        this.context=context;

    }

    public T getItem(int position) {
        if (null == mDatas && mDatas.isEmpty()) {
            return null;
        }
        if (position > -1 && position < mDatas.size()) {
            return mDatas.get(position);
        }
        return null;
    }

    public void setData(List<T> dataList) {
        if (null != mDatas) {
            mDatas.clear();
        } else {
            mDatas = new ArrayList<>();
        }

        if (null != dataList && !dataList.isEmpty()) {
            mDatas.addAll(dataList);
        }
    }

    public void addData(List<T> dataList) {
        if (null == mDatas) {
            mDatas = new ArrayList<>();
        }

        if (null != dataList && !dataList.isEmpty()) {
            this.mDatas.addAll(dataList);
        }
    }

    public void addData(T dataItem) {
        if (null == mDatas) {
            mDatas = new ArrayList<>();
        }

        if (null != dataItem) {
            this.mDatas.add(dataItem);
        }
    }

    public void addData(int positon,T dataItem){
        if (null == mDatas) {
            mDatas = new ArrayList<>();
        }

        if (null != dataItem) {
            this.mDatas.add(positon,dataItem);
        }
        notifyItemInserted(positon);
    }

    public void removeData(T dataItem) {
        if (null == mDatas || mDatas.isEmpty()) {
            return;
        }
        if (mDatas.contains(dataItem)) {
            mDatas.remove(dataItem);
        }
    }
}
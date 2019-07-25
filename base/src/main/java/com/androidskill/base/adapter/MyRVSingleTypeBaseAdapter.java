package com.androidskill.base.adapter;

import android.content.Context;

public abstract class MyRVSingleTypeBaseAdapter<T> extends MyRVMultiTypeBaseAdapter<T> {
    public MyRVSingleTypeBaseAdapter(Context context) {
        super(context);
        addItemViewDelegate(new MyRVSingleTypeDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return getItemLayoutResId();
            }

            @Override
            public void convert(ViewHolder holder, T item, int position) {

                MyRVSingleTypeBaseAdapter.this.convert(holder,item,position);
            }
        });

    }



    protected abstract int getItemLayoutResId();

    protected abstract void convert(ViewHolder holder, T item, int position);
}
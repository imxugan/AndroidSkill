package com.androidskill.base.adapter;

public abstract class MyRVSingleTypeDelegate<T> implements ItemViewDelegate<T> {
    @Override
    public boolean isForViewType(T item, int position)
    {
        //只有一种Item 返回true
        return true;
    }

}
package com.androidskill.base.view;
/**
 * Created by xugan on 2019/7/24.
 */
public interface IBaseView<T> {
    void onSuccessResultView(T t, byte flag);
    void onErrorResultView(T t, byte flag);
    void setProgressView(int visibility, byte flag);
}
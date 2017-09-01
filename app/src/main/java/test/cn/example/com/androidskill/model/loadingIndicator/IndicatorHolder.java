package test.cn.example.com.androidskill.model.loadingIndicator;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.androidskill.view.loadingIndicator.LoadingIndicatorView;

/**
 * Created by xgxg on 2017/8/30.
 */
public class IndicatorHolder extends RecyclerView.ViewHolder{
    public LoadingIndicatorView indicatorView;
    public View itemLayout;
    public IndicatorHolder(View itemView) {
        super(itemView);
        itemLayout = itemView.findViewById(R.id.itemLayout);
        indicatorView = (LoadingIndicatorView) itemView.findViewById(R.id.indicator);
    }
}

package test.cn.example.com.androidskill.ui.compact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import test.cn.example.com.androidskill.R;
import test.cn.example.com.util.LogUtil;

/**
 * Created by xugan on 2019/7/17.
 */

public class MyListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;
    private final LayoutInflater inflater;

    public MyListViewAdapter(Context context,List<String> data){
        this.mContext = context;
        this.mData = data;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return (null == mData)?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            viewHolder.btn = convertView.findViewById(R.id.btn);
            viewHolder.btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    LogUtil.e("false---      "+event.getAction());
                    return false;
                }
            });
            viewHolder.tv = convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.btn.setText(mData.get(position)+"     button");
        viewHolder.tv.setText(mData.get(position)+"     text view");
        return convertView;
    }

    class ViewHolder{
        MyButton btn;
        TextView tv;
    }
}

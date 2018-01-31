package com.doumengmengandroidbady.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.DiagramDataActivity;
import com.doumengmengandroidbady.response.ImageData;

import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 曲线图数据适配器
 * 创建日期: 2018/1/17 14:23
 */
public class DiagramDataAdapter extends BaseAdapter {
    private final Context context;
    private final DiagramDataActivity.DIAGRAM_TYPE type;
    private final List<ImageData> datas;

    public DiagramDataAdapter(Context context,List<ImageData> datas,DiagramDataActivity.DIAGRAM_TYPE type) {
        this.datas = datas;
        this.type = type;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if ( view == null ){
            view = LayoutInflater.from(context).inflate(R.layout.item_diagram_data,null);
        }
        TextView tv_left = view.findViewById(R.id.tv_left);
        TextView tv_center = view.findViewById(R.id.tv_center);
        TextView tv_right = view.findViewById(R.id.tv_right);

        ImageData data = datas.get(position);

        tv_left.setText(data.getRecordtime().split(" ")[0]);
        if ( type == DiagramDataActivity.DIAGRAM_TYPE.TYPE_HEIGHT){
            tv_center.setText(String.format(context.getResources().getString(R.string.diagram_data_month_day),data.getMonthAge(),data.getMonthDay()));
            tv_right.setText(String.valueOf(data.getHeight()));
        } else if ( type == DiagramDataActivity.DIAGRAM_TYPE.TYPE_WEIGHT){
            tv_center.setText(String.format(context.getResources().getString(R.string.diagram_data_month_day),data.getMonthAge(),data.getMonthDay()));
            tv_right.setText(String.valueOf(data.getWeight()));
        } else {
            tv_center.setText(String.valueOf(data.getHeight()));
            tv_right.setText(String.valueOf(data.getWeight()));
        }
        return view;
    }
}

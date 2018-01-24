package com.doumengmengandroidbady.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.doumengmengandroidbady.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public class PayAdapter extends BaseAdapter {

    private List<PayData> datas;

    public PayAdapter(List<PayData> datas) {
        this.datas = datas;
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
        ViewHolder holder = null;
        if ( view == null ){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dialog_pay,null);
            holder = new ViewHolder();
            holder.iv_icon = view.findViewById(R.id.iv_icon);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.cb_choose = view.findViewById(R.id.cb_choose);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        PayData data = datas.get(position);
        holder.iv_icon.setImageResource(data.getDrawableId());
        holder.tv_name.setText(data.getName());
        holder.cb_choose.setChecked(data.isCheck());
        return view;
    }

    public void setClickPosition(int position){
        for (int i=0;i<datas.size();i++){
            PayData data = datas.get(i);
            if ( i == position ){
                data.setCheck(true);
            } else {
                data.setCheck(false);
            }
        }
        notifyDataSetChanged();
    }

    public int getChoosePosition(){
        for (int i=0;i<datas.size();i++){
            PayData data = datas.get(i);
            if ( data.isCheck ){
                return i;
            }
        }
        return -1;
    }

    private static class ViewHolder{
        public ImageView iv_icon;
        private TextView tv_name;
        private CheckBox cb_choose;
    }

    public static class PayData{
        private int drawableId;
        private String name;
        private boolean isCheck;

        public PayData(int drawableId, String name, boolean isCheck) {
            this.drawableId = drawableId;
            this.name = name;
            this.isCheck = isCheck;
        }

        public int getDrawableId() {
            return drawableId;
        }

        public void setDrawableId(int drawableId) {
            this.drawableId = drawableId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }

}

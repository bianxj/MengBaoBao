package com.doumengmeng.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmeng.doctor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class AccountDetailAdapter extends RecyclerView.Adapter<AccountDetailAdapter.ViewHolder> {

    private List<AccountDetailData> datas;

    public AccountDetailAdapter(List<AccountDetailData> datas) {
        if ( datas == null ){
            this.datas = new ArrayList<>();
        } else {
            this.datas = datas;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_detail,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.initView(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_customer_info;
        private TextView tv_date;
        private TextView tv_time;
        private TextView tv_income;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_customer_info = itemView.findViewById(R.id.tv_customer_info);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_income = itemView.findViewById(R.id.tv_income);
        }

        private void initView(AccountDetailData data){
            tv_customer_info.setText(data.getName());
            tv_date.setText(data.getDate());
            tv_time.setText(data.getTime());

            double cost = Double.parseDouble(data.getCost());
            if ( cost >= 0  ) {
                tv_income.setText("+"+data.getCost());
            } else {
                tv_income.setText(data.getCost());
            }
        }

    }

    public static class AccountDetailData{
        private String name;
        private String date;
        private String time;
        private String cost;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }
    }

}

package com.doumengmeng.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmeng.doctor.R;

/**
 * Created by Administrator on 2018/3/7.
 */

public class AccountDetailAdapter extends RecyclerView.Adapter<AccountDetailAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_detail,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
    }

}

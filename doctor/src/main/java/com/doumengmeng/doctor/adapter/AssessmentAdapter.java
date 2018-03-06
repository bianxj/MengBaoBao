package com.doumengmeng.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmeng.doctor.R;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assessment,null);
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

        private TextView tv_month_age,tv_over_time,tv_accessment_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_month_age = itemView.findViewById(R.id.tv_month_age);
            tv_over_time = itemView.findViewById(R.id.tv_over_time);
            tv_accessment_time = itemView.findViewById(R.id.tv_accessment_time);
        }

    }

}

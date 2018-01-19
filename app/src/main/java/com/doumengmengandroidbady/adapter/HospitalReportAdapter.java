package com.doumengmengandroidbady.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.HospitalReportActivity;
import com.doumengmengandroidbady.entity.HospitalReport;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class HospitalReportAdapter extends RecyclerView.Adapter<HospitalReportAdapter.ViewHolder> {

    private List<HospitalReport> reports;

    public HospitalReportAdapter(List<HospitalReport> reports) {
        this.reports = reports;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_report,null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HospitalReport report = reports.get(position);
        holder.position = position;
        holder.tv_hospital_name.setText(report.getHospitalName());
        holder.tv_baby_month.setText(report.getMonthAge());
        holder.tv_record_date.setText(report.getReportTime());
        holder.tv_weight.setText(report.getWeight());
        holder.tv_height.setText(report.getHeight());
        holder.tv_bmi.setText(report.getBmi());
        holder.tv_develop.setText(report.getDevelop());
        holder.report = report;
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_hospital_name , tv_record_date , tv_baby_month;
        private TextView tv_weight , tv_height , tv_develop , tv_bmi;
        private int position;
        private HospitalReport report;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_hospital_name = itemView.findViewById(R.id.tv_hospital_name);
            tv_record_date = itemView.findViewById(R.id.tv_record_date);
            tv_baby_month = itemView.findViewById(R.id.tv_baby_month);
            tv_weight = itemView.findViewById(R.id.tv_weight);
            tv_height = itemView.findViewById(R.id.tv_height);
            tv_develop = itemView.findViewById(R.id.tv_develop);
            tv_bmi = itemView.findViewById(R.id.tv_bmi);
            itemView.setOnClickListener(listener);
        }
        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HospitalReportActivity.class);
                v.getContext().startActivity(intent);
            }
        };
    }

}

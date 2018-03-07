package com.doumengmeng.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmeng.doctor.R;

/**
 * Created by Administrator on 2017/12/19.
 */

public class HospitalReportAdapter extends RecyclerView.Adapter<HospitalReportAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_report,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.initView(report);
    }

    @Override
    public int getItemCount() {
//        return reports.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_hospital_name;
        private final TextView tv_record_date;
        private final TextView tv_baby_month;
        private final TextView tv_weight;
        private final TextView tv_height;
        private final TextView tv_develop;
        private final TextView tv_bmi;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_hospital_name = itemView.findViewById(R.id.tv_hospital_name);
            tv_record_date = itemView.findViewById(R.id.tv_record_date);
            tv_baby_month = itemView.findViewById(R.id.tv_baby_month);
            tv_weight = itemView.findViewById(R.id.tv_weight);
            tv_height = itemView.findViewById(R.id.tv_height);
            tv_develop = itemView.findViewById(R.id.tv_develop);
            tv_bmi = itemView.findViewById(R.id.tv_bmi);
//            itemView.setOnClickListener(listener);
        }

//        public void initView(HospitalReport report){
//            tv_hospital_name.setText(report.getHospitalName());
//            tv_baby_month.setText(report.getBabyMonth());
//            tv_bmi.setText(report.getHwResultString());
//            tv_develop.setText(report.getFeatureResultString());
//            tv_height.setText(report.getHeightResultString());
//            tv_record_date.setText(report.getRecordDay());
//            tv_weight.setText(report.getWeightResultString());
//            this.report = report;
//        }

//        private final View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), HospitalReportActivity.class);
//                intent.putExtra(HospitalReportActivity.IN_PARAM_REPORT_DATA, GsonUtil.getInstance().toJson(report));
//                v.getContext().startActivity(intent);
//            }
//        };
    }

}
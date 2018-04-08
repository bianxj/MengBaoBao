package com.doumengmeng.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.activity.AssessmentActivity;
import com.doumengmeng.customer.response.entity.Record;
import com.doumengmeng.customer.util.GsonUtil;

import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 记录信息适配器
 * 创建日期: 2018/1/15 14:37
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordHolder> {

    private final List<Record> records;

    public RecordAdapter(List<Record> records) {
        this.records = records;
    }

    @Override
    public RecordAdapter.RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record,null);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordAdapter.RecordHolder holder, int position) {
        Record record = records.get(position);
        holder.initView(record);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class RecordHolder extends RecyclerView.ViewHolder{

        private final TextView tv_read_state;
        private final TextView tv_record_date;
        private final TextView tv_baby_month;
        private final TextView tv_weight;
        private final TextView tv_height;
        private final TextView tv_bmi;
        private final TextView tv_develop;
        private final View view;
        private Record record;

        public RecordHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_read_state = itemView.findViewById(R.id.tv_read_state);
            tv_record_date = itemView.findViewById(R.id.tv_record_date);
            tv_baby_month = itemView.findViewById(R.id.tv_baby_month);
            tv_weight = itemView.findViewById(R.id.tv_weight);
            tv_height = itemView.findViewById(R.id.tv_height);
            tv_bmi = itemView.findViewById(R.id.tv_bmi);
            tv_develop = itemView.findViewById(R.id.tv_develop);
            view.setOnClickListener(listener);
        }

        private void initView(Record record){
            tv_baby_month.setText(record.getBabyMonth());
            tv_bmi.setText(record.getHwResultString());
            tv_develop.setText(record.getFeatureResultString());
            tv_height.setText(record.getHeightResultString());
            if ( record.isShowRecordState() ){
                tv_read_state.setVisibility(View.VISIBLE);
            } else {
                tv_read_state.setVisibility(View.INVISIBLE);
            }
            tv_record_date.setText(record.getRecordDay());
            tv_weight.setText(record.getWeightResultString());
            this.record = record;
        }

        private final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AssessmentActivity.class);
                intent.putExtra(AssessmentActivity.IN_PARAM_RECORD, GsonUtil.getInstance().toJson(record));
                context.startActivity(intent);
            }
        };

    }

}

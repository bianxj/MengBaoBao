package com.doumengmengandroidbady.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.entity.Record;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordHolder> {

    private List<Record> records;

    public RecordAdapter(List<Record> records) {
        this.records = records;
    }

    @Override
    public RecordAdapter.RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record,null);
        RecordHolder holder = new RecordHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordAdapter.RecordHolder holder, int position) {
        Record record = records.get(position);
        holder.tv_baby_month.setText(record.getBabyMonth());
        holder.tv_bmi.setText(record.getBmi());
        holder.tv_develop.setText(record.getDevelop());
        holder.tv_height.setText(record.getHeight());
        holder.tv_read_state.setText(record.getReadState());
        holder.tv_record_date.setText(record.getRecordDate());
        holder.tv_weight.setText(record.getWeight());
        holder.record = record;
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class RecordHolder extends RecyclerView.ViewHolder{

        private TextView tv_read_state;
        private TextView tv_record_date;
        private TextView tv_baby_month;
        private TextView tv_weight;
        private TextView tv_height;
        private TextView tv_bmi;
        private TextView tv_develop;
        private Record record;

        public RecordHolder(View itemView) {
            super(itemView);
            tv_read_state = itemView.findViewById(R.id.tv_read_state);
            tv_record_date = itemView.findViewById(R.id.tv_record_date);
            tv_baby_month = itemView.findViewById(R.id.tv_baby_month);
            tv_weight = itemView.findViewById(R.id.tv_weight);
            tv_height = itemView.findViewById(R.id.tv_height);
            tv_bmi = itemView.findViewById(R.id.tv_bmi);
            tv_develop = itemView.findViewById(R.id.tv_develop);
        }
    }

}

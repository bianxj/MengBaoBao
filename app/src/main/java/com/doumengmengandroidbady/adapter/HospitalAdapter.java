package com.doumengmengandroidbady.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.entity.Hospital;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private List<Hospital> hospitals;

    public HospitalAdapter(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hospital hospital = hospitals.get(position);
        ImageLoader.getInstance().displayImage(hospital.getImageUrl(),holder.civ_hospital);
        holder.tv_hospital_address.setText(hospital.getHospitalAddress());
        holder.tv_hospital_name.setText(hospital.getName());
        holder.hospital = hospital;
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView civ_hospital;
        private TextView tv_hospital_name , tv_hospital_address;
        private Hospital hospital;

        public ViewHolder(View itemView) {
            super(itemView);
            civ_hospital = itemView.findViewById(R.id.civ_hospital);
            tv_hospital_name = itemView.findViewById(R.id.tv_hospital_name);
            tv_hospital_address = itemView.findViewById(R.id.tv_hospital_address);
        }
    }

//    private Context context;
//    private List<Hospital> hospitals;
//
//    public HospitalAdapter(Context context,List<Hospital> hospitals) {
//        this.hospitals = hospitals;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return hospitals.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return hospitals.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if ( null == convertView ){
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_hospital,null);
//        }
//
//        CircleImageView civ_hospital = convertView.findViewById(R.id.civ_hospital);
//        TextView tv_hospital_name = convertView.findViewById(R.id.tv_hospital_name);
//        TextView tv_hospital_address = convertView.findViewById(R.id.tv_hospital_address);
//
//        Hospital hospital = hospitals.get(position);
//        ImageLoader.getInstance().displayImage(hospital.getImageUrl(),civ_hospital);
//        tv_hospital_name.setText(hospital.getName());
//        tv_hospital_address.setText(hospital.getHospitalAddress());
//        return null;
//    }
}

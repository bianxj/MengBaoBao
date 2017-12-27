package com.doumengmengandroidbady.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.HospitalDoctorActivity;
import com.doumengmengandroidbady.entity.HospitalEntity;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private List<HospitalEntity> hospitals;

    public HospitalAdapter(List<HospitalEntity> hospitals) {
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
        HospitalEntity hospital = hospitals.get(position);
        holder.initValue(hospital);
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView civ_hospital;
        private TextView tv_hospital_name , tv_hospital_address;
        private HospitalEntity hospital;

        public ViewHolder(View itemView) {
            super(itemView);
            civ_hospital = itemView.findViewById(R.id.civ_hospital);
            tv_hospital_name = itemView.findViewById(R.id.tv_hospital_name);
            tv_hospital_address = itemView.findViewById(R.id.tv_hospital_address);
            itemView.setOnClickListener(listener);
        }

        private void initValue(HospitalEntity hospital){
            this.hospital = hospital;
            ImageLoader.getInstance().displayImage(hospital.getHospitalicon(),civ_hospital);
            tv_hospital_address.setText(hospital.getHospitaladdress());
            tv_hospital_name.setText(hospital.getHospitalname());
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                Context context = view.getContext();
                Intent intent = new Intent(context, HospitalDoctorActivity.class);
                context.startActivity(intent);
            }
        };

    }

//    private Context context;
//    private List<HospitalEntity> hospitals;
//
//    public HospitalAdapter(Context context,List<HospitalEntity> hospitals) {
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
//        HospitalEntity hospital = hospitals.get(position);
//        ImageLoader.getInstance().displayImage(hospital.getHospitalicon(),civ_hospital);
//        tv_hospital_name.setText(hospital.getDoctorname());
//        tv_hospital_address.setText(hospital.getHospitaladdress());
//        return null;
//    }
}

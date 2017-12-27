package com.doumengmengandroidbady.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.DoctorInfoActivity;
import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {

    private List<DoctorEntity> doctors;

    public DoctorAdapter(List<DoctorEntity> doctors) {
        this.doctors = doctors;
    }

    @Override
    public DoctorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor,null);
        DoctorHolder hv = new DoctorHolder(view);
        return hv;
    }

    @Override
    public void onBindViewHolder(DoctorHolder holder, int position) {
        DoctorEntity doctor = doctors.get(position);

        ImageLoader.getInstance().displayImage(doctor.getDoctorimg(),holder.civ_head);
        holder.tv_doctor_hospital.setText(doctor.getHospital());
        holder.tv_doctor_position.setText(doctor.getPositionaltitles());
        holder.tv_doctor_name.setText(doctor.getDoctorname());
        holder.tv_doctor_skill.setText(doctor.getSpeciality());
        holder.doctor = doctor;
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public static class DoctorHolder extends  RecyclerView.ViewHolder{

        private CircleImageView civ_head;
        private TextView tv_doctor_name , tv_doctor_position;
        private TextView tv_doctor_hospital , tv_doctor_skill;
        private DoctorEntity doctor;

        public DoctorHolder(View itemView) {
            super(itemView);
            civ_head = itemView.findViewById(R.id.civ_head);
            tv_doctor_name = itemView.findViewById(R.id.tv_doctor_name);
            tv_doctor_position = itemView.findViewById(R.id.tv_doctor_position);
            tv_doctor_hospital = itemView.findViewById(R.id.tv_doctor_hospital);
            tv_doctor_skill = itemView.findViewById(R.id.tv_doctor_skill);
            itemView.setOnClickListener(listener);
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Context context = v.getContext();
                Intent intent = new Intent(context, DoctorInfoActivity.class);
                context.startActivity(intent);
            }
        };
    }

//    private Context context;
//    private List<DoctorEntity> doctors;
//
//    public DoctorAdapter(Context context,List<DoctorEntity> doctors) {
//        this.doctors = doctors;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return doctors.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return doctors.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if ( null == convertView  ){
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_doctor,null);
//        }
//
//        DoctorEntity doctor = doctors.get(position);
//
//        CircleImageView civ_head = convertView.findViewById(R.id.civ_head);
//        TextView tv_doctor_name = convertView.findViewById(R.id.tv_doctor_name);
//        TextView tv_doctor_position = convertView.findViewById(R.id.tv_doctor_position);
//        TextView tv_doctor_hospital = convertView.findViewById(R.id.tv_doctor_hospital);
//        TextView tv_doctor_skill = convertView.findViewById(R.id.tv_doctor_skill);
//
//        ImageLoader.getInstance().displayImage(doctor.getHeadUrl(),civ_head);
//        tv_doctor_name.setText(doctor.getDoctorname());
//        tv_doctor_position.setText(doctor.getPositionaltitles());
//        tv_doctor_hospital.setText(doctor.getHospital());
//        tv_doctor_skill.setText(doctor.getSpeciality());
//
//        return convertView;
//    }
}

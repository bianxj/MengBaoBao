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
import com.doumengmengandroidbady.activity.HospitalDoctorActivity;
import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.entity.HospitalEntity;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class HospitalDoctorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_HOSPITAL = 0x10;
    private final static int TYPE_DOCTOR = 0x11;

    private List<HospitalEntity> hospitals;
    private List<DoctorEntity> doctors;
    private List<Object> objects = new ArrayList<>();

    public HospitalDoctorAdapter(List<HospitalEntity> hospitals , List<DoctorEntity> doctors) {
        if (hospitals == null) {
            this.hospitals = new ArrayList<>();
        } else {
            this.hospitals = hospitals;
        }
        if ( doctors == null ){
            this.doctors = new ArrayList<>();
        } else {
            this.doctors = doctors;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( TYPE_HOSPITAL == viewType ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital,null);
            return new HospitalHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor,null);
            return new DoctorHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ( position < hospitals.size() ){
            HospitalEntity hospital = hospitals.get(position);
            ((HospitalHolder)holder).initValue(hospital);
        } else if ( position < hospitals.size() + doctors.size() ){
            DoctorEntity doctor = doctors.get(position-hospitals.size());
            ((DoctorHolder)holder).initValue(doctor);
        }
    }

    @Override
    public int getItemCount() {
        return hospitals.size()+doctors.size();
    }

    @Override
    public int getItemViewType(int position) {
        if ( position < hospitals.size() ){
            return TYPE_HOSPITAL;
        } else if ( position < hospitals.size() + doctors.size() ) {
            return TYPE_DOCTOR;
        }
        return super.getItemViewType(position);
    }

    public static class HospitalHolder extends RecyclerView.ViewHolder{
        private CircleImageView civ_hospital;
        private TextView tv_hospital_name , tv_hospital_address;
        private HospitalEntity hospital;

        public HospitalHolder(View itemView) {
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
                Context context = view.getContext();
                Intent intent = new Intent(context, HospitalDoctorActivity.class);
                intent.putExtra(HospitalDoctorActivity.IN_PARAM_HOSPITAL_ID,hospital.getHospitalid());
                intent.putExtra(HospitalDoctorActivity.IN_PARAM_HOSPITAL_NAME,hospital.getHospitalname());
                context.startActivity(intent);
            }
        };
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
                Context context = v.getContext();
                Intent intent = new Intent(context, DoctorInfoActivity.class);
                intent.putExtra(DoctorInfoActivity.IN_PARAM_DOCTOR_ID,doctor.getDoctorid());
                context.startActivity(intent);
            }
        };

        public void initValue(DoctorEntity doctor){
            ImageLoader.getInstance().displayImage(doctor.getDoctorimg(),civ_head);
            tv_doctor_hospital.setText(doctor.getHospital());
            tv_doctor_position.setText(doctor.getPositionaltitles());
            tv_doctor_name.setText(doctor.getDoctorname());
            tv_doctor_skill.setText(doctor.getSpeciality());
            this.doctor = doctor;
        }
    }

}

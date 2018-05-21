package com.doumengmeng.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.activity.DoctorInfoActivity;
import com.doumengmeng.customer.entity.DoctorEntity;
import com.doumengmeng.customer.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 医生列表适配器
 * 创建日期: 2018/1/8 11:22
 */
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {

    private final List<DoctorEntity> doctors;

    public DoctorAdapter(List<DoctorEntity> doctors) {
        this.doctors = doctors;
    }

    @Override
    public DoctorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor,null);
        return new DoctorHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorHolder holder, int position) {
        DoctorEntity doctor = doctors.get(position);
        holder.initValue(doctor,getDisplayImageOption());
//        ImageLoader.getInstance().displayImage(doctor.getDoctorimg(),holder.civ_head,getDisplayImageOption());
//        holder.tv_doctor_hospital.setText(doctor.getHospital());
//        holder.tv_doctor_position.setText(doctor.getPositionaltitles());
//        holder.tv_doctor_name.setText(doctor.getDoctorname());
//        holder.tv_doctor_skill.setText(doctor.getSpeciality());
//        holder.doctor = doctor;
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public static class DoctorHolder extends  RecyclerView.ViewHolder{

        private final CircleImageView civ_head;
        private final TextView tv_doctor_name;
        private final TextView tv_doctor_position;
        private final TextView tv_doctor_hospital;
        private final TextView tv_doctor_skill;
        private final View v_click;
        private DoctorEntity doctor;

        public DoctorHolder(View itemView) {
            super(itemView);
            civ_head = itemView.findViewById(R.id.civ_head);
            tv_doctor_name = itemView.findViewById(R.id.tv_doctor_name);
            tv_doctor_position = itemView.findViewById(R.id.tv_doctor_position);
            tv_doctor_hospital = itemView.findViewById(R.id.tv_doctor_hospital);
            tv_doctor_skill = itemView.findViewById(R.id.tv_doctor_skill);
            v_click = itemView.findViewById(R.id.v_click);
            v_click.setOnClickListener(listener);
        }

        private void initValue(DoctorEntity doctor,DisplayImageOptions options){
            this.doctor = doctor;
            ImageLoader.getInstance().displayImage(doctor.getDoctorimg(),civ_head,options);
            tv_doctor_hospital.setText(doctor.getHospital());
            tv_doctor_position.setText(doctor.getPositionaltitles());
            tv_doctor_name.setText(doctor.getDoctorname());
            tv_doctor_skill.setText(doctor.getSpeciality());
        }

        private final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DoctorInfoActivity.class);
                intent.putExtra(DoctorInfoActivity.IN_PARAM_DOCTOR_ID,doctor.getDoctorid());
                context.startActivity(intent);
            }
        };
    }

    private DisplayImageOptions options = null;
    private DisplayImageOptions getDisplayImageOption(){
        if ( options == null ) {
            DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
            builder.showImageOnFail(R.drawable.default_icon_doctor);
            builder.showImageForEmptyUri(R.drawable.default_icon_doctor);
            builder.showImageOnLoading(R.drawable.default_icon_doctor);
            builder.cacheInMemory(true);
            builder.cacheOnDisk(true);
            options = builder.build();
        }
        return options;
    }
}

package com.doumengmengandroidbady.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.entity.Doctor;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DoctorAdapter extends BaseAdapter {

    private Context context;
    private List<Doctor> doctors;

    public DoctorAdapter(Context context,List<Doctor> doctors) {
        this.doctors = doctors;
        this.context = context;
    }

    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Object getItem(int position) {
        return doctors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ( null == convertView  ){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_doctor,null);
        }

        Doctor doctor = doctors.get(position);

        CircleImageView civ_head = convertView.findViewById(R.id.civ_head);
        TextView tv_doctor_name = convertView.findViewById(R.id.tv_doctor_name);
        TextView tv_doctor_position = convertView.findViewById(R.id.tv_doctor_position);
        TextView tv_doctor_hospital = convertView.findViewById(R.id.tv_doctor_hospital);
        TextView tv_doctor_skill = convertView.findViewById(R.id.tv_doctor_skill);

        ImageLoader.getInstance().displayImage(doctor.getHeadUrl(),civ_head);
        tv_doctor_name.setText(doctor.getName());
        tv_doctor_position.setText(doctor.getPosition());
        tv_doctor_hospital.setText(doctor.getHospital());
        tv_doctor_skill.setText(doctor.getSkill());

        return convertView;
    }
}

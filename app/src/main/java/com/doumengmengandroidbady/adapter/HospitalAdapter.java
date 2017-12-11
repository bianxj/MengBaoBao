package com.doumengmengandroidbady.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.entity.Hospital;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class HospitalAdapter extends BaseAdapter {

    private Context context;
    private List<Hospital> hospitals;

    public HospitalAdapter(Context context,List<Hospital> hospitals) {
        this.hospitals = hospitals;
        this.context = context;
    }

    @Override
    public int getCount() {
        return hospitals.size();
    }

    @Override
    public Object getItem(int position) {
        return hospitals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ( null == convertView ){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hospital,null);
        }

        CircleImageView civ_hospital = convertView.findViewById(R.id.civ_hospital);
        TextView tv_hospital_name = convertView.findViewById(R.id.tv_hospital_name);
        TextView tv_hospital_address = convertView.findViewById(R.id.tv_hospital_address);

        Hospital hospital = hospitals.get(position);
        ImageLoader.getInstance().displayImage(hospital.getImageUrl(),civ_hospital);
        tv_hospital_name.setText(hospital.getName());
        tv_hospital_address.setText(hospital.getHospitalAddress());
        return null;
    }
}

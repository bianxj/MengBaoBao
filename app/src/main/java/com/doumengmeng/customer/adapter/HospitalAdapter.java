package com.doumengmeng.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.activity.HospitalDoctorActivity;
import com.doumengmeng.customer.entity.HospitalEntity;
import com.doumengmeng.customer.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private final List<HospitalEntity> hospitals;

    public HospitalAdapter(List<HospitalEntity> hospitals) {
        this.hospitals = hospitals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital,null);
        return new ViewHolder(view);
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

        private final CircleImageView civ_hospital;
        private final TextView tv_hospital_name;
        private final TextView tv_hospital_address;
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
            ImageLoader.getInstance().displayImage(hospital.getHospitalicon(),civ_hospital,getDisplayImageOption());
            tv_hospital_address.setText(hospital.getHospitaladdress());
            tv_hospital_name.setText(hospital.getHospitalname());
        }

        private final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, HospitalDoctorActivity.class);
                intent.putExtra(HospitalDoctorActivity.IN_PARAM_HOSPITAL_ID,hospital.getHospitalid());
                intent.putExtra(HospitalDoctorActivity.IN_PARAM_HOSPITAL_NAME,hospital.getHospitalname());
                context.startActivity(intent);
            }
        };

        private static DisplayImageOptions options = null;
        private DisplayImageOptions getDisplayImageOption(){
            if ( options == null ) {
                DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
                builder.showImageOnFail(R.drawable.default_icon_hospital);
                builder.showImageForEmptyUri(R.drawable.default_icon_hospital);
                builder.showImageOnLoading(R.drawable.default_icon_hospital);
                options = builder.build();
            }
            return options;
        }
    }

}

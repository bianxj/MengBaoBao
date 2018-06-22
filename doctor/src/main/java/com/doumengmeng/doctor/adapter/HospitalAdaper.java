package com.doumengmeng.doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.viewholder.InputContentHolder;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.view.CircleImageView;
import com.doumengmeng.doctor.view.HorizontalScrollTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */

public class HospitalAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM_HOSPITAL = 0x01;
    private final static int ITEM_INPUT_HOSPITAL = 0x02;

    private WeakReference<InputContentHolder.InputCompleteAction> weakReference;
    private List<HospitalData> datas;

    public HospitalAdaper(InputContentHolder.InputCompleteAction activity,List<HospitalData> datas) {
        this.datas = datas;
        weakReference = new WeakReference<InputContentHolder.InputCompleteAction>(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( ITEM_HOSPITAL == viewType ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital,null);
            return new HospitalHolder(view,initImageLoader(),weakReference);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_input_view,null);
            return new InputContentHolder(view,weakReference);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ( position < datas.size() ){
            ((HospitalHolder)holder).initData(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if ( position == datas.size() ){
            return ITEM_INPUT_HOSPITAL;
        } else {
            return ITEM_HOSPITAL;
        }
    }

    public static class HospitalHolder extends RecyclerView.ViewHolder{

        private DisplayImageOptions options;
        private WeakReference<InputContentHolder.InputCompleteAction> weakReference;
        private HospitalData data;
        private RelativeLayout rl_hospital;
        private CircleImageView civ_hospital;
        private HorizontalScrollTextView tv_hospital_name;
        private TextView tv_hospital_address;

        public HospitalHolder(View itemView,DisplayImageOptions options,WeakReference<InputContentHolder.InputCompleteAction> weakReference) {
            super(itemView);
            this.weakReference = weakReference;
            this.options = options;
            rl_hospital = itemView.findViewById(R.id.rl_hospital);
            civ_hospital = itemView.findViewById(R.id.civ_hospital);
            tv_hospital_name = itemView.findViewById(R.id.tv_hospital_name);

            tv_hospital_address = itemView.findViewById(R.id.tv_hospital_address);
            rl_hospital.setOnClickListener(listener);
        }

        public void initData(HospitalData data){
            this.data = data;
            ImageLoader.getInstance().displayImage(data.getHospitalUrl(),civ_hospital,options);
            tv_hospital_name.setText(data.getHospitalName());
            tv_hospital_name.init();
            tv_hospital_name.startScroll();
            tv_hospital_address.setText(data.getHospitalAddress());
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weakReference.get().selectComplete(data.getHospitalName());
            }
        };

    }

    private DisplayImageOptions options;
    private DisplayImageOptions initImageLoader(){
        if ( options == null ){
            DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
            builder.showImageOnLoading(R.drawable.default_icon_hospital);
            builder.showImageForEmptyUri(R.drawable.default_icon_hospital);
            builder.showImageOnFail(R.drawable.default_icon_hospital);
            options = builder.build();
        }
        return options;
    }

    public static class HospitalData{
        private String hospitalUrl;
        private String hospitalId;
        private String hospitalName;
        private String hospitalAddress;

        public String getHospitalUrl() {
            return UrlAddressList.BASE_IMAGE_URL+hospitalUrl;
        }

        public void setHospitalUrl(String hospitalUrl) {
            this.hospitalUrl = hospitalUrl;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getHospitalAddress() {
            return hospitalAddress;
        }

        public void setHospitalAddress(String hospitalAddress) {
            this.hospitalAddress = hospitalAddress;
        }
    }

}

package com.doumengmeng.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.adapter.DoctorAdapter;
import com.doumengmeng.customer.base.BaseActivity;
import com.doumengmeng.customer.db.DaoManager;
import com.doumengmeng.customer.entity.DoctorEntity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class HospitalDoctorActivity extends BaseActivity {

    public final static String IN_PARAM_HOSPITAL_ID = "hospitalId";
    public final static String IN_PARAM_HOSPITAL_NAME = "hospitalName";

    private String hospitalName;
    private RelativeLayout rl_back;
    private TextView tv_title;

    private XRecyclerView xrv_doctor;
    private DoctorAdapter doctorAdapter;
    private final List<DoctorEntity> doctors = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_doctor);
        getHospitalInfo();
        findView();
    }

    private void getHospitalInfo(){
        Intent intent = getIntent();
        if ( intent != null ){
            String hospitalId = intent.getStringExtra(IN_PARAM_HOSPITAL_ID);
            hospitalName = intent.getStringExtra(IN_PARAM_HOSPITAL_NAME);
            List<DoctorEntity> entities = DaoManager.getInstance().getDaotorDao().searchDoctorsByHospitalId(this,hospitalId);
            for (DoctorEntity entity:entities){
                entity.setHospital(hospitalName);
            }
            doctors.addAll(entities);
        } else {
            //TODO EXCEPTION
        }
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        xrv_doctor = findViewById(R.id.xrv_doctor);

        initView();
        initList();
    }

    private void initView() {
        rl_back.setOnClickListener(listener);
        tv_title.setText(hospitalName);
    }

    private void initList(){
        xrv_doctor.setLoadingMoreEnabled(false);
//        xrv_doctor.setFootView(new XLoadMoreFooter(this));
//        xrv_doctor.setLoadingListener(doctorLoadingListener);

        doctorAdapter = new DoctorAdapter(doctors);
        xrv_doctor.setAdapter(doctorAdapter);
        doctorAdapter.notifyDataSetChanged();
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

//    private XRecyclerView.LoadingListener doctorLoadingListener = new XRecyclerView.LoadingListener(){
//
//        @Override
//        public void onRefresh() {}
//
//        @Override
//        public void onLoadMore() {
//            if ( Config.isTest ){
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i = 0; i <10 ; i++) {
//                            DoctorEntity doctor = new DoctorEntity();
//                            doctor.setDoctorimg("http://img5.duitang.com/uploads/item/201510/02/20151002201518_8ZKWy.thumb.224_0.png");
//                            doctor.setDoctordesc("Describe1");
//                            doctor.setDoctorName("Name"+i);
//                            doctor.setHospital("HospitalEntity"+i);
//                            doctor.setPositionaltitles("Position"+i);
//                            doctor.setSpeciality("Skill"+i);
//                            doctors.add(doctor);
//                        }
//                        xrv_doctor.loadMoreComplete();
//                        doctorAdapter.notifyDataSetChanged();
//                    }
//                },2000);
//            }
//        }
//    };
//
//    private void requestDoctorList(){
//        //TODO
//        if ( Config.isTest ){
//            for (int i = 0; i <10 ; i++) {
//                DoctorEntity doctor = new DoctorEntity();
//                doctor.setDoctorimg("http://img5.duitang.com/uploads/item/201510/02/20151002201518_8ZKWy.thumb.224_0.png");
//                doctor.setDoctordesc("Describe1");
//                doctor.setDoctorName("Name"+i);
//                doctor.setHospital("HospitalEntity"+i);
//                doctor.setPositionaltitles("Position"+i);
//                doctor.setSpeciality("Skill"+i);
//                doctors.add(doctor);
//            }
//        }
//        doctorAdapter.notifyDataSetChanged();
//    }

}

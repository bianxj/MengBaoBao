package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.DoctorAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.entity.HospitalEntity;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class HospitalDoctorActivity extends BaseActivity {

    private HospitalEntity hospital;
    private RelativeLayout rl_back;
    private TextView tv_title;

    private XRecyclerView xrv_doctor;
    private DoctorAdapter doctorAdapter;
    private List<DoctorEntity> doctors = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_doctor);
        getHospitalInfo();
        findView();
    }

    private void getHospitalInfo(){
        //TODO
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
    }

    private void initList(){
        xrv_doctor.setLoadingMoreEnabled(true);
        xrv_doctor.setFootView(new XLoadMoreFooter(this));
        xrv_doctor.setLoadingListener(doctorLoadingListener);

        doctorAdapter = new DoctorAdapter(doctors);
        xrv_doctor.setAdapter(doctorAdapter);
        requestDoctorList();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
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

    private XRecyclerView.LoadingListener doctorLoadingListener = new XRecyclerView.LoadingListener(){

        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {
            if ( Config.isTest ){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <10 ; i++) {
                            DoctorEntity doctor = new DoctorEntity();
                            doctor.setDoctorimg("http://img5.duitang.com/uploads/item/201510/02/20151002201518_8ZKWy.thumb.224_0.png");
                            doctor.setDoctordesc("Describe1");
                            doctor.setDoctorname("Name"+i);
                            doctor.setHospital("HospitalEntity"+i);
                            doctor.setPositionaltitles("Position"+i);
                            doctor.setSpeciality("Skill"+i);
                            doctors.add(doctor);
                        }
                        xrv_doctor.loadMoreComplete();
                        doctorAdapter.notifyDataSetChanged();
                    }
                },2000);
            }
        }
    };

    private void requestDoctorList(){
        //TODO
        if ( Config.isTest ){
            for (int i = 0; i <10 ; i++) {
                DoctorEntity doctor = new DoctorEntity();
                doctor.setDoctorimg("http://img5.duitang.com/uploads/item/201510/02/20151002201518_8ZKWy.thumb.224_0.png");
                doctor.setDoctordesc("Describe1");
                doctor.setDoctorname("Name"+i);
                doctor.setHospital("HospitalEntity"+i);
                doctor.setPositionaltitles("Position"+i);
                doctor.setSpeciality("Skill"+i);
                doctors.add(doctor);
            }
        }
        doctorAdapter.notifyDataSetChanged();
    }

}

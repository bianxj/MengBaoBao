package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.DoctorAdapter;
import com.doumengmengandroidbady.adapter.HospitalAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.entity.Doctor;
import com.doumengmengandroidbady.entity.Hospital;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DoctorListActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private Button bt_search;

    private RadioButton rb_doctor , rb_hospital;

    private XRecyclerView xrv_doctor,xrv_hospital;

    private List<Doctor> doctors;
    private List<Hospital> hospitals;

    private DoctorAdapter doctorAdapter;
    private HospitalAdapter hospitalAdapter;

//    private HeadAndFootAdapter doctorAdapter;
//    private HeadAndFootAdapter hospitalAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        bt_search = findViewById(R.id.bt_search);
        rb_doctor = findViewById(R.id.rb_doctor);
        rb_hospital = findViewById(R.id.rb_hospital);

        xrv_doctor = findViewById(R.id.xrv_doctor);
        xrv_hospital = findViewById(R.id.xrv_hospital);
        initView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        bt_search.setOnClickListener(listener);

        rb_doctor.setOnCheckedChangeListener(changeListener);
        rb_hospital.setOnCheckedChangeListener(changeListener);

        doctors = new ArrayList<Doctor>();
        hospitals = new ArrayList<Hospital>();
        initDoctorListView();
        initHospitalListView();
    }

    private void initDoctorListView(){
        if (Config.isTest){
            for (int i = 0; i <10 ; i++) {
                Doctor doctor = new Doctor();
                doctor.setHeadUrl("http://img5.duitang.com/uploads/item/201510/02/20151002201518_8ZKWy.thumb.224_0.png");
                doctor.setDescribe("Describe1");
                doctor.setName("Name"+i);
                doctor.setHospital("Hospital"+i);
                doctor.setPosition("Position"+i);
                doctor.setSkill("Skill"+i);
                doctors.add(doctor);
            }
        }

        xrv_doctor.setLoadingMoreEnabled(true);
        xrv_doctor.setFootView(new XLoadMoreFooter(this));

        xrv_doctor.setLoadingListener(doctorLoadingListener);
        doctorAdapter = new DoctorAdapter(doctors);
        xrv_doctor.setAdapter(doctorAdapter);
    }

    private void initHospitalListView(){
        if (Config.isTest){
            for (int i = 0; i <10 ; i++) {
                Hospital hospital = new Hospital();
                hospital.setImageUrl("http://www.qqzhi.com/uploadpic/2014-10-04/013617459.jpg");
                hospital.setName("HospitalName"+i);
                hospital.setHospitalAddress("HospitalAddress"+i);
                hospitals.add(hospital);
            }
        }

        xrv_hospital.setLoadingMoreEnabled(true);
        xrv_hospital.setFootView(new XLoadMoreFooter(this));

        xrv_hospital.setLoadingListener(hospitalLoadingListener);
        hospitalAdapter = new HospitalAdapter(hospitals);
        xrv_hospital.setAdapter(hospitalAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_search:
                    startActivity(SearchActivity.class);
                    break;
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if ( isChecked ){
                if ( buttonView.getId() == rb_doctor.getId() ){
                    xrv_doctor.setVisibility(View.VISIBLE);
                    xrv_hospital.setVisibility(View.GONE);
                    rb_hospital.setChecked(false);
                } else {
                    xrv_doctor.setVisibility(View.GONE);
                    xrv_hospital.setVisibility(View.VISIBLE);
                    rb_doctor.setChecked(false);
                }
            }
        }
    };

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
                            Doctor doctor = new Doctor();
                            doctor.setHeadUrl("http://img5.duitang.com/uploads/item/201510/02/20151002201518_8ZKWy.thumb.224_0.png");
                            doctor.setDescribe("Describe1");
                            doctor.setName("Name"+i);
                            doctor.setHospital("Hospital"+i);
                            doctor.setPosition("Position"+i);
                            doctor.setSkill("Skill"+i);
                            doctors.add(doctor);
                        }
                        xrv_doctor.loadMoreComplete();
                        doctorAdapter.notifyDataSetChanged();
                    }
                },2000);
            }
        }
    };

    private XRecyclerView.LoadingListener hospitalLoadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {
            if ( Config.isTest ) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            Hospital hospital = new Hospital();
                            hospital.setImageUrl("http://www.qqzhi.com/uploadpic/2014-10-04/013617459.jpg");
                            hospital.setName("HospitalName" + i);
                            hospital.setHospitalAddress("HospitalAddress" + i);
                            hospitals.add(hospital);
                        }
                        xrv_hospital.loadMoreComplete();
                        hospitalAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        }
    };

    private void back(){
        finish();
    }

}

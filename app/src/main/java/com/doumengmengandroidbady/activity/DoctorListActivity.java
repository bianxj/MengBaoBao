package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.DoctorAdapter;
import com.doumengmengandroidbady.adapter.HeadAndFootAdapter;
import com.doumengmengandroidbady.adapter.HospitalAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.entity.Doctor;
import com.doumengmengandroidbady.entity.Hospital;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DoctorListActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private Button bt_search;

    private RadioButton rb_doctor , rb_hospital;

    private RecyclerView rv_doctor , rv_hospital;

    private List<Doctor> doctors;
    private List<Hospital> hospitals;

    private HeadAndFootAdapter doctorAdapter;
    private HeadAndFootAdapter hospitalAdapter;

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

        rv_doctor = findViewById(R.id.rv_doctor);
        rv_hospital = findViewById(R.id.rv_hospital);
        initView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        bt_search.setOnClickListener(listener);

        rb_doctor.setOnCheckedChangeListener(changeListener);
        rb_hospital.setOnCheckedChangeListener(changeListener);

        doctors = new ArrayList<Doctor>();
        hospitals = new ArrayList<Hospital>();

        if (Config.isTest){
            for (int i = 0; i <1000 ; i++) {
                Doctor doctor = new Doctor();
                doctor.setHeadUrl("http://img5.duitang.com/uploads/item/201510/02/20151002201518_8ZKWy.thumb.224_0.png");
                doctor.setDescribe("Describe1");
                doctor.setName("Name"+i);
                doctor.setHospital("Hospital"+i);
                doctor.setPosition("Position"+i);
                doctor.setSkill("Skill"+i);
                doctors.add(doctor);
            }

            for (int i = 0; i <1000 ; i++) {
                Hospital hospital = new Hospital();
                hospital.setImageUrl("http://www.qqzhi.com/uploadpic/2014-10-04/013617459.jpg");
                hospital.setName("HospitalName"+i);
                hospital.setHospitalAddress("HospitalAddress"+i);
                hospitals.add(hospital);
            }
        }

        rv_doctor.setVisibility(View.VISIBLE);
        doctorAdapter = new HeadAndFootAdapter(new DoctorAdapter(doctors));
        rv_doctor.setLayoutManager(new LinearLayoutManager(this));
        rv_doctor.setAdapter(doctorAdapter);
        doctorAdapter.notifyDataSetChanged();

        rv_hospital.setVisibility(View.GONE);
        hospitalAdapter = new HeadAndFootAdapter(new HospitalAdapter(hospitals));
        rv_hospital.setLayoutManager(new LinearLayoutManager(this));
        rv_hospital.setAdapter(hospitalAdapter);
        hospitalAdapter.notifyDataSetChanged();
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
                    rv_doctor.setVisibility(View.VISIBLE);
                    rv_hospital.setVisibility(View.GONE);
                    rb_hospital.setChecked(false);
                } else {
                    rv_doctor.setVisibility(View.GONE);
                    rv_hospital.setVisibility(View.VISIBLE);
                    rb_doctor.setChecked(false);
                }
            }
        }
    };

    private void back(){
        finish();
    }

}

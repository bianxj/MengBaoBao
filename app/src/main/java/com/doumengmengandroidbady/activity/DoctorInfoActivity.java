package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.response.Doctor;
import com.doumengmengandroidbady.response.Hospital;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2017/12/12.
 */

public class DoctorInfoActivity extends BaseActivity {

    private final static boolean isTest = false;

    public final static String IN_PARAM_DOCTOR_ID = "doctor_id";

//    private DoctorEntity doctor;

    private RelativeLayout rl_back;
    private TextView tv_title;
    private CircleImageView civ_head;
    private TextView tv_doctor_name , tv_doctor_position , tv_hospital;
    private TextView tv_doctor_introduce , tv_doctor_skill;
    private Button bt_choose;

    private Doctor doctor;
    private Hospital hospital;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        getNeedInfo();
        findView();
    }

    private void getNeedInfo(){
        if (isTest){
            doctor = new Doctor();
            doctor.setDoctorname("Name1");
            doctor.setDoctorimg("http://www.qqzhi.com/uploadpic/2015-01-22/022222987.jpg");
            doctor.setPositionaltitles("Position1");
            doctor.setDoctordesc("Describe1");
            doctor.setSpeciality("Skill");

            hospital = new Hospital();
            hospital.setHospitalname("Hospital1");
        } else {
            Intent intent = getIntent();
            String doctorId = intent.getStringExtra(IN_PARAM_DOCTOR_ID);
            doctor = DaoManager.getInstance().getDaotorDao().searchDoctorById(this,doctorId);
            if ( doctor != null ){
                hospital = DaoManager.getInstance().getHospitalDao().searchHospitalById(this,doctor.getDoctorid());
            }
        }
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        civ_head = findViewById(R.id.civ_head);
        tv_doctor_name = findViewById(R.id.tv_doctor_name);
        tv_doctor_position = findViewById(R.id.tv_doctor_position);
        tv_hospital = findViewById(R.id.tv_hospital);
        tv_doctor_introduce = findViewById(R.id.tv_doctor_introduce);
        tv_doctor_skill = findViewById(R.id.tv_doctor_skill);
        bt_choose = findViewById(R.id.bt_choose);

        initView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        bt_choose.setOnClickListener(listener);
        tv_title.setText(R.string.introduce);

        tv_doctor_name.setText(doctor.getDoctorname());
        tv_doctor_position.setText(doctor.getPositionaltitles());
        tv_doctor_introduce.setText(doctor.getDoctordesc());
        tv_doctor_skill.setText(doctor.getSpeciality());
        tv_hospital.setText(hospital.getHospitalname());
        ImageLoader.getInstance().displayImage(doctor.getDoctorimg(),civ_head);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_choose:
                    choose();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void choose(){
        //TODO
        startActivity(InputInfoActivity.class);
    }

}

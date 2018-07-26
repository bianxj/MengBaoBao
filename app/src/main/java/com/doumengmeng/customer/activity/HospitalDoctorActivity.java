package com.doumengmeng.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.adapter.DoctorAdapter;
import com.doumengmeng.customer.base.BaseSwipeActivity;
import com.doumengmeng.customer.db.DaoManager;
import com.doumengmeng.customer.entity.DoctorEntity;
import com.doumengmeng.customer.util.MyDialog;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class HospitalDoctorActivity extends BaseSwipeActivity {

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
            MyDialog.showPromptDialog(this, "数据异常,请重新尝试", new MyDialog.PromptDialogCallback() {
                @Override
                public void sure() {
                    back();
                }
            });
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

    protected void back(){
        finish();
    }

}

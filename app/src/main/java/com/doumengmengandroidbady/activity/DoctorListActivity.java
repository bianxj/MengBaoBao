package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.entity.HospitalEntity;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 萌宝宝医生医院
 * 创建日期: 2018/1/8 11:28
 */
public class DoctorListActivity extends BaseActivity {

    private final static int PAGE_SIZE = 100;
    private int doctor_page = 0;
    private int hospital_page = 0;

    private final static boolean isTest = Config.isTest;

    private RelativeLayout rl_back;
    private Button bt_search;

    private RadioButton rb_doctor , rb_hospital;

    private XRecyclerView xrv_doctor,xrv_hospital;

    private List<DoctorEntity> doctors;
    private List<HospitalEntity> hospitals;

    private DoctorAdapter doctorAdapter;
    private HospitalAdapter hospitalAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        doctors = new ArrayList<DoctorEntity>();
        hospitals = new ArrayList<HospitalEntity>();
        initDoctorListView();
        initHospitalListView();
    }

    private void initDoctorListView(){
        xrv_doctor.setLoadingMoreEnabled(true);
        xrv_doctor.setFootView(new XLoadMoreFooter(this));

        xrv_doctor.setLoadingListener(doctorLoadingListener);
        doctorAdapter = new DoctorAdapter(doctors);
        xrv_doctor.setAdapter(doctorAdapter);

        searchDoctorData();
    }

    private void initHospitalListView(){
        xrv_hospital.setLoadingMoreEnabled(true);
        xrv_hospital.setFootView(new XLoadMoreFooter(this));

        xrv_hospital.setLoadingListener(hospitalLoadingListener);
        hospitalAdapter = new HospitalAdapter(hospitals);
        xrv_hospital.setAdapter(hospitalAdapter);

        searchHospitalData();
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
            searchDoctorData();
        }
    };

    private XRecyclerView.LoadingListener hospitalLoadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {
            searchHospitalData();
        }
    };

    private void back(){
        finish();
    }

    //------------------------------------数据获取------------------------------------------------
    public final static int MESSAGE_SEARCH_DOCTOR = 0x01;
    public final static int MESSAGE_SEARCH_HOSPITAL = 0x02;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == MESSAGE_SEARCH_DOCTOR ) {
                doctor_page++;
                if ( tempDoctors != null ){
                    if ( tempDoctors.size() < PAGE_SIZE ){
                        xrv_doctor.setNoMore(true);
                    }
                    for (DoctorEntity doctorEntity:tempDoctors){
                        doctors.add(doctorEntity);
                    }
                    doctorAdapter.notifyDataSetChanged();
                } else {
                    xrv_doctor.setNoMore(true);
                }
            }
            if ( msg.what == MESSAGE_SEARCH_HOSPITAL ) {
                hospital_page++;
                if ( tempHospital != null ){
                    if ( tempHospital.size() < PAGE_SIZE ){
                        xrv_hospital.setNoMore(true);
                    }
                    for (HospitalEntity hospitalEntity:tempHospital){
                        hospitals.add(hospitalEntity);
                    }
                    doctorAdapter.notifyDataSetChanged();
                } else {
                    xrv_hospital.setNoMore(true);
                }
            }
        }
    };

    private void searchDoctorData(){
        new Thread(searchDoctorRunnable).start();
    }

    private List<DoctorEntity> tempDoctors = new ArrayList<>();
    private Runnable searchDoctorRunnable = new Runnable() {
        @Override
        public void run() {
            tempDoctors.clear();
            if ( isTest ){
                for (int i = 0; i <10 ; i++) {
                    DoctorEntity doctor = new DoctorEntity();
                    doctor.setDoctorimg("http://img5.duitang.com/uploads/item/201510/02/20151002201518_8ZKWy.thumb.224_0.png");
                    doctor.setDoctordesc("Describe1");
                    doctor.setDoctorname("Name"+i);
                    doctor.setHospital("HospitalEntity"+i);
                    doctor.setPositionaltitles("Position"+i);
                    doctor.setSpeciality("Skill"+i);
                    tempDoctors.add(doctor);
                }
            } else {
                List<DoctorEntity> doctorList = DaoManager.getInstance().getDaotorDao().searchDoctorList(DoctorListActivity.this,doctor_page,PAGE_SIZE);
                if ( doctorList != null && doctorList.size() > 0 ){
                    tempDoctors.addAll(doctorList);
                }
            }
            handler.sendEmptyMessage(MESSAGE_SEARCH_DOCTOR);
        }
    };

    private void searchHospitalData(){
        new Thread(searchHospitalRunnable).start();
    }

    private List<HospitalEntity> tempHospital = new ArrayList<>();
    private Runnable searchHospitalRunnable = new Runnable() {
        @Override
        public void run() {
            tempHospital.clear();
            if ( isTest ){
                for (int i = 0; i <10 ; i++) {
                    HospitalEntity hospital = new HospitalEntity();
                    hospital.setHospitalicon("http://www.qqzhi.com/uploadpic/2014-10-04/013617459.jpg");
                    hospital.setHospitalname("HospitalName"+i);
                    hospital.setHospitaladdress("HospitalAddress"+i);
                    tempHospital.add(hospital);
                }
            } else {
                List<HospitalEntity> hospitalList = DaoManager.getInstance().getHospitalDao().searchHospitalList(DoctorListActivity.this,hospital_page,PAGE_SIZE);
                if ( hospitalList != null && hospitalList.size() > 0 ){
                    tempHospital.addAll(hospitalList);
                }
            }
            handler.sendEmptyMessage(MESSAGE_SEARCH_HOSPITAL);
        }
    };

}

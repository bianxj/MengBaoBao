package com.doumengmeng.customer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.adapter.DoctorAdapter;
import com.doumengmeng.customer.adapter.HospitalAdapter;
import com.doumengmeng.customer.base.BaseSwipeActivity;
import com.doumengmeng.customer.db.DaoManager;
import com.doumengmeng.customer.entity.DoctorEntity;
import com.doumengmeng.customer.entity.HospitalEntity;
import com.doumengmeng.customer.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 萌宝宝医生医院
 * 创建日期: 2018/1/8 11:28
 */
public class DoctorListActivity extends BaseSwipeActivity {

    private final static int PAGE_SIZE = 100;
    private int doctor_page = 0;
    private int hospital_page = 0;

    private RelativeLayout rl_back;
    private Button bt_search;

    private RadioButton rb_doctor , rb_hospital;

    private XRecyclerView xrv_doctor,xrv_hospital;

    private List<DoctorEntity> doctors;
    private List<HospitalEntity> hospitals;

    private DoctorAdapter doctorAdapter;
    private HospitalAdapter hospitalAdapter;
    private DoctorListHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( handler != null ){
            handler.removeCallbacksAndMessages(null);
        }
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

        handler = new DoctorListHandler(this);

        doctors = new ArrayList<>();
        hospitals = new ArrayList<>();
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

    private final View.OnClickListener listener = new View.OnClickListener() {
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

    private final CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
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

    private final XRecyclerView.LoadingListener doctorLoadingListener = new XRecyclerView.LoadingListener(){

        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {
            searchDoctorData();
        }
    };

    private final XRecyclerView.LoadingListener hospitalLoadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {
            searchHospitalData();
        }
    };

    protected void back(){
        finish();
    }

    //------------------------------------数据获取------------------------------------------------

    private static class DoctorListHandler extends Handler{
        private final static int MESSAGE_SEARCH_DOCTOR = 0x01;
        private final static int MESSAGE_SEARCH_HOSPITAL = 0x02;
        private final WeakReference<DoctorListActivity> weakReference;

        public DoctorListHandler(DoctorListActivity activity) {
            weakReference = new WeakReference<DoctorListActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DoctorListActivity activity = weakReference.get();
            if ( msg.what == MESSAGE_SEARCH_DOCTOR ) {
                List<DoctorEntity> doctors = (List<DoctorEntity>) msg.obj;
                activity.searchDoctor(doctors);
            }
            if ( msg.what == MESSAGE_SEARCH_HOSPITAL ) {
                List<HospitalEntity> hospitals = (List<HospitalEntity>) msg.obj;
                activity.searchHospital(hospitals);
            }
        }
    }

    private void searchDoctor(List<DoctorEntity> datas){
        doctor_page++;
        if ( datas != null ){
            if ( datas.size() < PAGE_SIZE ){
                xrv_doctor.setNoMore(true);
            }
            doctors.addAll(datas);
            doctorAdapter.notifyDataSetChanged();
        } else {
            xrv_doctor.setNoMore(true);
        }
    }

    private void searchHospital(List<HospitalEntity> datas){
        hospital_page++;
        if ( datas != null ){
            if ( datas.size() < PAGE_SIZE ){
                xrv_hospital.setNoMore(true);
            }
            hospitals.addAll(datas);
            doctorAdapter.notifyDataSetChanged();
        } else {
            xrv_hospital.setNoMore(true);
        }
    }

    private void searchDoctorData(){
        new Thread(searchDoctorRunnable).start();
    }

    private final Runnable searchDoctorRunnable = new Runnable() {
        @Override
        public void run() {
            List<DoctorEntity> doctorList;
            doctorList = DaoManager.getInstance().getDaotorDao().searchDoctorList(DoctorListActivity.this,doctor_page,PAGE_SIZE);
            Message message = handler.obtainMessage();
            message.what = DoctorListHandler.MESSAGE_SEARCH_DOCTOR;
            message.obj = doctorList;
            handler.sendMessage(message);
        }
    };

    private void searchHospitalData(){
        new Thread(searchHospitalRunnable).start();
    }

    private final Runnable searchHospitalRunnable = new Runnable() {
        @Override
        public void run() {
            List<HospitalEntity> hospitalList;
            hospitalList = DaoManager.getInstance().getHospitalDao().searchHospitalList(DoctorListActivity.this,hospital_page,PAGE_SIZE);
            Message message = handler.obtainMessage();
            message.what = DoctorListHandler.MESSAGE_SEARCH_HOSPITAL;
            message.obj = hospitalList;
            handler.sendMessage(message);
        }
    };

}

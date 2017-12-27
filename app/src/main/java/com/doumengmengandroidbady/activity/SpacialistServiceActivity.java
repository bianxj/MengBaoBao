package com.doumengmengandroidbady.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.DoctorAdapter;
import com.doumengmengandroidbady.adapter.HospitalAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.entity.HospitalEntity;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class SpacialistServiceActivity extends BaseActivity {

    private final static int REQUEST_QR = 0x01;

    private RelativeLayout rl_back;
    private TextView tv_title;

    private TextView tv_location;
    private RelativeLayout rl_location_area;
    private EditText et_search;
    private ImageView iv_scan;
    private XRecyclerView xrv_search;
    private FrameLayout fl_content;

    private List<DoctorEntity> doctors = new ArrayList<>();
    private List<HospitalEntity> hospitals = new ArrayList<>();

    private DoctorAdapter doctorAdapter;
    private HospitalAdapter hospitalAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spacialist_service);
        findView();
        checkPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_QR ){
            if ( resultCode == RESULT_OK ){
                String value = data.getStringExtra(ScanActivity.RESULT_QR_VALUE);
                Toast.makeText(SpacialistServiceActivity.this,value,Toast.LENGTH_LONG).show();
            }
        }
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tv_location = findViewById(R.id.tv_location);
        rl_location_area = findViewById(R.id.rl_location_area);
        et_search = findViewById(R.id.et_search);
        iv_scan = findViewById(R.id.iv_scan);
        xrv_search = findViewById(R.id.xrv_search);
        fl_content = findViewById(R.id.fl_content);
        initView();
        initListView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        rl_location_area.setOnClickListener(listener);
        iv_scan.setOnClickListener(listener);

        tv_title.setText(R.string.spacialist_service);

        et_search.setOnEditorActionListener(onEditorActionListener);
    }

    private void initListView(){
        xrv_search.setLoadingMoreEnabled(true);
        xrv_search.setFootView(new XLoadMoreFooter(this));
        xrv_search.setLoadingListener(searchLoadingListener);

        doctorAdapter = new DoctorAdapter(doctors);
        hospitalAdapter = new HospitalAdapter(hospitals);
    }

    private XRecyclerView.LoadingListener searchLoadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {

        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_location_area:
                    if ( MyDialog.isShowingChooseCityDialog() ){
                        MyDialog.dismissChooseCityDialog();
                    } else {
                        MyDialog.showChooseCityDialog(SpacialistServiceActivity.this, rl_location_area, new MyDialog.ChooseCityCallback() {
                            @Override
                            public void choose(String city) {
                                tv_location.setText(city);
                                MyDialog.dismissChooseCityDialog();
                            }
                        });
                    }
                    break;
                case R.id.iv_scan:
                    Intent intent = new Intent(SpacialistServiceActivity.this,ScanActivity.class);
                    startActivityForResult(intent,REQUEST_QR);
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if ( i == EditorInfo.IME_ACTION_SEARCH) {
                ((InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(
                                SpacialistServiceActivity.this
                                        .getCurrentFocus()
                                        .getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                search();
            }
            return false;
        }
    };

    //---------------------------------查询模块--------------------------------------------------

    private void search(){
        try {
            buildSearchTask().execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestTask buildSearchTask() throws Throwable {
        return new RequestTask.Builder(searchCallBack).build();
    }

    private int times = 0;
    private RequestCallBack searchCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return null;
        }

        @Override
        public Context getContext() {
            return null;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            if (Config.isTest){
                    times++;
                    if ( times %2 == 0 ){
                        xrv_search.setAdapter(hospitalAdapter);
                        hospitals.clear();
                        for (int i = 0; i < 10; i++) {
                            HospitalEntity hospital = new HospitalEntity();
                            hospital.setHospitalicon("http://www.qqzhi.com/uploadpic/2014-10-04/013617459.jpg");
                            hospital.setHospitalname("HospitalName" + i);
                            hospital.setHospitaladdress("HospitalAddress" + i);
                            hospitals.add(hospital);
                        }
                        hospitalAdapter.notifyDataSetChanged();
                    } else {
                        xrv_search.setAdapter(doctorAdapter);
                        doctors.clear();
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
                        doctorAdapter.notifyDataSetChanged();
                    }
            }
        }

        @Override
        public String type() {
            return JSON;
        }
    };

    //---------------------------------检查权限------------------------------------------------
    private final static String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private final static int REQUEST_PERMISSION = 0x01;
    private void checkPermission(){
        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
            initLocation();
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( REQUEST_PERMISSION == requestCode ){
            if (PackageManager.PERMISSION_GRANTED != grantResults[0] ){
                if ( ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0]) ){
                    checkPermission();
                } else {
//                    Toast.makeText(this,"请打开照相机权限",Toast.LENGTH_LONG).show();
                }
            } else {
                initLocation();
            }
        }
    }

    //---------------------------------定位模块分割线---------------------------------------------

    private LocationClient client;
    private LocationClientOption option;

    private void initLocation(){
//        rl_location_area.setEnabled(false);
        client = new LocationClient(getApplicationContext());
        client.setLocOption(initLocationClientOption());
        client.registerLocationListener(locationListener);
        client.start();
    }

    private LocationClientOption initLocationClientOption(){
        if ( option == null ){
            option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            option.setCoorType("bd09ll");
            option.setIsNeedAddress(true);
        }
        return  option;
    }

    private BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if ( bdLocation != null ){
                String city = bdLocation.getCity();
                tv_location.setText(city);
            } else {
                //TODO
            }
            rl_location_area.setEnabled(true);
        }
    };

    private void destoryLocation(){
        if ( client != null ){
            client.unRegisterLocationListener(locationListener);
            if ( client.isStarted() ){
                client.stop();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK ){
            if ( MyDialog.isShowingChooseCityDialog() ){
                MyDialog.dismissChooseCityDialog();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

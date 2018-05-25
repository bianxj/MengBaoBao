package com.doumengmeng.doctor.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.HospitalAdaper;
import com.doumengmeng.doctor.adapter.viewholder.InputContentHolder;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.response.entity.Hospital;
import com.doumengmeng.doctor.util.AppUtil;
import com.doumengmeng.doctor.util.MyDialog;
import com.doumengmeng.doctor.util.PermissionUtil;
import com.doumengmeng.doctor.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class HospitalListActivity extends BaseActivity implements InputContentHolder.InputCompleteAction {

    private final static int REQUEST_LOCATION = 0x01;

//    public final static String OUT_PARAM_HOSPITAL_ID = "hospital_id";
    public final static String OUT_PARAM_HOSPITAL_NAME = "hospital_name";

    private RelativeLayout rl_back;
    private TextView tv_title;

    private RelativeLayout rl_location_area;
    private TextView tv_city;
    private ImageView iv_arrow;
    private XRecyclerView xrv;
    private HospitalAdaper adapter;
    private List<HospitalAdaper.HospitalData> hospitals = new ArrayList<>();

    private boolean isSkipToApp = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        findView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLocationPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearHandler(handler);
        destoryLocation();
    }

    private void findView(){
        initTitle();
        initLocationView();
        initHospitalList();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(getString(R.string.hospital));
        rl_back.setOnClickListener(listener);
    }

    private void initLocationView(){
        rl_location_area = findViewById(R.id.rl_location_area);
        tv_city = findViewById(R.id.tv_city);
        iv_arrow = findViewById(R.id.iv_arrow);

        rl_location_area.setOnClickListener(listener);
    }

    private void initHospitalList(){
        xrv = findViewById(R.id.xrv);
        xrv.setLoadingMoreEnabled(true);
        xrv.setFootView(new XLoadMoreFooter(this));
//        xrv.setLoadingListener(searchLoadingListener);

        adapter = new HospitalAdaper(this,hospitals);
        xrv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_location_area:
                    selectCity();
                    break;
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void selectCity(){
        executeRotateAnimation();
        if ( MyDialog.isShowingChooseCityDialog() ){
            MyDialog.dismissChooseCityDialog();
        } else {
            MyDialog.showChooseCityDialog(HospitalListActivity.this, rl_location_area, new MyDialog.ChooseCityCallback() {
                @Override
                public void choose(String city) {
                    executeRotateAnimation();
                    if ( DaoManager.getInstance().getHospitalDao().hasHospitalInCity(HospitalListActivity.this,city) ) {
                        setCity(city);
                        refreshHospitalList();
                    } else {
                        Toast.makeText(HospitalListActivity.this,"该地区还在开发中", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.dismissChooseCityDialog();
                }
            });
        }
    }

    private List<Hospital> tempHospitals;
    private void refreshHospitalList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                tempHospitals = DaoManager.getInstance().getHospitalDao().searchHospitalListByCity(HospitalListActivity.this,tv_city.getText().toString().trim());
                handler.sendEmptyMessage(HospitalHandler.ACTION_UPDATE_HOSPITAL);
            }
        }).start();
    }

    private void updateHospitalList(){
        hospitals.clear();
        for (int i = 0;i<tempHospitals.size();i++){
            HospitalAdaper.HospitalData data = new HospitalAdaper.HospitalData();
            Hospital hospital = tempHospitals.get(i);
            data.setHospitalName(hospital.getHospitalname());
            data.setHospitalAddress(hospital.getHospitaladdress());
            data.setHospitalUrl(hospital.getHospitalurl());
            data.setHospitalId(hospital.getHospitalid());
            hospitals.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    //-------------------------------------Animation start------------------------------------------
    private RotateAnimation rotateAnimation0To180;
    private RotateAnimation rotateAnimation180To360;
    private int rotateCount = 0;

    private void executeRotateAnimation(){
        iv_arrow.startAnimation(buildRotateAnimation());
        rotateCount++;
    }

    private RotateAnimation buildRotateAnimation(){
        if ( rotateCount%2 == 0 ) {
            return buildRotateAnimation0To180();
        } else {
            return buildRotateAnimation180To360();
        }
    }

    private RotateAnimation buildRotateAnimation0To180(){
        if (rotateAnimation0To180 == null) {
            rotateAnimation0To180 = new RotateAnimation(0F, 180F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
            rotateAnimation0To180.setInterpolator(new LinearInterpolator());
            rotateAnimation0To180.setDuration(300);
        }
        rotateAnimation0To180.setFillAfter(true);
        return rotateAnimation0To180;
    }

    private RotateAnimation buildRotateAnimation180To360(){
        if (rotateAnimation180To360 == null) {
            rotateAnimation180To360 = new RotateAnimation(180F, 360F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
            rotateAnimation180To360.setInterpolator(new LinearInterpolator());
            rotateAnimation180To360.setDuration(300);
        }
        rotateAnimation180To360.setFillAfter(false);
        return rotateAnimation180To360;
    }
    //-------------------------------------Animation end--------------------------------------------

    //---------------------------------检查权限------------------------------------------------
    private void checkLocationPermission(){
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            if (PermissionUtil.checkPermissionAndRequest(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                initLocation();
            }
        } else {
            skipToLocationService();
        }
    }

    private void skipToLocationService(){
        MyDialog.showPromptDialog(this, getString(R.string.dialog_content_open_location_service), new MyDialog.PromptDialogCallback() {
            @Override
            public void sure() {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, REQUEST_LOCATION);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, new PermissionUtil.RequestPermissionSuccess() {
            @Override
            public void success(String permission) {
                initLocation();
            }

            @Override
            public void denied(String permission) {

            }

            @Override
            public void alwaysDenied(String permission) {
                if ( isSkipToApp ) {
                    MyDialog.showPermissionDialog(HospitalListActivity.this, getResources().getString(R.string.dialog_content_location_permission), new MyDialog.ChooseDialogCallback() {
                        @Override
                        public void sure() {
                            AppUtil.openPrimession(HospitalListActivity.this);
                        }

                        @Override
                        public void cancel() {
                            locationFailed();
                        }
                    });
                }
            }
        }, isSkipToApp);
    }

    //---------------------------------定位模块 start---------------------------------------------

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

    private final BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if ( bdLocation != null && !TextUtils.isEmpty(bdLocation.getProvince())){
                String city = bdLocation.getProvince();
                setCity(city);
            } else {
                locationFailed();
            }

            refreshHospitalList();
//            rl_location_area.setEnabled(true);
            destoryLocation();
        }
    };

    private void locationFailed(){
//        tv_location_failed.setVisibility(View.VISIBLE);
    }

    private void destoryLocation(){
        if ( client != null ){
            client.unRegisterLocationListener(locationListener);
            if ( client.isStarted() ){
                client.stop();
            }
        }
    }

    private void setCity(String city){
        tv_city.setText(city);
    }
    //---------------------------------定位模块 end---------------------------------------------

    @Override
    public void selectComplete(String hospitalName){
        Intent intent = new Intent();
        intent.putExtra(OUT_PARAM_HOSPITAL_NAME,hospitalName);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void error(int type) {
        if ( 0 == type ){
            
        }
    }

    private HospitalHandler handler = new HospitalHandler(this);
    private static class HospitalHandler extends Handler{

        public final static int ACTION_UPDATE_HOSPITAL = 0x01;

        private WeakReference<HospitalListActivity> weakReference;

        public HospitalHandler(HospitalListActivity activity) {
            this.weakReference = new WeakReference<HospitalListActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( ACTION_UPDATE_HOSPITAL == msg.what ){
                weakReference.get().updateHospitalList();
            }
        }

    }

}

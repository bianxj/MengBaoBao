package com.doumengmengandroidbady.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.doumengmengandroidbady.adapter.HospitalDoctorAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.entity.HospitalEntity;
import com.doumengmengandroidbady.util.AppUtil;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.util.PermissionUtil;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 专家服务界面
 * 创建日期: 2018/1/23 14:55
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
    private ImageView iv_arrow;
    private TextView tv_location_failed;

    private List<DoctorEntity> doctors = new ArrayList<>();
    private List<HospitalEntity> hospitals = new ArrayList<>();

    private HospitalDoctorAdapter adapter;

    private boolean isSkipToApp = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spacialist_service);
        findView();
        checkLocationPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryLocation();
        if ( handler != null ){
            handler.removeCallbacksAndMessages(null);
        }
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
        iv_arrow = findViewById(R.id.iv_arrow);
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tv_location = findViewById(R.id.tv_location);
        rl_location_area = findViewById(R.id.rl_location_area);
        et_search = findViewById(R.id.et_search);
        iv_scan = findViewById(R.id.iv_scan);
        xrv_search = findViewById(R.id.xrv_search);
        fl_content = findViewById(R.id.fl_content);
        tv_location_failed = findViewById(R.id.tv_location_failed);
        iv_arrow = findViewById(R.id.iv_arrow);
        initView();
        initListView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        rl_location_area.setOnClickListener(listener);
        iv_scan.setOnClickListener(listener);
        tv_location_failed.setOnClickListener(listener);

        tv_title.setText(R.string.spacialist_service);

        tv_location.setText("北京市");
        tv_location.setTag("北京市");
        et_search.setOnEditorActionListener(onEditorActionListener);

        handler = new SpacialistServiceHandler(this);
    }

    private void initListView(){
        xrv_search.setLoadingMoreEnabled(true);
        xrv_search.setFootView(new XLoadMoreFooter(this));
        xrv_search.setLoadingListener(searchLoadingListener);


        adapter = new HospitalDoctorAdapter(hospitals,doctors);
        xrv_search.setAdapter(adapter);
    }

    private void setCity(String city){
        System.out.println("setCity:"+city);
        tv_location.setText(city);
        tv_location.setTag(city);
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
                    rotateCount++;
                    iv_arrow.setRotation(180F*rotateCount%360);
                    if ( MyDialog.isShowingChooseCityDialog() ){
                        MyDialog.dismissChooseCityDialog();
                    } else {
                        MyDialog.showChooseCityDialog(SpacialistServiceActivity.this, rl_location_area, new MyDialog.ChooseCityCallback() {
                            @Override
                            public void choose(String city) {
                                if ( DaoManager.getInstance().getHospitalDao().hasHospitalInCity(SpacialistServiceActivity.this,city) ) {
                                    setCity(city);
                                } else {
                                    Toast.makeText(SpacialistServiceActivity.this,"该地区还在开发中",Toast.LENGTH_SHORT).show();
                                }
                                MyDialog.dismissChooseCityDialog();
                            }
                        });
                    }
                    break;
                case R.id.iv_scan:
                    Intent intent = new Intent(SpacialistServiceActivity.this,ScanActivity.class);
                    startActivityForResult(intent,REQUEST_QR);
                    break;
                case R.id.tv_location_failed:
                    isSkipToApp = true;
                    checkLocationPermission();
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

//    private RotateAnimation animation;
    private int rotateCount = 0;
//    private RotateAnimation buildRotateAnimation(){
//        if ( animation == null ) {
//            animation = new RotateAnimation(0F, 180F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
//            animation.setFillBefore(true);
//            animation.setInterpolator(new LinearInterpolator());
//            animation.setDuration(500);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    rotateCount++;
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    iv_arrow.setRotation(180F*rotateCount%360);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//        }
//        return animation;
//    }

    //---------------------------------查询模块--------------------------------------------------

    private SpacialistServiceHandler handler = null;
    private static class SpacialistServiceHandler extends Handler{
        private final static int UPDATAE_LIST = 0x01;
        private WeakReference<SpacialistServiceActivity> weakReference;

        public SpacialistServiceHandler(SpacialistServiceActivity activity) {
            weakReference = new WeakReference<SpacialistServiceActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( UPDATAE_LIST == msg.what ){
                weakReference.get().updateList();
            }
        }
    }

    private List<DoctorEntity> tempDoctors;
    private List<HospitalEntity> tempHospitals;
    private void search(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name = et_search.getText().toString();
                String city = (String) tv_location.getTag();
                tempDoctors = DaoManager.getInstance().getDaotorDao().searchDoctorListByNameAndCity(SpacialistServiceActivity.this,name,city);
                tempHospitals = DaoManager.getInstance().getHospitalDao().searchHospitalListByNameAndCity(SpacialistServiceActivity.this,name,city);
                handler.sendEmptyMessage(SpacialistServiceHandler.UPDATAE_LIST);
            }
        }).start();
    }

    private void updateList(){
        doctors.clear();
        doctors.addAll(tempDoctors);
        hospitals.clear();
        hospitals.addAll(tempHospitals);
        adapter.notifyDataSetChanged();
    }

    //---------------------------------检查权限------------------------------------------------
    private void checkLocationPermission(){
        if (PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            initLocation();
        }
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
                    MyDialog.showPermissionDialog(SpacialistServiceActivity.this, getResources().getString(R.string.location_permission), new MyDialog.ChooseDialogCallback() {
                        @Override
                        public void sure() {
                            AppUtil.openPrimession(SpacialistServiceActivity.this);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                }
            }
        }, isSkipToApp);
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
                String city = bdLocation.getProvince();
                setCity(city);
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

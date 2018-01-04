package com.doumengmengandroidbady.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.doumengmengandroidbady.adapter.HospitalDoctorAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.entity.HospitalEntity;
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
    private ImageView iv_arrow;
    private TextView tv_location_failed;

    private List<DoctorEntity> doctors = new ArrayList<>();
    private List<HospitalEntity> hospitals = new ArrayList<>();

    private HospitalDoctorAdapter adapter;

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

        et_search.setOnEditorActionListener(onEditorActionListener);
    }

    private void initListView(){
        xrv_search.setLoadingMoreEnabled(true);
        xrv_search.setFootView(new XLoadMoreFooter(this));
        xrv_search.setLoadingListener(searchLoadingListener);


        adapter = new HospitalDoctorAdapter(hospitals,doctors);
        xrv_search.setAdapter(adapter);
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
                case R.id.tv_location_failed:
                    checkPermissionTwice();
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

    private final static int UPDATAE_LIST = 0x01;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( UPDATAE_LIST == msg.what ){
                updateList();
            }
        }
    };

    List<DoctorEntity> tempDoctors;
    List<HospitalEntity> tempHospitals;
    private void search(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name = et_search.getText().toString();
                tempDoctors = DaoManager.getInstance().getDaotorDao().searchDoctorListByName(SpacialistServiceActivity.this,name);
                tempHospitals = DaoManager.getInstance().getHospitalDao().searchHospitalListByName(SpacialistServiceActivity.this,name);
                handler.sendEmptyMessage(UPDATAE_LIST);
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
    private final static String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private final static int REQUEST_PERMISSION = 0x01;
    private final static int REQUEST_PERMISSIN_TWICE = 0x02;
    private void checkPermission(){
        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
            initLocation();
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        }
    }

    private void checkPermissionTwice(){
        MyDialog.showChooseDialog(SpacialistServiceActivity.this, getString(R.string.location_content), R.string.later, R.string.go_setting, new MyDialog.ChooseDialogCallback() {
            @Override
            public void sure() {
                //TODO
                //跳转到权限设置界面
                skipToSetPermission();
            }

            @Override
            public void cancel() {}
        });
//        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
//            initLocation();
//        } else {
//            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIN_TWICE);
//        }
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

        if ( REQUEST_PERMISSIN_TWICE == requestCode ){
            if (PackageManager.PERMISSION_GRANTED != grantResults[0] ) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    MyDialog.showChooseDialog(SpacialistServiceActivity.this, getString(R.string.location_content), R.string.later, R.string.go_setting, new MyDialog.ChooseDialogCallback() {
                        @Override
                        public void sure() {
                            //TODO
                            //跳转到权限设置界面
                            skipToSetPermission();
                        }

                        @Override
                        public void cancel() {}
                    });
                }
            }
        }
    }

    private void skipToSetPermission(){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
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

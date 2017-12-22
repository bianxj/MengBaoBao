package com.doumengmengandroidbady.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.camera.AmbientLightManager;
import com.doumengmengandroidbady.camera.CameraManager;
import com.doumengmengandroidbady.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;

/**
 * Created by Administrator on 2017/12/6.
 */

public class ScanActivity extends BaseActivity {

    private final static int REQUEST_ALBUM_OK = 0x01;
    private final static int REQUEST_PERMISSION = 0x02;

    public final static String RESULT_QR_VALUE = "result_rq";

    private RelativeLayout rl_back;
    private TextView tv_title;
    private TextView tv_photo_album;
    private CheckBox cb_flash;
    private ViewfinderView vfv_scan;

    private SurfaceView sv_scan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan);

        hasSurface = false;
        findView();
        initView();
        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( cameraManager != null ) {
            cb_flash.setChecked(false);
            if (hasSurface) {
                initCamera(holder);
            } else {
                holder.addCallback(holderCallback);
            }
        }
//        ambientLightManager.start(cameraManager,sensorCallBack);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        ambientLightManager.stop();
        if ( cameraManager != null ) {
            cameraManager.stopPreview();
            cameraManager.closeDriver();
            if (!hasSurface) {
                holder.removeCallback(holderCallback);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( REQUEST_PERMISSION == requestCode ){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initScan();
                onResume();
            } else {
                back();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        sv_scan = findViewById(R.id.sv_scan);
        tv_photo_album = findViewById(R.id.tv_photo_album);
        cb_flash = findViewById(R.id.cb_flash);
        vfv_scan = findViewById(R.id.vfv_scan);
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        tv_photo_album.setOnClickListener(listener);
        tv_title.setText(R.string.scan);
        cb_flash.setOnCheckedChangeListener(changeListener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.tv_photo_album:
                    openAlbum();
                    break;
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            cameraManager.setTorch(isChecked);
        }
    };

    private void openAlbum(){
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, REQUEST_ALBUM_OK);
    }

    private void back(){
        finish();
    }

    private void resultBack(String value){
        Intent intent = new Intent();
        intent.putExtra(RESULT_QR_VALUE,value);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    //--------------------------------------扫码处理代码--------------------------------------------

    private CameraManager cameraManager;
    private AmbientLightManager ambientLightManager;
    private SurfaceHolder holder;
    private Collection<BarcodeFormat> decodeFormats;
    private boolean hasSurface;

    private final static int DECODE_QR = 0x01;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == DECODE_QR ){
                requestCameraData();
            }
        }
    };

    private void checkPermission(){
        if ( ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            initScan();
        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION);
//            } else {
//                MyDialog.showPromptDialog(this, "请打开相机权限", new MyDialog.PromptDialogCallback() {
//                    @Override
//                    public void sure() {
//                        back();
//                    }
//                });
//            }
        }
    }

    private void initScan(){
        cameraManager = new CameraManager(getApplicationContext());
        ambientLightManager = new AmbientLightManager(getApplicationContext());
        decodeFormats = EnumSet.of(BarcodeFormat.QR_CODE);
        vfv_scan.setVisibility(View.VISIBLE);

        holder = sv_scan.getHolder();
        holder.addCallback(holderCallback);
    }

    private SurfaceHolder.Callback holderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if ( !hasSurface ) {
                hasSurface = true;
                initCamera(surfaceHolder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            hasSurface = false;
        }
    };

    private void initCamera(SurfaceHolder surfaceHolder){
        if ( cameraManager.isOpen() ){
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            cameraManager.startPreview();
            handler.sendEmptyMessage(DECODE_QR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestCameraData(){
        cameraManager.requestPreviewFrame(previewCallback);
    }

    private CameraManager.PreviewCallback previewCallback = new CameraManager.PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera, int height, int width) {
            Rect rect = new Rect(0,0,width,height);
            Result result = cameraManager.decode(rect,data,width,height);
            if ( result == null ){
                handler.sendEmptyMessage(DECODE_QR);
            } else {
                resultBack(result.getText());
            }
        }
    };

    private AmbientLightManager.SensorCallBack sensorCallBack = new AmbientLightManager.SensorCallBack() {
        @Override
        public void onSensorChanged(boolean isDark) {
        }
    };

}

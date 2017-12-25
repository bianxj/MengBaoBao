package com.doumengmengandroidbady.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.camera.CameraManager;
import com.doumengmengandroidbady.view.ViewfinderView;
import com.google.zxing.Result;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/6.
 */

public class ScanActivity extends BaseActivity {

    private final static int REQUEST_ALBUM_OK = 0x01;
    private final static int REQUEST_PERMISSION = 0x02;
    private String[] permissions = new String[]{Manifest.permission.CAMERA};

    public final static String RESULT_QR_VALUE = "result_rq";

    private RelativeLayout rl_back;
    private TextView tv_title;
    private TextView tv_photo_album;
    private CheckBox cb_flash;
    private ViewfinderView vfv_scan;
    private FrameLayout fl_shade;

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
        checkPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScan();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( REQUEST_PERMISSION == requestCode ){
            if (PackageManager.PERMISSION_GRANTED != grantResults[0] ){
                if ( ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0]) ){
                    checkPermission();
                } else {
                    Toast.makeText(this,"请打开照相机权限",Toast.LENGTH_LONG).show();
                }
            } else {
                startScan();
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
        fl_shade = findViewById(R.id.fl_shade);
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        tv_photo_album.setOnClickListener(listener);
        tv_title.setText(R.string.scan);
        cb_flash.setOnCheckedChangeListener(changeListener);

        holder = sv_scan.getHolder();
        holder.addCallback(holderCallback);
        cameraManager = new CameraManager(getApplicationContext());
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

    private SurfaceHolder holder;

    private void checkPermission(){
        if ( ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ){
            startScan();
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        }
    }

    private CameraManager cameraManager;
    private boolean hasSurface;
    private boolean canScan;

    private void startScan(){
        fl_shade.setVisibility(View.VISIBLE);
        canScan = true;
        if ( !hasSurface ){
            return;
        }
        try {
            if ( !cameraManager.isOpen() ) {
                cameraManager.openDriver(sv_scan.getHolder());
            }
            cameraManager.startPreview();
            cameraManager.requestPreviewFrame(previewCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopScan(){
        fl_shade.setVisibility(View.INVISIBLE);
        canScan = false;
        if ( cameraManager != null ){
            cameraManager.stopPreview();
            cameraManager.closeDriver();
        }
    }

    private CameraManager.PreviewCallback previewCallback = new CameraManager.PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera, int height, int width) {
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++)
                    rotatedData[x * height + height - y - 1] = data[x + y * width];
            }
            int tmp = width;
            width = height;
            height = tmp;
            Rect rect = cameraManager.getDecodeArea(vfv_scan.getScanRect());

            Result result = cameraManager.decode(rect,rotatedData,width,height);
            if ( result != null ){
                resultBack(result.getText());
//                Toast.makeText(ScanActivity.this,result.getText(), Toast.LENGTH_SHORT).show();
            } else {
                cameraManager.requestPreviewFrame(previewCallback);
            }
        }
    };

    private SurfaceHolder.Callback holderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            hasSurface = true;
            if ( canScan ){
                startScan();
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

}

package com.doumengmengandroidbady.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.util.PictureUtils;
import com.doumengmengandroidbady.view.CircleImageView;

/**
 * Created by Administrator on 2017/12/12.
 */
public class HeadImageActivity extends BaseActivity {

    private final static int REQUEST_PERMISSION_CAMERA = 0x10;
    private final static int REQUEST_PERMISSION_STORAGE = 0x02;

    private final static int REQUEST_CAMERA = 0x01;
    private final static int REQUEST_IMAGE = 0x02;

    private RelativeLayout rl_back;
    private TextView tv_title;

    private CircleImageView civ_head;
    private ImageView iv_camera , iv_picture;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_image);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        civ_head = findViewById(R.id.civ_head);
        iv_camera = findViewById(R.id.iv_camera);
        iv_picture = findViewById(R.id.iv_picture);
        initView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        iv_picture.setOnClickListener(listener);
        iv_camera.setOnClickListener(listener);
        tv_title.setText(R.string.head_image);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.iv_camera:
                    openCamera();
                    break;
                case R.id.iv_picture:
                    tackPicture();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void openCamera(){
        if ( checkCameraPermission() ) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private boolean checkCameraPermission(){
        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ){
            return true;
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
            return false;
        }
    }

    private void tackPicture(){
        if ( checkExternalStoragePermission() ){
            Intent intent = new Intent(Intent.ACTION_PICK) ;
            intent.setType("image/*") ;
            startActivityForResult(intent , REQUEST_IMAGE) ;
        }
    }

    private boolean checkExternalStoragePermission(){
        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){
            return true;
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_STORAGE);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( REQUEST_PERMISSION_CAMERA == requestCode ){
            if ( PackageManager.PERMISSION_GRANTED == grantResults[0] ){
                openCamera();
            } else {
                if ( ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0]) ){
                    checkCameraPermission();
                } else {
                    Toast.makeText(this,"请打开照相机权限",Toast.LENGTH_LONG).show();
                }
            }
        }
        if ( REQUEST_PERMISSION_STORAGE == requestCode ){
            if ( PackageManager.PERMISSION_GRANTED == grantResults[0] ){
                tackPicture();
            } else {
                if ( ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0]) ){
                    checkExternalStoragePermission();
                } else {
                    Toast.makeText(this,"请打开存储权限",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( REQUEST_CAMERA == requestCode && Activity.RESULT_OK == resultCode && null != data){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            civ_head.setImageBitmap(bitmap);
        }

        if ( REQUEST_IMAGE == requestCode && Activity.RESULT_OK == resultCode && null != data ) {
            if (data == null) return;
            String path;
            Uri uri = data.getData();
            int sdkVersion = Integer.valueOf(Build.VERSION.SDK);
            if (sdkVersion >= 19) {
                path = uri.getPath();
                path = PictureUtils.getPath_above19(HeadImageActivity.this, uri);
            } else {
                path = PictureUtils.getFilePath_below19(HeadImageActivity.this,uri);
            }
            civ_head.setImageBitmap(PictureUtils.getSmallBitmap(path,civ_head.getWidth(),civ_head.getHeight()));
        }
    }
}

package com.doumengmeng.doctor.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.util.AppUtil;
import com.doumengmeng.doctor.util.MyDialog;
import com.doumengmeng.doctor.util.PermissionUtil;
import com.doumengmeng.doctor.util.PictureUtils;
import com.doumengmeng.doctor.view.CircleImageView;

import java.io.File;
import java.io.IOException;

/**
 * 作者: 边贤君
 * 描述: 上传头像
 * 创建日期: 2018/1/8 13:53
 */
public class HeadImageActivity extends BaseActivity {

    private final static int REQUEST_CAMERA = 0x01;
    private final static int REQUEST_IMAGE = 0x02;
    private final static int REQUEST_CROP = 0x03;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        loadHeadImg();
    }

    private void loadHeadImg(){
        Bitmap bitmap = BitmapFactory.decodeFile(BaseApplication.getInstance().getHeadCropPath());
        if ( bitmap != null ){
            civ_head.setImageBitmap(bitmap);
        }
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
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
                    takePicture();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private Uri destUri = null;

    private void openCamera(){
        if ( PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.CAMERA) ){
            File picture = new File(BaseApplication.getInstance().getDoctorHeadImgPath());
            if ( picture.exists() ){
                picture.delete();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
                Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", picture);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                Uri uri = Uri.fromFile(picture);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private void cropFromCamera(File src) throws IOException {
        File dest = BaseApplication.getInstance().getHeadCropFile();
        boolean needPermission = false;
        Uri srcUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            srcUri = FileProvider.getUriForFile(this,
                    getPackageName() + ".fileProvider",
                    src);
            needPermission = true;
        } else {
            srcUri = Uri.fromFile(src);
            needPermission = false;
        }
        destUri = Uri.fromFile(dest);

        cropPicture(srcUri,destUri,needPermission);
    }

    private void takePicture(){
        if ( PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ){
            Intent intent = new Intent(Intent.ACTION_PICK) ;
            intent.setType("image/*") ;
            startActivityForResult(intent , REQUEST_IMAGE) ;
        }
    }

    private void cropFromPicture(Uri src) throws IOException {
        String path = PictureUtils.getGalleryImagePath(this,src);
        if ( !BaseApplication.getInstance().getHeadCropPath().equals(path) ) {
            File dest = BaseApplication.getInstance().getHeadCropFile();
            destUri = Uri.fromFile(dest);
            cropPicture(src, destUri, false);
        }
    }

    private void cropPicture(Uri srcUri , Uri destUri , boolean needPermission){
        //直接裁剪
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (needPermission) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", true);
        // aspectX,aspectY 是宽高的比例，这里设置正方形
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置要裁剪的宽高
        intent.putExtra("outputX", 300); //200dp
        intent.putExtra("outputY", 300);
        //是否支持缩放
        intent.putExtra("scale", true);
        //如果图片过大，会导致oom，这里设置为false
        intent.putExtra("return-data", false);
        intent.setDataAndType(srcUri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, destUri);
        intent.putExtra("noFaceDetection", true);
        //压缩图片
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, REQUEST_CROP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this,requestCode, permissions, grantResults, new PermissionUtil.RequestPermissionSuccess() {
            @Override
            public void success(String permission) {
                if ( Manifest.permission.CAMERA.equals(permission) ){
                    openCamera();
                }
                if ( Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission) ){
                    takePicture();
                }
            }

            @Override
            public void denied(String permission) {

            }

            @Override
            public void alwaysDenied(String permission) {
                String prompt = null;
                if ( Manifest.permission.CAMERA.equals(permission) ){
                    prompt = getResources().getString(R.string.dialog_content_camera_permission);
                }
                if ( Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission) ){
                    prompt = getResources().getString(R.string.dialog_content_storage_permission);
                }
                MyDialog.showPermissionDialog(HeadImageActivity.this, prompt, new MyDialog.ChooseDialogCallback() {
                    @Override
                    public void sure() {
                        AppUtil.openPrimession(HeadImageActivity.this);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( REQUEST_CAMERA == requestCode && Activity.RESULT_OK == resultCode){
            try {
                cropFromCamera(new File(BaseApplication.getInstance().getDoctorHeadImgPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if ( REQUEST_IMAGE == requestCode && Activity.RESULT_OK == resultCode && null != data ) {
            Uri uri = data.getData();
            try {
                cropFromPicture(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if ( REQUEST_CROP == requestCode && Activity.RESULT_OK == resultCode ) {
            Bitmap bitmap = BitmapFactory.decodeFile(BaseApplication.getInstance().getHeadCropPath());// BitmapFactory.decodeStream(getContentResolver().openInputStream(destUri));
            civ_head.setImageBitmap(bitmap);
            try {
                PictureUtils.saveBitmap(BaseApplication.getInstance().getHeadCropFile(), bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

package com.doumengmengandroidbady.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.net.HttpUtil;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.AppUtil;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.util.PermissionUtil;
import com.doumengmengandroidbady.util.PictureUtils;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 上传头像
 * 创建日期: 2018/1/8 13:53
 */
public class HeadImageActivity extends BaseActivity {

    private final static int REQUEST_CAMERA = 0x01;
    private final static int REQUEST_IMAGE = 0x02;

    private UserData userData;
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

        userData = BaseApplication.getInstance().getUserData();
        if ( !TextUtils.isEmpty(userData.getHeadimg()) ) {
            ImageLoader.getInstance().displayImage(userData.getHeadimg(),civ_head);
        }
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
        if (PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.CAMERA)){
            File picture = new File(BaseApplication.getInstance().getPersonHeadImgPath());
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

    private void tackPicture(){
        if ( PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ){
            Intent intent = new Intent(Intent.ACTION_PICK) ;
            intent.setType("image/*") ;
            startActivityForResult(intent , REQUEST_IMAGE) ;
        }
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
                    tackPicture();
                }
            }

            @Override
            public void denied(String permission) {

            }

            @Override
            public void alwaysDenied(String permission) {
                String prompt = null;
                if ( Manifest.permission.CAMERA.equals(permission) ){
                    prompt = getResources().getString(R.string.camera_permission);
                }
                if ( Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission) ){
                    prompt = getResources().getString(R.string.storage_permission);
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

    private Bitmap headImg = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String source = null;
        if ( REQUEST_CAMERA == requestCode && Activity.RESULT_OK == resultCode){
            source = BaseApplication.getInstance().getPersonHeadImgPath();
        }

        if ( REQUEST_IMAGE == requestCode && Activity.RESULT_OK == resultCode && null != data ) {
            if (data == null) return;
            Uri uri = data.getData();
            int sdkVersion = Integer.valueOf(Build.VERSION.SDK);
            if (sdkVersion >= 19) {
                source = PictureUtils.getPath_above19(HeadImageActivity.this, uri);
            } else {
                source = PictureUtils.getFilePath_below19(HeadImageActivity.this,uri);
            }
        }
        if ( Activity.RESULT_OK == resultCode ) {
            String target = BaseApplication.getInstance().getPersonHeadImgPath();
            PictureUtils.compressPicture(source,target,civ_head.getWidth(), civ_head.getHeight());
            Bitmap tempImg = PictureUtils.getSmallBitmap(target, civ_head.getWidth(), civ_head.getHeight());
            civ_head.setImageBitmap(tempImg);
            if (tempImg != headImg) {
                if ( headImg != null ) {
                    headImg.recycle();
                }
                headImg = tempImg;
            }
            uploadHeadImg();
        }
    }

    private void uploadHeadImg(){
        try {
            uploadHeadImageTask = new RequestTask.Builder(this,uploadHeadImageCallBack).build();
            uploadHeadImageTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestTask uploadHeadImageTask = null;

    private RequestCallBack uploadHeadImageCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_UPLOAD_HEAD_IMG;
        }


        @Override
        public Map<String, String> getContent() {
            UserData userData = BaseApplication.getInstance().getUserData();
            Map<String,String> map = new HashMap<>();
            map.put("sesId",userData.getSessionId());
            map.put("userId",userData.getUserid());

            List<HttpUtil.UploadFile> uploadFiles = new ArrayList<>();
            HttpUtil.UploadFile uploadFile = new HttpUtil.UploadFile();
            uploadFile.setFileName("userHead");
            uploadFile.setFilePath(BaseApplication.getInstance().getPersonHeadImgPath());
            uploadFiles.add(uploadFile);
            map.put(HttpUtil.TYPE_FILE,GsonUtil.getInstance().getGson().toJson(uploadFiles));
            return map;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            //修改用户头像数据
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                userData.setHeadimg(res.getString("headimg"));
                BaseApplication.getInstance().saveUserData(userData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String type() {
            return JSON;
        }
    };

}

package com.doumengmengandroidbady.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
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
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.camera.CameraManager;
import com.doumengmengandroidbady.util.PictureUtils;
import com.doumengmengandroidbady.view.ViewfinderView;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;

/**
 * 作者: 边贤君
 * 描述: 扫一扫
 * 创建日期: 2018/1/8 16:25
 */
public class ScanActivity extends BaseActivity {

    private final static int REQUEST_PERMISSION_STORAGE = 0x01;
    private final static int REQUEST_CAMERA_PERMISSION = 0x02;

    private final static int REQUEST_ALBUM = 0x11;
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
        checkCameraPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
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
        if ( REQUEST_CAMERA_PERMISSION == requestCode ){
            if (PackageManager.PERMISSION_GRANTED != grantResults[0] ){
                if ( ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0]) ){
                    checkCameraPermission();
                } else {
                    Toast.makeText(this,"请打开照相机权限",Toast.LENGTH_LONG).show();
                }
            } else {
                startScan();
            }
        }
        if ( REQUEST_PERMISSION_STORAGE == requestCode ){
            if ( PackageManager.PERMISSION_GRANTED != grantResults[0] ){
                if ( ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0]) ){
                    checkExternalStoragePermission();
                } else {
                    Toast.makeText(this,"请打开存储权限",Toast.LENGTH_LONG).show();
                }
            } else {
                openAlbum();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( REQUEST_ALBUM == requestCode && Activity.RESULT_OK == resultCode && null != data ) {
            String source = null;
            if (data == null) return;
            Uri uri = data.getData();
            int sdkVersion = Integer.valueOf(Build.VERSION.SDK);
            if (sdkVersion >= 19) {
                source = PictureUtils.getPath_above19(ScanActivity.this, uri);
            } else {
                source = PictureUtils.getFilePath_below19(ScanActivity.this,uri);
            }
            new Thread(new DecodeRunnable(source)).start();
        }
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
        startActivityForResult(albumIntent, REQUEST_ALBUM);
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

    private void checkCameraPermission(){
        if ( ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ){
            startScan();
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA_PERMISSION);
        }
    }

    private void checkExternalStoragePermission(){
        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){
            openAlbum();
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_STORAGE);
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
            int top = getResources().getDimensionPixelOffset(R.dimen.SelfActionBarHeight);
            Rect rect = cameraManager.getDecodeArea(vfv_scan.getScanRect(top,0));

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

    private final static int MESSAGE_DECODE_RESULT = 0x01;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( MESSAGE_DECODE_RESULT == msg.what ){
                if ( msg.obj == null ){
                    Toast.makeText(ScanActivity.this,"图片解析错误",Toast.LENGTH_LONG).show();
                } else {
                    Result result = (Result) msg.obj;
                    resultBack(result.getText());
                }
            }
        }
    };

    private Reader reader;
    private class DecodeRunnable implements Runnable{

        private String picturePath;

        public DecodeRunnable(String picturePath) {
            this.picturePath = picturePath;
        }

        @Override
        public void run() {
//            Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
//            hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
            DisplayMetrics metrics = BaseApplication.getInstance().getDisplayInfo();
            Bitmap bitmap = PictureUtils.getSmallBitmap(picturePath, metrics.widthPixels,metrics.heightPixels);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] pixels = new int[width*height];
            bitmap.getPixels(pixels,0,width,0,0,width,height);

            // 最新的库中，RGBLuminanceSource 的构造器参数不只是bitmap了
            RGBLuminanceSource source = new RGBLuminanceSource(width,height,pixels);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            if ( reader == null ){
                reader = new QRCodeReader();
            }

            Result result = null;

            // 尝试解析此bitmap，！！注意！！ 这个部分一定写到外层的try之中，因为只有在bitmap获取到之后才能解析。写外部可能会有异步的问题。（开始解析时bitmap为空）
            try {
                result = reader.decode(binaryBitmap);
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (ChecksumException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }
            Message message = handler.obtainMessage();
            message.what = MESSAGE_DECODE_RESULT;
            message.obj = result;
            handler.sendMessage(message);
        }
    }

}

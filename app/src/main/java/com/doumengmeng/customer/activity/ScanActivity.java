package com.doumengmeng.customer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseActivity;
import com.doumengmeng.customer.camera.CameraManager;
import com.doumengmeng.customer.util.AppUtil;
import com.doumengmeng.customer.util.MyDialog;
import com.doumengmeng.customer.util.PermissionUtil;
import com.doumengmeng.customer.util.PictureUtils;
import com.doumengmeng.customer.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.util.Hashtable;

/**
 * 作者: 边贤君
 * 描述: 扫一扫
 * 创建日期: 2018/1/8 16:25
 */
public class ScanActivity extends BaseActivity {

    private final static int REQUEST_PERMISSION_STORAGE = 0x01;
    private final static int REQUEST_CAMERA_PERMISSION = 0x02;

    private final static int REQUEST_ALBUM = 0x11;
    private final String[] permissions = new String[]{Manifest.permission.CAMERA};

    public final static String RESULT_QR_VALUE = "result_rq";

    private RelativeLayout rl_back;
    private TextView tv_title;
    private TextView tv_photo_album;
    private CheckBox cb_flash;
    private ViewfinderView vfv_scan;
    private FrameLayout fl_shade;
    private ImageView iv_lazer;

    private SurfaceView sv_scan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan);

        hasSurface = false;
        findView();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkCameraPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, new PermissionUtil.RequestPermissionSuccess() {
            @Override
            public void success(String permission) {
                if ( Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission) ){
                    openAlbum();
                }
                if ( Manifest.permission.CAMERA.equals(permission) ){
                    startScan();
                }
            }

            @Override
            public void denied(String permission) {
                if ( Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission) ){
                    checkExternalStoragePermission();
                }
                if ( Manifest.permission.CAMERA.equals(permission) ){
                    checkCameraPermission();
                }
            }

            @Override
            public void alwaysDenied(String permission) {
                if ( Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission) ){
                    MyDialog.showPermissionDialog(ScanActivity.this, getString(R.string.dialog_content_open_storage), new MyDialog.ChooseDialogCallback() {
                        @Override
                        public void sure() {
                            AppUtil.openPrimession(ScanActivity.this);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                }
                if ( Manifest.permission.CAMERA.equals(permission) ){
                    MyDialog.showPermissionDialog(ScanActivity.this, getString(R.string.dialog_content_open_camera), new MyDialog.ChooseDialogCallback() {
                        @Override
                        public void sure() {
                            AppUtil.openPrimession(ScanActivity.this);
                        }

                        @Override
                        public void cancel() {
                            back();
                        }
                    });
                }
            }
        });
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if ( REQUEST_CAMERA_PERMISSION == requestCode ){
//            if (PackageManager.PERMISSION_GRANTED != grantResults[0] ){
//                if ( ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0]) ){
//                    checkCameraPermission();
//                } else {
//                    Toast.makeText(this,"请打开照相机权限",Toast.LENGTH_LONG).show();
//                }
//            } else {
//                startScan();
//            }
//        }
//        if ( REQUEST_PERMISSION_STORAGE == requestCode ){
//            if ( PackageManager.PERMISSION_GRANTED != grantResults[0] ){
//                if ( ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0]) ){
//                    checkExternalStoragePermission();
//                } else {
//                    Toast.makeText(this,"请打开存储权限",Toast.LENGTH_LONG).show();
//                }
//            } else {
//                openAlbum();
//            }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( REQUEST_ALBUM == requestCode && Activity.RESULT_OK == resultCode && null != data ) {
            String source;
            Uri uri = data.getData();
            source = PictureUtils.getFilePath(ScanActivity.this,uri);
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
        iv_lazer = findViewById(R.id.iv_lazer);
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        tv_photo_album.setOnClickListener(listener);
        tv_title.setText(R.string.scan);
        cb_flash.setOnCheckedChangeListener(changeListener);

        holder = sv_scan.getHolder();
        holder.addCallback(holderCallback);
        cameraManager = new CameraManager(getApplicationContext());
//        fl_shade.setVisibility(View.VISIBLE);
    }

    private void startLazerAnime(){
        Rect rect = vfv_scan.getFrameRect();
        iv_lazer.setVisibility(View.VISIBLE);
        Animation animation = new TranslateAnimation(0,0,rect.top,rect.bottom-20);
        animation.setDuration(1500);
        animation.setRepeatCount(Animation.INFINITE);
        iv_lazer.clearAnimation();
        iv_lazer.startAnimation(animation);
    }

    private void stopLazerAnime(){
        Animation animation = iv_lazer.getAnimation();
        if ( animation != null ){
            animation.cancel();
        }
        iv_lazer.clearAnimation();
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
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

    private final CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
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

    protected void back(){
        finish();
    }

    private void resultBack(String value){
        if(value.startsWith("http://sj.qq.com/myapp/detail.htm?apkName=com.doumengmeng.customer&doctorId=")){
            String doctorId = value.replace("http://sj.qq.com/myapp/detail.htm?apkName=com.doumengmeng.customer&doctorId=","");
            Intent intent = new Intent(this,DoctorInfoActivity.class);
            intent.putExtra(DoctorInfoActivity.IN_PARAM_DOCTOR_ID,doctorId);
            startActivity(intent);
            finish();
        }
//        Intent intent = new Intent();
//        intent.putExtra(RESULT_QR_VALUE,value);
//        setResult(Activity.RESULT_OK,intent);
//        finish();
    }

    //--------------------------------------扫码处理代码--------------------------------------------

    private SurfaceHolder holder;

    private void checkCameraPermission(){
//        System.out.println("permission:"+ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA));
        if (PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.CAMERA)){
            startScan();
        }
    }

    private void checkExternalStoragePermission(){
        if (PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            openAlbum();
        }
    }

    private CameraManager cameraManager;
    private boolean hasSurface;
    private boolean canScan;

    private void startScan(){
        fl_shade.setVisibility(View.VISIBLE);
        startLazerAnime();

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
        } catch (Exception e){
            e.printStackTrace();
            MyDialog.showPermissionDialog(ScanActivity.this,getString(R.string.dialog_content_open_camera), new MyDialog.ChooseDialogCallback() {
                @Override
                public void sure() {
                    AppUtil.openPrimession(ScanActivity.this);
                }

                @Override
                public void cancel() {
                    back();
                }
            });
        }
    }

    private void stopScan(){
        fl_shade.setVisibility(View.INVISIBLE);
        canScan = false;
        if ( cameraManager != null ){
            cameraManager.stopPreview();
            cameraManager.closeDriver();
        }
        stopLazerAnime();
    }

    private final CameraManager.PreviewCallback previewCallback = new CameraManager.PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera, int height, int width) {
            new Thread(new ScanDecodeRunnable(data,height,width)).start();
        }
    };

    private class ScanDecodeRunnable implements Runnable{

        private byte[] data;
        private int height;
        private int width;

        public ScanDecodeRunnable(byte[] data, int height, int width) {
            this.data = data;
            this.height = height;
            this.width = width;
        }

        @Override
        public void run() {
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++)
                    rotatedData[x * height + height - y - 1] = data[x + y * width];
            }
            int tmp = width;
            width = height;
            height = tmp;
            int top = getResources().getDimensionPixelOffset(R.dimen.SelfActionBarHeight);
            Rect rect = cameraManager.getDecodeArea(vfv_scan.getScanRect(0,0));

            Result result = cameraManager.decode(rect,rotatedData,width,height);
            if ( result != null ){
//                resultBack(result.getText());
                Message message = handler.obtainMessage();
                message.what = MESSAGE_DECODE_RESULT;
                message.obj = result;
                handler.sendMessage(message);
//                Toast.makeText(ScanActivity.this,result.getText(), Toast.LENGTH_SHORT).show();
            } else {
                cameraManager.requestPreviewFrame(previewCallback);
            }
        }
    }

    private final SurfaceHolder.Callback holderCallback = new SurfaceHolder.Callback() {
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

    //---------------------------------二维码图片解析---------------------------------------------------
    private final static int MESSAGE_DECODE_RESULT = 0x01;
    private final Handler handler = new Handler(){
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

        private final String picturePath;

        public DecodeRunnable(String picturePath) {
            this.picturePath = picturePath;
        }

        @Override
        public void run() {
//            Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
//            hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
//            DisplayMetrics metrics = BaseApplication.getInstance().getDisplayInfo();
//            Bitmap bitmap = PictureUtils.getSmallBitmap(picturePath, metrics.widthPixels,metrics.heightPixels);
//
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            int[] pixels = new int[width*height];
//            bitmap.getPixels(pixels,0,width,0,0,width,height);
//
//            // 最新的库中，RGBLuminanceSource 的构造器参数不只是bitmap了
//            RGBLuminanceSource source = new RGBLuminanceSource(width,height,pixels);
//            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
//            if ( reader == null ){
//                reader = new QRCodeReader();
//            }
//
//            Result result = null;
//
//            // 尝试解析此bitmap，！！注意！！ 这个部分一定写到外层的try之中，因为只有在bitmap获取到之后才能解析。写外部可能会有异步的问题。（开始解析时bitmap为空）
//            try {
//                result = reader.decode(binaryBitmap);
//            } catch (NotFoundException e) {
//                e.printStackTrace();
//            } catch (ChecksumException e) {
//                e.printStackTrace();
//            } catch (FormatException e) {
//                e.printStackTrace();
//            }
//            Message message = handler.obtainMessage();
//            message.what = MESSAGE_DECODE_RESULT;
//            message.obj = result;
//            handler.sendMessage(message);

            Result result = decodeBarcodeRGB(picturePath);
            Message message = handler.obtainMessage();
            message.what = MESSAGE_DECODE_RESULT;
            message.obj = result;
            handler.sendMessage(message);
        }
    }

    /**
     * 解析二维码（使用解析RGB编码数据的方式）
     *
     * @param path
     * @return
     */
    private static Result decodeBarcodeRGB(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        Bitmap barcode = BitmapFactory.decodeFile(path, opts);
//        Result result = decodeBarcodeRGB(barcode);
//        barcode.recycle();
        return decodeBarcodeRGB(barcode);
    }

    /**
     * 解析二维码 （使用解析RGB编码数据的方式）
     *
     * @param barcode
     * @return
     */
    private static Result decodeBarcodeRGB(Bitmap barcode) {
        int width = barcode.getWidth();
        int height = barcode.getHeight();
        int[] data = new int[width * height];
        barcode.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));

        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        hints.put(DecodeHintType.TRY_HARDER,Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(bitmap1,hints);
        } catch (NotFoundException | FormatException | ChecksumException e) {
            e.printStackTrace();
        }
        barcode.recycle();
        return result;
    }

}

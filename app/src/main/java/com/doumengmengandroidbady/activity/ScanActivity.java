package com.doumengmengandroidbady.activity;

import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/6.
 */

public class ScanActivity extends BaseActivity {

//    private ViewfinderView vfv_scan;
//    private SurfaceView sv_scan;
//
//    private SurfaceHolder holder;
//    private CameraManager cameraManager;
//    private AmbientLightManager ambientLightManager;
//    private Reader reader;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setContentView(R.layout.activity_scan);
//        findView();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        ambientLightManager.start(cameraManager);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        ambientLightManager.stop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    private void findView(){
//        vfv_scan = findViewById(R.id.vfv_scan);
//        sv_scan = findViewById(R.id.sv_scan);
//
//        holder = sv_scan.getHolder();
//        holder.addCallback(callback);
//
//        cameraManager = new CameraManager(this);
//        ambientLightManager = new AmbientLightManager(this);
//    }
//
//    private void initCamera(SurfaceHolder surfaceHolder){
//        if ( surfaceHolder == null ){
//            //TODO
//        }
//        if ( cameraManager.isOpen() ){
//            return;
//        }
//
//        try {
//            cameraManager.openDriver(surfaceHolder);
//            cameraManager.startPreview();
//            cameraManager.requestPreviewFrame(handler,DECODE);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            initCamera(holder);
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//
//        }
//    };
//
//
//    private static final int DECODE = 0x01;
//    private static final int DECODE_SUCCEEDED = 0x02;
//    private static final int DECODE_FAILED = 0x03;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if ( msg.what == DECODE ){
//                decode((byte[]) msg.obj,msg.arg1,msg.arg2);
//            } else if ( msg.what == DECODE_SUCCEEDED ){
//
//            } else if ( msg.what == DECODE_FAILED ){
//                //TODO
//            }
//        }
//    };
//
//    private void decode(byte[] data,int width,int height){
//        PlanarYUVLuminanceSource source = buildLuminanceSource(data,width,height);
//        Result result = null;
//        if ( source != null ){
//            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//            try {
//                result = reader.decode(bitmap);
//            } catch (ReaderException e) {
//                e.printStackTrace();
//            } finally {
//                reader.reset();
//            }
//        }
//        Message message = Message.obtain(handler);
//        if ( result != null ){
//            message.what = DECODE_SUCCEEDED;
//            message.obj = result.getText();
//        } else {
//            message.what = DECODE_FAILED;
//        }
//        message.sendToTarget();
//    }
//
//    private PlanarYUVLuminanceSource buildLuminanceSource(byte[] data,int width,int height){
//        Rect rect = new Rect();
//        if (rect == null) {
//            return null;
//        }
//        // Go ahead and assume it's YUV rather than die.
//        return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top,
//                rect.width(), rect.height(), false);
//    }

}

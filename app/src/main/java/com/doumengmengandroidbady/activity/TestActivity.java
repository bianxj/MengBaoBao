package com.doumengmengandroidbady.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;

import com.doumengmengandroidbady.R;

/**
 * Created by Administrator on 2018/1/24.
 */
//just test
public class TestActivity extends Activity {

    //1、Camera
    private SurfaceView sv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findView();
    }

    private void findView(){
        sv = findViewById(R.id.sv);
    }

    private static class CameraUtil{

        private Camera camera;

        private void openCamera(){
            Camera.getNumberOfCameras();
        }

    }


}

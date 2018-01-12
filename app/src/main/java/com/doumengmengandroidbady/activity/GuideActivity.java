package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.view.AutoScrollViewPager;
import com.doumengmengandroidbady.view.DiagramView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/12/5.
 */
//TODO
public class GuideActivity extends BaseActivity {

    private AutoScrollViewPager asvp;
    private Button bt_guide_register , bt_guide_login;

    private DiagramView d_weight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findView();
        initView();
        initGuide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        asvp = findViewById(R.id.asvp);
        bt_guide_register = findViewById(R.id.bt_guide_register);
        bt_guide_login = findViewById(R.id.bt_guide_login);
    }

    private void initView(){
        bt_guide_register.setOnClickListener(listener);
        bt_guide_login.setOnClickListener(listener);

        int[] images = new int[]{R.drawable.v1,R.drawable.v2,R.drawable.v3};
        asvp.setImageList(images);
    }

    private void initGuide(){
        d_weight = findViewById(R.id.d_weight);

        DiagramView.DiagramBaseInfo weightParam = new DiagramView.DiagramBaseInfo();
        weightParam.setLowerLimitX(0);
        weightParam.setLowerLimitY(0);
        weightParam.setUpperLimitX(36);
        weightParam.setUpperLimitY(30);
        weightParam.setxLength(getResources().getDimension(R.dimen.x600px));
        weightParam.setyLength(getResources().getDimension(R.dimen.x622px));

        DiagramView.DiagramBaseInfo heightParam = new DiagramView.DiagramBaseInfo();
        heightParam.setLowerLimitX(0);
        heightParam.setUpperLimitX(36);
        heightParam.setLowerLimitY(45);
        heightParam.setUpperLimitY(120);
        heightParam.setxLength(getResources().getDimension(R.dimen.x600px));
        heightParam.setyLength(getResources().getDimension(R.dimen.x622px));


        DiagramView.DiagramBaseInfo bmi0_2Param = new DiagramView.DiagramBaseInfo();
        bmi0_2Param.setLowerLimitX(45);
        bmi0_2Param.setUpperLimitX(110);
        bmi0_2Param.setLowerLimitY(0);
        bmi0_2Param.setUpperLimitY(30);
        bmi0_2Param.setxLength(getResources().getDimension(R.dimen.x590px));
        bmi0_2Param.setyLength(getResources().getDimension(R.dimen.x622px));

        DiagramView.DiagramBaseInfo bmi2_5Param = new DiagramView.DiagramBaseInfo();
        bmi2_5Param.setLowerLimitX(65);
        bmi2_5Param.setUpperLimitX(120);
        bmi2_5Param.setLowerLimitY(0);
        bmi2_5Param.setUpperLimitY(30);
        bmi2_5Param.setxLength(getResources().getDimension(R.dimen.x588px));
        bmi2_5Param.setyLength(getResources().getDimension(R.dimen.x622px));

        DiagramView.DiagramParam param = new DiagramView.DiagramParam();
        List<DiagramView.DiagramPoint> points = new ArrayList<>();

        Random random = new Random(System.currentTimeMillis());
        for (int i= 0 ;i<16;i+=5){
            DiagramView.DiagramPoint point = new DiagramView.DiagramPoint();
            point.setType(1);
            point.setX(i+65);
            point.setY(i);
            points.add(point);
        }
        DiagramView.DiagramPoint point = new DiagramView.DiagramPoint();
        point.setType(0);
        point.setY(30);
        point.setX(120);
        points.add(point);
        param.setRedLine(points);
        d_weight.setParam(bmi2_5Param,param);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_guide_register:
                    startActivity(RegisterActivity.class);
                    break;
                case R.id.bt_guide_login:
                    startActivity(LoginActivity.class);
                    break;
            }
        }
    };

}

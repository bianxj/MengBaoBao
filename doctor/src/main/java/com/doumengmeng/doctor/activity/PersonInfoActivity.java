package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.response.entity.UserData;
import com.doumengmeng.doctor.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2018/3/1.
 */

public class PersonInfoActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private CircleImageView civ_head;
    private TextView tv_name,tv_hospital,tv_department,tv_professional_title,
            tv_service_cost,tv_income,tv_speciality,tv_intro,tv_identity_num;
    private ImageView iv_certification_p1 , iv_certification_p2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        findView();
    }

    private void findView(){
        initTitle();
        initDoctorInfo();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_back.setOnClickListener(listener);
        tv_title.setText(R.string.person_info);
    }

    private void initDoctorInfo(){
        civ_head = findViewById(R.id.civ_head);
        tv_name = findViewById(R.id.tv_name);
        tv_hospital = findViewById(R.id.tv_hospital);
        tv_department = findViewById(R.id.tv_department);
        tv_professional_title = findViewById(R.id.tv_professional_title);
        tv_service_cost = findViewById(R.id.tv_service_cost);
        tv_income = findViewById(R.id.tv_income);
        tv_speciality = findViewById(R.id.tv_speciality);
        tv_intro = findViewById(R.id.tv_intro);
        tv_identity_num = findViewById(R.id.tv_identity_num);
        iv_certification_p1 = findViewById(R.id.iv_certification_p1);
        iv_certification_p2 = findViewById(R.id.iv_certification_p2);

        tv_intro.setOnTouchListener(touchListener);
        tv_intro.setMovementMethod(ScrollingMovementMethod.getInstance());

        UserData userData = BaseApplication.getInstance().getUserData();
        tv_name.setText(userData.getDoctorName());
        tv_hospital.setText(DaoManager.getInstance().getHospitalDao().searchHospitalNameById(this,userData.getHospitalId()));
        tv_department.setText(userData.getDepartmentName());
        tv_professional_title.setText(userData.getPositionalTitles());
        tv_service_cost.setText(userData.getCost()+"元");
        tv_income.setText(Float.parseFloat(userData.getCost())/2+"元");
        tv_speciality.setText(userData.getSpeciality());
        tv_intro.setText(userData.getDoctorDesc());
        tv_identity_num.setText(userData.getDoctorCode());
//        ImageLoader.getInstance().displayImage(userData.getHeadimg(),civ_head);
        loadHeadImg(civ_head,userData.getHeadimg());
        ImageLoader.getInstance().displayImage(userData.getCertificateAUrl(),iv_certification_p1);
        ImageLoader.getInstance().displayImage(userData.getCertificateBUrl(),iv_certification_p2);
    }

    private void loadHeadImg(ImageView imageView, String urlHeadImg){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        builder.showImageOnLoading(R.drawable.default_icon_doctor);
        builder.showImageForEmptyUri(R.drawable.default_icon_doctor);
        builder.showImageOnFail(R.drawable.default_icon_doctor);
        ImageLoader.getInstance().displayImage(urlHeadImg,imageView,builder.build());
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }


    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                //通知父控件不要干扰
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            if(event.getAction()==MotionEvent.ACTION_MOVE){
                //通知父控件不要干扰
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            if(event.getAction()==MotionEvent.ACTION_UP){
                v.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        }
    };

}

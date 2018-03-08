package com.doumengmeng.doctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.view.CheckBoxLayout;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/3/1.
 */

public class InputDoctorInfoActivity extends BaseActivity {

    private final static int REQUEST_HEAD_IMG = 0x01;
    private final static int REQUEST_HOSPITAL = 0x02;
    private final static int REQUEST_DEPARTMENT = 0x03;
    private final static int REQUEST_FIRST_CERTIFICATE = 0x04;
    private final static int REQUEST_SECOND_CERTIFICATE = 0x05;

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title;

    private RelativeLayout rl_prompt,rl_prompt_close;
    private TextView tv_prompt;

    private EditText et_name,et_speciality,et_intro;
    private ImageView civ_head;
    private RelativeLayout rl_hospital,rl_department;
    private TextView tv_hospital ,tv_department;
    private TextView tv_income;
    private ImageView iv_certificate_example,iv_first_certificate, iv_second_certificate;
    private Button bt_submit;
    private CheckBoxLayout cbl_professional_title , cbl_service_cost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_doctor_info);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(uploadDoctorInfoTask);
    }

    private void findView(){
        initTitle();
        initPrompt();
        initContentView();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(getString(R.string.input_person_info));
        rl_complete.setVisibility(View.GONE);
        rl_back.setOnClickListener(listener);
    }

    private void initPrompt(){
        rl_prompt = findViewById(R.id.rl_prompt);
        rl_prompt_close = findViewById(R.id.rl_prompt_close);
        tv_prompt = findViewById(R.id.tv_prompt);

        rl_prompt.setVisibility(View.GONE);
    }

    private void initContentView(){
        et_name = findViewById(R.id.et_name);
        et_speciality = findViewById(R.id.et_speciality);
        et_intro = findViewById(R.id.et_intro);
        civ_head = findViewById(R.id.civ_head);
        rl_hospital = findViewById(R.id.rl_hospital);
        rl_department = findViewById(R.id.rl_department);
        tv_hospital = findViewById(R.id.tv_hospital);
        tv_department = findViewById(R.id.tv_department);
        tv_income = findViewById(R.id.tv_income);
        iv_certificate_example = findViewById(R.id.iv_certificate_example);
        iv_first_certificate = findViewById(R.id.iv_first_certificate);
        iv_second_certificate = findViewById(R.id.iv_second_certificate);
        bt_submit = findViewById(R.id.bt_submit);
        cbl_professional_title = findViewById(R.id.cbl_professional_title);
        cbl_service_cost = findViewById(R.id.cbl_service_cost);

        cbl_professional_title.setCheckBoxes(Arrays.asList(getResources().getStringArray(R.array.professional_titles)),true);
        cbl_service_cost.setCheckBoxes(Arrays.asList(getResources().getStringArray(R.array.service_cost)),true);
        cbl_service_cost.setOnItemSelectListener(selectListener);

        civ_head.setOnClickListener(listener);
        rl_hospital.setOnClickListener(listener);
        rl_department.setOnClickListener(listener);
        iv_certificate_example.setOnClickListener(listener);
        iv_first_certificate.setOnClickListener(listener);
        iv_second_certificate.setOnClickListener(listener);
        bt_submit.setOnClickListener(listener);
    }

    private CheckBoxLayout.OnItemSelectListener selectListener = new CheckBoxLayout.OnItemSelectListener() {
        @Override
        public void onItemClickListener(CompoundButton compoundButton) {
            refreshCost(compoundButton.getText().toString().trim());
        }
    };

    private void refreshCost(String cost){
        int realCost = Integer.parseInt(cost.replace("元",""));
        tv_income.setText(realCost+"元");
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.civ_head:
                    chooseHeadImage();
                    break;
                case R.id.rl_hospital:
                    selectHospital();
                    break;
                case R.id.rl_department:
                    selectDepartment();
                    break;
                case R.id.iv_certificate_example:
                    break;
                case R.id.iv_first_certificate:
                    break;
                case R.id.iv_second_certificate:
                    break;
                case R.id.bt_submit:
                    uploadDoctorInfo();
                    break;
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void chooseHeadImage(){
        Intent intent = new Intent(this,HeadImageActivity.class);
        startActivityForResult(intent,REQUEST_HEAD_IMG);
    }

    private void selectHospital(){
        Intent intent = new Intent(this,HospitalListActivity.class);
        startActivityForResult(intent,REQUEST_HOSPITAL);
    }

    private void selectDepartment(){
        Intent intent = new Intent(this,DepartmentActivity.class);
        startActivityForResult(intent,REQUEST_DEPARTMENT);
    }

    private void showCertificateExample(){

    }

    private void chooseFirstCertificate(){

    }

    private void chooseSecondCertificate(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( REQUEST_HEAD_IMG == requestCode ){
            refreshHeadImg();
        }
        if ( REQUEST_HOSPITAL == requestCode && Activity.RESULT_OK == resultCode){
            saveHospitalData(data);
        }
        if ( REQUEST_DEPARTMENT == requestCode && Activity.RESULT_OK == resultCode ){
            saveDepartmentName(data);
        }
    }

    private void refreshHeadImg(){
        Bitmap bitmap = BitmapFactory.decodeFile(BaseApplication.getInstance().getHeadCropPath());
        if ( bitmap != null ){
            civ_head.setImageBitmap(bitmap);
        }
    }

    private String hospitalName;
    private void saveHospitalData(Intent intent){
        hospitalName = intent.getStringExtra(HospitalListActivity.OUT_PARAM_HOSPITAL_NAME);
        tv_hospital.setText(hospitalName);
    }

    private String departmentName;
    private void saveDepartmentName(Intent intent){
        departmentName = intent.getStringExtra(DepartmentActivity.OUT_PARAM_DEPARTMENT_NAME);
        tv_department.setText(departmentName);
    }

    private RequestTask uploadDoctorInfoTask;

    private void uploadDoctorInfo(){
        //TODO
    }

    private void checkUploadDoctorInfo(){

    }

    private void buildUploadDoctorContent(){

    }

    private RequestCallBack uploadDoctorInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {

        }
    };

    private void back(){
        finish();
    }

    private final View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if ((view.getId() == R.id.et_speciality && canVerticalScroll(et_speciality))) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
            return false;
        }
    };

    /**
     * EditText竖直方向是否可以滚动
     * @param editText  需要判断的EditText
     * @return  true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

}

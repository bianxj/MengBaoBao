package com.doumengmeng.doctor.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.net.HttpUtil;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.request.entity.RequestDoctorInfo;
import com.doumengmeng.doctor.response.InputDoctorInfoResponse;
import com.doumengmeng.doctor.response.entity.UserData;
import com.doumengmeng.doctor.util.AppUtil;
import com.doumengmeng.doctor.util.EditTextUtil;
import com.doumengmeng.doctor.util.FormatCheckUtil;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MyDialog;
import com.doumengmeng.doctor.util.PermissionUtil;
import com.doumengmeng.doctor.util.PictureUtils;
import com.doumengmeng.doctor.view.CheckBoxLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private EditText et_name,et_speciality,et_intro,et_identity_num;
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
        BaseApplication.getInstance().clearHeadFile();
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
        rl_prompt_close.setOnClickListener(listener);
    }

    private void initContentView(){
        et_name = findViewById(R.id.et_name);
        et_speciality = findViewById(R.id.et_speciality);
        et_intro = findViewById(R.id.et_intro);
        et_identity_num = findViewById(R.id.et_identity_num);
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
        EditTextUtil.scrollEditText(et_intro);
        EditTextUtil.scrollEditText(et_speciality);

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
        tv_income.setText(realCost/2+"元");
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
                    showCertificateExample();
                    break;
                case R.id.iv_first_certificate:
                    chooseFirstCertificate();
                    break;
                case R.id.iv_second_certificate:
                    chooseSecondCertificate();
                    break;
                case R.id.bt_submit:
                    uploadDoctorInfo();
                    break;
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_prompt_close:
                    closePrompt();
                    break;
            }
        }
    };

    private void closePrompt(){
        rl_prompt.setVisibility(View.GONE);
    }

    private void showPrompt(String content){
        rl_prompt.setVisibility(View.VISIBLE);
        tv_prompt.setText(content);
    }

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
        MyDialog.showExampleDialog(this);
    }

    private void chooseFirstCertificate(){
        takePicture(REQUEST_FIRST_CERTIFICATE);
    }

    private void chooseSecondCertificate(){
        takePicture(REQUEST_SECOND_CERTIFICATE);
    }

    private void takePicture(int requestCode){
        if ( PermissionUtil.checkPermissionAndRequest(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ){
            Intent intent = new Intent(Intent.ACTION_PICK) ;
            intent.setType("image/*") ;
            startActivityForResult(intent , requestCode) ;
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, new PermissionUtil.RequestPermissionSuccess() {
            @Override
            public void success(String permission) {
                takePicture(requestCode);
            }

            @Override
            public void denied(String permission) {

            }

            @Override
            public void alwaysDenied(String permission) {
                MyDialog.showPermissionDialog(InputDoctorInfoActivity.this, getString(R.string.dialog_content_storage_permission), new MyDialog.ChooseDialogCallback() {
                    @Override
                    public void sure() {
                        AppUtil.openPrimession(InputDoctorInfoActivity.this);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        });
    }

    private boolean hasFirstCertificate = false;
    private boolean hasSecondCertificate = false;
//    private String firstCertificatePath;
//    private String secondCertificatePath;
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
        if ( REQUEST_FIRST_CERTIFICATE == requestCode && Activity.RESULT_OK == resultCode ){
            try {
                PictureUtils.saveGalleryBitmapToLocal(BaseApplication.getInstance().getCertificateOnePath(),this,data.getData());
                loadImageView(BaseApplication.getInstance().getCertificateOnePath(),iv_first_certificate);
                hasFirstCertificate = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if ( REQUEST_SECOND_CERTIFICATE == requestCode && Activity.RESULT_OK == resultCode ){
            try {
                PictureUtils.saveGalleryBitmapToLocal(BaseApplication.getInstance().getCertificateTwoPath(),this,data.getData());
                loadImageView(BaseApplication.getInstance().getCertificateTwoPath(),iv_second_certificate);
                hasSecondCertificate = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadImageView(String path,ImageView imageView){
        Bitmap bitmap = PictureUtils.getSmallBitmap(path,imageView.getWidth());
        imageView.setImageBitmap(bitmap);
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
        if ( checkUploadDoctorInfo() ) {
            try {
                uploadDoctorInfoTask = new RequestTask.Builder(this, uploadDoctorInfoCallBack)
                        .setContent(buildUploadDoctorContent())
                        .setUrl(UrlAddressList.URL_SAVE_DOCTOR)
                        .setType(RequestTask.DEFAULT).build();
                uploadDoctorInfoTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkUploadDoctorInfo(){
        if (TextUtils.isEmpty(et_name.getText().toString().trim()) ){
            showPrompt("姓名不能为空");
            return false;
        }

        if ( TextUtils.isEmpty(tv_hospital.getText().toString().trim()) ){
            showPrompt("请勾选执业医院");
            return false;
        }

        if ( TextUtils.isEmpty(tv_department.getText().toString().trim()) ){
            showPrompt("请勾选科室");
            return false;
        }

        if ( null == cbl_professional_title.getFirstCheckBox() ){
            showPrompt("请勾选职称");
            return false;
        }

        if ( null == cbl_service_cost.getFirstCheckBox() ){
            showPrompt("请勾选服务费");
            return false;
        }

        if ( TextUtils.isEmpty(et_identity_num.getText().toString().trim()) ){
            showPrompt("请输入身份证号");
            return false;
        }

        if (!FormatCheckUtil.isIdentityCard(et_identity_num.getText().toString().trim())){
            showPrompt("身份证格式不正确");
            return false;
        }

        if ( !hasFirstCertificate || !hasSecondCertificate ){
            showPrompt("证书信息不完全");
            return false;
        }

        return true;
    }

    private Map<String,String> buildUploadDoctorContent(){
        Map<String,String> map = new HashMap<>();

        UserData userData = BaseApplication.getInstance().getUserData();

        RequestDoctorInfo doctorInfo = new RequestDoctorInfo();
        doctorInfo.setDoctorId(userData.getDoctorId());
        CheckBox costBox = cbl_service_cost.getFirstCheckBox();

        CheckBox positionlBox = cbl_professional_title.getFirstCheckBox();

        doctorInfo.setDoctorName(et_name.getText().toString().trim());
        doctorInfo.setHospitalName(tv_hospital.getText().toString().trim());
        doctorInfo.setDepartmentName(tv_department.getText().toString().trim());
        doctorInfo.setPositionalTitles(positionlBox.getText().toString().trim());
        doctorInfo.setCost(costBox.getText().toString().trim().replace("元",""));
        doctorInfo.setSpeciality(et_speciality.getText().toString().trim());
        doctorInfo.setDoctorDesc(et_intro.getText().toString().trim());
        doctorInfo.setDoctorCode(et_identity_num.getText().toString().trim());

        List<HttpUtil.UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(new HttpUtil.UploadFile("doctorHead",BaseApplication.getInstance().getHeadCropPath()));
        uploadFiles.add(new HttpUtil.UploadFile("certificate1",BaseApplication.getInstance().getCertificateOnePath()));
        uploadFiles.add(new HttpUtil.UploadFile("certificate2",BaseApplication.getInstance().getCertificateTwoPath()));

        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getSessionId());
        map.put(UrlAddressList.PARAM, GsonUtil.getInstance().toJson(doctorInfo));
        map.put(HttpUtil.TYPE_FILE,GsonUtil.getInstance().toJson(uploadFiles));

        return map;
    }

    private RequestCallBack uploadDoctorInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(InputDoctorInfoActivity.this, ResponseErrorCode.getErrorMsg(result), null);
        }

        @Override
        public void onPostExecute(String result) {
            InputDoctorInfoResponse response = GsonUtil.getInstance().fromJson(result,InputDoctorInfoResponse.class);
            if ( 1 == response.getResult().getIsSuccess() ){
                MyDialog.showPromptDialog(InputDoctorInfoActivity.this, getString(R.string.dialog_content_wait_audit), new MyDialog.PromptDialogCallback() {
                    @Override
                    public void sure() {
                        BaseApplication.getInstance().clearHeadFile();
                        goLoginActivity();
                    }
                });
            } else {
                MyDialog.showPromptDialog(InputDoctorInfoActivity.this, getString(R.string.dialog_content_submit_error), null);
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK ) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goLoginActivity(){
        startActivity(LoginActivity.class);
        finish();
    }

    private void back(){
        startActivity(LoginActivity.class);
        finish();
    }

}

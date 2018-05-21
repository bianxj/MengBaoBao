package com.doumengmeng.customer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseActivity;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.config.Constants;
import com.doumengmeng.customer.db.DaoManager;
import com.doumengmeng.customer.entity.RoleType;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.response.AliPayResponse;
import com.doumengmeng.customer.response.entity.Doctor;
import com.doumengmeng.customer.response.entity.Hospital;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.AppUtil;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.util.MyDialog;
import com.doumengmeng.customer.util.PermissionUtil;
import com.doumengmeng.customer.view.CircleImageView;
import com.doumengmeng.customer.view.PayItemLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 医生详情界面
 * 创建日期: 2018/1/23 15:23
 */
public class DoctorInfoActivity extends BaseActivity {

    public final static String IN_PARAM_DOCTOR_ID = "doctor_id";
    public final static String IN_PARAM_DOCTOR_NAME = "doctor_name";

    private RelativeLayout rl_parent;
    private RelativeLayout rl_back;
    private TextView tv_title;
    private CircleImageView civ_head;
    private TextView tv_doctor_name , tv_doctor_position , tv_hospital;
    private TextView tv_doctor_introduce , tv_doctor_skill;
    private Button bt_choose;

    private Doctor doctor;
    private Hospital hospital;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        findView();
        getNeedInfo();
        registerIWXApi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterIWXApi();
        stopTask(preAliPayTask);
        stopTask(preIwxPayTask);
        if ( handler != null ){
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, new PermissionUtil.RequestPermissionSuccess() {
            @Override
            public void success(String permission) {
                choose();
            }

            @Override
            public void denied(String permission) {

            }

            @Override
            public void alwaysDenied(String permission) {
                String prompt = null;
                if ( Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission) ){
                    prompt = getResources().getString(R.string.dialog_content_storage_permission);
                }
                if ( Manifest.permission.READ_PHONE_STATE.equals(permission) ){
                    prompt = getResources().getString(R.string.dialog_content_read_phone_permission);
                }
                MyDialog.showPermissionDialog(DoctorInfoActivity.this, prompt, new MyDialog.ChooseDialogCallback() {
                    @Override
                    public void sure() {
                        AppUtil.openPrimession(DoctorInfoActivity.this);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        });
    }

    private void getNeedInfo(){
            Intent intent = getIntent();
            String doctorId = intent.getStringExtra(IN_PARAM_DOCTOR_ID);
            if (TextUtils.isEmpty(doctorId) ){
                String doctorName = intent.getStringExtra(IN_PARAM_DOCTOR_NAME);
                doctor = DaoManager.getInstance().getDaotorDao().searchDoctorByName(this, doctorName);
            } else {
                doctor = DaoManager.getInstance().getDaotorDao().searchDoctorById(this, doctorId);
            }
            if (doctor != null) {
                hospital = DaoManager.getInstance().getHospitalDao().searchHospitalById(this, doctor.getHospitalid());
                initView();
            } else {
                MyDialog.showPromptDialog(this, "查无此人", new MyDialog.PromptDialogCallback() {
                    @Override
                    public void sure() {
                        back();
                    }
                });
            }
    }

    private void findView(){
        rl_parent = findViewById(R.id.rl_parent);
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        civ_head = findViewById(R.id.civ_head);
        tv_doctor_name = findViewById(R.id.tv_doctor_name);
        tv_doctor_position = findViewById(R.id.tv_doctor_position);
        tv_hospital = findViewById(R.id.tv_hospital);
        tv_doctor_introduce = findViewById(R.id.tv_doctor_introduce);
        tv_doctor_skill = findViewById(R.id.tv_doctor_skill);
        bt_choose = findViewById(R.id.bt_choose);
    }

    private void initView(){
        if ( BaseApplication.getInstance().isUpperThan37Month() ){
            bt_choose.setVisibility(View.GONE);
        } else {
            bt_choose.setVisibility(View.VISIBLE);
        }

        rl_back.setOnClickListener(listener);
        bt_choose.setOnClickListener(listener);
        tv_title.setText(R.string.doctor_info_introduce);

        tv_doctor_name.setText(doctor.getDoctorname());
        tv_doctor_position.setText(doctor.getPositionaltitles());
        tv_doctor_introduce.setText(doctor.getDoctordesc());
        tv_doctor_skill.setText(doctor.getSpeciality());
        tv_hospital.setText(hospital.getHospitalname());
        ImageLoader.getInstance().displayImage(doctor.getDoctorImageUrl(),civ_head,initDisplayImageOptions());
    }

    private DisplayImageOptions initDisplayImageOptions(){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        builder.showImageOnFail(R.drawable.default_icon_doctor);
        builder.showImageForEmptyUri(R.drawable.default_icon_doctor);
        builder.showImageOnLoading(R.drawable.default_icon_doctor);
        return builder.build();
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_choose:
                    choose();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void choose(){
        if (PermissionUtil.checkPermissionAndRequest(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            List<PayItemLayout.PayData> datas = new ArrayList<>();
            datas.add(new PayItemLayout.PayData(R.drawable.alipay_logo_60,"支付宝",true));
            if (api.isWXAppInstalled()) {
                datas.add(new PayItemLayout.PayData(R.drawable.iwx_logo, "微信", false));
            }
            if (PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.READ_PHONE_STATE)){
                MyDialog.showPayDialog(this, rl_parent, new MyDialog.PayCallBack() {
                    @Override
                    public void alipay() {
                        preAliPay();
                    }

                    @Override
                    public void iwxpay() {
                        preIwxPay();
                    }
                },datas,doctor.getCost(),15*60);
            }
        }
    }

//    private void showAppPermissionDialog(int strings){
//        MyDialog.showPermissionDialog(this, getString(strings), new MyDialog.ChooseDialogCallback() {
//            @Override
//            public void sure() {
//                AppUtil.openPrimession(DoctorInfoActivity.this);
//            }
//
//            @Override
//            public void cancel() {
//                back();
//            }
//        });
//    }

    /**
     * 作者: 边贤君
     * 描述: 跳转至下一个界面
     * 日期: 2018/1/19 14:30
     */
    private void goNext(){
        BaseApplication.getInstance().addRecordTimes();
        if (RoleType.FREE_NET_USER == BaseApplication.getInstance().getRoleType() ){
            startActivity(InputInfoActivity.class);
        } else {
            startActivity(RecordActivity.class);
        }
        BaseApplication.getInstance().payRoleType();
    }

    //-----------------------------------支付------------------------------------------------

    //-----------------------------------支付宝----------------------------------------------
    private RequestTask preAliPayTask = null;

    private void preAliPay(){
        try {
            preAliPayTask = new RequestTask.Builder(this, preAliPayCallBack)
                    .setUrl(UrlAddressList.URL_PRE_ALI_PAY)
                    .setType(RequestTask.DEFAULT)
                    .setContent(buildPreAliPayContent())
                    .build();
            preAliPayTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, String> buildPreAliPayContent() {
        UserData userData = BaseApplication.getInstance().getUserData();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userData.getUserid());
            object.put("accountmobile",userData.getAccountmobile());
            object.put("orderdevice","1");
            object.put("doctorid",doctor.getDoctorid());
//            object.put("totalamout",doctor.getCost());
            object.put("totalamout","0.01");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        return map;
    }

    private final RequestCallBack preAliPayCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            AliPayResponse response = GsonUtil.getInstance().fromJson(result, AliPayResponse.class);
            aliPay(response.getResult().getaLiPayBody());
        }
    };

    private static class AlipayHandler extends Handler{
        private final static int MESSAGE_ALI_PAY = 0x01;
        private final WeakReference<DoctorInfoActivity> weakReference;
        private Map<String,String> aliResult = null;

        public AlipayHandler(DoctorInfoActivity activity) {
            weakReference = new WeakReference<DoctorInfoActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_ALI_PAY: {
                    aliResult = (Map<String, String>) msg.obj;
                    if ( TextUtils.equals("9000", aliResult.get("resultStatus")) ){
                        weakReference.get().goNext();
                    } else {
                        Toast.makeText(weakReference.get(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    }

    private AlipayHandler handler = null;

    private void aliPay(final String orderInfo){
        if ( handler == null ){
            handler = new AlipayHandler(this);
        }
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                PayTask alipay = new PayTask(DoctorInfoActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = AlipayHandler.MESSAGE_ALI_PAY;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    //-----------------------------------微信支付---------------------------------------------------

    private IWXAPI api;
    private PayBroadcastReceiver receiver;

    private void registerIWXApi(){
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(Constants.WX_APP_ID);
        receiver = new PayBroadcastReceiver(this);
        registerReceiver(receiver,new IntentFilter(PayBroadcastReceiver.ACTION_IWX_PAY_RESULT));
    }

    private void unregisterIWXApi(){
        api.unregisterApp();
        unregisterReceiver(receiver);
    }

    private RequestTask preIwxPayTask;
    private void preIwxPay(){
        try {
            preIwxPayTask = new RequestTask.Builder(this,preIwxPayCallBack)
                    .setUrl(UrlAddressList.URL_PRE_IWX_PAY)
                    .setType(RequestTask.JSON|RequestTask.PROMPT)
                    .setContent(buildPreIwxPayContent())
                    .build();
            preIwxPayTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, String> buildPreIwxPayContent() {
        UserData userData = BaseApplication.getInstance().getUserData();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userData.getUserid());
            object.put("accountmobile",userData.getAccountmobile());
            object.put("orderdevice","1");
            object.put("doctorid",doctor.getDoctorid());
//            object.put("totalamout",Integer.parseInt(doctor.getCost())*100+"");
            object.put("totalamout","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        return map;
    }

    private final RequestCallBack preIwxPayCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                iwxPay(res.getString("wxPayBody"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
//        {"appid":"wxd0b4d1dd8772805a","partnerid":"1498861492","prepayid":"wx20180209150413137f6d69e90053885330","package":"Sign=WXPay","noncestr":"3aaa3db6a8983226601cac5dde15a26b","timestamp":"1518159854","sign":"AD61CD62A988253F69DEEB80F532F6C1"}
        }
    };

    public final static int REQUEST_WX = 0x01;
    private void iwxPay(final String result){
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
        api.registerApp(Constants.WX_APP_ID);
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            PayReq request = new PayReq();
            request.appId = Constants.WX_APP_ID;
            request.partnerId = object.getString("partnerid");
            request.prepayId= object.getString("prepayid");
            request.packageValue = object.getString("package");
            request.nonceStr= object.getString("noncestr");
            request.timeStamp= object.getString("timestamp");
            request.sign= object.getString("sign");
            api.sendReq(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_WX ){
            if ( resultCode == Activity.RESULT_OK){
                goNext();
            } else {
                Toast.makeText(DoctorInfoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class PayBroadcastReceiver extends BroadcastReceiver{
        public final static String ACTION_IWX_PAY_RESULT = "iwx_pay_result";
        public final static String PARAM_RESULT_CODE = "result_code";
        private WeakReference<DoctorInfoActivity> weakReference;

        public PayBroadcastReceiver(DoctorInfoActivity activity) {
            weakReference = new WeakReference<DoctorInfoActivity>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if ( ACTION_IWX_PAY_RESULT.equals(intent.getAction()) ){
                int resultCode = intent.getIntExtra(PARAM_RESULT_CODE,-1);
                if ( 0 == resultCode ){
                    weakReference.get().goNext();
                } else {
                    Toast.makeText(weakReference.get(), "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

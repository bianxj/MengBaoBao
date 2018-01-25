package com.doumengmengandroidbady.activity;

import android.Manifest;
import android.content.Intent;
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
import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.response.Doctor;
import com.doumengmengandroidbady.response.Hospital;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.AppUtil;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.util.PermissionUtil;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 医生详情界面
 * 创建日期: 2018/1/23 15:23
 */
public class DoctorInfoActivity extends BaseActivity {

    private final static boolean isTest = false;

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
        getNeedInfo();
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(aliPayTask);
        stopTask(aliPayResponseTask);
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
                    prompt = getResources().getString(R.string.storage_permission);
                }
                if ( Manifest.permission.READ_PHONE_STATE.equals(permission) ){
                    prompt = getResources().getString(R.string.read_phone_permission);
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
        if (isTest){
            doctor = new Doctor();
            doctor.setDoctorname("Name1");
            doctor.setDoctorimg("http://www.qqzhi.com/uploadpic/2015-01-22/022222987.jpg");
            doctor.setPositionaltitles("Position1");
            doctor.setDoctordesc("Describe1");
            doctor.setSpeciality("Skill");

            hospital = new Hospital();
            hospital.setHospitalname("Hospital1");
        } else {
            Intent intent = getIntent();
            String doctorId = intent.getStringExtra(IN_PARAM_DOCTOR_ID);
            if (TextUtils.isEmpty(doctorId) ){
                String doctorName = intent.getStringExtra(IN_PARAM_DOCTOR_NAME);
                doctor = DaoManager.getInstance().getDaotorDao().searchDoctorByName(this, doctorName);
            } else {
                doctor = DaoManager.getInstance().getDaotorDao().searchDoctorById(this, doctorId);
            }
            if (doctor != null) {
                hospital = DaoManager.getInstance().getHospitalDao().searchHospitalById(this, doctor.getDoctorid());
            }
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

        initView();
    }

    private void initView(){
        if ( BaseApplication.getInstance().isUpperThan37Month() ){
            bt_choose.setVisibility(View.GONE);
        } else {
            bt_choose.setVisibility(View.VISIBLE);
        }

        rl_back.setOnClickListener(listener);
        bt_choose.setOnClickListener(listener);
        tv_title.setText(R.string.introduce);

        tv_doctor_name.setText(doctor.getDoctorname());
        tv_doctor_position.setText(doctor.getPositionaltitles());
        tv_doctor_introduce.setText(doctor.getDoctordesc());
        tv_doctor_skill.setText(doctor.getSpeciality());
        tv_hospital.setText(hospital.getHospitalname());
        ImageLoader.getInstance().displayImage(doctor.getDoctorimg(),civ_head,initDisplayImageOptions());
    }

    private DisplayImageOptions initDisplayImageOptions(){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        builder.showImageOnFail(R.drawable.default_icon_doctor);
        builder.showImageForEmptyUri(R.drawable.default_icon_doctor);
        builder.showImageOnLoading(R.drawable.default_icon_doctor);
        return builder.build();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
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
            if (PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.READ_PHONE_STATE)){
                MyDialog.showPayDialog(this, rl_parent, new MyDialog.PayCallBack() {
                    @Override
                    public void alipay() {
                        aliPay();
                    }

                    @Override
                    public void iwxpay() {

                    }
                },doctor.getCost(),15*60);
            }
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 跳转至下一个界面
     * 日期: 2018/1/19 14:30
     */
    private void goNext(){
        BaseApplication.getInstance().addRecordTimes();
        if ( BaseApplication.getInstance().isPay() ){
            startActivity(RecordActivity.class);
        } else {
            startActivity(InputInfoActivity.class);
        }
    }

    //-----------------------------------支付------------------------------------------------

    private RequestTask aliPayTask = null;
    private RequestTask aliPayResponseTask = null;

    private void aliPay(){
        try {
            aliPayTask = new RequestTask.Builder(this,aliPayCallBack).build();
            aliPayTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestCallBack aliPayCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_ALI_PAY;
        }

        @Override
        public Map<String, String> getContent() {
            UserData userData = BaseApplication.getInstance().getUserData();
            JSONObject object = new JSONObject();
            try {
                object.put("userId",userData.getUserid());
//                object.put("totalamout",doctor.getCost());
                object.put("totalamout","0.01");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Map<String,String> map = new HashMap<>();
            map.put(UrlAddressList.PARAM,object.toString());
            map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
            return map;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            System.out.println(result);
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                //调用支付宝支付
                alipay(res.getString("aLiPayBody"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int type() {
            return DEFAULT;
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 支付宝成功回调
     * 日期: 2018/1/23 16:10
     */
    private void alipayResponse(Map<String,String> map){
        try {
            aliResult = map;
            aliPayResponseTask = new RequestTask.Builder(this,aliPayResponseCallBack).build();
            aliPayResponseTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestCallBack aliPayResponseCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_ALI_PAY_RESPONCE;
        }

        @Override
        public Map<String, String> getContent() {
            UserData userData = BaseApplication.getInstance().getUserData();
            Map<String,String> map = new HashMap<>();
            JSONObject object = new JSONObject();
            try {
                object.put("accountmobile",userData.getAccountmobile());
                object.put("userId",userData.getUserid());
                object.put("doctorid",doctor.getDoctorid());
                object.put("orderdevice","1");

                JSONObject result = new JSONObject(aliResult.get("result"));
                JSONObject response = result.getJSONObject("alipay_trade_app_pay_response");

                JSONObject aLiInfo = new JSONObject();
                aLiInfo.put("tradeno",response.getString("trade_no"));
                aLiInfo.put("outtradeno",response.getString("out_trade_no"));
                aLiInfo.put("totalamount",response.getString("total_amount"));

                object.put("aLiInfo",aLiInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put(UrlAddressList.PARAM,object.toString());
            map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getUserData().getSessionId());
            return map;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                int isSuccess = res.getInt("isSuccess");
                if ( isSuccess == 1 ){
                    goNext();
                } else {
                    Toast.makeText(DoctorInfoActivity.this,"支付失败",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int type() {
            return DEFAULT;
        }
    };

    private static class AlipayHandler extends Handler{
        private final static int MESSAGE_ALI_PAY = 0x01;
        private WeakReference<DoctorInfoActivity> weakReference;
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
                        weakReference.get().alipayResponse(aliResult);
                    } else {
                        Toast.makeText(weakReference.get(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }

        public Map<String, String> getAliResult() {
            return aliResult;
        }
    }

    private Map<String,String> aliResult = null;
    private AlipayHandler handler = null;

    private void alipay(final String orderInfo){
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
    private final static String APP_ID = "";
    private void iwxPay(){
        IWXAPI api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.handleIntent(getIntent(),eventHandler);

        PayReq request = new PayReq();
        request.appId = "wxd930ea5d5a258f4f";
        request.partnerId = "1900000109";
        request.prepayId= "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr= "1101000000140429eb40476f8896f4c9";
        request.timeStamp= "1398746574";
        request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        api.sendReq(request);
    }

    private IWXAPIEventHandler eventHandler = new IWXAPIEventHandler() {
        @Override
        public void onReq(BaseReq baseReq) {
        }

        @Override
        public void onResp(BaseResp resp) {
            if(resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
                if( "0".equals(resp.errCode) ){
                    Toast.makeText(DoctorInfoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DoctorInfoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

}

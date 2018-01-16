package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.doumengmengandroidbady.response.Doctor;
import com.doumengmengandroidbady.response.Hospital;
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

import java.util.Map;

/**
 * Created by Administrator on 2017/12/12.
 */

public class DoctorInfoActivity extends BaseActivity {

    private final static boolean isTest = false;

    public final static String IN_PARAM_DOCTOR_ID = "doctor_id";
    public final static String IN_PARAM_DOCTOR_NAME = "doctor_name";

//    private DoctorEntity doctor;

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
        //TODO
        startActivity(InputInfoActivity.class);
    }

    //-----------------------------------支付------------------------------------------------

    private final static int MESSAGE_ALI_PAY = 0x01;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_ALI_PAY: {
                    Map<String,String> result = (Map<String, String>) msg.obj;
                    if ( TextUtils.equals("9000",result.get("resultStatus")) ){
                        Toast.makeText(DoctorInfoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DoctorInfoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    private void alipay(){
        final String orderInfo = null;   // 订单信息
//        /**
//         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//         *
//         * orderInfo的获取必须来自服务端；
//         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(DoctorInfoActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = MESSAGE_ALI_PAY;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


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

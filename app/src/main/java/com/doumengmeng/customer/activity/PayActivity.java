package com.doumengmeng.customer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseSwipeActivity;
import com.doumengmeng.customer.config.Constants;
import com.doumengmeng.customer.db.DaoManager;
import com.doumengmeng.customer.entity.RoleType;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.response.AliPayResponse;
import com.doumengmeng.customer.response.entity.Doctor;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.view.DiscountItemLayout;
import com.doumengmeng.customer.view.PayItemLayout;
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

public class PayActivity extends BaseSwipeActivity {

    public final static String IN_PARAM_DOCTOR_ID = "doctor_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        registerIWXApi();
        initView();
        timeHandler.sendEmptyMessage(KEEP_TIME);
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
        if ( timeHandler != null ){
            timeHandler.removeCallbacksAndMessages(null);
        }
    }

    private void initView(){
        initTitle();
        initContent();
    }

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title;
    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(getString(R.string.pay_title));
        rl_back.setOnClickListener(listener);
    }

    private TextView tv_lost_time;
    private DiscountItemLayout discount_layout;
    private PayItemLayout pay_layout;
    private Button bt_submit;
    private Doctor doctor;
    private void initContent(){
        tv_lost_time = findViewById(R.id.tv_lost_time);
        discount_layout = findViewById(R.id.discount_layout);
        pay_layout = findViewById(R.id.pay_layout);
        bt_submit = findViewById(R.id.bt_submit);

        bt_submit.setOnClickListener(listener);
        String doctorId = getIntent().getStringExtra(IN_PARAM_DOCTOR_ID);
        doctor = DaoManager.getInstance().getDaotorDao().searchDoctorById(this, doctorId);
        initDiscountItem();
        initPayFromItem();
    }

    private void initDiscountItem(){
        List<DiscountItemLayout.DiscountData> datas = new ArrayList<>();
        List<String> counts = doctor.getDiscounts();
        List<String> costs = doctor.getDiscountsCost();
        for (int i=0;i<counts.size();i++){
            datas.add(new DiscountItemLayout.DiscountData(counts.get(i),costs.get(i),doctor.getCost()));
        }
        discount_layout.addDiscounts(datas);
    }

    private void initPayFromItem() {
        List<PayItemLayout.PayData> datas = new ArrayList<>();
        datas.add(new PayItemLayout.PayData(R.drawable.alipay_logo_60, "支付宝", true));
        if (api.isWXAppInstalled()) {
            datas.add(new PayItemLayout.PayData(R.drawable.iwx_logo, "微信支付", false));
        }
        pay_layout.addItems(datas);
        pay_layout.getSelectPostion();
    }

    private void pay(){
        if ( checkPay() ){
            int chooseId = pay_layout.getSelectPostion();
            if ( chooseId != -1 ){
                if ( chooseId == 0 ){
                    preAliPay();
                } else {
                    preIwxPay();
                }
            }
        }

    }

    private boolean checkPay(){
        if ( discount_layout.getSelectIndex() == DiscountItemLayout.UNSELECT ){
            Toast.makeText(this,"请选择套餐",Toast.LENGTH_SHORT).show();
            return false;
        }

        if ( pay_layout.getSelectPostion() == PayItemLayout.UNSELECT ){
            Toast.makeText(this,"请选择支付渠道",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 作者: 边贤君
     * 描述: 跳转至下一个界面
     * 日期: 2018/1/19 14:30
     */
    private void goNext() {
        //TODO
        String count = doctor.getDiscounts().get(discount_layout.getSelectIndex());
        BaseApplication.getInstance().addRecordTimes(Integer.parseInt(count));
        bt_submit.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (RoleType.FREE_NET_USER == BaseApplication.getInstance().getRoleType()) {
                    startActivity(InputInfoActivity.class);
                } else {
                    startActivity(RecordActivity.class);
                }
                BaseApplication.getInstance().payRoleType();
            }
        }, 100);
    }

    protected void back(){
        finish();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_submit:
                    pay();
                    break;
            }
        }
    };

    private int time = 15 * 60;
    private final static int  KEEP_TIME = 0x01;
    private Handler timeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == KEEP_TIME ) {
                if (time <= 0) {
                    back();
                } else {
                    tv_lost_time.setText(String.format(getString(R.string.dialog_minute_second), time / 60, time % 60));
                    time--;
                    timeHandler.sendEmptyMessageDelayed(KEEP_TIME, 1000);
                }
            }
        }
    };

    private RequestTask preAliPayTask = null;

    //-----------------------------------支付宝----------------------------------------------
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
        int index = discount_layout.getSelectIndex();
        UserData userData = BaseApplication.getInstance().getUserData();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userData.getUserid());
            object.put("accountmobile",userData.getAccountmobile());
            object.put("orderdevice","1");
            object.put("doctorid",doctor.getDoctorid());
            object.put("totalamout",doctor.getDiscountsCost().get(index));
            object.put("numberOfTimes",doctor.getDiscounts().get(index));
            object.put("cost",doctor.getCost());
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

    private static class AlipayHandler extends Handler {
        private final static int MESSAGE_ALI_PAY = 0x01;
        private final WeakReference<PayActivity> weakReference;
        private Map<String,String> aliResult = null;

        public AlipayHandler(PayActivity activity) {
            weakReference = new WeakReference<PayActivity>(activity);
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

                PayTask alipay = new PayTask(PayActivity.this);
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
        int index = discount_layout.getSelectIndex();
        UserData userData = BaseApplication.getInstance().getUserData();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userData.getUserid());
            object.put("accountmobile",userData.getAccountmobile());
            object.put("orderdevice","1");
            object.put("doctorid",doctor.getDoctorid());
            object.put("totalamout",String.valueOf((int)(Float.parseFloat(doctor.getDiscountsCost().get(index))*100)));
            object.put("numberOfTimes",doctor.getDiscounts().get(index));
            object.put("cost",doctor.getCost());
//            object.put("totalamout","2");
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
                Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class PayBroadcastReceiver extends BroadcastReceiver {
        public final static String ACTION_IWX_PAY_RESULT = "iwx_pay_result";
        public final static String PARAM_RESULT_CODE = "result_code";
        private WeakReference<PayActivity> weakReference;

        public PayBroadcastReceiver(PayActivity activity) {
            weakReference = new WeakReference<PayActivity>(activity);
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

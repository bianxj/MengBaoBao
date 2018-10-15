package com.doumengmeng.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseSwipeActivity;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.response.ConfirmRefundResponse;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.util.MyDialog;
import com.doumengmeng.customer.view.CheckBoxLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 申请退款页面
 */
public class ApplyRefundActivity extends BaseSwipeActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_refund);
        initView();
        searchRefund();
    }

    private void initView(){
        initTitle();
        initContent();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_back.setOnClickListener(listener);
        tv_title.setText(getString(R.string.apply_refund_title));
    }

    private TextView tv_refund_content;
    private CheckBoxLayout cbl_refund_reason;
    private TextView tv_confirm;
    private void initContent(){
        tv_refund_content = findViewById(R.id.tv_refund_content);
        cbl_refund_reason = findViewById(R.id.cbl_refund_reason);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(listener);
        initRefundReason();
    }

    private void initRefundReason(){
        List<String> datas = Arrays.asList("买多了/买错了","医生未按约提供服务","效果不满意","有更优惠的活动","后悔了/不想要了","其他原因");
        cbl_refund_reason.setCheckBoxes(datas,true);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.tv_confirm:
                    confirmRefund();
                    break;
            }
        }
    };

    protected void back(){
        finish();
    }

    private RequestTask confirmRefundTask = null;
    private void confirmRefund(){
        if ( checkConfirmRefund() ) {
            try {
                confirmRefundTask = new RequestTask.Builder(this, confirmRefundCallback)
                        .setType(RequestTask.DEFAULT)
                        .setContent(buildConfirmFefund())
                        .setUrl(UrlAddressList.URL_CONFIRM_REFUND)
                        .build();
                confirmRefundTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkConfirmRefund(){
        if ( cbl_refund_reason.getFirstCheckBox() == null ){
            MyDialog.showPromptDialog(this,"请选择退款原因",null);
            return false;
        }
        return true;
    }

    private Map<String,String> buildConfirmFefund(){
        UserData userData = BaseApplication.getInstance().getUserData();
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userData.getUserid());
            object.put("refundReason",cbl_refund_reason.getFirstCheckBox().getText().toString());
            map.put(UrlAddressList.PARAM,object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        return  map;
    }

    private RequestCallBack confirmRefundCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            ConfirmRefundResponse response = GsonUtil.getInstance().fromJson(result,ConfirmRefundResponse.class);
            Intent intent = new Intent(ApplyRefundActivity.this,RefundResultActivity.class);
            intent.putExtra(RefundResultActivity.IN_PARAM_REFUND_DATA,GsonUtil.getInstance().toJson(response.getResult()));
            startActivity(intent);
            finish();
        }
    };

    private RequestTask searchRefundTask=null;
    private void searchRefund(){
        try {
            searchRefundTask = new RequestTask.Builder(this,searchRefundCallback)
                                .setType(RequestTask.DEFAULT)
                                .setContent(buildRefundData())
                                .setUrl(UrlAddressList.ULR_SEARCH_REFUND)
                                .build();
            searchRefundTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String,String> buildRefundData(){
        UserData userData = BaseApplication.getInstance().getUserData();
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userData.getUserid());
            map.put(UrlAddressList.PARAM,object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.SESSION_ID, userData.getSessionId());
        return map;
    }

    private RequestCallBack searchRefundCallback = new RequestCallBack() {
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
                JSONObject res = object.optJSONObject("result");
                int start;
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("您购买的是");
                start = builder.length();
                builder.append(res.optString("doctorName"));
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.first_blue)),start,builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.append("医生的");
                start = builder.length();
                builder.append(res.optString("totalCount"));
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.first_blue)),start,builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.append("次专家服务，已使用");
                start = builder.length();
                builder.append(res.optString("alreadyUsed"));
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.first_blue)),start,builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                builder.append("次，已按您的退款要求退还剩余次数的服务费用");

                tv_refund_content.setText(builder);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}

package com.doumengmeng.doctor.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.activity.AccountDetailActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseFragment;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.response.AccountResponse;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/26.
 */

public class AccountFragment extends BaseFragment {

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title,tv_complete;
    private RelativeLayout rl_details;
    private TextView tv_month_buy, tv_month_assessment,tv_total_buy,tv_total_assessment,tv_total_over_time,tv_month_income;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,null);
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchAccountData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( hidden ){
            stopTask(searchAccountTask);
        } else {
            searchAccountData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissRatesPrompt();
        stopTask(searchAccountTask);
    }

    private void findView(View view){
        initTitle(view);
        initAccountData(view);
    }

    private void initTitle(View view){
        rl_back = view.findViewById(R.id.rl_back);
        rl_complete = view.findViewById(R.id.rl_complete);
        tv_title = view.findViewById(R.id.tv_title);
        tv_complete = view.findViewById(R.id.tv_complete);

        rl_back.setVisibility(View.GONE);
        tv_title.setText(getString(R.string.account));
        rl_complete.setVisibility(View.VISIBLE);
        tv_complete.setText(getString(R.string.detail));

        rl_complete.setOnClickListener(listener);
    }

    private void initAccountData(View view){
        rl_details = view.findViewById(R.id.rl_details);
        tv_month_income = view.findViewById(R.id.tv_month_income);
        tv_month_buy = view.findViewById(R.id.tv_month_buy);
        tv_month_assessment = view.findViewById(R.id.tv_month_assessment);
        tv_total_buy = view.findViewById(R.id.tv_total_buy);
        tv_total_assessment = view.findViewById(R.id.tv_total_assessment);
        tv_total_over_time = view.findViewById(R.id.tv_total_over_time);

        //TODO
        rl_details.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if ( R.id.rl_complete == view.getId() ){
                goAccountDetailsActivity();
            }
            if ( R.id.rl_details == view.getId() ){
                showRatesPrompt();
            }
        }
    };

    private void goAccountDetailsActivity(){
        startActivity(AccountDetailActivity.class);
    }

    private Dialog ratesDialog = null;
    private void showRatesPrompt(){
        dismissRatesPrompt();
        ratesDialog = new Dialog(getContext(),R.style.MyDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_prompt_white,null);
        RelativeLayout rl_close = view.findViewById(R.id.rl_close);
        rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratesDialog.dismiss();
            }
        });
        ratesDialog.setContentView(view);
        ratesDialog.show();
    }

    private void dismissRatesPrompt(){
        if ( ratesDialog != null ){
            ratesDialog.dismiss();
            ratesDialog = null;
        }
    }

    private RequestTask searchAccountTask;

    public void searchAccountData(){
        try {
            searchAccountTask = new RequestTask.Builder(getContext(),searchAccountCallBack)
                    .setContent(buildRequestAccount())
                    .setType(RequestTask.DEFAULT)
                    .setUrl(UrlAddressList.URL_ACCOUNT_DATA)
                    .build();
            searchAccountTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public Map<String,String> buildRequestAccount(){
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("doctorId",BaseApplication.getInstance().getUserData().getDoctorId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID, BaseApplication.getInstance().getSessionId());
        return map;
    }

    private RequestCallBack searchAccountCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(getContext(), ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            AccountResponse response = GsonUtil.getInstance().fromJson(result, AccountResponse.class);
            refreshAccountData(response.getResult());
        }
    };


    private void refreshAccountData(AccountResponse.Result result){
        tv_month_income.setText(String.format(getString(R.string.format_money),result.getMonthPrice()));
        tv_month_assessment.setText(String.format(getString(R.string.format_person),result.getMonthEvaluation()));
        tv_month_buy.setText(String.format(getString(R.string.format_person),result.getPurchaseThisMonth()));
        tv_total_assessment.setText(String.format(getString(R.string.format_person),result.getAccumulativeEvaluation()));
        tv_total_buy.setText(String.format(getString(R.string.format_person),result.getAccumulativePurchase()));
        tv_total_over_time.setText(String.format(getString(R.string.format_person),result.getAllFail()));
    }

}

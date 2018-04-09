package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.AccountDetailAdapter;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.response.AccountDetailResponse;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/1.
 */

public class AccountDetailActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private XRecyclerView xrv;
    private AccountDetailAdapter adapter;
    private List<AccountDetailAdapter.AccountDetailData> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(searchAccountDetailTask);
    }

    private void findView(){
        initTitle();
        initListView();
        searchAccountDetail();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(getString(R.string.detail));
        rl_back.setOnClickListener(listener);
    }

    private void initListView(){
        xrv = findViewById(R.id.xrv);
        xrv.setLoadingMoreEnabled(true);
        xrv.setFootView(new XLoadMoreFooter(this));
        adapter = new AccountDetailAdapter(datas);
        xrv.setAdapter(adapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private RequestTask searchAccountDetailTask;

    private void searchAccountDetail(){
        try {
            searchAccountDetailTask = new RequestTask.Builder(this,searchAccountDetailCallBack)
                    .setContent(buildRequest())
                    .setType(RequestTask.DEFAULT)
                    .setUrl(UrlAddressList.URL_ACCOUNT_DETAIL)
                    .build();
            searchAccountDetailTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String,String> buildRequest(){
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

    private RequestCallBack searchAccountDetailCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
//            MyDialog.showPromptDialog(AccountDetailActivity.this, ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            AccountDetailResponse response = GsonUtil.getInstance().fromJson(result,AccountDetailResponse.class);
            refreshAccountData(response.getResult());
        }
    };

    private void refreshAccountData(AccountDetailResponse.Result result){
        List<AccountDetailResponse.Detail> details = result.getDetailedList();
        datas.clear();
        for (AccountDetailResponse.Detail detail:details){
            AccountDetailAdapter.AccountDetailData data = new AccountDetailAdapter.AccountDetailData();
            data.setDate(detail.getEvaluationtime().split(" ")[0]);
            data.setName(detail.getTruename());
            data.setCost(detail.getBuyprice());
            data.setTime(detail.getEvaluationtime().split(" ")[1]);
            datas.add(data);
        }
        adapter.notifyDataSetChanged();
    }

}

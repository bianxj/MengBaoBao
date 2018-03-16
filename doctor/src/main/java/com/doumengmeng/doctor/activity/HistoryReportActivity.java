package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.HistoryReportAdapter;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.response.AssessmentDetailResponse;
import com.doumengmeng.doctor.response.HistoryReportResponse;
import com.doumengmeng.doctor.response.entity.HistoryReport;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MyDialog;
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

public class HistoryReportActivity extends BaseActivity {

    public final static String IN_PARAM_USER_DATA = "in_user_data";
    public final static String IN_PARAM_DOCTOR_ID = "in_doctor_id";

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XRecyclerView xrv;

    private HistoryReportAdapter adapter;
    private List<HistoryReport> reports = new ArrayList<>();
    private AssessmentDetailResponse.User user;
    private String doctorId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_report);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(recordTask);
    }

    private void findView(){
        initTitle();
        initListView();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_back.setOnClickListener(listener);
        tv_title.setText(R.string.history_report);
    }

    private void initListView(){
        xrv = findViewById(R.id.xrv);
        xrv.setLoadingMoreEnabled(true);
        xrv.setFootView(new XLoadMoreFooter(this));

        user = GsonUtil.getInstance().fromJson(getIntent().getStringExtra(IN_PARAM_USER_DATA), AssessmentDetailResponse.User.class);
        doctorId = getIntent().getStringExtra(IN_PARAM_DOCTOR_ID);

        xrv.setLoadingListener(loadingListener);
        adapter = new HistoryReportAdapter(reports,user);
        xrv.setAdapter(adapter);

        searchHistoryReport();
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

    private RequestTask recordTask;
    private void searchHistoryReport(){
        try {
            recordTask = new RequestTask.Builder(this, searchHistoryReportCallBack)
                    .setUrl(UrlAddressList.URL_SEARCH_HISTORY_REPORT)
                    .setType(RequestTask.DEFAULT)
                    .setContent(buildHistoryReportRequest())
                    .build();
            recordTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, String> buildHistoryReportRequest() {
        Map<String ,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",user.getUserid());
            object.put("doctorId",doctorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID, BaseApplication.getInstance().getSessionId());
        return map;
    }

    private final RequestCallBack searchHistoryReportCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(HistoryReportActivity.this, ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            HistoryReportResponse response = GsonUtil.getInstance().fromJson(result, HistoryReportResponse.class);
            HistoryReportResponse.Result result1 = response.getResult();
            List<HistoryReport> list = result1.getRecordList();

            reports.clear();
            for (HistoryReport record : list) {
                record.setImageData(result1.getImgList());
                reports.add(record);
            }
            adapter.notifyDataSetChanged();
            xrv.setNoMore(true);
        }
    };

    private final XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {

        }
    };


}

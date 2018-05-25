package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.HospitalReportAdapter;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseLoadingListener;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.response.AssessmentDetailResponse;
import com.doumengmeng.doctor.response.HospitalReportResponse;
import com.doumengmeng.doctor.response.entity.HospitalReport;
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

public class HospitalReportActivity extends BaseActivity {

    public final static String IN_PARAM_USER_DATA = "in_user_data";
    public final static String IN_PARAM_DOCTOR_ID = "in_doctor_id";

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XRecyclerView xrv;

    private HospitalReportAdapter adapter;
    private List<HospitalReport> reports = new ArrayList<>();
    private AssessmentDetailResponse.User user;
    private String doctorId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_report);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(searchHospitalReportTask);
    }

    private void findView(){
        initTitle();
        initListView();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.hospital_report));

        rl_back.setOnClickListener(listener);
    }

    private BaseLoadingListener loadingListener;
    private void initListView(){
        xrv = findViewById(R.id.xrv);
        xrv.setLoadingMoreEnabled(true);
        xrv.setFootView(new XLoadMoreFooter(this));

        adapter = new HospitalReportAdapter(reports);
        xrv.setAdapter(adapter);

        if ( loadingListener == null ){
            loadingListener = new BaseLoadingListener(xrv);
        }
        xrv.setLoadingListener(loadingListener);

        user = GsonUtil.getInstance().fromJson(getIntent().getStringExtra(IN_PARAM_USER_DATA), AssessmentDetailResponse.User.class);
        doctorId = getIntent().getStringExtra(IN_PARAM_DOCTOR_ID);
        searchHospitalReport();
    }

    private RequestTask searchHospitalReportTask;
    private void searchHospitalReport(){
        try {
            searchHospitalReportTask = new RequestTask.Builder(this, searchHospitalReportCallBack)
                    .setUrl(UrlAddressList.URL_SEARCH_HOSPITAL_REPORT)
                    .setType(RequestTask.DEFAULT)
                    .setContent(buildSearchReportContent())
                    .build();
            searchHospitalReportTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String,String> buildSearchReportContent(){
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.SESSION_ID, BaseApplication.getInstance().getSessionId());
        JSONObject object = new JSONObject();
        try {
            object.put("userId",user.getUserid());
            object.put("doctorId",doctorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.PARAM,object.toString());
        return map;
    }

    private final RequestCallBack searchHospitalReportCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(HospitalReportActivity.this, ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            HospitalReportResponse response = GsonUtil.getInstance().fromJson(result,HospitalReportResponse.class);

            if ( null != response.getResult().getChildRecordList() ) {
                reports.addAll(response.getResult().getChildRecordList());
                for (HospitalReport report:reports){
                    report.setImageData(response.getResult().getImgList());
                }
            }
            adapter.notifyDataSetChanged();
        }
    };


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
}

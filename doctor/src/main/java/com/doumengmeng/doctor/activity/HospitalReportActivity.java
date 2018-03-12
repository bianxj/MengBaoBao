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
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.response.HospitalReportResponse;
import com.doumengmeng.doctor.response.entity.HospitalReport;
import com.doumengmeng.doctor.util.GsonUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/1.
 */

public class HospitalReportActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XRecyclerView xrv;

    private HospitalReportAdapter adapter;
    private List<HospitalReport> reports = new ArrayList<>();

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

    private void initListView(){
        xrv = findViewById(R.id.xrv);
        adapter = new HospitalReportAdapter(reports);
        xrv.setAdapter(adapter);

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
//        UserData userData = BaseApplication.getInstance().getUserData();
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.SESSION_ID, BaseApplication.getInstance().getSessionId());
//        map.put(UrlAddressList.PARAM,userData.getUserid());
        return map;
    }

    private final RequestCallBack searchHospitalReportCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            HospitalReportResponse response = GsonUtil.getInstance().fromJson(result,HospitalReportResponse.class);

            xrv.setNoMore(true);
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

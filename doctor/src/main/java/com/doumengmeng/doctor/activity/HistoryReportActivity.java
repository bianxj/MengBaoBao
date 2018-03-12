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
import com.doumengmeng.doctor.response.HistoryReportResponse;
import com.doumengmeng.doctor.response.entity.HistoryReport;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/1.
 */

public class HistoryReportActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XRecyclerView xrv;

    private HistoryReportAdapter adapter;
    private List<HistoryReport> reports;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_report);
        findView();
    }

    private void findView(){
        initTitle();
        initListView();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_back.setOnClickListener(listener);
    }

    private void initListView(){
        xrv = findViewById(R.id.xrv);
        xrv.setLoadingMoreEnabled(true);
        xrv.setFootView(new XLoadMoreFooter(this));

        xrv.setLoadingListener(loadingListener);
        adapter = new HistoryReportAdapter(reports);
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
                    .setUrl(UrlAddressList.URL_GET_HISTORY_REPORT)
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
//        map.put(UrlAddressList.PARAM,userData.getUserid());
        map.put(UrlAddressList.SESSION_ID, BaseApplication.getInstance().getSessionId());
        return map;
    }

    private final RequestCallBack searchHistoryReportCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

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

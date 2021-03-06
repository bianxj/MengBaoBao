package com.doumengmeng.customer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.adapter.HospitalReportAdapter;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseFragment;
import com.doumengmeng.customer.base.BaseLoadingListener;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.response.HospitalReportResponse;
import com.doumengmeng.customer.response.entity.HospitalReport;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/11.
 */

public class HospitalReportFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XRecyclerView xrv_report;
    private List<HospitalReport> reports;
    private HospitalReportAdapter hospitalReportAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_hospital_report,null);
        findView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopTask(searchHospitalRecordTask);
    }

    @Override
    public void onResume() {
        super.onResume();
        if ( isVisible() && !isHidden() ) {
            initView();
            MobclickAgent.onPageStart(getClass().getSimpleName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if ( isVisible() && !isHidden() ) {
            MobclickAgent.onPageEnd(getClass().getSimpleName());
        }
    }

    private void findView(View view){
        xrv_report = view.findViewById(R.id.xrv_report);
        rl_back = view.findViewById(R.id.rl_back);
        tv_title = view.findViewById(R.id.tv_title);
    }

    private BaseLoadingListener loadingListener;
    private void initView(){
        rl_back.setVisibility(View.GONE);
        tv_title.setText(R.string.hospital_report);

        reports = new ArrayList<>();

        xrv_report.setLoadingMoreEnabled(true);
        xrv_report.setFootView(new XLoadMoreFooter(getContext()));

        if ( loadingListener == null ){
            loadingListener = new BaseLoadingListener(xrv_report);
        }

        xrv_report.setLoadingListener(loadingListener);
        hospitalReportAdapter = new HospitalReportAdapter(reports);
        xrv_report.setAdapter(hospitalReportAdapter);
        xrv_report.setLayoutManager(new LinearLayoutManager(getContext()));
        hospitalReportAdapter.notifyDataSetChanged();
        searchHospitalRecord();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){
            initView();
        } else {
            stopTask(searchHospitalRecordTask);
        }
    }

    private RequestTask searchHospitalRecordTask;
    private void searchHospitalRecord(){
        try {
            searchHospitalRecordTask = new RequestTask.Builder(getContext(),searchHospitalRecordCallBack)
                    .setUrl(UrlAddressList.URL_SEARCH_HOSPITAL_RECODR)
                    .setType(RequestTask.DEFAULT)
                    .setContent(buildSearchReportContent())
                    .build();
            searchHospitalRecordTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String,String> buildSearchReportContent(){
        UserData userData = BaseApplication.getInstance().getUserData();
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        map.put(UrlAddressList.PARAM,userData.getUserid());
        return map;
    }

    private final RequestCallBack searchHospitalRecordCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            HospitalReportResponse response = GsonUtil.getInstance().fromJson(result,HospitalReportResponse.class);

//            xrv_report.setNoMore(true);
            if ( null != response.getResult().getChildRecordList() ) {
                reports.addAll(response.getResult().getChildRecordList());
                for (HospitalReport report:reports){
                    report.setImageData(response.getResult().getImgList());
                }
            }
            hospitalReportAdapter.notifyDataSetChanged();
        }
    };

}

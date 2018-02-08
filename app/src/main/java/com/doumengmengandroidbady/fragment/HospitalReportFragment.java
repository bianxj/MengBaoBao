package com.doumengmengandroidbady.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.HospitalReportAdapter;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.base.BaseFragment;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.response.HospitalReportResponse;
import com.doumengmengandroidbady.response.entity.HospitalReport;
import com.doumengmengandroidbady.response.entity.UserData;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
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
        initView();
        MobclickAgent.onPageStart(getClass().getSimpleName());
        MobclickAgent.onResume(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    private void findView(View view){
        xrv_report = view.findViewById(R.id.xrv_report);
        rl_back = view.findViewById(R.id.rl_back);
        tv_title = view.findViewById(R.id.tv_title);
    }

    private void initView(){
        rl_back.setVisibility(View.GONE);
        tv_title.setText(R.string.hospital_report);

        reports = new ArrayList<>();

        xrv_report.setLoadingMoreEnabled(true);
        xrv_report.setFootView(new XLoadMoreFooter(getContext()));

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

    private final XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    xrv_report.setNoMore(true);
                }
            },2000);
        }
    };

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

            xrv_report.setNoMore(true);
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

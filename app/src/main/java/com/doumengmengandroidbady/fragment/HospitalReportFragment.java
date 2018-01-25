package com.doumengmengandroidbady.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.HospitalReportAdapter;
import com.doumengmengandroidbady.base.BaseFragment;
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.entity.HospitalReport;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class HospitalReportFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private XRecyclerView xrv_report;
    private List<HospitalReport> reports;
    private HospitalReportAdapter hospitalReportAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_report,null);
        findView(view);
        return view;
    }

    private void findView(View view){
        xrv_report = view.findViewById(R.id.xrv_report);
        rl_back = view.findViewById(R.id.rl_back);
        tv_title = view.findViewById(R.id.tv_title);
        initView();
    }

    private void initView(){
        rl_back.setVisibility(View.GONE);
        tv_title.setText(R.string.hospital_report);

        reports = new ArrayList<>();
        if (Config.isTest){
            for (int i = 0; i <10 ; i++) {
                HospitalReport report = new HospitalReport();
                report.setBmi("正常范围");
                report.setWeight("正常范围");
                report.setHeight("正常范围");
                report.setDevelop("正常范围");
                report.setHospitalName("医院"+i);
                report.setMonthAge("月龄"+i);
                report.setReportTime("时间"+i);
                reports.add(report);
            }
        }

        xrv_report.setLoadingMoreEnabled(true);
        xrv_report.setFootView(new XLoadMoreFooter(getContext()));

        xrv_report.setLoadingListener(loadingListener);
        hospitalReportAdapter = new HospitalReportAdapter(reports);
        xrv_report.setAdapter(hospitalReportAdapter);
        xrv_report.setLayoutManager(new LinearLayoutManager(getContext()));
        hospitalReportAdapter.notifyDataSetChanged();
    }

    private XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
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

}

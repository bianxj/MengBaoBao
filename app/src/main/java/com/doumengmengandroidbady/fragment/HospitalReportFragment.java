package com.doumengmengandroidbady.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.HeadAndFootAdapter;
import com.doumengmengandroidbady.adapter.HospitalReportAdapter;
import com.doumengmengandroidbady.base.BaseFragment;
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.entity.HospitalReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class HospitalReportFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private RecyclerView rv_report;
    private HeadAndFootAdapter adapter;
    private List<HospitalReport> reports;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_report,null);
        findView(view);
        return view;
    }

    private void findView(View view){
        rv_report = view.findViewById(R.id.rv_report);
        rl_back = view.findViewById(R.id.rl_back);
        tv_title = view.findViewById(R.id.tv_title);
        initView();
    }

    private void initView(){
        rl_back.setVisibility(View.GONE);
        tv_title.setText(R.string.hospital_report);

        reports = new ArrayList<HospitalReport>();
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
        rv_report.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HeadAndFootAdapter(new HospitalReportAdapter(reports));
        rv_report.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}

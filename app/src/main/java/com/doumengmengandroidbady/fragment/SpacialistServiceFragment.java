package com.doumengmengandroidbady.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.SpacialistServiceActivity;
import com.doumengmengandroidbady.activity.SupplementRecordActivity;
import com.doumengmengandroidbady.adapter.RecordAdapter;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.base.BaseFragment;
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.entity.Record;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.view.CircleImageView;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/11.
 */

public class SpacialistServiceFragment extends BaseFragment {

    private LinearLayout ll_buy;
    private RelativeLayout rl_supplement_record;
    private TextView tv_baby_name,tv_appraisal_count,tv_buy;
    private CircleImageView cim_baby;
    private RelativeLayout rl_add_record;
    private XRecyclerView xrv_record;

    private RelativeLayout rl_unbuy;
    private Button bt_buy;

    private List<Record> records = new ArrayList<>();
    private RecordAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spacialist_service,null);
        findView(view);
        getRecord();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void findView(View view){
        ll_buy = view.findViewById(R.id.ll_buy);
        rl_supplement_record = view.findViewById(R.id.rl_supplement_record);
        tv_baby_name = view.findViewById(R.id.tv_baby_name);
        tv_appraisal_count = view.findViewById(R.id.tv_appraisal_count);
        tv_buy = view.findViewById(R.id.tv_buy);
        cim_baby = view.findViewById(R.id.cim_baby);
        rl_add_record = view.findViewById(R.id.rl_add_record);
        xrv_record = view.findViewById(R.id.xrv_record);

        rl_unbuy = view.findViewById(R.id.rl_unbuy);
        bt_buy = view.findViewById(R.id.bt_buy);
        initView();
        initRecyclerView();
    }

    private void initView(){
        if (BaseApplication.getInstance().isPay()){
            ll_buy.setVisibility(View.VISIBLE);
            rl_unbuy.setVisibility(View.GONE);

            rl_supplement_record.setOnClickListener(listener);
            tv_buy.setOnClickListener(listener);
            rl_add_record.setOnClickListener(listener);
        } else {
            ll_buy.setVisibility(View.GONE);
            rl_unbuy.setVisibility(View.VISIBLE);

            bt_buy.setOnClickListener(listener);
        }
    }

    private void initRecyclerView(){
        xrv_record.setLoadingMoreEnabled(true);
        xrv_record.setFootView(new XLoadMoreFooter(getContext()));

        xrv_record.setLoadingListener(loadingListener);
        adapter = new RecordAdapter(records);
        xrv_record.setAdapter(adapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_buy:
                    startActivity(SpacialistServiceActivity.class);
                    break;
                case R.id.rl_supplement_record:
                    startActivity(SupplementRecordActivity.class);
                    break;
                case R.id.tv_buy:
                    startActivity(SpacialistServiceActivity.class);
                    break;
                case R.id.rl_add_record:
                    //TODO
                    break;
            }
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){
            initView();
        }
    }

    private void getRecord(){
        try {
            buildRecordTask().execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestTask buildRecordTask() throws Throwable {
        return new RequestTask.Builder(getRecordCallBack).build();
    }

    private RequestCallBack getRecordCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return null;
        }

        @Override
        public Context getContext() {
            return null;
        }

        @Override
        public Map<String, String> getContent() {
            return null;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            if (Config.isTest){
                for (int i = 0; i < 10 ; i++) {
                    Record record = new Record();
                    record.setBabyMonth("BabyMonth"+i);
                    record.setBmi("BMI"+i);
                    record.setDevelop("Develop"+i);
                    record.setHeight("Height"+i);
                    record.setReadState("State"+i);
                    record.setRecordDate("RecordDate"+i);
                    record.setWeight("Weight"+i);
                    records.add(record);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public String type() {
            return JSON;
        }
    };

    private XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {

        }
    };

}

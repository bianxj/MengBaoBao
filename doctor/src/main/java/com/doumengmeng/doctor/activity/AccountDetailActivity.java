package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.AccountDetailAdapter;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class AccountDetailActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private XRecyclerView xrv;
    private AccountDetailAdapter adapter;
    private List<AccountDetailAdapter.AccountDetailData> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        findView();
    }

    private void findView(){
        initTitle();
        initListView();
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
//        xrv.setLoadingListener(searchLoadingListener);

        datas = new ArrayList<>();
        for (int i = 0; i < 20 ; i++){
            AccountDetailAdapter.AccountDetailData data = new AccountDetailAdapter.AccountDetailData();
            data.setCost("Cost"+1);
            data.setDate("Date"+1);
            data.setTime("Time"+1);
            data.setName("Name"+1);
            datas.add(data);
        }
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

}

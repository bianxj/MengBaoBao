package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.AccountDetailAdapter;
import com.doumengmeng.doctor.base.BaseActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by Administrator on 2018/3/1.
 */

public class AccountDetailActivity extends BaseActivity {

    private RelativeLayout rl_back;

    private XRecyclerView xrv;
    private AccountDetailAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);

        rl_back.setOnClickListener(listener);

        xrv = findViewById(R.id.xrv);
        initView();
    }

    private void initView(){

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

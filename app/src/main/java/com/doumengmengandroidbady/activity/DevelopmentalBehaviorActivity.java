package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.BehaviorAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by Administrator on 2018/1/4.
 */

public class DevelopmentalBehaviorActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private RelativeLayout rl_complete;
    private TextView tv_complete;

    private XRecyclerView xrv_behavior;
    private BehaviorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developmental_behavior);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        rl_complete = findViewById(R.id.rl_complete);
        tv_complete = findViewById(R.id.tv_complete);

        xrv_behavior = findViewById(R.id.xrv_behavior);
        initView();
    }

    private void initView(){
        adapter = new BehaviorAdapter();
        xrv_behavior.setPullRefreshEnabled(false);
        xrv_behavior.setLoadingMoreEnabled(false);
        xrv_behavior.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        tv_title.setText(R.string.developmental_hehavior);
        tv_complete.setText(R.string.complete);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    submit();
                    break;
            }
        }
    };

    private void back(){

    }

    private void submit(){

    }

}

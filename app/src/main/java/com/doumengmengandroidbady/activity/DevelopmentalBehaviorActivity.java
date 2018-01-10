package com.doumengmengandroidbady.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.BehaviorAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.response.Feature;
import com.doumengmengandroidbady.util.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 发育行为
 * 创建日期: 2018/1/9 11:23
 */
public class DevelopmentalBehaviorActivity extends BaseActivity {

    public final static String IN_PARAM_DEVELOPMENT = "in_param_development";
    public final static String IN_PARAM_MONTH_AGE = "in_param_month_age";
    public final static String IN_PARAM_RECORD_TIME = "in_param_month_version";


    public final static String OUT_PARAM_DEVELOPMENT = "out_param_development";

    private RelativeLayout rl_back;
    private TextView tv_title;
    private RelativeLayout rl_complete;
    private TextView tv_complete;

    private XRecyclerView xrv_behavior;
    private BehaviorAdapter adapter;

    private String recordTime;
    private List<String> ages = new ArrayList<>();
    private List<String> selections = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developmental_behavior);
        getParam();
        findView();
    }

    private void getParam(){
        Intent intent = getIntent();
        String age = intent.getStringExtra(IN_PARAM_MONTH_AGE);
        ages.add(age);

        recordTime = intent.getStringExtra(IN_PARAM_RECORD_TIME);
        String developments = intent.getStringExtra(IN_PARAM_DEVELOPMENT);
        if (!TextUtils.isEmpty(developments)){
            selections = GsonUtil.getInstance().getGson().fromJson(developments,new TypeToken<List<String>>(){}.getType());
        }
        if ( selections == null ){
            selections = new ArrayList<>();
        }
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
        tv_complete.setText(R.string.complete);
        rl_complete.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);

        xrv_behavior.setPullRefreshEnabled(false);
        xrv_behavior.setLoadingMoreEnabled(false);

        List<Feature> features = DaoManager.getInstance().getFeatureDao().searchFeatureList(this,ages,recordTime);
        adapter = new BehaviorAdapter(features,selections);

        xrv_behavior.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void submit(){
        Intent intent = new Intent();
        intent.putExtra(OUT_PARAM_DEVELOPMENT,GsonUtil.getInstance().getGson().toJson(selections));
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

}

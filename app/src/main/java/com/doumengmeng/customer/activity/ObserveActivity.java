package com.doumengmeng.customer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.adapter.ObserveAdapter;
import com.doumengmeng.customer.base.BaseActivity;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.db.DaoManager;
import com.doumengmeng.customer.entity.RoleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 观察要点
 * 创建日期: 2018/1/8 10:53
 */
public class ObserveActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private ImageView iv_ad;
    private Button bt_buy;

    private ListView lv_observe;
    private ObserveAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observe);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        iv_ad = findViewById(R.id.iv_ad);
        bt_buy = findViewById(R.id.bt_buy);

        lv_observe = findViewById(R.id.lv_observe);
        initView();
    }

    private void initView() {
        List<String> contents = null;
        if ( BaseApplication.getInstance().getRoleType() == RoleType.FREE_NET_USER || BaseApplication.getInstance().getRoleType() == RoleType.FREE_HOSPITAL_USER){
            //免费用户
            contents = new ArrayList<>();
            contents.addAll(Arrays.asList(getResources().getStringArray(R.array.free_observe_content)));
            bt_buy.setVisibility(View.VISIBLE);
            iv_ad.setVisibility(View.VISIBLE);
        } else {
            //付费用户
            bt_buy.setVisibility(View.GONE);
            iv_ad.setVisibility(View.GONE);
            contents = DaoManager.getInstance().getGrowthDao().searchGrowth(this);
        }

        rl_back.setOnClickListener(listener);
        bt_buy.setOnClickListener(listener);
        tv_title.setText(R.string.oberve_matter);

        adapter = new ObserveAdapter(this,contents);
        lv_observe.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_buy:
                    startActivity(SpacialistServiceActivity.class);
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

}

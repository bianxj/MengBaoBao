package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.ObserveAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.db.DaoManager;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class ObserveActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private ImageView iv_ad;
    private Button bt_buy;

    private List<String> contents;
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
        rl_back.setOnClickListener(listener);
        bt_buy.setOnClickListener(listener);
        tv_title.setText(R.string.oberve_matter);

        contents = DaoManager.getInstance().getGrowthDao().searchGrowth(this);
        adapter = new ObserveAdapter(this,contents);
        lv_observe.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.bt_buy:
                    //TODO
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

}

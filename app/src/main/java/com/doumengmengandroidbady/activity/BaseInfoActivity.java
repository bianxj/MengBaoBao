package com.doumengmengandroidbady.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.view.BaseInfoLayout;

/**
 * Created by Administrator on 2017/12/13.
 */

public class BaseInfoActivity extends BaseActivity {

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title,tv_complete;

    private BaseInfoLayout base_info;

    private RequestTask changeBaseInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);
        findView();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(changeBaseInfo);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_complete = findViewById(R.id.tv_complete);
        tv_title = findViewById(R.id.tv_title);
        base_info = findViewById(R.id.base_info);
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);

        tv_title.setText(R.string.base_info);
        rl_complete.setVisibility(View.VISIBLE);
        tv_complete.setText(R.string.title_save);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    changeBaseInfo();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void changeBaseInfo(){
        if ( base_info.checkBaseInfo() ){
            try {
                buildChangeBaseInfo().execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private RequestTask buildChangeBaseInfo() throws Throwable {
        changeBaseInfo = new RequestTask.Builder(changeBaseInfoCallBack).build();
        return changeBaseInfo;
    }

    private RequestCallBack changeBaseInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            //TODO
        }

        @Override
        public String getUrl() {
            //TODO
            return null;
        }

        @Override
        public Context getContext() {
            //TODO
            return BaseInfoActivity.this;
        }

        @Override
        public void onError(String result) {
            //TODO
        }

        @Override
        public void onPostExecute(String result) {
            //TODO
            finish();
        }
    };

}

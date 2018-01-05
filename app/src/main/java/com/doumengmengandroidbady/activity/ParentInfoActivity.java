package com.doumengmengandroidbady.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.response.ParentInfo;
import com.doumengmengandroidbady.view.ParentInfoLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ParentInfoActivity extends BaseActivity {

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title,tv_complete;

    private ParentInfoLayout parent_info;

    private RequestTask changeParentInfoTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_info);
        findView();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(changeParentInfoTask);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);
        tv_complete = findViewById(R.id.tv_complete);

        parent_info = findViewById(R.id.parent_info);
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);
        rl_complete.setVisibility(View.VISIBLE);

        tv_title.setText(R.string.parent_info);
        tv_complete.setText(R.string.title_save);
        parent_info.setParentInfo(BaseApplication.getInstance().getParentInfo());
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    changeParentInfo();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void changeParentInfo(){
        if ( checkData() ){
            try {
                buildChangeParentInfoTask().execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkData(){
        if ( ParentInfo.isSamleValue(BaseApplication.getInstance().getParentInfo(),parent_info.getParentInfo()) ){
            return false;
        }
        return true;
    }

    private RequestTask buildChangeParentInfoTask() throws Throwable {
        changeParentInfoTask = new RequestTask.Builder(changeParentInfoCallBack).build();
        return changeParentInfoTask;
    }

    private RequestCallBack changeParentInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            //TODO
        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_SAVE_PARENT_INFO;
        }

        @Override
        public Context getContext() {
            return ParentInfoActivity.this;
        }

        @Override
        public Map<String, String> getContent() {
            Map<String,String> map = new HashMap<>();
            ParentInfo info = parent_info.getParentInfo();
            JSONObject object = new JSONObject();
            try {
                object.put("userId",BaseApplication.getInstance().getUserData().getUserid());
                JSONObject parentInfo = new JSONObject();
                parentInfo.put("DadName",info.getDadName());
                parentInfo.put("DadEducation",info.getDadEducation());
                parentInfo.put("DadHeight",info.getDadHeight());
                parentInfo.put("DadWeight",info.getDadWeight());
                parentInfo.put("DadBMI",info.getDadBMI());
                parentInfo.put("MumName",info.getMumName());
                parentInfo.put("MumEducation",info.getMumEducation());
                parentInfo.put("MumHeight",info.getMumHeight());
                parentInfo.put("MumWeight",info.getMumWeight());
                parentInfo.put("MumBMI",info.getMumBMI());

                object.put("parentInfo",parentInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put(UrlAddressList.PARAM,object.toString());
            map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getUserData().getSessionId());
            return map;
        }

        @Override
        public void onError(String result) {
            //TODO
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                if ( 1 == res.getInt("isEditParent") ){
                    BaseApplication.getInstance().saveParentInfo(parent_info.getParentInfo());
                    back();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String type() {
            return JSON;
        }
    };

}

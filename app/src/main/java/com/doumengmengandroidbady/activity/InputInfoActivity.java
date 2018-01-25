package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.request.entity.InputUserInfo;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.view.BaseInfoLayout;
import com.doumengmengandroidbady.view.ParentInfoLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 信息输入页面
 * 创建日期: 2018/1/16 11:35
 */
public class InputInfoActivity extends BaseActivity {

    //-------------------------------Title控件----------------------------------------
    private RelativeLayout rl_back;
    private TextView tv_title;
    private RelativeLayout rl_complete;
    private TextView tv_complete;

    //-------------------------------基本信息控件-------------------------------------
    private BaseInfoLayout baby_info;
    //---------------------------父母信息控件----------------------------------------
    private ParentInfoLayout parent_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(submitInfoTask);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_back.setVisibility(View.GONE);

        tv_title = findViewById(R.id.tv_title);
        rl_complete = findViewById(R.id.rl_complete);
        tv_complete = findViewById(R.id.tv_complete);

        baby_info = findViewById(R.id.baby_info);
        baby_info.setType(BaseInfoLayout.TYPE.EDITABLE);

        parent_info = findViewById(R.id.parent_info);
        parent_info.setType(ParentInfoLayout.TYPE.EDITABLE_SHOW_MARK);
        initView();
    }

    private void initView(){
        rl_back.setVisibility(View.GONE);
        rl_complete.setOnClickListener(listener);

        rl_complete.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.input_info);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    complete();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    //----------------------------------------数据提交-----------------------------------------------

    private RequestTask submitInfoTask;

    private void complete(){
        if ( checkData() ){
            MyDialog.showChooseDialog(this, getString(R.string.prompt_submit_base_info), R.string.prompt_bt_edit, R.string.prompt_bt_sure_submit, new MyDialog.ChooseDialogCallback() {
                @Override
                public void sure() {
                    submitInfo();
                }

                @Override
                public void cancel() {
                    //UNDO
                }
            });
        }
    }

    private void submitInfo(){
        try {
            submitInfoTask = new RequestTask.Builder(this,submitInfoCallBack).build();
            submitInfoTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private InputUserInfo inputData = new InputUserInfo();
    private RequestCallBack submitInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_SAVE_USER_INFO;
        }

        @Override
        public Map<String, String> getContent() {
            UserData userData = BaseApplication.getInstance().getUserData();
            inputData.setUserId(userData.getUserid());
            inputData.parentInfo = parent_info.getParentInfo();
            inputData.babyInfo = baby_info.getBabyInfo();
            Map<String,String> map = new HashMap<>();
            String json = GsonUtil.getInstance().getGson().toJson(inputData);
            map.put("paramStr",json);
            map.put("sesId",userData.getSessionId());
            return map;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object =  new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                if ( 1 == res.optInt("isSaveUser") ){
                    startActivity(RecordActivity.class);
                } else {
                    //TODO ?
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int type() {
            return DEFAULT;
        }
    };

    private boolean checkData(){

        if ( !baby_info.checkBaseInfo() ){
            tv_title.setText(baby_info.getCheckErrorMsg());
            return false;
        }

        if ( !parent_info.checkParentInfo() ){
            tv_title.setText(parent_info.getErrorMsg());
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

}

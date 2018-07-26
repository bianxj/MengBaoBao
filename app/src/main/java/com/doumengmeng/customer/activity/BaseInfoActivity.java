package com.doumengmeng.customer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseSwipeActivity;
import com.doumengmeng.customer.entity.RoleType;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.request.ResponseErrorCode;
import com.doumengmeng.customer.request.entity.InputUserInfo;
import com.doumengmeng.customer.response.SubmitInfoResponse;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.util.MyDialog;
import com.doumengmeng.customer.view.BaseInfoLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 基本信息
 * 创建日期: 2018/1/16 10:02
 */
public class BaseInfoActivity extends BaseSwipeActivity {

    private RelativeLayout rl_back,rl_complete,rl_close;
    private TextView tv_title,tv_complete;

    private ScrollView sv;
    private BaseInfoLayout base_info;

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
        MyDialog.dismissPromptTopDialog();
        stopTask(uploadBaseInfo);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        rl_close = findViewById(R.id.rl_close);
        tv_complete = findViewById(R.id.tv_complete);
        tv_title = findViewById(R.id.tv_title);
        base_info = findViewById(R.id.base_info);

        sv = findViewById(R.id.sv);
    }

    private void initView(){
        rl_close.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);

        tv_title.setText(R.string.base_info);
        sv.scrollTo(0,0);

        if (RoleType.PAY_HOSPITAL_USER == BaseApplication.getInstance().getRoleType()){
            base_info.setType(BaseInfoLayout.TYPE.READ_ONLY);
        } else {
            base_info.setType(BaseInfoLayout.TYPE.EDITABLE_NET_USER);
            rl_complete.setVisibility(View.VISIBLE);
            tv_complete.setText(R.string.title_save);
        }
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    changeBaseInfo();
                    break;
                case R.id.rl_close:
                    clearPromptTitle();
                    break;
            }
        }
    };

    protected void back(){
        finish();
    }

    private RequestTask uploadBaseInfo;
    private void changeBaseInfo(){
        if ( checkBaseInfo() ){
            try {
                uploadBaseInfo = new RequestTask.Builder(this, uploadBaseInfoCallBack)
                        .setUrl(UrlAddressList.URL_SAVE_USER_INFO)
                        .setType(RequestTask.DEFAULT)
                        .setContent(buildUploadBaseInfoContent())
                        .build();
                uploadBaseInfo.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkBaseInfo(){
        if ( !base_info.checkBaseInfo() ){
            showPromptTitle(base_info.getCheckErrorMsg());
//            tv_title.setText(base_info.getCheckErrorMsg());
            return false;
        }
        return true;
    }

    private Map<String, String> buildUploadBaseInfoContent() {
        InputUserInfo inputUserInfo = new InputUserInfo();
        UserData userData = BaseApplication.getInstance().getUserData();
        inputUserInfo.setUserId(userData.getUserid());
        inputUserInfo.babyInfo = base_info.getBabyInfo();

        String json = GsonUtil.getInstance().toJson(inputUserInfo);

        Map<String,String> map = new HashMap<>();
        map.put("paramStr",json);
        map.put("sesId",userData.getSessionId());
        return map;
    }

    private final RequestCallBack uploadBaseInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(BaseInfoActivity.this, ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            SubmitInfoResponse response = GsonUtil.getInstance().fromJson(result,SubmitInfoResponse.class);
            if ( 1 == response.getResult().getIsSaveUser() ){
                BaseApplication.getInstance().saveBabyInfo(base_info.getBabyInfo());
                MyDialog.showPromptDialog(BaseInfoActivity.this, getString(R.string.dialog_content_edit_success), new MyDialog.PromptDialogCallback() {
                    @Override
                    public void sure() {
                        back();
                    }
                });
            } else {
                MyDialog.showPromptDialog(BaseInfoActivity.this,getString(R.string.dialog_content_edit_failed),null);
            }
        }
    };

    protected void clearPromptTitle(){
        tv_title.setText(R.string.base_info);
        rl_complete.setVisibility(View.VISIBLE);
        rl_back.setVisibility(View.VISIBLE);
        rl_close.setVisibility(View.GONE);
    }

    protected void showPromptTitle(String message){
        MyDialog.showPromptTopDialog(this,getWindow().getDecorView(),message);
//        tv_title.setText(message);
//        rl_back.setVisibility(View.GONE);
//        rl_complete.setVisibility(View.GONE);
//        rl_close.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( rl_close.getVisibility() == View.VISIBLE ){
            if ( keyCode == KeyEvent.KEYCODE_BACK ){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

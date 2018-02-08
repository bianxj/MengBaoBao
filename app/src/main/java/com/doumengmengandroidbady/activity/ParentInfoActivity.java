package com.doumengmengandroidbady.activity;

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
import com.doumengmengandroidbady.request.ResponseErrorCode;
import com.doumengmengandroidbady.request.entity.InputUserInfo;
import com.doumengmengandroidbady.response.SubmitInfoResponse;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.view.ParentInfoLayout;

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
        parent_info.setType(ParentInfoLayout.TYPE.EDITABLE_NO_MARK);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
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
                changeParentInfoTask = new RequestTask.Builder(this,changeParentInfoCallBack)
                        .setUrl(UrlAddressList.URL_SAVE_USER_INFO)
                        .setType(RequestTask.DEFAULT)
                        .setContent(buildChangeParentContent())
                        .build();
                changeParentInfoTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkData(){
        if ( !parent_info.checkParentInfo() ){
            tv_title.setText(parent_info.getErrorMsg());
//            showPromptDialog(parent_info.getErrorMsg());
            return false;
        }
        return true;
    }

    private Map<String, String> buildChangeParentContent() {
        InputUserInfo inputData = new InputUserInfo();
        inputData.setUserId(BaseApplication.getInstance().getUserData().getUserid());

        inputData.parentInfo = parent_info.getParentInfo();

        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM, GsonUtil.getInstance().toJson(inputData));
        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getUserData().getSessionId());
        return map;
    }

    private final RequestCallBack changeParentInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {}

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(ParentInfoActivity.this, ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            SubmitInfoResponse response = GsonUtil.getInstance().fromJson(result,SubmitInfoResponse.class);
            if ( 1 == response.getResult().getIsSaveUser() ){
                BaseApplication.getInstance().saveParentInfo(parent_info.getParentInfo());
                MyDialog.showPromptDialog(ParentInfoActivity.this, getString(R.string.dialog_content_edit_success), new MyDialog.PromptDialogCallback() {
                    @Override
                    public void sure() {
                        back();
                    }
                });
            } else {
                MyDialog.showPromptDialog(ParentInfoActivity.this, getString(R.string.dialog_content_edit_failed), null);
            }
        }
    };

}

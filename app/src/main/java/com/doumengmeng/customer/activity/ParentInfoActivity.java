package com.doumengmeng.customer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.util.MyDialog;
import com.doumengmeng.customer.view.ParentInfoLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ParentInfoActivity extends BaseSwipeActivity {

    private RelativeLayout rl_back,rl_complete,rl_close;
    private TextView tv_title,tv_complete;

    private ParentInfoLayout parent_info;
    private RequestTask uploadParentInfoTask;

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
        MyDialog.dismissPromptTopDialog();
        stopTask(uploadParentInfoTask);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        rl_close = findViewById(R.id.rl_close);
        tv_title = findViewById(R.id.tv_title);
        tv_complete = findViewById(R.id.tv_complete);

        parent_info = findViewById(R.id.parent_info);
    }

    private void initView(){
        rl_close.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);
        rl_complete.setVisibility(View.VISIBLE);

        tv_title.setText(R.string.parent_info);
        tv_complete.setText(R.string.title_save);
        if (RoleType.PAY_NET_USER == BaseApplication.getInstance().getRoleType() ){
            parent_info.setType(ParentInfoLayout.TYPE.EDITABLE_NO_MARK_EXCEPT_EDUCATION);
        } else {
            parent_info.setType(ParentInfoLayout.TYPE.EDITABLE_NO_MARK);
        }
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    uploadParentInfo();
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

    private void uploadParentInfo(){
        if ( checkData() ){
            try {
                uploadParentInfoTask = new RequestTask.Builder(this, uploadParentInfoCallBack)
                        .setUrl(UrlAddressList.URL_SAVE_USER_INFO)
                        .setType(RequestTask.DEFAULT)
                        .setContent(buildUploadParentContent())
                        .build();
                uploadParentInfoTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkData(){
        if ( !parent_info.checkParentInfo() ){
            showPromptTitle(parent_info.getErrorMsg());
//            tv_title.setText(parent_info.getErrorMsg());
//            showPromptDialog(parent_info.getErrorMsg());
            return false;
        }
        return true;
    }

    private Map<String, String> buildUploadParentContent() {
        InputUserInfo inputData = new InputUserInfo();
        inputData.setUserId(BaseApplication.getInstance().getUserData().getUserid());

        inputData.parentInfo = parent_info.getParentInfo();

        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM, GsonUtil.getInstance().toJson(inputData));
        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getUserData().getSessionId());
        return map;
    }

    private final RequestCallBack uploadParentInfoCallBack = new RequestCallBack() {
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


    protected void clearPromptTitle(){
        tv_title.setText(R.string.parent_info);
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

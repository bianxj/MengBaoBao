package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.entity.RoleType;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.request.entity.InputUserInfo;
import com.doumengmengandroidbady.response.entity.UserData;
import com.doumengmengandroidbady.response.SubmitInfoResponse;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.view.BaseInfoLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 基本信息
 * 创建日期: 2018/1/16 10:02
 */
public class BaseInfoActivity extends BaseActivity {

    private RelativeLayout rl_back,rl_complete;
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
        stopTask(changeBaseInfo);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_complete = findViewById(R.id.tv_complete);
        tv_title = findViewById(R.id.tv_title);
        base_info = findViewById(R.id.base_info);

        sv = findViewById(R.id.sv);
    }

    private void initView(){
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
            }
        }
    };

    private void back(){
        finish();
    }

    private RequestTask changeBaseInfo;
    private void changeBaseInfo(){
        if ( base_info.checkBaseInfo() ){
            try {
                changeBaseInfo = new RequestTask.Builder(this,changeBaseInfoCallBack)
                        .setUrl(UrlAddressList.URL_SAVE_USER_INFO)
                        .setType(RequestTask.DEFAULT)
                        .setContent(buildChangeBaseInfoContent())
                        .build();
                changeBaseInfo.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            String errorMsg = base_info.getCheckErrorMsg();
        }
    }

    private Map<String, String> buildChangeBaseInfoContent() {
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

    private final RequestCallBack changeBaseInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
        }

        @Override
        public void onError(String result) {
            //TODO
        }

        @Override
        public void onPostExecute(String result) {
            SubmitInfoResponse response = GsonUtil.getInstance().fromJson(result,SubmitInfoResponse.class);
            if ( 1 == response.getResult().getIsSaveUser() ){
                BaseApplication.getInstance().saveBabyInfo(base_info.getBabyInfo());
                MyDialog.showPromptDialog(BaseInfoActivity.this, getString(R.string.change_base_info_content), new MyDialog.PromptDialogCallback() {
                    @Override
                    public void sure() {
                        back();
                    }
                });
            } else {
                //TODO
            }
        }
    };

}

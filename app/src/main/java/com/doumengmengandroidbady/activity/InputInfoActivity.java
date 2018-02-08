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
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.db.GrowthDao;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.request.entity.InputUserInfo;
import com.doumengmengandroidbady.response.entity.UserData;
import com.doumengmengandroidbady.response.SubmitInfoResponse;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.view.BaseInfoLayout;
import com.doumengmengandroidbady.view.ParentInfoLayout;

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

    private final View.OnClickListener listener = new View.OnClickListener() {
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

    private RequestTask submitInfoTask;
    private void submitInfo(){
        try {
            submitInfoTask = new RequestTask.Builder(this,submitInfoCallBack)
                    .setUrl(UrlAddressList.URL_SAVE_USER_INFO)
                    .setType(RequestTask.DEFAULT)
                    .setContent(buildSubmitInfoContent())
                    .build();
            submitInfoTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, String> buildSubmitInfoContent() {
        UserData userData = BaseApplication.getInstance().getUserData();
        inputData.setUserId(userData.getUserid());
        inputData.parentInfo = parent_info.getParentInfo();
        inputData.babyInfo = baby_info.getBabyInfo();
        Map<String,String> map = new HashMap<>();
        String json = GsonUtil.getInstance().toJson(inputData);
        map.put("paramStr",json);
        map.put("sesId",userData.getSessionId());
        return map;
    }

    private final InputUserInfo inputData = new InputUserInfo();
    private final RequestCallBack submitInfoCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            SubmitInfoResponse response = GsonUtil.getInstance().fromJson(result,SubmitInfoResponse.class);
            if ( 1 == response.getResult().getIsSaveUser() ){
                BaseApplication.getInstance().saveBabyInfo(baby_info.getBabyInfo());
                BaseApplication.getInstance().saveParentInfo(parent_info.getParentInfo());

                BaseApplication.getInstance().saveDayList(response.getResult().getDayList());

                DaoManager.getInstance().deleteTable(InputInfoActivity.this, GrowthDao.TABLE_NAME);
                DaoManager.getInstance().getGrowthDao().saveGrowthList(InputInfoActivity.this,response.getResult().getGrowthList());
                startActivity(RecordActivity.class);
            } else {
                //TODO
                showPromptDialog("提交信息失败");
            }
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

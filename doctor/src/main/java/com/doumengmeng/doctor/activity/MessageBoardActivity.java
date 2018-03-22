package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.response.MessageBoardResponse;
import com.doumengmeng.doctor.util.EditTextUtil;
import com.doumengmeng.doctor.util.FormatCheckUtil;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/1.
 */

public class MessageBoardActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private EditText et_message_content,et_contact;
    private ImageView iv_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_message_board);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(submitMessageBoardTask);
    }

    private void findView(){
        initTitle();
        initContent();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.message_board);

        rl_back.setOnClickListener(listener);
    }

    private void initContent(){
        et_message_content = findViewById(R.id.et_message_content);
        et_contact = findViewById(R.id.et_contact);
        iv_submit = findViewById(R.id.iv_submit);

        EditTextUtil.scrollEditText(et_message_content);
        EditTextUtil.scrollEditText(et_contact);

        iv_submit.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.iv_submit:
                    submitMessageBoard();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private RequestTask submitMessageBoardTask;
    private void submitMessageBoard(){
        if ( checkMessageBoardData() ){
            try {
                submitMessageBoardTask = new RequestTask.Builder(this,submitMessageBoardCallBack)
                        .setUrl(UrlAddressList.URL_SUBMIT_MESSAGE_BOARD)
                        .setContent(buildMessageBoardContent())
                        .setType(RequestTask.DEFAULT)
                        .build();
                submitMessageBoardTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private boolean checkMessageBoardData(){
        if (TextUtils.isEmpty(et_message_content.getText().toString().trim())){
            Toast.makeText(this,"请填写问题",Toast.LENGTH_SHORT).show();
            return false;
        }

        String contact = et_contact.getText().toString().trim();
        if (TextUtils.isEmpty(contact)){
            Toast.makeText(this,"请填写联系方式",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!FormatCheckUtil.isPhone(contact)&&!FormatCheckUtil.isEmail(contact)){
            Toast.makeText(this,"联系方式 需要手机或邮箱",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Map<String,String> buildMessageBoardContent(){
        Map<String,String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("doctorId",BaseApplication.getInstance().getUserData().getDoctorId());
            object.put("content",et_message_content.getText().toString().trim());
            object.put("contactInformation",et_contact.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.PARAM, object.toString());
        map.put(UrlAddressList.SESSION_ID, BaseApplication.getInstance().getSessionId());
        return map;
    }

    private RequestCallBack submitMessageBoardCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(MessageBoardActivity.this, ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            MessageBoardResponse response = GsonUtil.getInstance().fromJson(result,MessageBoardResponse.class);
            if ( response.getResult() != null && 1 == response.getResult().getIsSuccess() ){
                MyDialog.showPromptDialog(MessageBoardActivity.this, "提交成功", new MyDialog.PromptDialogCallback() {
                    @Override
                    public void sure() {
                        back();
                    }
                });
            } else {
                MyDialog.showPromptDialog(MessageBoardActivity.this, "提交失败",null);
            }
        }
    };

}

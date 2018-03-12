package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.util.EditTextUtil;

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

        if (TextUtils.isEmpty(et_contact.getText().toString().trim())){
            Toast.makeText(this,"请填写联系方式",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Map<String,String> buildMessageBoardContent(){
        //TODO
        Map<String,String> map = new HashMap<>();
        return map;
    }

    //TODO
    private RequestCallBack submitMessageBoardCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {

        }
    };

}

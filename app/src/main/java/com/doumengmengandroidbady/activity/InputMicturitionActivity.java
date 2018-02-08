package com.doumengmengandroidbady.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseInputDataActivity;

/**
 * 作者: 边贤君
 * 描述: 排尿
 * 创建日期: 2018/1/10 13:33
 */
public class InputMicturitionActivity extends BaseInputDataActivity {

    public final static String IN_PARAM_MICTURITION = "in_micturition";
    public final static String OUT_PARAM_MICTURITION = "micturition";

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title;

    private EditText et_input_data;
    private LinearLayout ll_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micturition);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);

        et_input_data = findViewById(R.id.et_input_data_two);
        ll_content = findViewById(R.id.ll_content);
        initView();
    }

    private void initView(){
        tv_title.setText(R.string.record_micturition);
        rl_complete.setVisibility(View.VISIBLE);

        rl_complete.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);

        generateListView(ll_content,getResources().getStringArray(R.array.micturition_content));

        Intent intent = getIntent();
        if ( intent != null ){
            String micturition = intent.getStringExtra(IN_PARAM_MICTURITION);
            et_input_data.setText(micturition);
        }
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

    private void back() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void complete(){
        if ( checkData() ){
            Intent intent = new Intent();
            intent.putExtra(OUT_PARAM_MICTURITION,et_input_data.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        String data = et_input_data.getText().toString();
        if (TextUtils.isEmpty(data)){
            showPromptTitle("排尿天/次 不能为空");
            return false;
        }

        float countDay = Float.parseFloat(data);
        if ( countDay < 0 || countDay > 30 ){
            showPromptTitle("排尿天/次 范围为0~30");
            return false;
        }
        return true;
    }

    private void showPromptTitle(String message){
        tv_title.setText(message);
    }
}

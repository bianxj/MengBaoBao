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
    private TextView tv_title,tv_complete;

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
        tv_complete = findViewById(R.id.tv_complete);

        et_input_data = findViewById(R.id.et_input_data_two);
        ll_content = findViewById(R.id.ll_content);
        initView();
    }

    private void initView(){
        tv_title.setText(R.string.micturition);
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
        String hourString = et_input_data.getText().toString();
        if (TextUtils.isEmpty(hourString)){
            showPromptDialog("睡眠时间不能为空");
            return false;
        }

        float hour = Float.parseFloat(hourString);
        if ( hour <= 0 || hour >= 24 ){
            showPromptDialog("睡眠时间范围为0~24");
            return false;
        }
        return true;
    }
}

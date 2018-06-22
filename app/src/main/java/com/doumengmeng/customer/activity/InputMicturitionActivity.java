package com.doumengmeng.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseInputDataActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 排尿
 * 创建日期: 2018/1/10 13:33
 */
public class InputMicturitionActivity extends BaseInputDataActivity {

    public final static String IN_PARAM_MICTURITION = "in_micturition";
    public final static String OUT_PARAM_MICTURITION = "micturition";

//    private RelativeLayout rl_back,rl_complete;
//    private TextView tv_title;

    private EditText et_input_data;
    private LinearLayout ll_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initChildData() {
        setContentView(R.layout.activity_micturition);
        findView();
    }

    private void findView(){
        et_input_data = findViewById(R.id.et_input_data_two);
        ll_content = findViewById(R.id.ll_content);
        initView();
    }

    private void initView(){
        List<String> value = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.micturition_content)));
        if ( month == 0 ){
            value.remove(1);
        } else {
            value.remove(0);
        }
        generateListView(ll_content, value.toArray(new String[0]));

        Intent intent = getIntent();
        if ( intent != null ){
            String micturition = intent.getStringExtra(IN_PARAM_MICTURITION);
            et_input_data.setText(micturition);
        }
    }

    @Override
    protected void back() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    protected void complete(){
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
            showPromptTitle("请输入排尿信息");
            return false;
        }

        float countDay = Float.parseFloat(data);
        if ( countDay < 0 || countDay > 30 ){
            showPromptTitle("排尿 范围为0~30");
            return false;
        }
        return true;
    }

    @Override
    protected int getTitleName() {
        return R.string.record_micturition;
    }

}

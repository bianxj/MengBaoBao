package com.doumengmeng.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseInputDataActivity;
import com.doumengmeng.customer.util.EditTextUtil;
import com.doumengmeng.customer.util.FormatCheckUtil;

/**
 * 作者: 边贤君
 * 描述: 夜间睡眠
 * 创建日期: 2018/1/17 13:23
 */
public class InputNightSleepActivity extends BaseInputDataActivity {
    public final static String IN_PARAM_NIGHT_SLEEP = "in_night_sleep";
    public final static String OUT_PARAM_NIGHT_SLEEP = "night_sleep";

//    private RelativeLayout rl_back,rl_complete;
//    private TextView tv_title;

    private EditText et_input_data;
    private TextView tv_reference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initChildData() {
        setContentView(R.layout.activity_night_sleep);
        findView();
    }

    private void findView(){
        et_input_data = findViewById(R.id.et_input_data_two);
        tv_reference = findViewById(R.id.tv_reference);
        initView();
    }

    private void initView(){
        tv_reference.setText(getResources().getStringArray(R.array.sleep_reference)[month]);

        Intent intent = getIntent();
        if ( intent != null ){
            String night = intent.getStringExtra(IN_PARAM_NIGHT_SLEEP);
            et_input_data.setText(night);
        }
        new EditTextUtil(et_input_data);
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
            intent.putExtra(OUT_PARAM_NIGHT_SLEEP,et_input_data.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        String hourString = et_input_data.getText().toString();
        if (TextUtils.isEmpty(hourString)){
            showPromptTitle("请输入睡眠时间");
            return false;
        }

        if (!FormatCheckUtil.isDecimalNumber(hourString)){
            showPromptTitle("睡眠格式不正确");
            return false;
        }
        float hour = Float.parseFloat(hourString);
        if ( hour < 0 || hour > 12 ){
            showPromptTitle("输入有误");
            return false;
        }
        return true;
    }

    @Override
    protected int getTitleName() {
        return R.string.record_night_sleep;
    }


}

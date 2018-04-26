package com.doumengmeng.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseInputDataActivity;
import com.doumengmeng.customer.util.EditTextUtil;

/**
 * 作者: 边贤君
 * 描述: 日间睡眠
 * 创建日期: 2018/1/10 13:24
 */
public class InputDaySleepActivity extends BaseInputDataActivity {

    public final static String IN_PARAM_DAY_SLEEP = "in_day_sleep";
    public final static String OUT_PARAM_DAY_SLEEP = "day_sleep";

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title;

    private EditText et_input_data;
    private TextView tv_reference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_sleep);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);

        et_input_data = findViewById(R.id.et_input_data_two);
        tv_reference = findViewById(R.id.tv_reference);
        initView();
    }

    private void initView(){
        tv_title.setText(R.string.record_day_sleep);
        rl_complete.setVisibility(View.VISIBLE);

        rl_complete.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);

        tv_reference.setText(getResources().getStringArray(R.array.sleep_reference)[month]);

        Intent intent = getIntent();
        if ( intent != null ){
            String day = intent.getStringExtra(IN_PARAM_DAY_SLEEP);
            et_input_data.setText(day);
        }
        new EditTextUtil(et_input_data);
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
            intent.putExtra(OUT_PARAM_DAY_SLEEP,et_input_data.getText().toString());
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

        float hour = Float.parseFloat(hourString);
        if ( hour < 0 || hour > 12 ){
            showPromptTitle("睡眠时间 0~12");
            return false;
        }

        return true;
    }

    private void showPromptTitle(String message){
        tv_title.setText(message);
    }

}

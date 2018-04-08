package com.doumengmeng.customer.activity;

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

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseInputDataActivity;

/**
 * 作者: 边贤君
 * 描述: 排便
 * 创建日期: 2018/1/10 13:20
 */
public class InputCacationActivity extends BaseInputDataActivity {

    public final static String IN_PARAM_CACATION_DAY = "in_cacation_day";
    public final static String IN_PARAM_CACATION_COUNT = "in_cacation_count";

    public final static String OUT_PARAM_CACATION_DAY = "cacation_day";
    public final static String OUT_PARAM_CACATION_COUNT = "cacation_count";

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title;

    private LinearLayout ll_content;
    private EditText et_input_data_one,et_input_data_two;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cacation);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);

        et_input_data_one = findViewById(R.id.et_input_data_one);
        et_input_data_two = findViewById(R.id.et_input_data_two);
        ll_content = findViewById(R.id.ll_content);
        initView();
    }

    private void initView(){
        tv_title.setText(R.string.record_cacation);
        rl_complete.setVisibility(View.VISIBLE);

        rl_complete.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);

        String content = getResources().getStringArray(R.array.cacation_content)[month];
        generateListView(ll_content,content.split("。"));

        Intent intent = getIntent();
        if ( intent != null ){
            String day = intent.getStringExtra(IN_PARAM_CACATION_DAY);
            String count = intent.getStringExtra(IN_PARAM_CACATION_COUNT);
            et_input_data_one.setText(day);
            et_input_data_two.setText(count);
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
            intent.putExtra(OUT_PARAM_CACATION_DAY,et_input_data_one.getText().toString());
            intent.putExtra(OUT_PARAM_CACATION_COUNT,et_input_data_two.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        String day = et_input_data_one.getText().toString();
        String count = et_input_data_two.getText().toString();

        if (TextUtils.isEmpty(day)){
            showPromptTitle("请输入天数");
            return false;
        }

        if (TextUtils.isEmpty(count)){
            showPromptTitle("请输入次数");
            return false;
        }

        if ( Integer.parseInt(day) < 0 || Integer.parseInt(day) > 30 ){
            showPromptTitle("天数 0~30");
            return false;
        }

        if ( Integer.parseInt(count) < 0 || Integer.parseInt(day) > 30 ){
            showPromptTitle("次数 0~30");
            return false;
        }

        return true;
    }

    private void showPromptTitle(String message){
        tv_title.setText(message);
    }

}
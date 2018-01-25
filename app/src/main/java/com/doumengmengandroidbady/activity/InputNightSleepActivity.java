package com.doumengmengandroidbady.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseInputDataActivity;

/**
 * 作者: 边贤君
 * 描述: 夜间睡眠
 * 创建日期: 2018/1/17 13:23
 */
public class InputNightSleepActivity extends BaseInputDataActivity {
    public final static String IN_PARAM_NIGHT_SLEEP = "in_night_sleep";
    public final static String OUT_PARAM_NIGHT_SLEEP = "night_sleep";

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title,tv_complete;

    private EditText et_input_data;
    private TextView tv_reference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_sleep);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);
        tv_complete = findViewById(R.id.tv_complete);

        et_input_data = findViewById(R.id.et_input_data_two);
        tv_reference = findViewById(R.id.tv_reference);
        initView();
    }

    private void initView(){
        tv_title.setText(R.string.night_sleep);
        rl_complete.setVisibility(View.VISIBLE);

        rl_complete.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);

        tv_reference.setText(getResources().getStringArray(R.array.sleep_reference)[month]);

        Intent intent = getIntent();
        if ( intent != null ){
            String night = intent.getStringExtra(IN_PARAM_NIGHT_SLEEP);
            et_input_data.setText(night);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
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
            intent.putExtra(OUT_PARAM_NIGHT_SLEEP,et_input_data.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        return true;
    }
}

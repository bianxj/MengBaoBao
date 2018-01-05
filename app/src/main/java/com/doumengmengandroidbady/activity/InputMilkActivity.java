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
import com.doumengmengandroidbady.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/26.
 */

public class InputMilkActivity extends BaseActivity {
    public final static String RESULT_FORMULA_MILK = "result_formula_milk";
    public final static String RESULT_FORMULA_MILK_COUNT = "result_formula_milk_count";

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title,tv_complete;

    private EditText et_input_data_one,et_input_data_two;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_feeding);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);
        tv_complete = findViewById(R.id.tv_complete);

        et_input_data_one = findViewById(R.id.et_input_data);
        et_input_data_two = findViewById(R.id.et_input_data_two);
        initView();
    }

    private void initView(){
        tv_title.setText(R.string.cacation);
        rl_complete.setVisibility(View.VISIBLE);

        rl_complete.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);
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
            intent.putExtra(RESULT_FORMULA_MILK,et_input_data_one.getText().toString());
            intent.putExtra(RESULT_FORMULA_MILK_COUNT,et_input_data_two.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        return true;
    }
}
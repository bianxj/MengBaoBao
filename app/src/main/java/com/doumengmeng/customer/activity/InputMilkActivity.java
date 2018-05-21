package com.doumengmeng.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseInputDataActivity;
import com.doumengmeng.customer.view.MyGifPlayer;

/**
 * 作者: 边贤君
 * 描述: 配方奶喂养
 * 创建日期: 2018/1/19 13:53
 */
public class InputMilkActivity extends BaseInputDataActivity {

    public final static String IN_PARAM_FORMULA_MILK = "in_formula_milk";
    public final static String IN_PARAM_FORMULA_MILK_COUNT = "in_formula_milk_count";

    public final static String OUT_PARAM_FORMULA_MILK = "formula_milk";
    public final static String OUT_PARAM_FORMULA_MILK_COUNT = "formula_milk_count";

//    private RelativeLayout rl_back,rl_complete;
//    private TextView tv_title;

    private EditText et_input_data_one,et_input_data_two;
    private TextView tv_reference , tv_formula_title , tv_formula_content , tv_other_title;
    private LinearLayout ll_other_content;
    private MyGifPlayer player;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initChildData() {
        setContentView(R.layout.activity_other_feeding);
        findView();
    }

    private void findView(){
        et_input_data_one = findViewById(R.id.et_input_data);
        et_input_data_two = findViewById(R.id.et_input_data_two);

        player = findViewById(R.id.player);
        tv_reference = findViewById(R.id.tv_reference);
        tv_formula_title = findViewById(R.id.tv_formula_title);
        tv_formula_content = findViewById(R.id.tv_formula_content);
        tv_formula_content = findViewById(R.id.tv_formula_content);
        tv_other_title = findViewById(R.id.tv_other_title);
        ll_other_content = findViewById(R.id.ll_other_content);
        initView();
    }

    private void initView(){
        player.setGif(R.drawable.gif_breast);
        tv_reference.setText(getResources().getStringArray(R.array.milk_reference)[month]);
        tv_formula_title.setText(getResources().getString(R.string.formula_input_title));
        tv_other_title.setText(getResources().getString(R.string.other_input_title));

        if( month >= 4 ) {
            tv_formula_content.setText(getResources().getString(R.string.formula_milk_upper_4));
        } else {
            tv_formula_content.setText(getResources().getString(R.string.formula_milk_lower_4));
        }
        generateListView(ll_other_content,getResources().getStringArray(R.array.other_milk));

        Intent intent = getIntent();
        if ( intent != null ){
            String milk = intent.getStringExtra(IN_PARAM_FORMULA_MILK);
            String milk_count = intent.getStringExtra(IN_PARAM_FORMULA_MILK_COUNT);
            et_input_data_one.setText(milk);
            et_input_data_two.setText(milk_count);
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
            intent.putExtra(OUT_PARAM_FORMULA_MILK,et_input_data_one.getText().toString());
            intent.putExtra(OUT_PARAM_FORMULA_MILK_COUNT,et_input_data_two.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        String ml = et_input_data_one.getText().toString().trim();
        String count = et_input_data_two.getText().toString().trim();

        if ( TextUtils.isEmpty(ml) && TextUtils.isEmpty(count) ){
            return true;
        }

        if ( TextUtils.isEmpty(ml) ) {
            showPromptTitle("请输入配方奶喂养量");
            return false;
        }
        if ( TextUtils.isEmpty(count) ) {
            showPromptTitle("请输入喂养次数");
            return false;
        }
        if ( Float.parseFloat(ml) <= 0 || Float.parseFloat(ml) > 2000 ){
            showPromptTitle("配方奶喂养量 范围为0~2000");
            return false;
        }
        if ( Float.parseFloat(count) <= 0 || Float.parseFloat(count) > 30  ){
            showPromptTitle("喂养次数 范围为0~30");
            return false;
        }
        return true;
    }

    @Override
    protected int getTitleName() {
        return R.string.record_formula_milk_title;
    }


}

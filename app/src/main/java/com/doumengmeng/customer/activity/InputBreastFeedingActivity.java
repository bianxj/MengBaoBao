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
 * 描述: 母乳喂养
 * 创建日期: 2018/1/10 13:27
 */
public class InputBreastFeedingActivity extends BaseInputDataActivity {

    public final static String IN_PARAM_BREAST_FEEDING = "in_breast_feeding";
    public final static String IN_PARAM_BREAST_FEEDING_COUNT = "in_breast_feeding_count";

    public final static String OUT_PARAM_BREAST_FEEDING = "breast_feeding";
    public final static String OUT_PARAM_BREAST_FEEDING_COUNT = "breast_feeding_count";

//    private RelativeLayout rl_back,rl_complete;
//    private TextView tv_title;

    private EditText et_input_data_one,et_input_data_two;
    private MyGifPlayer player;
    private TextView tv_reference;
    private TextView tv_input_title;
    private LinearLayout ll_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initChildData() {
        setContentView(R.layout.activity_brest_feeding);
        findView();
    }

    private void findView(){
        et_input_data_one = findViewById(R.id.et_input_data);
        et_input_data_two = findViewById(R.id.et_input_data_two);
        player = findViewById(R.id.player);
        tv_reference = findViewById(R.id.tv_reference);
        tv_input_title = findViewById(R.id.tv_input_title);
        ll_content = findViewById(R.id.ll_content);
        initView();
    }

    private void initView(){
        player.setGif(R.drawable.gif_breast);

        tv_reference.setText(getResources().getStringArray(R.array.milk_reference)[month]);
        tv_input_title.setText(getResources().getString(R.string.breast_input_title));
        generateListView(ll_content,getResources().getStringArray(R.array.breast_milk));

        Intent intent = getIntent();
        if ( intent != null ){
            String breast = intent.getStringExtra(IN_PARAM_BREAST_FEEDING);
            String breast_count = intent.getStringExtra(IN_PARAM_BREAST_FEEDING_COUNT);

            et_input_data_one.setText(breast);
            et_input_data_two.setText(breast_count);
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
            intent.putExtra(OUT_PARAM_BREAST_FEEDING,et_input_data_one.getText().toString());
            intent.putExtra(OUT_PARAM_BREAST_FEEDING_COUNT,et_input_data_two.getText().toString());
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
            showPromptTitle("请输入母乳喂养量");
            return false;
        }
        if ( TextUtils.isEmpty(count) ) {
            showPromptTitle("请输入喂养次数");
            return false;
        }
        if ( Float.parseFloat(ml) <= 0 || Float.parseFloat(ml) > 2000  ){
            showPromptTitle("母乳喂养量 0~2000");
            return false;
        }
        if ( Float.parseFloat(count) <= 0 || Float.parseFloat(count) > 30 ){
            showPromptTitle("喂养次数 0~30");
            return false;
        }
        return true;
    }

    @Override
    protected int getTitleName() {
        return R.string.record_breastfeeding_title;
    }

}

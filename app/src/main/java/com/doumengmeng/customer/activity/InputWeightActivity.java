package com.doumengmeng.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseInputDataActivity;
import com.doumengmeng.customer.response.entity.DayList;
import com.doumengmeng.customer.util.EditTextUtil;
import com.doumengmeng.customer.view.MyGifPlayer;

/**
 * Created by Administrator on 2017/12/12.
 */

public class InputWeightActivity extends BaseInputDataActivity {

    public final static String IN_PARAM_WEIGHT = "in_weight";
    public final static String OUT_PARAM_WEIGHT = "weight";

    private MyGifPlayer player;
//    private RelativeLayout rl_back,rl_complete;
//    private TextView tv_title;

    private EditText et_input_data;
    private TextView tv_reference,tv_increase,tv_input_title;
    private LinearLayout ll_content , ll_remark;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initChildData() {
        setContentView(R.layout.activity_weight);
        findView();
    }

    private void findView(){
        player = findViewById(R.id.player);
        et_input_data = findViewById(R.id.et_input_data);
        tv_reference = findViewById(R.id.tv_reference);
        tv_increase = findViewById(R.id.tv_increase);
        tv_input_title = findViewById(R.id.tv_input_title);
        ll_content = findViewById(R.id.ll_content);
        ll_remark = findViewById(R.id.ll_remark);
        initView();
    }

    private void initView(){
        new EditTextUtil(et_input_data);
        player.setGif(R.drawable.gif_weight);

        DayList dayList = BaseApplication.getInstance().getDayList();
        if ( Integer.parseInt(dayList.getCurrentMonth()) >= 24 ){
            month = Integer.parseInt(dayList.getCurrentMonth());
        }

        //参考值
        if ( isBoy ) {
            tv_reference.setText(getResources().getStringArray(R.array.weight_reference_boy)[month]);
        } else {
            tv_reference.setText(getResources().getStringArray(R.array.weight_reference_girl)[month]);
        }
        //增长
        String increase = getResources().getStringArray(R.array.weight_increase)[month];
        if (TextUtils.isEmpty(increase) ){
            tv_increase.setVisibility(View.GONE);
        } else {
            tv_increase.setText(increase);
        }
        //标题
        tv_input_title.setText(getResources().getString(R.string.weight_input_title));
        //内容
        generateListView(ll_content,getResources().getStringArray(R.array.weight_content));
        //备注
        generateListView(ll_remark,getResources().getStringArray(R.array.weight_remark),true);

        Intent intent = getIntent();
        if ( intent != null ){
            String weight = intent.getStringExtra(IN_PARAM_WEIGHT);
            et_input_data.setText(weight);
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
            intent.putExtra(OUT_PARAM_WEIGHT,et_input_data.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        String weightString = et_input_data.getText().toString().trim();

        if ( TextUtils.isEmpty(weightString) ) {
            showPromptTitle("请输入体重信息");
            return false;
        }
        float weight = Float.parseFloat(et_input_data.getText().toString().trim());
        if ( weight <= 0 || weight > 150 ){
            showPromptTitle("体重 0~150kg");
            return false;
        }
        return true;
    }

    @Override
    protected int getTitleName() {
        return R.string.assessment_weight;
    }


}

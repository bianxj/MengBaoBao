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
import com.doumengmeng.customer.base.BaseInputDataActivity;
import com.doumengmeng.customer.util.EditTextUtil;
import com.doumengmeng.customer.view.MyGifPlayer;

/**
 * Created by Administrator on 2017/12/26.
 */

public class InputChestActivity extends BaseInputDataActivity {

    public final static String IN_PARAM_CHEST = "in_chest";
    public final static String OUT_PARAM_CHEST = "chest";

//    private RelativeLayout rl_back,rl_complete;
//    private TextView tv_title;

    private TextView tv_increase,tv_reference,tv_std;
    private TextView tv_input_title;
    private LinearLayout ll_content;
    private MyGifPlayer player;
    private LinearLayout ll_remark;

    private EditText et_input_data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initChildData() {
        setContentView(R.layout.activity_chest);
        findView();
    }

    private void findView(){
        tv_increase = findViewById(R.id.tv_increase);
        tv_reference = findViewById(R.id.tv_reference);
        tv_std = findViewById(R.id.tv_std);
        et_input_data = findViewById(R.id.et_input_data);
        tv_input_title = findViewById(R.id.tv_input_title);
        ll_content = findViewById(R.id.ll_content);
        player = findViewById(R.id.player);
        ll_remark = findViewById(R.id.ll_remark);
        initView();
    }

    private void initView(){
        new EditTextUtil(et_input_data);
        player.setGif(R.drawable.gif_chest);

        //增长
        String increase = getResources().getStringArray(R.array.chest_increase)[month];
        if (TextUtils.isEmpty(increase) ){
            tv_increase.setVisibility(View.GONE);
        } else {
            tv_increase.setText(increase);
        }
        //参考值
        if ( isBoy ){
            tv_reference.setText(getResources().getStringArray(R.array.chest_reference_boy)[month]);
            tv_std.setText(getResources().getStringArray(R.array.chest_std_boy)[month]);
        } else {
            tv_reference.setText(getResources().getStringArray(R.array.chest_reference_girl)[month]);
            tv_std.setText(getResources().getStringArray(R.array.chest_std_girl)[month]);
        }
        //标题
        tv_input_title.setText(getResources().getString(R.string.chest_input_title));
        //内容
        generateListView(ll_content,getResources().getStringArray(R.array.chest_content));
        //备注
        generateListView(ll_remark,getResources().getStringArray(R.array.chest_remark));

        Intent intent = getIntent();
        if ( intent != null ){
            String chest = intent.getStringExtra(IN_PARAM_CHEST);
            et_input_data.setText(chest);
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
            intent.putExtra(OUT_PARAM_CHEST,et_input_data.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        String chestString = et_input_data.getText().toString().trim();

        if ( TextUtils.isEmpty(chestString) ) {
            showPromptTitle("请输入胸围信息");
            return false;
        }
        float chest = Float.parseFloat(et_input_data.getText().toString().trim());
        if ( chest <= 0 || chest > 120 ){
            showPromptTitle("胸围 0~120cm");
            return false;
        }
        return true;
    }

    @Override
    protected int getTitleName() {
        return R.string.record_chest_circumference;
    }

}

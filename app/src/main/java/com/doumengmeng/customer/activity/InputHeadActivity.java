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
import com.doumengmeng.customer.util.FormatCheckUtil;
import com.doumengmeng.customer.view.MyGifPlayer;

/**
 * 作者: 边贤君
 * 描述: 测量头围
 * 创建日期: 2018/1/18 9:35
 */
public class InputHeadActivity extends BaseInputDataActivity {

    public final static String IN_PARAM_HEAD = "in_head";
    public final static String OUT_PARAM_HEAD = "head";

//    private RelativeLayout rl_back,rl_complete;
//    private TextView tv_title;

    private TextView tv_increase,tv_reference,tv_std,tv_dest;
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
        setContentView(R.layout.activity_head);
        findView();
    }

    private void findView(){
        tv_dest = findViewById(R.id.tv_dest);
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
        player.setGif(R.drawable.gif_head);
        new EditTextUtil(et_input_data);

//        if ( month < 36 ){
//            tv_dest.setText(getString(R.string.head_reference_dest_2015));
//        } else {
//            tv_dest.setText(getString(R.string.head_reference_dest_2005));
//        }

        //增长
        String increase = getResources().getStringArray(R.array.head_increase)[month];
        if (TextUtils.isEmpty(increase) ){
            tv_increase.setVisibility(View.GONE);
        } else {
            tv_increase.setVisibility(View.VISIBLE);
            tv_increase.setText(increase);
        }
        //参考值
        if ( isBoy ){
            tv_reference.setText(getResources().getStringArray(R.array.head_reference_boy)[month]);
            tv_std.setText(getResources().getStringArray(R.array.head_std_boy)[month]);
        } else {
            tv_reference.setText(getResources().getStringArray(R.array.head_reference_girl)[month]);
            tv_std.setText(getResources().getStringArray(R.array.head_std_girl)[month]);
        }
        //标题
        tv_input_title.setText(getResources().getString(R.string.head_input_title));
        //内容
        generateListView(ll_content,getResources().getStringArray(R.array.head_content));
        //备注
        generateListView(ll_remark,getResources().getStringArray(R.array.head_remark),true);

        Intent intent = getIntent();
        if ( intent != null ){
            String head = intent.getStringExtra(IN_PARAM_HEAD);
            et_input_data.setText(head);
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
            intent.putExtra(OUT_PARAM_HEAD,et_input_data.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        String headString = et_input_data.getText().toString().trim();

        if ( TextUtils.isEmpty(headString) ) {
            showPromptTitle("请输入头围信息");
            return false;
        }

        if (!FormatCheckUtil.isDecimalNumber(headString)){
            showPromptTitle("头围格式不正确");
            return false;
        }
        float head = Float.parseFloat(et_input_data.getText().toString().trim());
        if ( head <= 0 || head > 70 ){
            showPromptTitle("头围 0~70cm");
            return false;
        }
        return true;
    }

    @Override
    protected int getTitleName() {
        return R.string.record_head_circumference;
    }

}

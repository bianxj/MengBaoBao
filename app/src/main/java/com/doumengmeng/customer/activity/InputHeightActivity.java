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
 * 描述: 测量身高页面
 * 创建日期: 2018/1/10 11:43
 */
public class InputHeightActivity extends BaseInputDataActivity {

    private String suggest = "<p><font color=\"#EF4399\">* 建议：</font>身高测量宜在清晨进行；身高测量应在医 院用标准的量床测量。</p>";

    public final static String IN_PARAM_HEIGHT = "in_height";
    public final static String OUT_PARAM_HEIGHT = "height";

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
        setContentView(R.layout.activity_height);
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
        player.setGif(R.drawable.gif_height);

        //参考值
        if ( isBoy ) {
            tv_reference.setText(getResources().getStringArray(R.array.height_reference_boy)[month]);
        } else {
            tv_reference.setText(getResources().getStringArray(R.array.height_reference_girl)[month]);
        }
        //增长
        String increase = getResources().getStringArray(R.array.height_increase)[month];
        if (TextUtils.isEmpty(increase) ){
            tv_increase.setVisibility(View.GONE);
        } else {
            tv_increase.setVisibility(View.VISIBLE);
            tv_increase.setText(increase);
        }
        //标题
        tv_input_title.setText(getResources().getString(R.string.height_input_title));
        //内容
        generateListView(ll_content,getResources().getStringArray(R.array.height_content));
        //备注
        generateListView(ll_remark,getResources().getStringArray(R.array.height_remark),true);

        Intent intent = getIntent();
        if ( intent != null ) {
            String height = intent.getStringExtra(IN_PARAM_HEIGHT);
            et_input_data.setText(height);
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
            intent.putExtra(OUT_PARAM_HEIGHT,et_input_data.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        String heightString = et_input_data.getText().toString().trim();

        if ( TextUtils.isEmpty(heightString) ) {
            showPromptTitle("请输入身高");
            return false;
        }

        if (!FormatCheckUtil.isDecimalNumber(heightString)){
            showPromptTitle("身高格式不正确");
            return false;
        }

        float height = Float.parseFloat(et_input_data.getText().toString().trim());
        if ( height <= 0 || height > 250 ){
            showPromptTitle("身高 0~250cm");
            return false;
        }
        return true;
    }

    @Override
    protected int getTitleName() {
        return R.string.assessment_height;
    }


}

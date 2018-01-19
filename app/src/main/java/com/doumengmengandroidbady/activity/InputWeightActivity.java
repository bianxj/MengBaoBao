package com.doumengmengandroidbady.activity;

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

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseInputDataActivity;
import com.doumengmengandroidbady.util.EditTextUtil;
import com.doumengmengandroidbady.view.MyGifPlayer;

/**
 * Created by Administrator on 2017/12/12.
 */

public class InputWeightActivity extends BaseInputDataActivity {

    public final static String OUT_PARAM_WEIGHT = "weight";

    private MyGifPlayer player;
    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title,tv_complete;

    private EditText et_input_data;
    private TextView tv_reference,tv_increase,tv_input_title;
    private LinearLayout ll_content , ll_remark;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);
        tv_complete = findViewById(R.id.tv_complete);

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
        tv_title.setText(R.string.weight);
        rl_complete.setVisibility(View.VISIBLE);

        rl_complete.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);
        new EditTextUtil(et_input_data);
        player.setGif(R.drawable.gif_height);

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
        generateListView(ll_remark,getResources().getStringArray(R.array.weight_remark));
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
            intent.putExtra(OUT_PARAM_WEIGHT,et_input_data.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        return true;
    }

}

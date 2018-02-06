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
 * 作者: 边贤君
 * 描述: 测量升高页面
 * 创建日期: 2018/1/10 11:43
 */
public class InputHeightActivity extends BaseInputDataActivity {

    private String suggest = "<p><font color=\"#EF4399\">* 建议：</font>身高测量宜在清晨进行；身高测量应在医 院用标准的量床测量。</p>";

    public final static String IN_PARAM_HEIGHT = "in_height";
    public final static String OUT_PARAM_HEIGHT = "height";

    private MyGifPlayer player;
    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title;

    private EditText et_input_data;
    private TextView tv_reference,tv_increase,tv_input_title;
    private LinearLayout ll_content , ll_remark;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);

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
        tv_title.setText(R.string.assessment_height);
        rl_complete.setVisibility(View.VISIBLE);

        rl_complete.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);
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
            tv_increase.setText(increase);
        }
        //标题
        tv_input_title.setText(getResources().getString(R.string.height_input_title));
        //内容
        generateListView(ll_content,getResources().getStringArray(R.array.height_content));
        //备注
        generateListView(ll_remark,getResources().getStringArray(R.array.height_remark));

        Intent intent = getIntent();
        if ( intent != null ) {
            String height = intent.getStringExtra(IN_PARAM_HEIGHT);
            et_input_data.setText(height);
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
            intent.putExtra(OUT_PARAM_HEIGHT,et_input_data.getText().toString());
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean checkData(){
        String heightString = et_input_data.getText().toString().trim();

        if ( TextUtils.isEmpty(heightString) ) {
            showPromptDialog("身高数据不能为空");
            return false;
        }
        float height = Float.parseFloat(et_input_data.getText().toString().trim());
        if ( height <= 0 || height > 250 ){
            showPromptDialog("身高数据需要在0~250cm之间");
            return false;
        }
        return true;
    }

}

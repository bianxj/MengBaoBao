package com.doumengmeng.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.adapter.DiagramDataAdapter;
import com.doumengmeng.customer.base.BaseActivity;
import com.doumengmeng.customer.response.entity.ImageData;
import com.doumengmeng.customer.util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 曲线图详情
 * 创建日期: 2018/1/17 13:42
 */
public class DiagramDataActivity extends BaseActivity {

    public enum DIAGRAM_TYPE{
        TYPE_HEIGHT,
        TYPE_WEIGHT,
        TYPE_HEIGHT_WEIGHT
    }

    public final static String IN_PARAM_IMAGE_DATA = "image_data";
    public final static String IN_PARAM_TYPE = "type";

    private RelativeLayout rl_back;
    private TextView tv_title;

    private TextView tv_title_left , tv_title_center , tv_title_right;
    private ListView lv;

//    private List<ImageData> datas;
    private DiagramDataAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram_data);
        findView();
        initView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tv_title_left = findViewById(R.id.tv_title_left);
        tv_title_center = findViewById(R.id.tv_title_center);
        tv_title_right = findViewById(R.id.tv_title_right);
        lv = findViewById(R.id.lv);
    }

    private void initView(){
        rl_back.setOnClickListener(listener);

        Intent intent = getIntent();
        String param = intent.getStringExtra(IN_PARAM_IMAGE_DATA);
        int type = intent.getIntExtra(IN_PARAM_TYPE,DIAGRAM_TYPE.TYPE_HEIGHT.ordinal());
        List<ImageData> datas = GsonUtil.getInstance().fromJson(param,new TypeToken<List<ImageData>>(){}.getType());

        DIAGRAM_TYPE tt;
        tv_title_left.setText(getResources().getString(R.string.diagram_data_date));
        if ( type == DIAGRAM_TYPE.TYPE_HEIGHT.ordinal() ){
            tv_title.setText(getResources().getString(R.string.diagram_data_height_record));
            tt = DIAGRAM_TYPE.TYPE_HEIGHT;
            tv_title_center.setText(getResources().getString(R.string.diagram_data_baby_age));
            tv_title_right.setText(getResources().getString(R.string.diagram_data_height));
        } else if ( type == DIAGRAM_TYPE.TYPE_WEIGHT.ordinal() ){
            tv_title.setText(getResources().getString(R.string.diagram_data_weight_record));
            tt = DIAGRAM_TYPE.TYPE_WEIGHT;
            tv_title_center.setText(getResources().getString(R.string.diagram_data_baby_age));
            tv_title_right.setText(getResources().getString(R.string.diagram_data_weight));
        } else {
            tv_title.setText(getResources().getString(R.string.diagram_data_height_weight_ratio));
            tt = DIAGRAM_TYPE.TYPE_HEIGHT_WEIGHT;
            tv_title_center.setText(getResources().getString(R.string.diagram_data_height));
            tv_title_right.setText(getResources().getString(R.string.diagram_data_weight));
        }
        adapter = new DiagramDataAdapter(this,datas,tt);
        lv.setAdapter(adapter);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

}

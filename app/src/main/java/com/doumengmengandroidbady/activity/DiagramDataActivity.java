package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.DiagramDataAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.response.ImageData;
import com.doumengmengandroidbady.util.GsonUtil;
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

    private List<ImageData> datas;
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
        datas = GsonUtil.getInstance().getGson().fromJson(param,new TypeToken<List<ImageData>>(){}.getType());

        DIAGRAM_TYPE tt;
        tv_title_left.setText("日期");
        if ( type == DIAGRAM_TYPE.TYPE_HEIGHT.ordinal() ){
            tv_title.setText("身高记录");
            tt = DIAGRAM_TYPE.TYPE_HEIGHT;
            tv_title_center.setText("宝宝年龄");
            tv_title_right.setText("身高cm");
        } else if ( type == DIAGRAM_TYPE.TYPE_WEIGHT.ordinal() ){
            tv_title.setText("体重记录");
            tt = DIAGRAM_TYPE.TYPE_WEIGHT;
            tv_title_center.setText("宝宝年龄");
            tv_title_right.setText("体重kg");
        } else {
            tv_title.setText("身长体重比");
            tt = DIAGRAM_TYPE.TYPE_HEIGHT_WEIGHT;
            tv_title_center.setText("身高cm");
            tv_title_right.setText("体重kg");
        }
        adapter = new DiagramDataAdapter(this,datas,tt);
        lv.setAdapter(adapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
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

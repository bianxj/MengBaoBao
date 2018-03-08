package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.view.CheckBoxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class GuideActivity extends BaseActivity {

    private Button bt_experience;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findView();
        initView();
    }

    private void initView(){
        CheckBoxLayout cbl = findViewById(R.id.cbl);
        List<String> contents = new ArrayList<>();
        contents.add("主治医师");
        contents.add("主任医师");
        contents.add("副主任医师");
        contents.add("住院医师");
        contents.add("住院医师");
        contents.add("住院医师");
        contents.add("住院医师");
        contents.add("住院医师");
        contents.add("住院医师");
        contents.add("住院医师");
        contents.add("住院医师");
        contents.add("测试1");
        contents.add("测试2");
        contents.add("测试4");
        contents.add("测试3");
        contents.add("测试7");
        cbl.setCheckBoxes(contents,false);
    }

    private void findView(){
        bt_experience = findViewById(R.id.bt_experience);
        bt_experience.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.bt_experience:
                    startActivity(LoginActivity.class);
//                    initView();
                    break;
            }
        }
    };

}

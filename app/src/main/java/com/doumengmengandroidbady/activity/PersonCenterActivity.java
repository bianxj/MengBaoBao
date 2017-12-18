package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.util.MyDialog;

/**
 * Created by Administrator on 2017/12/8.
 */

public class PersonCenterActivity extends BaseActivity {

    private TextView tv_baby_name ,tv_baby_month;
    private TextView tv_title;
    private RelativeLayout rl_back;
    private RelativeLayout rl_child_info , rl_parent_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        findView();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        tv_title = findViewById(R.id.tv_title);
        rl_back = findViewById(R.id.rl_back);
        rl_child_info = findViewById(R.id.rl_child_info);
        rl_parent_info = findViewById(R.id.rl_parent_info);

        tv_baby_name = findViewById(R.id.tv_baby_name);
        tv_baby_month = findViewById(R.id.tv_baby_month);
    }

    private void initView(){
        tv_title.setText(R.string.person_center);

        if (!BaseApplication.getInstance().isPay()){
            tv_baby_month.setVisibility(View.INVISIBLE);
        }

        rl_back.setOnClickListener(listener);
        rl_child_info.setOnClickListener(listener);
        rl_parent_info.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_child_info:
                    switchToChildInfo();
                    break;
                case R.id.rl_parent_info:
                    switchToParentInfo();
                    break;
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void switchToChildInfo(){
        if ( BaseApplication.getInstance().isPay() ){
            startActivity(BaseInfoActivity.class);
        } else {
            showNeedByDialog();
        }
    }

    private void switchToParentInfo(){
        if ( BaseApplication.getInstance().isPay() ){
            startActivity(ParentInfoActivity.class);
        } else {
            showNeedByDialog();
        }
    }

    private void showNeedByDialog(){
        MyDialog.showChooseDialog(this, getString(R.string.prompt_need_buy),R.string.prompt_bt_cancel,R.string.prompt_bt_buy, new MyDialog.ChooseDialogCallback() {
            @Override
            public void sure() {
                startActivity(SpacialistServiceActivity.class);
            }

            @Override
            public void cancel() {}
        });
    }

}

package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;

/**
 * Created by Administrator on 2017/12/8.
 */
public class CallCenterActivity extends BaseActivity {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private ImageView iv_message_board;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        findView();
    }

    private void findView(){
        initTitle();
        initContent();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.call_center);

        rl_back.setOnClickListener(listener);
    }

    private void initContent(){
        iv_message_board = findViewById(R.id.iv_message_board);

        iv_message_board.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.iv_message_board:
                    goMessageBoard();
                    break;
            }
        }
    };

    private void goMessageBoard(){
        startActivity(MessageBoardActivity.class);
    }

    private void back(){
        finish();
    }

}

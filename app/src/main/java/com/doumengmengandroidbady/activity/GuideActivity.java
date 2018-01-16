package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.view.AutoScrollViewPager;
import com.doumengmengandroidbady.view.DiagramView;

/**
 * Created by Administrator on 2017/12/5.
 */
//TODO
public class GuideActivity extends BaseActivity {

    private AutoScrollViewPager asvp;
    private Button bt_guide_register , bt_guide_login;

    private DiagramView d_weight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findView();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        asvp = findViewById(R.id.asvp);
        bt_guide_register = findViewById(R.id.bt_guide_register);
        bt_guide_login = findViewById(R.id.bt_guide_login);
    }

    private void initView(){
        bt_guide_register.setOnClickListener(listener);
        bt_guide_login.setOnClickListener(listener);

        int[] images = new int[]{R.drawable.v1,R.drawable.v2,R.drawable.v3};
        asvp.setImageList(images);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_guide_register:
                    startActivity(RegisterActivity.class);
                    break;
                case R.id.bt_guide_login:
                    startActivity(LoginActivity.class);
                    break;
            }
        }
    };

}

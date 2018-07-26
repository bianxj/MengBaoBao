package com.doumengmeng.customer.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseSwipeActivity;
import com.doumengmeng.customer.view.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 引导页
 * 创建日期: 2018/2/9 14:47
 */
public class GuideActivity extends BaseSwipeActivity {

    private AutoScrollViewPager asvp;
    private Button bt_guide_register , bt_guide_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
//        setSwipeEnabled(false);
        findView();
        initView();
    }

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        if (BaseApplication.getInstance().hasAccountData()){
            Intent intent = new Intent(this,LoadingActivity.class);
            intent.putExtra(LoadingActivity.IN_PARAM_AUTO_LOGIN,true);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findView(){
        bt_guide_register = findViewById(R.id.bt_guide_register);
        bt_guide_login = findViewById(R.id.bt_guide_login);
    }

    private void initView(){
        bt_guide_register.setOnClickListener(listener);
        bt_guide_login.setOnClickListener(listener);
        asvp = findViewById(R.id.asvp);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.icon_guide_1);
        images.add(R.drawable.icon_guide_2);
        images.add(R.drawable.icon_guide_3);
//        int[] images = new int[]{R.drawable.icon_guide_1,R.drawable.icon_guide_2,R.drawable.icon_guide_3};
        asvp.setImageList(images);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //截取返回键
        if ( keyCode == KeyEvent.KEYCODE_BACK ){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

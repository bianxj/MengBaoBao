package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseFragmentActivity;
import com.doumengmeng.doctor.fragment.AboutFragment;
import com.doumengmeng.doctor.fragment.AccountFragment;
import com.doumengmeng.doctor.fragment.HomeFragment;
import com.doumengmeng.doctor.fragment.MessageDetailFragment;
import com.doumengmeng.doctor.fragment.MessageFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/3/1.
 */

public class MainActivity extends BaseFragmentActivity {

//    private FrameLayout fl_content;
//    private FrameLayout fl_home,fl_account,fl_message,fl_about;

    //首页
    private static final String PAGE_HOME = "page_home";
    //账户
    private static final String PAGE_ACCOUNT = "page_account";
    //消息
    public static final String PAGE_MESSAGE = "page_message";
    //我
    private static final String PAGE_ABOUT = "page_about";
    //消息详情
    public static final String PAGE_MESSAGE_DETAIL = "page_message_detail";

    private TextView tv_home_count,tv_message_count;

    private FragmentManager fm;
    private Fragment currentFragment;
    private Map<String,Fragment> fragmentMap = new HashMap<>();
    private final Map<String,FrameLayout> bottomMap = new HashMap<>();
    private String page;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setDefaultPage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCornerMark();
    }

    private void findView(){
//        fl_content = findViewById(R.id.fl_content);
        tv_home_count = findViewById(R.id.tv_home_count);
        tv_message_count = findViewById(R.id.tv_message_count);
        initView();
    }

    private void initView(){
        initFragment();
    }

    private void initFragment(){
        bottomMap.put(PAGE_HOME,(FrameLayout) findViewById(R.id.fl_home));
        bottomMap.put(PAGE_ACCOUNT,(FrameLayout) findViewById(R.id.fl_account));
        bottomMap.put(PAGE_MESSAGE,(FrameLayout) findViewById(R.id.fl_message));
        bottomMap.put(PAGE_ABOUT,(FrameLayout) findViewById(R.id.fl_about));

        Set<String> keys = bottomMap.keySet();
        for (String key:keys){
            bottomMap.get(key).setOnClickListener(listener);
        }

        fm = getSupportFragmentManager();
        fragmentMap.put(PAGE_HOME,new HomeFragment());
        fragmentMap.put(PAGE_ACCOUNT,new AccountFragment());
        fragmentMap.put(PAGE_MESSAGE,new MessageFragment());
        fragmentMap.put(PAGE_ABOUT,new AboutFragment());
        fragmentMap.put(PAGE_MESSAGE_DETAIL,new MessageDetailFragment());
    }

    private void setDefaultPage(){
        switchFragment(PAGE_HOME);
    }

    private void refreshCornerMark(){
//        tv_home_count.setText();
//        tv_message_count.setText();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.fl_home:
                    switchFragment(PAGE_HOME);
                    break;
                case R.id.fl_account:
                    switchFragment(PAGE_ACCOUNT);
                    break;
                case R.id.fl_message:
                    switchFragment(PAGE_MESSAGE);
                    break;
                case R.id.fl_about:
                    switchFragment(PAGE_ABOUT);
                    break;
            }
        }
    };

    private void switchFragment(String page){
        this.page = page;
//        BaseApplication.getInstance().saveMainPage(page);
        Fragment fragment = fragmentMap.get(page);
        if ( !fragment.isVisible() ){
            FragmentTransaction transaction = fm.beginTransaction();
            if ( currentFragment != null ){
                transaction.hide(currentFragment);
            }
            if (fragment.isAdded()) {
                transaction.show(fragment);
            } else {
                transaction.add(R.id.fl_content,fragment).show(fragment);
            }
            currentFragment = fragment;
            transaction.commitAllowingStateLoss();
            refreshBottomMenu(page);
        }
    }

    private void refreshBottomMenu(String page){
        Set<String> keys = bottomMap.keySet();
        for (String key:keys) {
            FrameLayout layout = bottomMap.get(key);
            CheckBox button = (CheckBox) layout.getChildAt(0);
            TextView textView = (TextView) layout.getChildAt(1);
            if ( key.equals(page) ){
                button.setChecked(true);
                textView.setTextColor(ContextCompat.getColor(this,R.color.btmMenuColor));
            } else {
                button.setChecked(false);
                textView.setTextColor(ContextCompat.getColor(this,R.color.seven_gray));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //截取返回键
        if ( keyCode == KeyEvent.KEYCODE_BACK ){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

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
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseFragment;
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


    private FragmentManager fm;
    private Fragment currentFragment;
    private Map<String,BaseFragment> fragmentMap = new HashMap<>();
    private final Map<String,FrameLayout> bottomMap = new HashMap<>();
    private String page;

//    private UpdateSuperScriptReceiver receiver;
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
        refreshSuperScript();
        register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregister();
    }

    private void findView(){
//        fl_content = findViewById(R.id.fl_content);
        initView();
    }

    private void initView(){
        initFragment();
        initSuperScript();
    }

    private void initFragment(){
        bottomMap.put(PAGE_HOME,(FrameLayout) findViewById(R.id.fl_home));
        bottomMap.put(PAGE_ACCOUNT,(FrameLayout) findViewById(R.id.fl_account));
        bottomMap.put(PAGE_MESSAGE,(FrameLayout) findViewById(R.id.fl_message));
        bottomMap.put(PAGE_MESSAGE_DETAIL,(FrameLayout) findViewById(R.id.fl_message));
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

    private TextView tv_home_count,tv_message_count;
    private void initSuperScript(){
        tv_home_count = findViewById(R.id.tv_home_count);
        tv_message_count = findViewById(R.id.tv_message_count);
        refreshSuperScript();

//        receiver = new UpdateSuperScriptReceiver(this);
    }

    private void register(){
//        IntentFilter filter = new IntentFilter(UpdateSuperScriptReceiver.ACTION_UPDATE_SCRIPT);
//        registerReceiver(receiver,filter);
    }

    private void unregister(){
//        if ( receiver != null ){
//            unregisterReceiver(receiver);
//        }
    }

    private void setDefaultPage(){
        switchFragment(PAGE_HOME);
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

    public void switchFragment(String page){
        switchFragment(page,null);
    }

    public void switchFragment(String page, Object object){
        this.page = page;
//        BaseApplication.getInstance().saveMainPage(page);
        BaseFragment fragment = fragmentMap.get(page);
        if ( object != null ){
            fragment.setObject(object);
        }
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
            if ( PAGE_MESSAGE.equals(page) && PAGE_MESSAGE_DETAIL.equals(key)){
                continue;
            }
            if ( PAGE_MESSAGE_DETAIL.equals(page) && PAGE_MESSAGE.equals(key)){
                continue;
            }
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
            BaseApplication.getInstance().finishApp(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void refreshSuperScript(){
        int messgeCount = BaseApplication.getInstance().getMessageCount();
        int assessmentCount = BaseApplication.getInstance().getAssessmentCount();
        refreshSuperScriptItem(tv_home_count,assessmentCount);
        refreshSuperScriptItem(tv_message_count,messgeCount);
    }

    private void refreshSuperScriptItem(TextView tv_script, int count){
        if ( count <= 0 ){
            tv_script.setVisibility(View.GONE);
        } else {
            if ( count > 99 ){
                tv_script.setText("99+");
            } else {
                tv_script.setText(count+"");
            }
            tv_script.setVisibility(View.VISIBLE);
        }
    }

}

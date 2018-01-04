package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.SideMenuAdapter;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.base.BaseFragmentActivity;
import com.doumengmengandroidbady.entity.SideMenuItem;
import com.doumengmengandroidbady.fragment.BabyKnowledgeFragment;
import com.doumengmengandroidbady.fragment.HomePageFragment;
import com.doumengmengandroidbady.fragment.HospitalReportFragment;
import com.doumengmengandroidbady.fragment.LessonFragment;
import com.doumengmengandroidbady.fragment.SpacialistServiceFragment;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//TODO
public class MainActivity extends BaseFragmentActivity {

    public static final String SHOW_PAGE = "show_page";

    public static final String PAGE_HOME = "home_page";
    public static final String PAGE_BABY_KNOWLEDGE = "baby_knowledge_page";
    public static final String PAGE_SPACIALIST_SERVICE = "spacialist_service_page";
    public static final String PAGE_HOSPITAL_REPORT = "hospital_report_page";
    public static final String PAGE_LESSON = "lesson_page";

    private DrawerLayout dl_main;
    private FrameLayout fl_content;

    private RelativeLayout rl_home_page , rl_baby_knowledge , rl_spacialist_service , rl_hospital_report , rl_meng_lesson;
//    private ImageView iv_home_page , iv_baby_knowledge , iv_spacialist_service , iv_hospital_report , iv_meng_lesson;
//    private TextView tv_home_page , tv_baby_knowledge , tv_spacialist_service , tv_hospital_report , tv_meng_lesson;

    private FragmentManager fm;
    private Fragment currentFragment;
    private Map<String,Fragment> fragmentMap;
    private Map<String,RelativeLayout> bottomMap = new HashMap<>();

    private ListView lv_side_menu;
    private SideMenuAdapter adapter;

    private CircleImageView civ_head;
    private CheckBox cb_male;
    private TextView tv_baby_name , tv_baby_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
        initFragment();
        showFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void findView(){
        fl_content = findViewById(R.id.fl_content);

        rl_home_page = findViewById(R.id.rl_home_page);
        rl_baby_knowledge = findViewById(R.id.rl_baby_knowledge);
        rl_spacialist_service = findViewById(R.id.rl_spacialist_service);
        rl_hospital_report = findViewById(R.id.rl_hospital_report);
        rl_meng_lesson = findViewById(R.id.rl_meng_lesson);

        lv_side_menu = findViewById(R.id.lv_side_menu);
        dl_main = findViewById(R.id.dl_main);

        civ_head = findViewById(R.id.civ_head);
        cb_male = findViewById(R.id.cb_male);
        tv_baby_name = findViewById(R.id.tv_baby_name);
        tv_baby_age = findViewById(R.id.tv_baby_age);
    }

    private void initView() {
        bottomMap.put(PAGE_HOME,rl_home_page);
        bottomMap.put(PAGE_BABY_KNOWLEDGE,rl_baby_knowledge);
        bottomMap.put(PAGE_SPACIALIST_SERVICE,rl_spacialist_service);
        bottomMap.put(PAGE_HOSPITAL_REPORT,rl_hospital_report);
        bottomMap.put(PAGE_LESSON,rl_meng_lesson);

        rl_home_page.setOnClickListener(listener);
        rl_baby_knowledge.setOnClickListener(listener);
        rl_spacialist_service.setOnClickListener(listener);
        rl_hospital_report.setOnClickListener(listener);
        rl_meng_lesson.setOnClickListener(listener);

        initSideMenu();
    }

    private void initSideMenu(){
        List<SideMenuItem> items = new ArrayList<SideMenuItem>();
        items.add(new SideMenuItem(R.drawable.icon_person_center,R.string.person_center,PersonCenterActivity.class));
        items.add(new SideMenuItem(R.drawable.icon_shop,R.string.shop,SpacialistServiceActivity.class));
        items.add(new SideMenuItem(R.drawable.icon_contact,R.string.contact,ContactActivity.class));
        items.add(new SideMenuItem(R.drawable.icon_about,R.string.about,AboutActivity.class));
        items.add(new SideMenuItem(R.drawable.icon_setting,R.string.setting,SettingActivity.class));

        dl_main.addDrawerListener(drawerListener);
        adapter = new SideMenuAdapter(this,items);
        lv_side_menu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv_side_menu.setOnItemClickListener(itemClickListener);
    }

    private void initFragment(){
        fm = getSupportFragmentManager();
        fragmentMap = new HashMap<String,Fragment>();
        fragmentMap.put(PAGE_HOME,new HomePageFragment());
        fragmentMap.put(PAGE_BABY_KNOWLEDGE,new BabyKnowledgeFragment());
        fragmentMap.put(PAGE_LESSON,new LessonFragment());
        fragmentMap.put(PAGE_SPACIALIST_SERVICE,new SpacialistServiceFragment());
        fragmentMap.put(PAGE_HOSPITAL_REPORT,new HospitalReportFragment());
    }

    private void initData(){
        UserData userData = BaseApplication.getInstance().getUserData();
        if (TextUtils.isEmpty(userData.getHeadimg())){

        } else {
            ImageLoader.getInstance().displayImage(userData.getHeadimg(),civ_head);
        }
        tv_baby_age.setText(userData.getBabyAge());
        tv_baby_name.setText(userData.getTruename());
        cb_male.setChecked(userData.isMale());
    }

    private void showFragment(){
        Intent intent = getIntent();
        if ( intent != null && intent.getStringExtra(SHOW_PAGE) != null ){
            String page = intent.getStringExtra(SHOW_PAGE);
            switchFragment(page);
        } else {
            switchFragment(PAGE_HOME);
        }
    }

    private DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {}

        @Override
        public void onDrawerOpened(View drawerView) {
//            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
//            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        @Override
        public void onDrawerStateChanged(int newState) {}
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_home_page:
                    switchFragment(PAGE_HOME);
                    dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    break;
                case R.id.rl_baby_knowledge:
                    switchFragment(PAGE_BABY_KNOWLEDGE);
                    dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
                case R.id.rl_spacialist_service:
                    switchFragment(PAGE_SPACIALIST_SERVICE);
                    dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
                case R.id.rl_hospital_report:
                    switchFragment(PAGE_HOSPITAL_REPORT);
                    dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
                case R.id.rl_meng_lesson:
                    switchFragment(PAGE_LESSON);
                    dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
            }
        }
    };

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SideMenuItem item = (SideMenuItem) adapter.getItem(position);
            startActivity(item.getSwitchActivity());
        }
    };

    public void toggleSideMenu(){
        if ( dl_main.isDrawerOpen(Gravity.LEFT) ){
            dl_main.closeDrawer(Gravity.LEFT);
        } else {
            dl_main.openDrawer(Gravity.LEFT);
        }
    }

    private void switchFragment(String page){
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
            RelativeLayout layout = bottomMap.get(key);
            CheckBox button = (CheckBox) layout.getChildAt(0);
            TextView textView = (TextView) layout.getChildAt(1);
            if ( key.equals(page) ){
                button.setChecked(true);
                textView.setTextColor(ContextCompat.getColor(this,R.color.btmMenuColor));
            } else {
                button.setChecked(false);
                textView.setTextColor(ContextCompat.getColor(this,R.color.btmMenuUnColor));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK ){
            if ( dl_main.isDrawerOpen(Gravity.LEFT) ) {
                dl_main.closeDrawer(Gravity.LEFT);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

package com.doumengmeng.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.adapter.SideMenuAdapter;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseFragmentActivity;
import com.doumengmeng.customer.entity.RoleType;
import com.doumengmeng.customer.entity.SideMenuItem;
import com.doumengmeng.customer.fragment.BabyKnowledgeFragment;
import com.doumengmeng.customer.fragment.HomePageFragment;
import com.doumengmeng.customer.fragment.HospitalReportFragment;
import com.doumengmeng.customer.fragment.LessonFragment;
import com.doumengmeng.customer.fragment.SpacialistServiceFragment;
import com.doumengmeng.customer.response.entity.DayList;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.AppUtil;
import com.doumengmeng.customer.util.MyDialog;
import com.doumengmeng.customer.util.NotificationUtil;
import com.doumengmeng.customer.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者: 边贤君
 * 描述: 主页面
 * 创建日期: 2018/1/8 10:15
 */
public class MainActivity extends BaseFragmentActivity {

    public static final String SHOW_PAGE = "show_page";

    //首页
    private static final String PAGE_HOME = "home_page";
    //育儿知识
    private static final String PAGE_BABY_KNOWLEDGE = "baby_knowledge_page";
    //专家服务
    public static final String PAGE_SPACIALIST_SERVICE = "spacialist_service_page";
    //医院报告
    private static final String PAGE_HOSPITAL_REPORT = "hospital_report_page";
    //萌课堂
    private static final String PAGE_LESSON = "lesson_page";

    //抽屉
    private DrawerLayout dl_main;

    private RelativeLayout rl_home_page , rl_baby_knowledge , rl_spacialist_service , rl_hospital_report , rl_meng_lesson;

    private FragmentManager fm;
    private Fragment currentFragment;
    private Map<String,Fragment> fragmentMap;
    private final Map<String,RelativeLayout> bottomMap = new HashMap<>();

    private ListView lv_side_menu;
    private SideMenuAdapter adapter;

    private CircleImageView civ_head;
    private CheckBox cb_male;
    private TextView tv_baby_name , tv_baby_age;

    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
        initFragment();
        loadHomePage();
        checkNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        showDefaultFragment();
    }

    private void loadHomePage(){
        page = BaseApplication.getInstance().getMainPage();
        if ( page == null ) {
            page = PAGE_HOME;
        }
//        switchFragment(page);
    }

    private void findView(){

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
        List<SideMenuItem> items = new ArrayList<>();
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

    private void checkNotification(){
        if ( BaseApplication.getInstance().isFistLogin() ){
            BaseApplication.getInstance().saveLoginCount();
            MyDialog.showNotificationDialog(this, new MyDialog.NotificationCallback() {
                @Override
                public void sure() {
                    if (!NotificationUtil.isNotificationEnable()){
                        MyDialog.showChooseDialog(MainActivity.this, "萌宝宝想给您发送的通知", R.string.dialog_btn_prompt_later, R.string.prompt_bt_allow, new MyDialog.ChooseDialogCallback() {
                            @Override
                            public void sure() {
                                AppUtil.openPrimession(MainActivity.this);
                            }

                            @Override
                            public void cancel() {

                            }
                        });
                    }
                }

                @Override
                public void cancel() {

                }
            });
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 初始化子页面
     * 日期: 2018/1/8 10:23
     */
    private void initFragment(){
        fm = getSupportFragmentManager();
        fragmentMap = new HashMap<>();
        fragmentMap.put(PAGE_HOME,new HomePageFragment());
        fragmentMap.put(PAGE_BABY_KNOWLEDGE,new BabyKnowledgeFragment());
        fragmentMap.put(PAGE_LESSON,new LessonFragment());
        fragmentMap.put(PAGE_SPACIALIST_SERVICE,new SpacialistServiceFragment());
        fragmentMap.put(PAGE_HOSPITAL_REPORT,new HospitalReportFragment());
    }

    private void initData(){
        DayList dayList = BaseApplication.getInstance().getDayList();
        UserData userData = BaseApplication.getInstance().getUserData();
        if (RoleType.FREE_NET_USER == BaseApplication.getInstance().getRoleType() || RoleType.FREE_HOSPITAL_USER == BaseApplication.getInstance().getRoleType()){
            loadHeadImg(userData.isMale(),"");
            tv_baby_name.setVisibility(View.INVISIBLE);
            tv_baby_age.setVisibility(View.INVISIBLE);
            cb_male.setVisibility(View.INVISIBLE);
        } else {
            loadHeadImg(userData.isMale(),userData.getHeadimg());
            tv_baby_name.setVisibility(View.VISIBLE);
            tv_baby_age.setVisibility(View.VISIBLE);
            cb_male.setVisibility(View.VISIBLE);
            tv_baby_age.setText(String.format(getResources().getString(R.string.home_page_month_age),dayList.getCurrentMonth(),dayList.getCurrentDay()));
            cb_male.setChecked(userData.isMale());
            tv_baby_name.setText(userData.getTruename());
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 加载头像
     * 参数:
     * 日期: 2018/1/8 10:35
     */
    private void loadHeadImg(boolean isMale,String urlHeadImg){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        if ( isMale ){
            builder.showImageOnLoading(R.drawable.default_icon_boy);
            builder.showImageForEmptyUri(R.drawable.default_icon_boy);
            builder.showImageOnFail(R.drawable.default_icon_boy);
        } else {
            builder.showImageOnLoading(R.drawable.default_icon_girl);
            builder.showImageForEmptyUri(R.drawable.default_icon_girl);
            builder.showImageOnFail(R.drawable.default_icon_girl);
        }
        ImageLoader.getInstance().displayImage(urlHeadImg,civ_head,builder.build());
    }

    private void showDefaultFragment(){
        Intent intent = getIntent();
        if ( intent != null && intent.getStringExtra(SHOW_PAGE) != null ){
            page = intent.getStringExtra(SHOW_PAGE);
            setIntent(null);
        }
        switchFragment(page);
    }

    private final DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {}

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {}

        @Override
        public void onDrawerStateChanged(int newState) {}
    };

    private final View.OnClickListener listener = new View.OnClickListener() {
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
                    if ( RoleType.PAY_HOSPITAL_USER == BaseApplication.getInstance().getRoleType() || RoleType.FREE_HOSPITAL_USER == BaseApplication.getInstance().getRoleType() ){
                        switchFragment(PAGE_HOSPITAL_REPORT);
                        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    } else {
                        MyDialog.showPromptDialog(MainActivity.this,"您在医院里没有做过“萌宝宝”测评，所以没有报告哦！",null);
                    }
                    break;
                case R.id.rl_meng_lesson:
                    switchFragment(PAGE_LESSON);
                    dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    break;
            }
        }
    };

    private final AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SideMenuItem item = (SideMenuItem) adapter.getItem(position);
            startActivity(item.getSwitchActivity());
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 展开或收合抽屉
     * 日期: 2018/1/8 10:25
     */
    public void toggleSideMenu(){
        if ( dl_main.isDrawerOpen(Gravity.LEFT) ){
            dl_main.closeDrawer(Gravity.LEFT);
        } else {
            dl_main.openDrawer(Gravity.LEFT);
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 切换子页面
     * 参数: 
     * 日期: 2018/1/8 10:25
     */
    private void switchFragment(String page){
        this.page = page;
        BaseApplication.getInstance().saveMainPage(page);
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

    /**
     * 作者: 边贤君
     * 描述: 刷新Tab标签
     * 参数:
     * 日期: 2018/1/8 10:26
     */
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
                textView.setTextColor(ContextCompat.getColor(this,R.color.seven_gray));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //截取返回键
        if ( keyCode == KeyEvent.KEYCODE_BACK ){
            if ( dl_main.isDrawerOpen(Gravity.LEFT) ) {
                dl_main.closeDrawer(Gravity.LEFT);
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.SideMenuAdapter;
import com.doumengmengandroidbady.base.BaseFragmentActivity;
import com.doumengmengandroidbady.entity.SideMenuItem;

import java.util.ArrayList;
import java.util.List;

//TODO
public class MainActivity extends BaseFragmentActivity {

    private DrawerLayout dl_main;
    private FrameLayout fl_content;
    private RelativeLayout rl_home_page , rl_baby_knowledge , rl_spacialist_service , rl_hospital_report , rl_meng_lesson;
//    private ImageView iv_home_page , iv_baby_knowledge , iv_spacialist_service , iv_hospital_report , iv_meng_lesson;
//    private TextView tv_home_page , tv_baby_knowledge , tv_spacialist_service , tv_hospital_report , tv_meng_lesson;

    private ListView lv_side_menu;

    private SideMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        configView();
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
    }

    private void configView() {
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
        items.add(new SideMenuItem(R.drawable.icon_shop,R.string.shop,ShopActivity.class));
        items.add(new SideMenuItem(R.drawable.icon_contact,R.string.contact,ContactActivity.class));
        items.add(new SideMenuItem(R.drawable.icon_about,R.string.about,AboutActivity.class));
        items.add(new SideMenuItem(R.drawable.icon_setting,R.string.setting,SettingActivity.class));

        dl_main.addDrawerListener(drawerListener);
        adapter = new SideMenuAdapter(this,items);
        lv_side_menu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv_side_menu.setOnItemClickListener(itemClickListener);
    }

    private DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {}

        @Override
        public void onDrawerOpened(View drawerView) {
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        @Override
        public void onDrawerStateChanged(int newState) {}
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_home_page:
                    toggleSideMenu();
                    break;
                case R.id.rl_baby_knowledge:
                    break;
                case R.id.rl_spacialist_service:
                    break;
                case R.id.rl_hospital_report:
                    break;
                case R.id.rl_meng_lesson:
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

    private void toggleSideMenu(){
        if ( dl_main.isDrawerOpen(Gravity.LEFT) ){
            dl_main.closeDrawer(Gravity.LEFT);
        } else {
            dl_main.openDrawer(Gravity.LEFT);
        }
    }

    private void switchFragment(Fragment fragment){

    }

    private void refreshBottomMenu(ViewGroup group){

    }

}

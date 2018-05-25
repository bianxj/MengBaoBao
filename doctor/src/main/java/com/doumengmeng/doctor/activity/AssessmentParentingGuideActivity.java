package com.doumengmeng.doctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.ParentingGuideAdapter;
import com.doumengmeng.doctor.base.BaseTimeFragmentActivity;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.fragment.ParentingGuideFragment;
import com.doumengmeng.doctor.response.entity.Nurture;
import com.doumengmeng.doctor.util.FormulaUtil;
import com.doumengmeng.doctor.util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/3/1.
 */

public class AssessmentParentingGuideActivity extends BaseTimeFragmentActivity {

    public final static String IN_PARAM_MONTH_AGE = "in_month_age";
    public final static String IN_PARAM_VALIDITY_TIME = "in_validity_time";
    public final static String IN_PARAM_SELECTED_NURTURE = "in_selected_nurture";

    public final static String OUT_PARAM_SELECTED_NUTRURE = "out_selected_nurture";

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title;

    private TextView tv_over_time;
    private TabLayout tab_layout;
    private ViewPager vp;
    private ParentingGuideAdapter adapter;
    private FragmentManager fm;

    private List<ParentingGuideFragment> fragments = new ArrayList<>();

    private List<Nurture> nurtures = new ArrayList<>();
    private List<Nurture> customs = new ArrayList<>();
    private Map<String,List<Nurture>>  nurtureMap;
    private String validityTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_parenting_guide);
        findView();
    }

    private void findView(){
        initTitle();
        initOverTime();
        initParentingGuide();
        minuteCallBack();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);

        rl_complete.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);
        tv_title.setText(R.string.parenting_guide);
    }

    private void initOverTime(){
        validityTime = getIntent().getStringExtra(IN_PARAM_VALIDITY_TIME);
        tv_over_time = findViewById(R.id.tv_over_time);
    }

    private void initParentingGuide(){
        fm = getSupportFragmentManager();
        tab_layout = findViewById(R.id.tab);
        vp = findViewById(R.id.vp);

        //TODO
        int age = getIntent().getIntExtra(IN_PARAM_MONTH_AGE,0);
        String nurturesValue =  getIntent().getStringExtra(IN_PARAM_SELECTED_NURTURE);

        List<Nurture> nurtures = DaoManager.getInstance().getNurtureDao().searchNurtureByAge(this,age);
        if (!TextUtils.isEmpty(nurturesValue)){
            List<Nurture> selectedNurtures = GsonUtil.getInstance().fromJson(nurturesValue,new TypeToken<List<Nurture>>(){}.getType());
            for (Nurture nurture:nurtures){
                if ( selectedNurtures.contains(nurture) ){
                    nurture.setChoose(true);
                }
            }
            for (Nurture nurture:selectedNurtures){
                if ( nurture.isCustom() ){
                    customs.add(nurture);
                }
            }
        }
        nurtureMap = classifyNurtures(nurtures);
        buildTabTitles(tab_layout,nurtureMap.keySet());
        buildPageFragments(vp,fragments,nurtureMap,age+"");
    }

    private void buildPageFragments(ViewPager vp,List<ParentingGuideFragment> fragments,Map<String, List<Nurture>> nurtureMap,String age){
        adapter = new ParentingGuideAdapter(fm);
        Set<String> keys = nurtureMap.keySet();
        for (String key:keys){
            ParentingGuideFragment fragment = new ParentingGuideFragment();
            List<Nurture> nurtures = nurtureMap.get(key);
            nurtures.add(buildCustomNurture(nurtures.get(0)));
            fragment.setNurtures(age,nurtureMap.get(key));
            adapter.addFragment(fragment);
            fragments.add(fragment);
        }
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(pageChangeListener);
        adapter.notifyDataSetChanged();
    }

    private Nurture buildCustomNurture(Nurture nurture){
        for (Nurture custom:customs){
            if ( custom.getNurtureTypeId().equals(nurture.getNurtureTypeId()) ){
                return custom;
            }
        }
        Nurture custom = new Nurture();
        custom.setChoose(false);
        custom.setCustom(true);
        custom.setNurtureTypeId(nurture.getNurtureTypeId());
        custom.setNurtureType(nurture.getNurtureType());
        return custom;
    }

    private void buildTabTitles(TabLayout tab_layout, Set<String> tabNames){
        tab_layout.addOnTabSelectedListener(selectedListener);
        for (String title:tabNames){
            tab_layout.addTab(createTab(tab_layout,title));
        }
    }

    private TabLayout.Tab createTab(TabLayout tab_layout,String title){
        TabLayout.Tab t = tab_layout.newTab();
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab_title,null);
        CheckBox cb = view.findViewById(R.id.cb_tab);
        cb.setText(title);
        t.setCustomView(view);
        return t;
    }

    private Map<String,List<Nurture>> classifyNurtures(List<Nurture> nurtures){
        Map<String,List<Nurture>> map = new LinkedHashMap<>();
        for (Nurture nurture:nurtures){
            if ( map.containsKey(nurture.getNurtureType()) ){
                map.get(nurture.getNurtureType()).add(nurture);
            } else {
                List<Nurture> type = new ArrayList<>();
                type.add(nurture);
                map.put(nurture.getNurtureType(),type);
            }
        }
        return map;
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            refreshViewPager(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private TabLayout.OnTabSelectedListener selectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            CheckBox cb = tab.getCustomView().findViewById(R.id.cb_tab);
            cb.setChecked(true);
            refreshViewPager(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            CheckBox cb = tab.getCustomView().findViewById(R.id.cb_tab);
            cb.setChecked(false);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void refreshViewPager(int position){
        tab_layout.getTabAt(position).select();
        vp.setCurrentItem(position);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    complete();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void complete(){
        nurtures.clear();
        for (ParentingGuideFragment fragment:fragments){
            nurtures.addAll(fragment.getNurtureList());
        }
        Intent intent = new Intent();
        intent.putExtra(OUT_PARAM_SELECTED_NUTRURE,GsonUtil.getInstance().toJson(nurtures));
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void minuteCallBack() {
        //TODO
        if ( tv_over_time != null ) {
            String time = FormulaUtil.getTimeDifference(validityTime);
            if ( time != null ) {
                tv_over_time.setText(time);
            }
        }
    }
}

package com.doumengmeng.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.ParentingGuideAdapter;
import com.doumengmeng.doctor.base.BaseFragmentActivity;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.fragment.ParentingGuideFragment;
import com.doumengmeng.doctor.response.entity.Nurture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Administrator on 2018/3/1.
 */

public class AssessmentParentingGuideActivity extends BaseFragmentActivity {

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title;

    private TextView tv_over_time;
    private TabLayout tab;
    private ViewPager vp;
    private ParentingGuideAdapter adapter;
    private FragmentManager fm;

    private List<ParentingGuideFragment> fragments = new ArrayList<>();

    private Map<String,List<Nurture>>  nurtureMap;

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
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);

        rl_complete.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);
    }

    private void initOverTime(){
        tv_over_time = findViewById(R.id.tv_over_time);
    }

    private void initParentingGuide(){
        fm = getSupportFragmentManager();
        tab = findViewById(R.id.tab);
        vp = findViewById(R.id.vp);

        //TODO
        nurtureMap = classifyNurtures(DaoManager.getInstance().getNurtureDao().searchNurtureByAge(this,""));
        buildTab(nurtureMap.keySet());
        buildPages(nurtureMap);
    }

    private List<Nurture> buildTestData(){
        List<Nurture> nurtures = new ArrayList<>();
        String[] titles = new String[]{"test1","test2","test3","test4","test5","test6"};
        String[] ages = new String[]{"20","21","22","23","24"};
        int buildCount = 6;

        for (String title:titles ){
            for (String age:ages){
                for (int i=0;i<buildCount;i++){
                    Nurture nurture = new Nurture();
                    nurture.setCustom(false);
                    nurture.setChoose(false);
//                    nurture.setId();
                }
            }
        }

        return nurtures;
    }

    private void buildPages(Map<String,List<Nurture>> nurtureMap){
        adapter = new ParentingGuideAdapter(fm);
        Set<String> keys = nurtureMap.keySet();
        for (String key:keys){
            ParentingGuideFragment fragment = new ParentingGuideFragment();
            fragment.setNurtures(nurtureMap.get(key));
            adapter.addFragment(fragment);
            fragments.add(fragment);
        }
        vp.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void buildTab(Set<String> tabNames){
        for (String title:tabNames){
            TabLayout.Tab t = tab.newTab();
            View view = LayoutInflater.from(this).inflate(R.layout.item_tab_title,null);
            CheckBox cb = view.findViewById(R.id.cb_tab);
            cb.setText(title);
            t.setCustomView(view);
            tab.addTab(t);
        }
    }

    private Map<String,List<Nurture>> classifyNurtures(List<Nurture> nurtures){
        Map<String,List<Nurture>> map = new TreeMap<>();
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

    }

}

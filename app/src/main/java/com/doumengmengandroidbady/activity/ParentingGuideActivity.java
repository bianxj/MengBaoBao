package com.doumengmengandroidbady.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.ParentingGuideViewPagerAdapter;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.base.BaseFragmentActivity;
import com.doumengmengandroidbady.fragment.ParentingGuideFragment;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.response.ParentingGuidanceData;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParentingGuideActivity extends BaseFragmentActivity {

    public final static String IN_PARAM_RECORD_ID = "record_id";

    private RelativeLayout rl_back;
    private TextView tv_title;

    private TabLayout tab;
    private ViewPager vp;
    private ParentingGuideViewPagerAdapter adapter;

    private String recordId;
    private ParentingGuidanceData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parenting_guide);
        findView();
//        initData();
        tv_title.setText("养育指导");
        getRecordId();
        getParentingGuideData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(PGTask);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tab = findViewById(R.id.tab);
        vp = findViewById(R.id.vp);

        adapter = new ParentingGuideViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getRecordId(){
        Intent intent = getIntent();
        recordId = intent.getStringExtra(IN_PARAM_RECORD_ID);
    }

    private void getParentingGuideData(){
        try {
            PGTask = new RequestTask.Builder(this,PGCallBack).build();
            PGTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestTask PGTask;

    private RequestCallBack PGCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_PARENTING_GUIDANCE;
        }

        @Override
        public Map<String, String> getContent() {
            Map<String,String> map = new HashMap<>();
            UserData userData = BaseApplication.getInstance().getUserData();

            JSONObject object = new JSONObject();
            try {
                object.put("userId",userData.getUserid());
                object.put("recordId",recordId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put(UrlAddressList.PARAM,object.toString());
            map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
            return map;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");

                data = GsonUtil.getInstance().fromJson(res.toString(),ParentingGuidanceData.class);
                initData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int type() {
            return DEFAULT;
        }
    };

    private void initData(){
        List<ParentingGuidanceData.ParentingGuideClass> list = data.getParentingGuidanceList();
        for (int i = 0;i<list.size();i++){
            ParentingGuidanceData.ParentingGuideClass cls = list.get(i);

            TabLayout.Tab subTab = tab.newTab();
            subTab.setText(cls.getGuidanceType());
            subTab.setTag(i);
            tab.addTab(subTab);

            ParentingGuideFragment fragment = new ParentingGuideFragment();
            fragment.setParentingGuideItem(cls.getGuidanceList());
            adapter.addFragment(fragment);
        }
        adapter.notifyDataSetChanged();
    }

    private void back(){
        finish();
    }

}

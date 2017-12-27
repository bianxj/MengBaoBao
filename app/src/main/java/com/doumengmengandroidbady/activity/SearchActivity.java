package com.doumengmengandroidbady.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.adapter.DoctorAdapter;
import com.doumengmengandroidbady.adapter.HospitalAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.entity.HospitalEntity;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class SearchActivity extends BaseActivity {

    private LinearLayout ll_back,ll_search;
    private RelativeLayout rl_close;
    private EditText et_search;
    private TextView tv_search;

    private LinearLayout ll_history;
    private Button bt_clear_history;
    private LinearLayout ll_history_content;

    private XRecyclerView xrv_search;

    private List<DoctorEntity> doctors = new ArrayList<>();
    private List<HospitalEntity> hospitals = new ArrayList<>();
    private HospitalAdapter hospitalAdapter;
    private DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findView();
    }

    private void findView(){
        ll_back = findViewById(R.id.ll_back);
        rl_close = findViewById(R.id.rl_close);
        ll_search = findViewById(R.id.ll_search);

        et_search = findViewById(R.id.et_search);
        tv_search = findViewById(R.id.tv_search);
        ll_history = findViewById(R.id.ll_history);
        ll_history_content = findViewById(R.id.ll_history_content);
        bt_clear_history = findViewById(R.id.bt_clear_history);

        xrv_search = findViewById(R.id.xrv_search);
        initView();
    }

    private void initView(){
        ll_back.setOnClickListener(listener);
        rl_close.setOnClickListener(listener);
        ll_search.setOnClickListener(listener);
        bt_clear_history.setOnClickListener(listener);

        tv_search.setText(R.string.cancel);
        rl_close.setVisibility(View.GONE);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( charSequence.length() > 0 ){
                    rl_close.setVisibility(View.VISIBLE);
                    tv_search.setText(R.string.search);
                }  else {
                    rl_close.setVisibility(View.INVISIBLE);
                    tv_search.setText(R.string.cancel);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initSearchList();
        initSearchHistory();
    }

    private void initSearchList(){
        xrv_search.setLoadingMoreEnabled(true);
        xrv_search.setFootView(new XLoadMoreFooter(this));
        xrv_search.setLoadingListener(searchLoadingListener);

        doctorAdapter = new DoctorAdapter(doctors);
        hospitalAdapter = new HospitalAdapter(hospitals);
    }

    private void initSearchHistory(){
        array = BaseApplication.getInstance().getSearchHistory();
        if ( array == null ){
            array = new JSONArray();
        }
        if ( array.length() > 0 ) {
            try {
                refreshSearchHistory(array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private XRecyclerView.LoadingListener searchLoadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {

        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_back:
                    back();
                    break;
                case R.id.rl_close:
                    et_search.setText("");
                    rl_close.setVisibility(View.INVISIBLE);
                    break;
                case R.id.ll_search:
                    if ( getString(R.string.cancel) == tv_search.getText().toString() ){
                        back();
                    } else {
                        search();
                    }
                    break;
                case R.id.bt_clear_history:
                    clearHistoryItem();
                    break;
            }
        }
    };

    private void search(){
        try {
            buildSearchTask().execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void back(){
        finish();
    }

    private RequestTask buildSearchTask() throws Throwable {
        return new RequestTask.Builder(searchCallBack).build();
    }

    private int times = 0;
    private RequestCallBack searchCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return null;
        }

        @Override
        public Context getContext() {
            return null;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            if (Config.isTest){
                times++;
                if ( times %2 == 0 ){
                    xrv_search.setAdapter(hospitalAdapter);
                    for (int i = 0; i < 10; i++) {
                        HospitalEntity hospital = new HospitalEntity();
                        hospital.setHospitalicon("http://www.qqzhi.com/uploadpic/2014-10-04/013617459.jpg");
                        hospital.setHospitalname("HospitalName" + i);
                        hospital.setHospitaladdress("HospitalAddress" + i);
                        hospitals.add(hospital);
                    }
                    hospitalAdapter.notifyDataSetChanged();
                } else {
                    xrv_search.setAdapter(doctorAdapter);
                    doctors.clear();
                    for (int i = 0; i <10 ; i++) {
                        DoctorEntity doctor = new DoctorEntity();
                        doctor.setDoctorimg("http://img5.duitang.com/uploads/item/201510/02/20151002201518_8ZKWy.thumb.224_0.png");
                        doctor.setDoctordesc("Describe1");
                        doctor.setDoctorname("Name"+i);
                        doctor.setHospital("HospitalEntity"+i);
                        doctor.setPositionaltitles("Position"+i);
                        doctor.setSpeciality("Skill"+i);
                        doctors.add(doctor);
                    }
                    doctorAdapter.notifyDataSetChanged();
                }
                String historyWord = et_search.getText().toString();
                try {
                    putSearchHistory(historyWord);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ll_history.setVisibility(View.GONE);
            }
        }

        @Override
        public String type() {
            return JSON;
        }
    };

    private JSONArray array = null;
    private List<View> historyItem = new ArrayList<>();
    public final static int HISTORY_ITEM_COUNT = 3;

    private void putSearchHistory(String keyword) throws JSONException {
        for (int i = 0; i < array.length(); i++) {
            if ( keyword.equals(array.getString(i)) ){
                array.remove(i);
                break;
            }
        }
        if ( array.length() >= HISTORY_ITEM_COUNT ){
            array.remove(0);
        }
        array.put(keyword);
        BaseApplication.getInstance().saveSearchHistory(array);
    }

    private void refreshSearchHistory(JSONArray array) throws JSONException {
        if (ll_history.getVisibility() != View.VISIBLE) {
            ll_history.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < array.length(); i++) {
            View view = ll_history_content.getChildAt(i);
            if ( view == null ){
                view = buildHistoryItem();
                ll_history_content.addView(view);
            }
            TextView tv_search_history = view.findViewById(R.id.tv_search_history);
            tv_search_history.setText(array.getString(array.length()-1-i));
        }
    }

    private void clearHistoryItem(){
        ll_history_content.removeAllViews();
        ll_history.setVisibility(View.GONE);
        while ( array.length() > 0 ){
            array.remove(0);
        }
        for (int i = 0; i <historyItem.size() ; i++) {
            historyItem.get(i).setVisibility(View.GONE);
        }
        BaseApplication.getInstance().clearSearchHistory();
    }

    private View buildHistoryItem(){
        View view = null;
        for (int i = 0; i < historyItem.size(); i++) {
            if ( historyItem.get(i).getVisibility() != View.VISIBLE ){
                view = historyItem.get(i);
                view.setVisibility(View.VISIBLE);
                break;
            }
        }
        if ( view == null ) {
            view = LayoutInflater.from(this).inflate(R.layout.item_search_history,null);
            historyItem.add(view);
        }
        TextView tv_search_history = view.findViewById(R.id.tv_search_history);
        tv_search_history.setOnClickListener(historyClickListener);
        return view;
    }

    private View.OnClickListener historyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String keyword = ((TextView)view).getText().toString();
            et_search.setText(keyword);
            search();
        }
    };

}

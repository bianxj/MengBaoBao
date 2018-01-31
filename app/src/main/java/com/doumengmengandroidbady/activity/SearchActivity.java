package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.doumengmengandroidbady.adapter.HospitalDoctorAdapter;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.entity.DoctorEntity;
import com.doumengmengandroidbady.entity.HospitalEntity;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 搜索
 * 创建日期: 2018/1/8 11:51
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

    private final List<DoctorEntity> doctors = new ArrayList<>();
    private final List<HospitalEntity> hospitals = new ArrayList<>();
    private HospitalDoctorAdapter adapter;
    private SearchHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( handler != null ){
            handler.removeCallbacksAndMessages(null);
        }
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
        ll_back.setVisibility(View.GONE);

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

        handler = new SearchHandler(this);
        initSearchList();
        initSearchHistory();
    }

    private void initSearchList(){
        xrv_search.setLoadingMoreEnabled(true);
        xrv_search.setFootView(new XLoadMoreFooter(this));

        adapter = new HospitalDoctorAdapter(hospitals,doctors);
        xrv_search.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

    private final View.OnClickListener listener = new View.OnClickListener() {
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
                    if ( getString(R.string.cancel).equals(tv_search.getText().toString()) ){
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

    //-----------------------------------------查询----------------------------------------------
    private static class SearchHandler extends Handler{
        private final static int MESSAGE_SEARCH = 0x01;
        private final WeakReference<SearchActivity> weakReference;

        public SearchHandler(SearchActivity activity) {
            weakReference = new WeakReference<SearchActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MESSAGE_SEARCH){
                String history = (String) msg.obj;
                weakReference.get().updateList(history);
            }
        }
    }

    private void updateList(String history){
        xrv_search.setNoMore(true);
        hospitals.clear();
        doctors.clear();
        if (tempDoctors != null || tempHospitals != null) {
            hospitals.addAll(tempHospitals);
            doctors.addAll(tempDoctors);
        }
        adapter.notifyDataSetChanged();

        if (tempDoctors != null || tempHospitals != null) {
            if (tempHospitals.size() > 0 || tempDoctors.size() > 0) {
                try {
                    putSearchHistory(history);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ll_history.setVisibility(View.GONE);
            }
        }
    }

    private void search(){
        new Thread(searchRunnable).start();
    }

    private List<DoctorEntity> tempDoctors = null;
    private List<HospitalEntity> tempHospitals = null;
    private final Runnable searchRunnable = new Runnable() {
        @Override
        public void run() {
            String search = et_search.getText().toString().trim();
            tempDoctors = DaoManager.getInstance().getDaotorDao().searchDoctorListByName(SearchActivity.this,search);
            tempHospitals = DaoManager.getInstance().getHospitalDao().searchHospitalListByName(SearchActivity.this,search);

            Message message = handler.obtainMessage();
            message.what = SearchHandler.MESSAGE_SEARCH;
            message.obj = search;
            handler.sendMessage(message);
        }
    };


    private void back(){
        finish();
    }

    //--------------------------------------搜索历史处理--------------------------------------------
    private JSONArray array = null;
    private final List<View> historyItem = new ArrayList<>();
    private final static int HISTORY_ITEM_COUNT = 3;

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

    private final View.OnClickListener historyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String keyword = ((TextView)view).getText().toString();
            et_search.setText(keyword);
            search();
        }
    };

}

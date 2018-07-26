package com.doumengmeng.doctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.adapter.DepartmentAdapter;
import com.doumengmeng.doctor.adapter.viewholder.InputContentHolder;
import com.doumengmeng.doctor.base.BaseSwipeActivity;
import com.doumengmeng.doctor.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class DepartmentActivity extends BaseSwipeActivity implements InputContentHolder.InputCompleteAction {

    public final static String OUT_PARAM_DEPARTMENT_NAME = "department_name";

    private RelativeLayout rl_back;
    private TextView tv_title;

    private XRecyclerView xrv;
    private DepartmentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        findView();
    }

    private void findView(){
        initTitle();
        initListView();
    }

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        tv_title.setText(getString(R.string.department));
        rl_back.setOnClickListener(listener);
    }

    private void initListView(){
        xrv = findViewById(R.id.xrv);
        xrv.setLoadingMoreEnabled(true);
        xrv.setFootView(new XLoadMoreFooter(this));
//        xrv.setLoadingListener(searchLoadingListener);

        String[] strings = new String[]{"儿童保健科","发育行为儿科","儿科"};

        List<DepartmentAdapter.DepartmentData> datas = new ArrayList<>();
        for (String string:strings){
            DepartmentAdapter.DepartmentData data = new DepartmentAdapter.DepartmentData();
            data.setDepartmentName(string);
            datas.add(data);
        }

        adapter = new DepartmentAdapter(this,datas);
        xrv.setAdapter(adapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    protected void back(){
        finish();
    }

    @Override
    public void selectComplete(String content) {
        Intent intent = new Intent();
        intent.putExtra(OUT_PARAM_DEPARTMENT_NAME,content);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void error(int type) {

    }
}

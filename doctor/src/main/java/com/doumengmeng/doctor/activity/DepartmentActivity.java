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
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class DepartmentActivity extends BaseActivity implements InputContentHolder.InputCompleteAction {

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

        List<DepartmentAdapter.DepartmentData> datas = new ArrayList<>();
        for (int i = 0 ; i< 40;i++){
            DepartmentAdapter.DepartmentData data = new DepartmentAdapter.DepartmentData();
            data.setDepartmentName("Name"+i);
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

    private void back(){
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

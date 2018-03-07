package com.doumengmeng.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by Administrator on 2018/2/26.
 */

public class MessageFragment extends BaseFragment {

    private LinearLayout ll_no_message;
    private XRecyclerView xrv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,null);
        findView(view);
        return view;
    }

    private void findView(View view){
        ll_no_message = view.findViewById(R.id.ll_no_message);
        xrv = view.findViewById(R.id.xrv);

        initView(view);
    }

    private void initView(View view){

    }

}

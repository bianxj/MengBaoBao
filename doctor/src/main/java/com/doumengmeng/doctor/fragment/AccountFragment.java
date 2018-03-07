package com.doumengmeng.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseFragment;

/**
 * Created by Administrator on 2018/2/26.
 */

public class AccountFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,null);
        findView(view);
        return view;
    }

    private void findView(View view){
        initView(view);
    }

    private void initView(View view){

    }

}

package com.doumengmengandroidbady.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseFragment;

/**
 * Created by Administrator on 2017/12/5.
 */
//TODO
public class HomePageFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page,null);
        return view;
    }


}

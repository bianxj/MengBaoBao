package com.doumengmengandroidbady.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.MainActivity;
import com.doumengmengandroidbady.activity.ObserveActivity;
import com.doumengmengandroidbady.base.BaseFragment;

/**
 * Created by Administrator on 2017/12/5.
 */
//TODO
public class HomePageFragment extends BaseFragment {

    private MainActivity activity;
    private RelativeLayout rl_side_menu;
    private TextView tv_baby_name;
    private LinearLayout ll_observe_point,ll_doctor_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_home_page,null);
        findView(view);
        configView();
        return view;
    }

    private void findView(View view){
        rl_side_menu = view.findViewById(R.id.rl_side_menu);
        tv_baby_name = view.findViewById(R.id.tv_baby_name);
        ll_observe_point = view.findViewById(R.id.ll_observe_point);
        ll_doctor_list = view.findViewById(R.id.ll_doctor_list);
    }

    private void configView(){
        rl_side_menu.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_side_menu:
                    activity.toggleSideMenu();
                    break;
                case R.id.ll_observe_point:
                    startActivity(ObserveActivity.class);
                    break;
                case R.id.ll_doctor_list:
//                    startActivity();
                    break;
            }
        }
    };

}

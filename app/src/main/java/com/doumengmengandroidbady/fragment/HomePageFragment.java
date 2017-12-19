package com.doumengmengandroidbady.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.DoctorListActivity;
import com.doumengmengandroidbady.activity.HeadImageActivity;
import com.doumengmengandroidbady.activity.MainActivity;
import com.doumengmengandroidbady.activity.ObserveActivity;
import com.doumengmengandroidbady.base.BaseFragment;
import com.doumengmengandroidbady.view.CircleImageView;

/**
 * Created by Administrator on 2017/12/5.
 */
//TODO
public class HomePageFragment extends BaseFragment {

    private MainActivity activity;

    private RelativeLayout rl_side_menu;
    private TextView tv_baby_name;
    private LinearLayout ll_observe_point,ll_doctor_list;
    private TextView tv_observe_point , tv_doctor_list;
    private TextView tv_observe_point_content , tv_doctor_list_content;
    private FrameLayout rl_baby_head;
    private CircleImageView civ_baby;
    private ImageView iv_sex;
    private TextView tv_baby_age;
    private ViewPager vp_home;
    private LinearLayout ll_home_dot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_home_page,null);
        findView(view);
        initView();
        return view;
    }

    private void findView(View view){
        rl_side_menu = view.findViewById(R.id.rl_side_menu);
        tv_baby_name = view.findViewById(R.id.tv_baby_name);
        ll_observe_point = view.findViewById(R.id.ll_observe_point);
        ll_doctor_list = view.findViewById(R.id.ll_doctor_list);
        tv_observe_point_content = view.findViewById(R.id.tv_observe_point_content);
        tv_doctor_list_content = view.findViewById(R.id.tv_doctor_list_content);
        rl_baby_head = view.findViewById(R.id.rl_baby_head);
        civ_baby = view.findViewById(R.id.civ_baby);
        iv_sex = view.findViewById(R.id.iv_sex);
        tv_baby_age = view.findViewById(R.id.tv_baby_age);
        vp_home = view.findViewById(R.id.vp_home);
        ll_home_dot = view.findViewById(R.id.ll_home_dot);
    }

    private void initView(){
        rl_side_menu.setOnClickListener(listener);
        ll_observe_point.setOnClickListener(listener);
        ll_doctor_list.setOnClickListener(listener);
        rl_baby_head.setOnClickListener(listener);
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
                    startActivity(DoctorListActivity.class);
                    break;
                case R.id.rl_baby_head:
                    startActivity(HeadImageActivity.class);
                    break;
            }
        }
    };

}

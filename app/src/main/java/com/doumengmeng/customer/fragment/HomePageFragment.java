package com.doumengmeng.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.activity.DoctorInfoActivity;
import com.doumengmeng.customer.activity.DoctorListActivity;
import com.doumengmeng.customer.activity.HeadImageActivity;
import com.doumengmeng.customer.activity.MainActivity;
import com.doumengmeng.customer.activity.ObserveActivity;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseFragment;
import com.doumengmeng.customer.response.entity.DayList;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.view.AutoScrollViewPager;
import com.doumengmeng.customer.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 作者: 边贤君
 * 描述: 首页
 * 创建日期: 2018/1/8 11:09
 */
public class HomePageFragment extends BaseFragment {

    private MainActivity activity;

    private RelativeLayout rl_side_menu;
    private TextView tv_baby_name;
    private RelativeLayout rl_male;
    private Space space_observ_point;
    private LinearLayout ll_observe_point,ll_doctor_list;
    private FrameLayout rl_baby_head;
    private CircleImageView civ_baby;
    private CheckBox cb_male;
    private TextView tv_baby_age;
    private AutoScrollViewPager asvp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_home_page,null);
        findView(view);
        initView();
        return view;
    }

    private void findView(View view){
        rl_side_menu = view.findViewById(R.id.rl_side_menu);
        tv_baby_name = view.findViewById(R.id.tv_baby_name);
        rl_male = view.findViewById(R.id.rl_male);
        space_observ_point = view.findViewById(R.id.space_observ_point);
        ll_observe_point = view.findViewById(R.id.ll_observe_point);
        ll_doctor_list = view.findViewById(R.id.ll_doctor_list);
        rl_baby_head = view.findViewById(R.id.rl_baby_head);
        civ_baby = view.findViewById(R.id.civ_baby);
        cb_male = view.findViewById(R.id.cb_male);
        tv_baby_age = view.findViewById(R.id.tv_baby_age);
        asvp = view.findViewById(R.id.asvp);
    }

    private void initView(){
        if ( BaseApplication.getInstance().isUpperThan37Month() ){
            space_observ_point.setVisibility(View.GONE);
            ll_observe_point.setVisibility(View.GONE);
        } else {
            space_observ_point.setVisibility(View.VISIBLE);
            ll_observe_point.setVisibility(View.VISIBLE);
        }
        rl_side_menu.setOnClickListener(listener);
        ll_observe_point.setOnClickListener(listener);
        ll_doctor_list.setOnClickListener(listener);
        rl_baby_head.setOnClickListener(listener);

        //初始化轮播图
        int[] images = new int[]{R.drawable.banner1,R.drawable.banner2,R.drawable.banner3};
        asvp.setImageList(images);
        asvp.setOnClickCallBack(onClickCallBack);
    }

    private void initData(){
        DayList dayList = BaseApplication.getInstance().getDayList();
        UserData userData = BaseApplication.getInstance().getUserData();
        if ( BaseApplication.getInstance().isPay() ) {
            tv_baby_name.setVisibility(View.VISIBLE);
            rl_male.setVisibility(View.VISIBLE);

            tv_baby_name.setText(userData.getTruename());
            cb_male.setChecked(userData.isMale());
            tv_baby_age.setText(String.format(getResources().getString(R.string.home_page_month_age),dayList.getCurrentMonth(),dayList.getCurrentDay()));
            loadHeadImg(userData.isMale(),userData.getHeadimg());
        } else {
            tv_baby_name.setVisibility(View.INVISIBLE);
            rl_male.setVisibility(View.INVISIBLE);
            loadHeadImg(userData.isMale(),"");
        }
    }

    /**
     * 作者: 边贤君
     * 描述: 加载头像
     * 参数:
     * 日期: 2018/1/8 10:35
     */
    private void loadHeadImg(boolean isMale,String urlHeadImg){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        if ( isMale ){
            builder.showImageOnLoading(R.drawable.default_icon_boy);
            builder.showImageForEmptyUri(R.drawable.default_icon_boy);
            builder.showImageOnFail(R.drawable.default_icon_boy);
        } else {
            builder.showImageOnLoading(R.drawable.default_icon_girl);
            builder.showImageForEmptyUri(R.drawable.default_icon_girl);
            builder.showImageOnFail(R.drawable.default_icon_girl);
        }
        ImageLoader.getInstance().displayImage(urlHeadImg,civ_baby,builder.build());
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_side_menu:
                    //切换抽屉
                    activity.toggleSideMenu();
                    break;
                case R.id.ll_observe_point:
                    //观察要点
                    startActivity(ObserveActivity.class);
                    break;
                case R.id.ll_doctor_list:
                    //医院医生列表
                    startActivity(DoctorListActivity.class);
                    break;
                case R.id.rl_baby_head:
                    //上传头像
                    if ( BaseApplication.getInstance().isPay() ) {
                        startActivity(HeadImageActivity.class);
                    }
                    break;
            }
        }
    };

    private final AutoScrollViewPager.OnClickCallBack onClickCallBack = new AutoScrollViewPager.OnClickCallBack() {
        @Override
        public void onClick(int position) {
//            String doctorName = "马骏";
//            if ( 1 == position ) {
//                doctorName = "金星明";
//            } else if ( 2 == position ){
//                doctorName = "章依雯";
//            }
            String doctorId = "17";
            if ( 1 == position ){
                doctorId = "4";
            } else if ( 2 == position ){
                doctorId = "49";
            }
            Intent intent = new Intent(getContext(),DoctorInfoActivity.class);
            intent.putExtra(DoctorInfoActivity.IN_PARAM_DOCTOR_ID , doctorId);
            startActivity(intent);
        }
    };

}

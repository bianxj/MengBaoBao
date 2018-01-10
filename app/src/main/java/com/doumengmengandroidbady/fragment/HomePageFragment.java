package com.doumengmengandroidbady.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.DoctorInfoActivity;
import com.doumengmengandroidbady.activity.DoctorListActivity;
import com.doumengmengandroidbady.activity.HeadImageActivity;
import com.doumengmengandroidbady.activity.MainActivity;
import com.doumengmengandroidbady.activity.ObserveActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.base.BaseFragment;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.view.AutoScrollViewPager;
import com.doumengmengandroidbady.view.CircleImageView;
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
    private LinearLayout ll_observe_point,ll_doctor_list;
    private TextView tv_observe_point , tv_doctor_list;
    private TextView tv_observe_point_content , tv_doctor_list_content;
    private FrameLayout rl_baby_head;
    private CircleImageView civ_baby;
    private CheckBox cb_male;
    private TextView tv_baby_age;
    private AutoScrollViewPager asvp;

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
        cb_male = view.findViewById(R.id.cb_male);
        tv_baby_age = view.findViewById(R.id.tv_baby_age);
        asvp = view.findViewById(R.id.asvp);
    }

    private void initView(){
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
        UserData userData = BaseApplication.getInstance().getUserData();
        tv_baby_name.setText(userData.getTruename());
        cb_male.setChecked(userData.isMale());
        if ( BaseApplication.getInstance().isPay() ) {
            loadHeadImg(userData.isMale(),userData.getHeadimg());
        } else {
            loadHeadImg(userData.isMale(),"");
        }
        tv_baby_age.setText(userData.getBabyAge());
    }

    /**
     * 作者: 边贤君
     * 描述: 加载头像
     * 参数:
     * 日期: 2018/1/8 10:35
     */
    private void loadHeadImg(boolean isMale,String urlHeadImg){
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
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

    private View.OnClickListener listener = new View.OnClickListener() {
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

    private AutoScrollViewPager.OnClickCallBack onClickCallBack = new AutoScrollViewPager.OnClickCallBack() {
        @Override
        public void onClick(int position) {
            Toast.makeText(getContext(),position+"",Toast.LENGTH_SHORT).show();
            String doctorName = "马骏";
            if ( 1 == position ) {
                doctorName = "金星明";
            } else if ( 2 == position ){
                doctorName = "章依雯";
            }
            Intent intent = new Intent(getContext(),DoctorInfoActivity.class);
            intent.putExtra(DoctorInfoActivity.IN_PARAM_DOCTOR_NAME , doctorName);
            startActivity(intent);
        }
    };

}

package com.doumengmengandroidbady.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.response.entity.DayList;
import com.doumengmengandroidbady.response.entity.UserData;
import com.doumengmengandroidbady.util.MyDialog;
import com.doumengmengandroidbady.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 作者: 边贤君
 * 描述: 个人中心
 * 创建日期: 2018/1/8 10:39
 */
public class PersonCenterActivity extends BaseActivity {

    private TextView tv_baby_name ,tv_baby_age;
    private TextView tv_title;
    private CircleImageView civ_baby_img;
    private CheckBox cb_male;
    private RelativeLayout rl_back;
    private RelativeLayout rl_child_info , rl_parent_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        findView();
        initView();
    }

    private void findView(){
        tv_title = findViewById(R.id.tv_title);
        rl_back = findViewById(R.id.rl_back);
        rl_child_info = findViewById(R.id.rl_child_info);
        rl_parent_info = findViewById(R.id.rl_parent_info);

        tv_baby_name = findViewById(R.id.tv_baby_name);
        tv_baby_age = findViewById(R.id.tv_baby_age);
        cb_male = findViewById(R.id.cb_male);
        civ_baby_img = findViewById(R.id.civ_baby_img);
    }

    private void initView(){
        tv_title.setText(R.string.person_center);

        DayList dayList = BaseApplication.getInstance().getDayList();
        UserData userData = BaseApplication.getInstance().getUserData();
        if (BaseApplication.getInstance().isPay()){
            //付费用户
            tv_baby_age.setVisibility(View.VISIBLE);
            cb_male.setVisibility(View.VISIBLE);
            tv_baby_name.setVisibility(View.VISIBLE);
            tv_baby_age.setText(String.format(getResources().getString(R.string.home_page_month_age),dayList.getCurrentMonth(),dayList.getCurrentDay()));
            cb_male.setChecked(userData.isMale());
            tv_baby_name.setText(userData.getTruename());
            loadHeadImg(userData);
        } else {
            //免费用户
            tv_baby_age.setVisibility(View.INVISIBLE);
            cb_male.setVisibility(View.INVISIBLE);
            tv_baby_name.setVisibility(View.INVISIBLE);
            if ( userData.isMale() ){
                civ_baby_img.setImageResource(R.drawable.default_icon_boy);
            } else {
                civ_baby_img.setImageResource(R.drawable.default_icon_girl);
            }
        }

        rl_back.setOnClickListener(listener);
        rl_child_info.setOnClickListener(listener);
        rl_parent_info.setOnClickListener(listener);
    }

    /**
     * 作者: 边贤君
     * 描述: 加载头像
     * 参数:
     * 日期: 2018/1/8 10:35
     */
    private void loadHeadImg(UserData userData){
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        if ( userData.isMale() ){
            builder.showImageOnLoading(R.drawable.default_icon_boy);
            builder.showImageForEmptyUri(R.drawable.default_icon_boy);
            builder.showImageOnFail(R.drawable.default_icon_boy);
        } else {
            builder.showImageOnLoading(R.drawable.default_icon_girl);
            builder.showImageForEmptyUri(R.drawable.default_icon_girl);
            builder.showImageOnFail(R.drawable.default_icon_girl);
        }
        ImageLoader.getInstance().displayImage(userData.getHeadimg(),civ_baby_img,builder.build());
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_child_info:
                    switchToChildInfo();
                    break;
                case R.id.rl_parent_info:
                    switchToParentInfo();
                    break;
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void switchToChildInfo(){
        if ( BaseApplication.getInstance().isPay() ){
            startActivity(BaseInfoActivity.class);
        } else {
            showNeedBuyDialog();
        }
    }

    private void switchToParentInfo(){
        if ( BaseApplication.getInstance().isPay() ){
            startActivity(ParentInfoActivity.class);
        } else {
            showNeedBuyDialog();
        }
    }

    private void showNeedBuyDialog(){
        MyDialog.showChooseDialog(this, getString(R.string.prompt_need_buy),R.string.prompt_bt_cancel,R.string.prompt_bt_buy, new MyDialog.ChooseDialogCallback() {
            @Override
            public void sure() {
                //跳转至找医生界面
                startActivity(SpacialistServiceActivity.class);
                back();
            }

            @Override
            public void cancel() {}
        });
    }

}

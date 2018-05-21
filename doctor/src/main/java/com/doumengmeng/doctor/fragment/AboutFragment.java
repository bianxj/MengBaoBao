package com.doumengmeng.doctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.activity.AboutActivity;
import com.doumengmeng.doctor.activity.AgreementActivity;
import com.doumengmeng.doctor.activity.CallCenterActivity;
import com.doumengmeng.doctor.activity.ChangePwdActivity;
import com.doumengmeng.doctor.activity.PersonInfoActivity;
import com.doumengmeng.doctor.adapter.AboutAdapter;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseFragment;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.response.entity.UserData;
import com.doumengmeng.doctor.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class AboutFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private ListView lv;

    private CircleImageView civ_head;
    private TextView tv_doctor_name,tv_positional_titles,tv_hospital,tv_doctor_department;

    private AboutAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,null);
        findView(view);
        return view;
    }

    private void findView(View view){
        initTitle(view);
        initDoctorInfo(view);
        initAboutList(view);
    }

    private void initTitle(View view){
        rl_back = view.findViewById(R.id.rl_back);
        rl_back.setVisibility(View.GONE);
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.i));
    }

    private void initDoctorInfo(View view){
        civ_head = view.findViewById(R.id.civ_head);
        tv_doctor_name = view.findViewById(R.id.tv_doctor_name);
        tv_positional_titles = view.findViewById(R.id.tv_positional_titles);
        tv_hospital = view.findViewById(R.id.tv_hospital);
        tv_doctor_department = view.findViewById(R.id.tv_doctor_department);

        UserData userData = BaseApplication.getInstance().getUserData();
        loadHeadImg(civ_head,userData.getHeadimg());
//        ImageLoader.getInstance().displayImage(userData.getHeadimg(),civ_head);
        tv_doctor_name.setText(userData.getDoctorName());
        tv_positional_titles.setText(userData.getPositionalTitles());
        tv_hospital.setText(DaoManager.getInstance().getHospitalDao().searchHospitalNameById(getContext(),userData.getHospitalId()));
        tv_doctor_department.setText(userData.getDepartmentName());
    }

    private void loadHeadImg(ImageView imageView, String urlHeadImg){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        builder.showImageOnLoading(R.drawable.default_icon_doctor);
        builder.showImageForEmptyUri(R.drawable.default_icon_doctor);
        builder.showImageOnFail(R.drawable.default_icon_doctor);
        ImageLoader.getInstance().displayImage(urlHeadImg,imageView,builder.build());
    }

    private void initAboutList(View view){
        lv = view.findViewById(R.id.lv);
        adapter = new AboutAdapter(initAboutItem());
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv.setOnItemClickListener(itemClickListener);
    }

    private List<AboutAdapter.AboutItem> initAboutItem(){
        List<AboutAdapter.AboutItem> items = new ArrayList<>();
        items.add(new AboutAdapter.AboutItem(R.drawable.icon_person_info,getString(R.string.person_info)));
        items.add(new AboutAdapter.AboutItem(R.drawable.icon_call_center,getString(R.string.call_center)));
        items.add(new AboutAdapter.AboutItem(R.drawable.icon_about,getString(R.string.about)));
        items.add(new AboutAdapter.AboutItem(R.drawable.icon_password,getString(R.string.change_password)));
        items.add(new AboutAdapter.AboutItem(R.drawable.icon_agreement,getString(R.string.agreement)));
        items.add(new AboutAdapter.AboutItem(R.drawable.icon_logout,getString(R.string.logout)));
        return items;
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            switch(position){
                case 0:
                    goPersonInfo();
                    break;
                case 1:
                    goCallCenter();
                    break;
                case 2:
                    goAbout();
                    break;
                case 3:
                    goChangePassword();
                    break;
                case 4:
                    goAgreement();
                    break;
                case 5:
                    logout();
                    break;
            }
        }
    };

    private void goPersonInfo(){
        startActivity(PersonInfoActivity.class);
    }

    private void goCallCenter(){
        startActivity(CallCenterActivity.class);
    }

    private void goAbout(){
        startActivity(AboutActivity.class);
    }

    private void goChangePassword(){
        startActivity(ChangePwdActivity.class);
    }

    private void goAgreement(){
        Intent intent = new Intent(getContext(),AgreementActivity.class);
        intent.putExtra(AgreementActivity.HIDE_BOTTOM,true);
        getContext().startActivity(intent);
    }

    private void logout(){
        BaseApplication.getInstance().skipToGuide(getActivity());
    }

}

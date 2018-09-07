package com.doumengmeng.doctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.activity.AboutActivity;
import com.doumengmeng.doctor.activity.AgreementActivity;
import com.doumengmeng.doctor.activity.CallCenterActivity;
import com.doumengmeng.doctor.activity.ChangePwdActivity;
import com.doumengmeng.doctor.activity.PersonInfoActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseFragment;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.response.entity.UserData;
import com.doumengmeng.doctor.util.MyDialog;
import com.doumengmeng.doctor.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/26.
 */

public class AboutFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
//    private ListView lv;
//    private ScrollView sv;
    private LinearLayout ll_about;

    private CircleImageView civ_head;
    private TextView tv_doctor_name,tv_positional_titles,tv_hospital,tv_doctor_department;

//    private AboutAdapter adapter;
    private UserData userData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,null);
        findView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTask(orderReceiveTask);
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

        userData = BaseApplication.getInstance().getUserData();
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
        ll_about = view.findViewById(R.id.ll_about);

        ll_about.addView(createAboutSwitch(R.drawable.icon_time_clock,getString(R.string.order_receive),userData.getReceptionStatus().equals("1")));
        ll_about.addView(createAboutItem(ITEM_PERSON_INFO,R.drawable.icon_person_info,getString(R.string.person_info)));
        ll_about.addView(createAboutItem(ITEM_CALL_CENTER,R.drawable.icon_call_center,getString(R.string.call_center)));
        ll_about.addView(createAboutItem(ITEM_ABOUT,R.drawable.icon_about,getString(R.string.about)));
        ll_about.addView(createAboutItem(ITEM_CHANGE_PASSWORD,R.drawable.icon_password,getString(R.string.change_password)));
        ll_about.addView(createAboutItem(ITEM_AGREEMENT,R.drawable.icon_agreement,getString(R.string.agreement)));
        ll_about.addView(createAboutItem(ITEM_LOGOUT,R.drawable.icon_logout,getString(R.string.logout)));

//        lv = view.findViewById(R.id.lv);
//        adapter = new AboutAdapter(initAboutItem());
//        lv.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        lv.setOnItemClickListener(itemClickListener);

    }

    private View createAboutItem(int index,int icon,String title){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_about,null);

        ImageView iv_icon = view.findViewById(R.id.iv_icon);
        TextView tv_title = view.findViewById(R.id.tv_title);

        iv_icon.setImageResource(icon);
        tv_title.setText(title);
        view.setTag(index);
        view.setOnClickListener(itemClickListener);
        return view;
    }

    private Switch sw;
    private View createAboutSwitch(int icon,String title,boolean isChecked){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_switch,null);

        ImageView iv_icon = view.findViewById(R.id.iv_icon);
        TextView tv_title = view.findViewById(R.id.tv_title);

        tv_title.setText(title);
        iv_icon.setImageResource(icon);
        sw = view.findViewById(R.id.sw);
        sw.setChecked(isChecked);
        sw.setOnCheckedChangeListener(checkedChangeListener);
        return view;
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
            if ( !buttonView.isPressed() ){
                return;
            }
            if ( isChecked ){
                orderReceive();
            } else {
                MyDialog.showChooseDialog(getContext(), getString(R.string.prompt_order_close),R.string.prompt_bt_sure,R.string.prompt_bt_cancel,new MyDialog.ChooseDialogCallback() {
                    @Override
                    public void sure() {
                        buttonView.setChecked(true);
                    }

                    @Override
                    public void cancel() {
                        orderReceive();
                    }
                });
            }
        }
    };

//    private List<AboutAdapter.AboutItem> initAboutItem(){
//        List<AboutAdapter.AboutItem> items = new ArrayList<>();
//        items.add(new AboutAdapter.AboutItem(R.drawable.icon_person_info,getString(R.string.person_info)));
//        items.add(new AboutAdapter.AboutItem(R.drawable.icon_call_center,getString(R.string.call_center)));
//        items.add(new AboutAdapter.AboutItem(R.drawable.icon_about,getString(R.string.about)));
//        items.add(new AboutAdapter.AboutItem(R.drawable.icon_password,getString(R.string.change_password)));
//        items.add(new AboutAdapter.AboutItem(R.drawable.icon_agreement,getString(R.string.agreement)));
//        items.add(new AboutAdapter.AboutItem(R.drawable.icon_logout,getString(R.string.logout)));
//        return items;
//    }

    private final static int ITEM_PERSON_INFO = 0x01;
    private final static int ITEM_CALL_CENTER = 0x02;
    private final static int ITEM_ABOUT = 0x03;
    private final static int ITEM_CHANGE_PASSWORD = 0x04;
    private final static int ITEM_AGREEMENT = 0x05;
    private final static int ITEM_LOGOUT = 0x06;
    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            switch (index){
                case ITEM_PERSON_INFO:
                    goPersonInfo();
                    break;
                case ITEM_CALL_CENTER:
                    goCallCenter();
                    break;
                case ITEM_ABOUT:
                    goAbout();
                    break;
                case ITEM_CHANGE_PASSWORD:
                    goChangePassword();
                    break;
                case ITEM_AGREEMENT:
                    goAgreement();
                    break;
                case ITEM_LOGOUT:
                    logout();
                    break;
            }
        }
    };

//    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//            switch(position){
//                case 0:
//                    goPersonInfo();
//                    break;
//                case 1:
//                    goCallCenter();
//                    break;
//                case 2:
//                    goAbout();
//                    break;
//                case 3:
//                    goChangePassword();
//                    break;
//                case 4:
//                    goAgreement();
//                    break;
//                case 5:
//                    logout();
//                    break;
//            }
//        }
//    };

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

    private RequestTask  orderReceiveTask;
    private void orderReceive(){
        try {
            orderReceiveTask = new RequestTask.Builder(getContext(),orderReceiveCallBack)
                    .setContent(initOrderReceiveContent())
                    .setType(RequestTask.DEFAULT)
                    .setUrl(UrlAddressList.URL_ORDER_RECEIVE)
                    .build();
            orderReceiveTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String,String> initOrderReceiveContent(){
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            int status = sw.isChecked() ? 1 : 0;
            object.put("doctorId",userData.getDoctorId());
            object.put("receptionStatus",status+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getSessionId());
        return  map;

    }

    private RequestCallBack orderReceiveCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            sw.setChecked(!sw.isChecked());
            if ( ResponseErrorCode.getErrorCode(result) == -204 ) {
                MyDialog.showPromptDialog(getContext(),getString(R.string.prompt_order_close_failed),null);
            } else {
                MyDialog.showPromptDialog(getContext(),ResponseErrorCode.getErrorMsg(result),null);
            }
        }

        @Override
        public void onPostExecute(String result) {
            //TODO
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                if ( res.getInt("isSuccess") == 1 ){
                    userData.setReceptionStatus(sw.isChecked()?"1":"0");
                    BaseApplication.getInstance().saveUserData(userData);
                    if ( sw.isChecked() ){
                        MyDialog.showPromptDialog(getContext(),getString(R.string.prompt_order_receive),null);
                    } else {
                        MyDialog.showPromptDialog(getContext(),getString(R.string.prompt_order_close_after),null);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            MyDialog.showPromptDialog(getContext(),getString(R.string.prompt_order_receive),null);
        }
    };

}

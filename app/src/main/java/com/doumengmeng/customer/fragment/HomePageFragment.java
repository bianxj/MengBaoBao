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
import com.doumengmeng.customer.activity.AssessmentActivity;
import com.doumengmeng.customer.activity.DoctorInfoActivity;
import com.doumengmeng.customer.activity.DoctorListActivity;
import com.doumengmeng.customer.activity.HeadImageActivity;
import com.doumengmeng.customer.activity.MainActivity;
import com.doumengmeng.customer.activity.ObserveActivity;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseFragment;
import com.doumengmeng.customer.db.DaoManager;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.response.AllRecordResponse;
import com.doumengmeng.customer.response.InitConfigureResponse;
import com.doumengmeng.customer.response.entity.DayList;
import com.doumengmeng.customer.response.entity.Doctor;
import com.doumengmeng.customer.response.entity.Record;
import com.doumengmeng.customer.response.entity.RecordResult;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.view.AutoScrollViewPager;
import com.doumengmeng.customer.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RelativeLayout rl_observe_point,rl_doctor_list;
    private FrameLayout rl_baby_head;
    private CircleImageView civ_baby;
    private CheckBox cb_male;
    private TextView tv_baby_age;
    private AutoScrollViewPager asvp;

    private LinearLayout ll_unread_report;

    private List<InitConfigureResponse.Banner> banners;

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
        rl_observe_point = view.findViewById(R.id.rl_observe_point);
        rl_doctor_list = view.findViewById(R.id.rl_doctor_list);
        rl_baby_head = view.findViewById(R.id.rl_baby_head);
        civ_baby = view.findViewById(R.id.civ_baby);
        cb_male = view.findViewById(R.id.cb_male);
        tv_baby_age = view.findViewById(R.id.tv_baby_age);
        asvp = view.findViewById(R.id.asvp);

        ll_unread_report = view.findViewById(R.id.ll_unread_report);
    }

    private void initView(){
        if ( BaseApplication.getInstance().isUpperThan37Month() ){
            space_observ_point.setVisibility(View.GONE);
            rl_observe_point.setVisibility(View.GONE);
        } else {
            space_observ_point.setVisibility(View.VISIBLE);
            rl_observe_point.setVisibility(View.VISIBLE);
        }
        rl_side_menu.setOnClickListener(listener);
        rl_observe_point.setOnClickListener(listener);
        rl_doctor_list.setOnClickListener(listener);
        rl_baby_head.setOnClickListener(listener);

        //初始化轮播图
        banners = BaseApplication.getInstance().loadBannerData();
        List<String> urls = new ArrayList<>();
        for (InitConfigureResponse.Banner banner:banners) {
            urls.add(UrlAddressList.IMAGE_URL + banner.getImgurl());
        }
//
//        List<Integer> images = new ArrayList<>();
//        images.add(R.drawable.banner1);
//        images.add(R.drawable.banner2);
//        images.add(R.drawable.banner3);

        asvp.setUrlList(urls);

//        int[] images = new int[]{R.drawable.banner1,R.drawable.banner2,R.drawable.banner3};
//        asvp.setImageList(images);
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
        if ( !isHidden() ){
            getRecord();
        }
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_side_menu:
                    //切换抽屉
                    activity.toggleSideMenu();
                    break;
                case R.id.rl_observe_point:
                    //观察要点
                    startActivity(ObserveActivity.class);
                    break;
                case R.id.rl_doctor_list:
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

            InitConfigureResponse.Banner banner = banners.get(position-1);

            Intent intent = new Intent(getContext(),DoctorInfoActivity.class);
            intent.putExtra(DoctorInfoActivity.IN_PARAM_DOCTOR_ID , banner.getDoctorid());
            startActivity(intent);


//            String doctorId = "17";
//            if ( 1 == position ){
//                doctorId = "4";
//            } else if ( 2 == position ){
//                doctorId = "49";
//            }
//            Intent intent = new Intent(getContext(),DoctorInfoActivity.class);
//            intent.putExtra(DoctorInfoActivity.IN_PARAM_DOCTOR_ID , doctorId);
//            startActivity(intent);
        }
    };


    private List<Record> records = new ArrayList<>();
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){
            getRecord();
        } else {
            stopTask(recordTask);
        }
    }

    private RequestTask recordTask;
    private void getRecord(){
        try {
            recordTask = new RequestTask.Builder(getActivity(),getRecordCallBack)
                    .setUrl(UrlAddressList.URL_SEARCH_UNREAD_RECORD)
                    .setType(RequestTask.NO_LOADING)
                    .setContent(buildRecordContent())
                    .build();
            recordTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, String> buildRecordContent() {
        UserData userData = BaseApplication.getInstance().getUserData();
        Map<String ,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,userData.getUserid());
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        return map;
    }

    private final RequestCallBack getRecordCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            AllRecordResponse response = GsonUtil.getInstance().fromJson(result, AllRecordResponse.class);
            RecordResult result1 = response.getResult();
            List<Record> list = result1.getRecordList();

            records.clear();
            for (Record record : list) {
                record.setImageData(result1.getImgList());
                records.add(record);
            }
            buildRecordList();
//            xrv_record.setNoMore(true);
        }
    };

    private void buildRecordList(){
        ll_unread_report.removeAllViews();
        if ( records != null && records.size() > 0 ) {
            for (int i = 0; i < records.size(); i++) {
                ll_unread_report.addView(buildRecordView(i, records.get(i)));
            }
        }
    }

    private View buildRecordView(int index,Record record){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_unread_report,null);
        TextView tv_content = view.findViewById(R.id.tv_content);
        Doctor doctor = DaoManager.getInstance().getDaotorDao().searchDoctorById(getContext(),record.getDoctorid());
        tv_content.setText("宝妈，"+doctor.getDoctorname()+"医生给你家宝宝做的评估出来了～～");
        view.setTag(index);
        view.setOnClickListener(recordListener);
        return view;
    }

    private View.OnClickListener recordListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = (int) view.getTag();
            Intent intent = new Intent(getContext(), AssessmentActivity.class);
            intent.putExtra(AssessmentActivity.IN_PARAM_RECORD, GsonUtil.getInstance().toJson(records.get(index)));
            getContext().startActivity(intent);
        }
    };

}

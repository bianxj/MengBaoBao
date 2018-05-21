package com.doumengmeng.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.activity.MainActivity;
import com.doumengmeng.doctor.activity.QRActivity;
import com.doumengmeng.doctor.adapter.AssessmentAdapter;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseTimeFragment;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.response.AssessmentListResponse;
import com.doumengmeng.doctor.response.entity.AssessmentItem;
import com.doumengmeng.doctor.response.entity.UserData;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MyDialog;
import com.doumengmeng.doctor.view.CircleImageView;
import com.doumengmeng.doctor.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/26.
 */

public class HomeFragment extends BaseTimeFragment {

    private TextView tv_doctor_name,tv_doctor_positionaltitle,tv_hospital,tv_doctor_department;
    private RelativeLayout rl_qr;
    private TextView tv_hint;
    private LinearLayout ll_no_data , ll_complete;
    private CircleImageView civ_head;
    private XRecyclerView xrv;

    private AssessmentAdapter adapter;
    private List<AssessmentItem> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        stopTask(searchAssessmentTask);
        if ( !isHidden() ) {
            searchAssessmentList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopTask(searchAssessmentTask);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){
            searchAssessmentList();
        } else {
            stopTask(searchAssessmentTask);
        }
    }

    @Override
    public void minuteCallBack() {
        if ( View.VISIBLE == xrv.getVisibility() ) {
            refreshAssessment();
        }
    }

    private void findView(View view){
        initDoctorInfo(view);
        initAssessmentList(view);
    }

    private void initDoctorInfo(View view){
        civ_head = view.findViewById(R.id.civ_head);
        tv_doctor_name = view.findViewById(R.id.tv_doctor_name);
        tv_doctor_department = view.findViewById(R.id.tv_doctor_department);
        tv_doctor_positionaltitle = view.findViewById(R.id.tv_doctor_positionaltitle);
        tv_hospital = view.findViewById(R.id.tv_hospital);
        rl_qr = view.findViewById(R.id.rl_qr);
        tv_hint = view.findViewById(R.id.tv_hint);

        UserData userData = BaseApplication.getInstance().getUserData();
        if ( userData != null ){
            tv_doctor_name.setText(userData.getDoctorName());
            tv_doctor_department.setText(userData.getDepartmentName());
            tv_doctor_positionaltitle.setText(userData.getPositionalTitles());
            tv_hospital.setText(DaoManager.getInstance().getHospitalDao().searchHospitalNameById(getContext(),userData.getHospitalId()));
            loadHeadImg(civ_head, userData.getHeadimg());
        }
        rl_qr.setOnClickListener(listener);
    }

    private void loadHeadImg(ImageView imageView,String urlHeadImg){
        DisplayImageOptions.Builder builder = BaseApplication.getInstance().defaultDisplayImage();
        builder.showImageOnLoading(R.drawable.default_icon_doctor);
        builder.showImageForEmptyUri(R.drawable.default_icon_doctor);
        builder.showImageOnFail(R.drawable.default_icon_doctor);
        ImageLoader.getInstance().displayImage(urlHeadImg,imageView,builder.build());
    }

    private void initAssessmentList(View view){
        xrv = view.findViewById(R.id.xrv);
        xrv.setLoadingMoreEnabled(true);
        xrv.setFootView(new XLoadMoreFooter(getContext()));

        adapter = new AssessmentAdapter(items);
        xrv.setAdapter(adapter);
        ll_no_data = view.findViewById(R.id.ll_no_data);
        ll_complete = view.findViewById(R.id.ll_complete);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_qr:
                    goQRActivity();
                    break;
            }
        }
    };

    private void goQRActivity(){
        startActivity(QRActivity.class);
    }

    private RequestTask searchAssessmentTask;
    private void searchAssessmentList(){
        try {
            searchAssessmentTask = new RequestTask.Builder(getContext(),searchAssessmentCallback)
                    .setContent(buildRequestAssessment())
                    .setType(RequestTask.NO_LOADING)
                    .setUrl(UrlAddressList.URL_SEARCH_ASSESSMENT)
                    .build();
            searchAssessmentTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String,String> buildRequestAssessment(){
        Map<String,String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("doctorId",BaseApplication.getInstance().getUserData().getDoctorId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getSessionId());
        return map;
    }

    private RequestCallBack searchAssessmentCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(getContext(), ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            AssessmentListResponse response = GsonUtil.getInstance().fromJson(result,AssessmentListResponse.class);
            String notice = response.getResult().getNotice();
            if (!TextUtils.isEmpty(notice) ){
                if ( !BaseApplication.getInstance().getMaintain().equals(notice) ) {
                    MyDialog.showMaintainDialog(getContext(), notice);
                    BaseApplication.getInstance().saveMaintain(notice);
                }
            }

            if ( 0 == response.getResult().getHasEvaluation() ){
                refreshAssessment(null);
                showNoData();
            } else {
                if ( response.getResult().getUserRecordList().size() == 0 ){
                    refreshAssessment(null);
                    showAllComplete();
                } else {
                    refreshAssessment(response.getResult().getUserRecordList());
                }
            }
        }
    };

    public void showNoData(){
        ll_no_data.setVisibility(View.VISIBLE);
        ll_complete.setVisibility(View.GONE);
        xrv.setVisibility(View.GONE);
        tv_hint.setVisibility(View.GONE);
    }

    public void showAllComplete(){
        ll_no_data.setVisibility(View.GONE);
        ll_complete.setVisibility(View.VISIBLE);
        xrv.setVisibility(View.GONE);
        tv_hint.setVisibility(View.GONE);
    }

    public void refreshAssessment(List<AssessmentItem> serviceItems){
        ll_no_data.setVisibility(View.GONE);
        ll_complete.setVisibility(View.GONE);
        xrv.setVisibility(View.VISIBLE);
        tv_hint.setVisibility(View.VISIBLE);

        items.clear();
        if ( serviceItems != null ) {
            items.addAll(serviceItems);
        }
        refreshAssessment();
    }

    public synchronized void refreshAssessment(){
        BaseApplication.getInstance().saveAssessmentCount(items.size());
        refreshScript();
        adapter.notifyDataSetChanged();
    }

    public void refreshScript(){
        ((MainActivity)getActivity()).refreshSuperScript();
    }

}

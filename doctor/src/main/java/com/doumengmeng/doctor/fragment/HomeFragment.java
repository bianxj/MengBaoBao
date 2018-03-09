package com.doumengmeng.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.activity.QRActivity;
import com.doumengmeng.doctor.adapter.AssessmentAdapter;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseFragment;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.response.entity.UserData;
import com.doumengmeng.doctor.view.CircleImageView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by Administrator on 2018/2/26.
 */

public class HomeFragment extends BaseFragment {

    private TextView tv_doctor_name,tv_doctor_positionaltitle,tv_hospital,tv_doctor_department;
    private RelativeLayout rl_qr;
    private LinearLayout ll_no_data , ll_complete;
    private CircleImageView civ_head;
    private XRecyclerView xrv;

    private AssessmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        findView(view);
        return view;
    }

    private void findView(View view){
        initDoctorInfo(view);
        initAssessmentList(view);
    }

    private void initDoctorInfo(View view){
        tv_doctor_name = view.findViewById(R.id.tv_doctor_name);
        tv_doctor_department = view.findViewById(R.id.tv_doctor_department);
        tv_doctor_positionaltitle = view.findViewById(R.id.tv_doctor_positionaltitle);
        tv_hospital = view.findViewById(R.id.tv_hospital);
        rl_qr = view.findViewById(R.id.rl_qr);

        UserData userData = BaseApplication.getInstance().getUserData();
        if ( userData != null ){
            tv_doctor_name.setText(userData.getDoctorName());
            tv_doctor_department.setText(userData.getDepartmentName());
            tv_doctor_positionaltitle.setText(userData.getPositionalTitles());
            tv_hospital.setText(DaoManager.getInstance().getHospitalDao().searchHospitalNameById(getContext(),userData.getHospitalId()));
        }
        rl_qr.setOnClickListener(listener);
    }

    private void initAssessmentList(View view){
        xrv = view.findViewById(R.id.xrv);
        ll_no_data = view.findViewById(R.id.ll_no_data);
        ll_complete = view.findViewById(R.id.ll_complete);
    }

    private void refreshAssessmentList(){
        //TODO
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

}

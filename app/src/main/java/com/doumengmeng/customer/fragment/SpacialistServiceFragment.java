package com.doumengmeng.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.activity.RecordActivity;
import com.doumengmeng.customer.activity.SpacialistServiceActivity;
import com.doumengmeng.customer.adapter.RecordAdapter;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseFragment;
import com.doumengmeng.customer.base.BaseLoadingListener;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.request.ResponseErrorCode;
import com.doumengmeng.customer.request.task.RefundCheckTask;
import com.doumengmeng.customer.response.AllRecordResponse;
import com.doumengmeng.customer.response.entity.Record;
import com.doumengmeng.customer.response.entity.RecordResult;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.util.MyDialog;
import com.doumengmeng.customer.view.CircleImageView;
import com.doumengmeng.customer.view.XLoadMoreFooter;
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
 * 作者: 边贤君
 * 描述: 专家服务
 * 创建日期: 2018/1/15 13:18
 */
public class SpacialistServiceFragment extends BaseFragment {

    private LinearLayout ll_buy,ll_btn_buy;
    private RelativeLayout rl_supplement_record;
    private TextView tv_baby_name,tv_appraisal_count,tv_buy;
    private CircleImageView civ_baby;
    private RelativeLayout rl_add_record;
    private XRecyclerView xrv_record;

    private RelativeLayout rl_unbuy;
    private Button bt_buy;

    private final List<Record> records = new ArrayList<>();
    private RecordAdapter adapter;

    private UserData userData;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_spacialist_service,null);
        findView(view);
        initRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if ( !isHidden() ){
            initView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopTask(recordTask);
        stopTask(addRecordCheckTask);
        if ( task != null ){
            stopTask(task.getRequestTask());
        }
    }

    private void findView(View view){
        ll_buy = view.findViewById(R.id.ll_buy);
        rl_supplement_record = view.findViewById(R.id.rl_supplement_record);
        tv_baby_name = view.findViewById(R.id.tv_baby_name);
        tv_appraisal_count = view.findViewById(R.id.tv_appraisal_count);
        ll_btn_buy = view.findViewById(R.id.ll_btn_buy);
        tv_buy = view.findViewById(R.id.tv_buy);
        civ_baby = view.findViewById(R.id.civ_baby);
        rl_add_record = view.findViewById(R.id.rl_add_record);
        xrv_record = view.findViewById(R.id.xrv_record);

        rl_unbuy = view.findViewById(R.id.rl_unbuy);
        bt_buy = view.findViewById(R.id.bt_buy);
    }

    private void initView(){
        userData = BaseApplication.getInstance().getUserData();
        if (BaseApplication.getInstance().isPay()){
            ll_buy.setVisibility(View.VISIBLE);
            rl_unbuy.setVisibility(View.GONE);

            String recordTimes = userData.getRecordtimes();
            tv_appraisal_count.setText(String.format(getResources().getString(R.string.spacialist_service_count),recordTimes));
            tv_baby_name.setText(userData.getTruename());
            loadHeadImg(userData.isMale(), userData.getHeadimg());
            rl_supplement_record.setOnClickListener(listener);
            ll_btn_buy.setOnClickListener(listener);
//            tv_buy.setOnClickListener(listener);
            rl_add_record.setOnClickListener(listener);

            rl_add_record.setVisibility(View.VISIBLE);
//            //TODO
//            if ( BaseApplication.getInstance().isUpperThan37Month() ){
//                rl_add_record.setVisibility(View.GONE);
//                tv_buy.setVisibility(View.GONE);
//            } else {
//                if (TextUtils.isEmpty(recordTimes) || Integer.parseInt(recordTimes) <= 0) {
//                    rl_add_record.setVisibility(View.GONE);
//                } else {
//                    rl_add_record.setVisibility(View.VISIBLE);
//                }
//            }
            getRecord();
        } else {
            ll_buy.setVisibility(View.GONE);
            rl_unbuy.setVisibility(View.VISIBLE);

            bt_buy.setOnClickListener(listener);
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

    private BaseLoadingListener loadingListener;
    private void initRecyclerView(){
        xrv_record.setLoadingMoreEnabled(true);
        xrv_record.setFootView(new XLoadMoreFooter(getContext()));

        if ( loadingListener == null ){
            loadingListener = new BaseLoadingListener(xrv_record);
        }
        xrv_record.setLoadingListener(loadingListener);
        adapter = new RecordAdapter(records);
        xrv_record.setAdapter(adapter);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.ll_btn_buy:
                    startActivity(SpacialistServiceActivity.class);
                    break;
                case R.id.tv_buy:
                    startActivity(SpacialistServiceActivity.class);
                    break;
                case R.id.rl_add_record:
                    //TODO
                    addRecordCheck();
                    break;
                case R.id.bt_buy:
                    startActivity(SpacialistServiceActivity.class);
                    break;
            }
        }
    };

    private void addRecord(){
//        String recordTimes = userData.getRecordtimes();
//        if ( BaseApplication.getInstance().isUpperThan37Month() ){
//            MyDialog.showPromptDialog(getContext(), getString(R.string.prompt_over_36), new MyDialog.PromptDialogCallback() {
//                @Override
//                public void sure() {
//
//                }
//            });
//        } else {
//            if (TextUtils.isEmpty(recordTimes) || Integer.parseInt(recordTimes) <= 0) {
//                MyDialog.showChooseDialog(getContext(), getString(R.string.prompt_lost_zero), R.string.prompt_bt_cancel, R.string.prompt_bt_buy, new MyDialog.ChooseDialogCallback() {
//                    @Override
//                    public void sure() {
//                        startActivity(SpacialistServiceActivity.class);
//                    }
//
//                    @Override
//                    public void cancel() {
//
//                    }
//                });
//            } else {
                startActivity(RecordActivity.class);
//            }
//        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){
            initView();
        } else {
            stopTask(recordTask);
        }
    }

    private RequestTask recordTask;
    private void getRecord(){
        try {
            recordTask = new RequestTask.Builder(getActivity(),getRecordCallBack)
                    .setUrl(UrlAddressList.URL_GET_ALL_RECORD)
                    .setType(RequestTask.NO_LOADING)
                    .setContent(buildRecordContent())
                    .build();
            recordTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, String> buildRecordContent() {
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
            adapter.notifyDataSetChanged();
//            xrv_record.setNoMore(true);
        }
    };

    private RefundCheckTask task;
    private void addRecordCheck(){
        String recordTimes = userData.getRecordtimes();
        if ( BaseApplication.getInstance().isUpperThan37Month() ){
            MyDialog.showPromptDialog(getContext(), getString(R.string.prompt_over_36), new MyDialog.PromptDialogCallback() {
                @Override
                public void sure() {

                }
            });
        } else {
            if (TextUtils.isEmpty(recordTimes) || Integer.parseInt(recordTimes) <= 0) {
                MyDialog.showChooseDialog(getContext(), getString(R.string.prompt_lost_zero), R.string.prompt_bt_cancel, R.string.prompt_bt_buy, new MyDialog.ChooseDialogCallback() {
                    @Override
                    public void sure() {
                        startActivity(SpacialistServiceActivity.class);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            } else {
                checkRecord();
            }
        }
    }

    private RequestTask addRecordCheckTask;

    private void checkRecord(){
        try {
            addRecordCheckTask = new RequestTask.Builder(getContext(),addRecordCheckCallBack)
                    .setContent(initAddRecordCheckData())
                    .setType(RequestTask.DEFAULT)
                    .setUrl(UrlAddressList.URL_ADD_RECORD_CHECK)
                    .build();
            addRecordCheckTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String,String> initAddRecordCheckData(){
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userData.getUserid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        return map;
    }

    private RequestCallBack addRecordCheckCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            if (ResponseErrorCode.getErrorCode(result) == ResponseErrorCode.ERROR_DOCTOR_ALREADY_CLOSE){
                MyDialog.showPromptDialog(getContext(),getString(R.string.prompt_not_order_call),null);
            } else {
                MyDialog.showPromptDialog(getContext(),ResponseErrorCode.getErrorMsg(result),null);
            }
        }

        @Override
        public void onPostExecute(String result) {
            task = new RefundCheckTask(getContext(), RefundCheckTask.RefundType.ADD_RECORD, new RefundCheckTask.RefundCallBack() {
                @Override
                public void success() {
                    addRecord();
                }
            });
            task.startTask();
        }
    };


//    private RequestTask refundCheckTask = null;
//    private void refundCheck(){
//        try {
//            refundCheckTask = new RequestTask.Builder(getContext(),refundCheckCallback)
//                    .setContent(buildRefundCheckData())
//                    .setType(RequestTask.LOADING|RequestTask.JSON)
//                    .setUrl(UrlAddressList.URL_REFUND_CHECK).build();
//            refundCheckTask.execute();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    private Map<String,String> buildRefundCheckData(){
//        Map<String,String> map = new HashMap<>();
//        JSONObject object = new JSONObject();
//        try {
//            object.put("userId",userData.getUserid());
//            map.put(UrlAddressList.PARAM,object.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getUserData().getSessionId());
//        return map;
//    }
//
//    private RequestCallBack refundCheckCallback = new RequestCallBack() {
//        @Override
//        public void onPreExecute() {}
//
//        @Override
//        public void onError(String result) {
//            if (ResponseErrorCode.getErrorCode(result) == 1 ){
//                MyDialog.showPromptDialog(getContext(),getString(R.string.prompt_refund_add_record),null);
//            } else {
//                MyDialog.showPromptDialog(getContext(),ResponseErrorCode.getErrorMsg(result),null);
//            }
//        }
//
//        @Override
//        public void onPostExecute(String result) {
//            addRecord();
//        }
//    };

}

package com.doumengmengandroidbady.fragment;

import android.content.Context;
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

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.RecordActivity;
import com.doumengmengandroidbady.activity.SpacialistServiceActivity;
import com.doumengmengandroidbady.activity.SupplementRecordActivity;
import com.doumengmengandroidbady.adapter.RecordAdapter;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.base.BaseFragment;
import com.doumengmengandroidbady.entity.RoleType;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.response.Record;
import com.doumengmengandroidbady.response.RecordResult;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.view.CircleImageView;
import com.doumengmengandroidbady.view.XLoadMoreFooter;
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

    private LinearLayout ll_buy;
    private RelativeLayout rl_supplement_record;
    private TextView tv_baby_name,tv_appraisal_count,tv_buy;
    private CircleImageView civ_baby;
    private RelativeLayout rl_add_record;
    private XRecyclerView xrv_record;

    private RelativeLayout rl_unbuy;
    private Button bt_buy;

    private List<Record> records = new ArrayList<>();
    private RecordAdapter adapter;

    private UserData userData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spacialist_service,null);
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopTask(recordTask);
    }

    private void findView(View view){
        ll_buy = view.findViewById(R.id.ll_buy);
        rl_supplement_record = view.findViewById(R.id.rl_supplement_record);
        tv_baby_name = view.findViewById(R.id.tv_baby_name);
        tv_appraisal_count = view.findViewById(R.id.tv_appraisal_count);
        tv_buy = view.findViewById(R.id.tv_buy);
        civ_baby = view.findViewById(R.id.civ_baby);
        rl_add_record = view.findViewById(R.id.rl_add_record);
        xrv_record = view.findViewById(R.id.xrv_record);

        rl_unbuy = view.findViewById(R.id.rl_unbuy);
        bt_buy = view.findViewById(R.id.bt_buy);
    }

    private void initView(){
        userData = BaseApplication.getInstance().getUserData();
        if (RoleType.FREE_USER != BaseApplication.getInstance().getRoleType()){
            getRecord();
            ll_buy.setVisibility(View.VISIBLE);
            rl_unbuy.setVisibility(View.GONE);

            tv_baby_name.setText(userData.getTruename());
            loadHeadImg(userData.isMale(),userData.getHeadimg());
            String recordTimes = userData.getRecordtimes();
            tv_appraisal_count.setText("您还剩"+recordTimes+"次测评");

            if (TextUtils.isEmpty(recordTimes) || Integer.parseInt(recordTimes) <= 0 ){
                rl_add_record.setVisibility(View.GONE);
            } else {
                rl_add_record.setVisibility(View.VISIBLE);
            }

            rl_supplement_record.setOnClickListener(listener);
            tv_buy.setOnClickListener(listener);
            rl_add_record.setOnClickListener(listener);
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

    private void initRecyclerView(){
        xrv_record.setLoadingMoreEnabled(true);
        xrv_record.setFootView(new XLoadMoreFooter(getContext()));

        xrv_record.setLoadingListener(loadingListener);
        adapter = new RecordAdapter(records);
        xrv_record.setAdapter(adapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bt_buy:
                    startActivity(SpacialistServiceActivity.class);
                    break;
                case R.id.rl_supplement_record:
                    startActivity(SupplementRecordActivity.class);
                    break;
                case R.id.tv_buy:
                    startActivity(SpacialistServiceActivity.class);
                    break;
                case R.id.rl_add_record:
                    //TODO
                    startActivity(RecordActivity.class);
                    break;
            }
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){
            initView();
        }
    }

    private RequestTask recordTask;
    private void getRecord(){
        try {
            recordTask = new RequestTask.Builder(getRecordCallBack).build();
            recordTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestCallBack getRecordCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_GET_ALL_RECORD;
        }

        @Override
        public Context getContext() {
            return getActivity();
        }

        @Override
        public Map<String, String> getContent() {
            Map<String ,String> map = new HashMap<>();
            map.put(UrlAddressList.PARAM,userData.getUserid());
            map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
            return map;
        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                RecordResult recordResult = GsonUtil.getInstance().getGson().fromJson(res.toString(),RecordResult.class);
                List<Record> list = recordResult.getRecordList();

                records.clear();
                for (Record record:list){
                    record.setImageData(recordResult.getImgList());
                    records.add(record);
                }
                adapter.notifyDataSetChanged();
                xrv_record.setNoMore(true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String type() {
            return JSON;
        }
    };

    private XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {

        }
    };

}

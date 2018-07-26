package com.doumengmeng.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.activity.MainActivity;
import com.doumengmeng.doctor.adapter.MessageAdapter;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseFragment;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.response.MessageDeleteResponse;
import com.doumengmeng.doctor.response.ReadMessageResponse;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/7.
 */

public class MessageDetailFragment extends BaseFragment {

    public final static String IN_PARAM_MESSAGE_DATA = "in_message_data";

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title,tv_complete;

    private MessageAdapter.MessageData data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_detail,null);
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
        readMessage();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( hidden ){
            stopTask(deleteMessageTask);
            stopTask(readMessageTask);
        } else {
            refreshData();
            readMessage();
        }
    }

    private void findView(View view){
        initTitle(view);
        initContent(view);
    }

    private void initTitle(View view){
        rl_back = view.findViewById(R.id.rl_back);
        rl_complete = view.findViewById(R.id.rl_complete);
        tv_title = view.findViewById(R.id.tv_title);
        tv_complete = view.findViewById(R.id.tv_complete);

        tv_title.setText(R.string.message_details);
        tv_complete.setText(R.string.delete);

        rl_complete.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);
    }

    private TextView tv_message_title,tv_message_date,tv_message_content;
    private void initContent(View view){
        tv_message_title = view.findViewById(R.id.tv_message_title);
        tv_message_date = view.findViewById(R.id.tv_message_date);
        tv_message_content = view.findViewById(R.id.tv_message_content);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    delete();
                    break;
            }
        }
    };

    protected void back(){
        ((MainActivity)getContext()).switchFragment(MainActivity.PAGE_MESSAGE);
    }

    private void delete(){
        deleteMessage(data);
    }

    private void refreshData(){
        if ( obj != null ){
            data = (MessageAdapter.MessageData) obj;

            tv_message_title.setText(data.getMessageTitle());
            tv_message_date.setText(data.getMessageDate());
            tv_message_content.setText(data.getMessageDesc());
        }
    }

    private RequestTask readMessageTask;
    private void readMessage(){
        if ( data != null && !data.isRead() ) {
            try {
                readMessageTask = new RequestTask.Builder(getContext(), readMessageCallBack)
                        .setContent(buildRequestReadMessage(data))
                        .setType(RequestTask.NO_LOADING)
                        .setUrl(UrlAddressList.URL_READ_MESSAGE)
                        .build();
                readMessageTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private Map<String, String> buildRequestReadMessage(MessageAdapter.MessageData data){
        Map<String,String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("doctorId", BaseApplication.getInstance().getUserData().getDoctorId());
            object.put("newsId",data.getMessageId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getSessionId());
        return map;
    }

    private RequestCallBack readMessageCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            ReadMessageResponse response = GsonUtil.getInstance().fromJson(result,ReadMessageResponse.class);
            if ( response.getResult() != null && 1 == response.getResult().getIsRead() ){
                data.setRead(true);
                saveAndFreshScript();
            }
        }
    };

    private RequestTask deleteMessageTask;
    private void deleteMessage(MessageAdapter.MessageData data){
        try {
            deleteMessageTask = new RequestTask.Builder(getContext(),deleteMessageCallBack)
                    .setContent(buildRequestDeleteMessage(data))
                    .setType(RequestTask.DEFAULT)
                    .setUrl(UrlAddressList.URL_DELETE_MESSAGE)
                    .build();
            deleteMessageTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String,String> buildRequestDeleteMessage(MessageAdapter.MessageData data){
        Map<String,String> map = new HashMap<>();

        JSONObject object = new JSONObject();
        try {
            object.put("doctorId", BaseApplication.getInstance().getUserData().getDoctorId());
            object.put("newsId",data.getMessageId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        map.put(UrlAddressList.PARAM,object.toString());
        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getSessionId());
        return map;
    }

    private RequestCallBack deleteMessageCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(getContext(), ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            MessageDeleteResponse response = GsonUtil.getInstance().fromJson(result,MessageDeleteResponse.class);
            if ( response.getResult() != null && "1".equals(response.getResult().getIsDeleted()) ){
                back();
            }
        }
    };

    private void saveAndFreshScript(){
        BaseApplication.getInstance().saveMessageCount(BaseApplication.getInstance().getMessageCount()-1);
        ((MainActivity)getActivity()).refreshSuperScript();
    }

}

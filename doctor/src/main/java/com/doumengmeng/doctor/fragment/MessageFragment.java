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
import com.doumengmeng.doctor.activity.MainActivity;
import com.doumengmeng.doctor.adapter.MessageAdapter;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.base.BaseFragment;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.response.MessageDeleteResponse;
import com.doumengmeng.doctor.response.MessageResponse;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MyDialog;
import com.doumengmeng.doctor.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/26.
 */

public class MessageFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private LinearLayout ll_no_message;
    private XRecyclerView xrv;
    private MessageAdapter adapter;
    private List<MessageAdapter.MessageData> datas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,null);
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchMessage();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ){
            searchMessage();
        } else {
            stopTask(searchMessageTask);
            stopTask(deleteMessageTask);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopTask(searchMessageTask);
        stopTask(deleteMessageTask);
    }

    private void findView(View view){
        initTitle(view);
        initMessageList(view);
    }

    private void initTitle(View view){
        rl_back = view.findViewById(R.id.rl_back);
        tv_title = view.findViewById(R.id.tv_title);

        tv_title.setText(getString(R.string.message));
        rl_back.setVisibility(View.GONE);
    }

    private void initMessageList(View view){
        ll_no_message = view.findViewById(R.id.ll_no_message);
        xrv = view.findViewById(R.id.xrv);
        xrv.setLoadingMoreEnabled(false);
        xrv.setFootView(new XLoadMoreFooter(getContext()));
        adapter = new MessageAdapter(xrv,datas,deleteCallBack);
        xrv.setAdapter(adapter);

        refreshMessageList();
        saveAndFreshScript();
    }

    private void refreshMessageList(){
        if ( datas.size() > 0 ) {
            ll_no_message.setVisibility(View.GONE);
        } else {
            ll_no_message.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    private RequestTask searchMessageTask;
    private void searchMessage(){
        try {
            searchMessageTask = new RequestTask.Builder(getContext(),searchMessageCallBack)
                    .setContent(buildRequestSearchMessage())
                    .setType(RequestTask.NO_LOADING)
                    .setUrl(UrlAddressList.URL_SEARCH_MESSAGE)
                    .build();
            searchMessageTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String ,String> buildRequestSearchMessage(){
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("doctorId",BaseApplication.getInstance().getUserData().getDoctorId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.PARAM, object.toString());
        map.put(UrlAddressList.SESSION_ID, BaseApplication.getInstance().getSessionId());
        return map;
    }

    private RequestCallBack searchMessageCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(getContext(), ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            MessageResponse response = GsonUtil.getInstance().fromJson(result,MessageResponse.class);
            datas.clear();
            List<MessageResponse.News> news = response.getResult().getDoctorNews();

            for (MessageResponse.News message:news){
                MessageAdapter.MessageData data = new MessageAdapter.MessageData();
                data.setMessageId(message.getNewsid());
                data.setRead("1".equals(message.getNewsstate()));
                data.setMessageTitle(message.getNewsname());
                data.setMessageDesc(message.getNewscontent());
                data.setMessageDate(message.getNewsdate().split(" ")[0]);
                if ("1".equals(message.getNewstype())){
                    data.setMessageType(MessageAdapter.MESSAGE_TYPE.HOLIDAY);
                } else if ( "2".equals(message.getNewstype()) ){
                    data.setMessageType(MessageAdapter.MESSAGE_TYPE.MAINTAIN);
                } else if ( "3".equals(message.getNewstype()) ){
                    data.setMessageType(MessageAdapter.MESSAGE_TYPE.OVER_TIME);
                } else {
                    data.setMessageType(MessageAdapter.MESSAGE_TYPE.WAIT_ASSESSMENT);
                }
                datas.add(data);
            }
            refreshMessageList();
            saveAndFreshScript();
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
            object.put("doctorId",BaseApplication.getInstance().getUserData().getDoctorId());
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
//            MyDialog.showPromptDialog(getContext(), ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            MessageDeleteResponse response = GsonUtil.getInstance().fromJson(result,MessageDeleteResponse.class);
            if ( response.getResult() != null && "1".equals(response.getResult().getIsDeleted()) ){
                adapter.removeMessage(response.getResult().getNewsId());
                saveAndFreshScript();
            }
        }
    };

    private MessageAdapter.OnDeleteCallBack deleteCallBack = new MessageAdapter.OnDeleteCallBack() {
        @Override
        public void delete(MessageAdapter.MessageData data) {
            deleteMessage(data);
        }
    };

    private void saveAndFreshScript(){
        BaseApplication.getInstance().saveMessageCount(adapter.getUnReadMessageCount());
        ((MainActivity)getActivity()).refreshSuperScript();
    }

}

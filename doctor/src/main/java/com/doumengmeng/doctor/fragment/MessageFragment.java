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
import com.doumengmeng.doctor.adapter.MessageAdapter;
import com.doumengmeng.doctor.base.BaseFragment;
import com.doumengmeng.doctor.view.XLoadMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class MessageFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;

    private LinearLayout ll_no_message;
    private XRecyclerView xrv;
    private MessageAdapter adapter;
    private List<MessageAdapter.MessageData> datas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,null);
        findView(view);
        return view;
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

        datas = new ArrayList<>();
        for (int i = 0 ;i<10;i++){
            MessageAdapter.MessageData data = new MessageAdapter.MessageData();
            if ( i%4 == 0 ) {
                data.setMessageType(MessageAdapter.MESSAGE_TYPE.HOLIDAY);
            } else if ( i%4 == 1 ){
                data.setMessageType(MessageAdapter.MESSAGE_TYPE.MAINTAIN);
            } else if ( i%4 == 2 ){
                data.setMessageType(MessageAdapter.MESSAGE_TYPE.OVER_TIME);
            } else if ( i%4 == 3 ){
                data.setMessageType(MessageAdapter.MESSAGE_TYPE.WAIT_ASSESSMENT);
            }
            data.setMessageTitle("Test"+i);
            data.setMessageDate("Date"+i);
            data.setMessageDesc("Desc"+i);
            datas.add(data);
        }

        adapter = new MessageAdapter(datas);
        xrv.setAdapter(adapter);
        refreshMessageList();
    }

    private void refreshMessageList(){
        if ( datas.size() > 0 ) {
            ll_no_message.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            ll_no_message.setVisibility(View.VISIBLE);
        }
    }

}

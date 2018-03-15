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
import com.doumengmeng.doctor.base.BaseFragment;

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

    private void findView(View view){
        initTitle(view);
        initContent(view);
    }

    private void initTitle(View view){
        rl_back = view.findViewById(R.id.rl_back);
        rl_complete = view.findViewById(R.id.rl_back);
        tv_title = view.findViewById(R.id.tv_title);
        tv_complete = view.findViewById(R.id.tv_complete);

        tv_title.setText(R.string.message_details);
        tv_complete.setText(R.string.delete);

        rl_back.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);
    }

    private TextView tv_message_title,tv_message_date,tv_message_content;
    private void initContent(View view){
        tv_message_title = view.findViewById(R.id.tv_message_title);
        tv_message_date = view.findViewById(R.id.tv_message_date);
        tv_message_content = view.findViewById(R.id.tv_message_content);
        if ( obj != null ){
            data = (MessageAdapter.MessageData) obj;

            tv_message_title.setText(data.getMessageTitle());
            tv_message_date.setText(data.getMessageDate());
            tv_message_content.setText(data.getMessageDesc());
        }
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

    private void back(){
        ((MainActivity)getContext()).switchFragment(MainActivity.PAGE_MESSAGE);
    }

    private void delete(){
        //TODO
    }

}

package com.doumengmeng.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.activity.BabyKnowledgeDetailsActivity;
import com.doumengmeng.customer.adapter.BabyKnowledgeAdapter;
import com.doumengmeng.customer.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 育儿知识
 * 创建日期: 2018/1/8 13:57
 */
public class BabyKnowledgeFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private GridView gv;
    private BabyKnowledgeAdapter adapter;
//    private List<String> infos = null;
    private List<String> pages = null;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_baby_knowledge,null);
        findView(view);
        configView();
        return view;
    }

    private void findView(View view){
        rl_back = view.findViewById(R.id.rl_back);
        tv_title = view.findViewById(R.id.tv_title);
        gv = view.findViewById(R.id.gv);
    }

    private void configView(){
        rl_back.setVisibility(View.INVISIBLE);
        tv_title.setText(R.string.baby_knowledge);
        initGridView();
    }

    private void initGridView(){
        List<String> infos = new ArrayList<>();
        pages = new ArrayList<>();
        genTimeQuantum(infos,pages);
        adapter = new BabyKnowledgeAdapter(getContext(),infos);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(itemClickListener);
    }

    private void genTimeQuantum(List<String> infos , List<String> pages){
        infos.add("0~1个月");
        infos.add("1~3个月");
        infos.add("4~6个月");
        infos.add("6~9个月");
        infos.add("9~12个月");
        infos.add("12~18个月");
        infos.add("18~24个月");
        infos.add("24~36个月");

        pages.add("file:///android_asset/babyknowledge0-1.html");
        pages.add("file:///android_asset/babyknowledge1-3.html");
        pages.add("file:///android_asset/babyknowledge4-6.html");
        pages.add("file:///android_asset/babyknowledge6-9.html");
        pages.add("file:///android_asset/babyknowledge9-12.html");
        pages.add("file:///android_asset/babyknowledge12-18.html");
        pages.add("file:///android_asset/babyknowledge18-24.html");
        pages.add("file:///android_asset/babyknowledge24-36.html");
    }

    private final AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String title = (String) adapter.getItem(position);
            Intent intent = new Intent(getContext(), BabyKnowledgeDetailsActivity.class);
            intent.putExtra(BabyKnowledgeDetailsActivity.IN_PARAM_TITLE,title);
            intent.putExtra(BabyKnowledgeDetailsActivity.IN_PARAM_PAGE,pages.get(position));
            startActivity(intent);
        }
    };

}

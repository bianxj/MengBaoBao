package com.doumengmengandroidbady.fragment;

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

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.activity.BabyKnowledegDetailsActivity;
import com.doumengmengandroidbady.adapter.BabyKnowledgeAdapter;
import com.doumengmengandroidbady.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class BabyKnowledgeFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private GridView gv;
    private BabyKnowledgeAdapter adapter;
    private List<String> infos = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        infos = genTimeQuantum(36,3);
        adapter = new BabyKnowledgeAdapter(getContext(),infos);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(itemClickListener);
    }

    private List<String> genTimeQuantum(int maxMonth,int split){
        List<String> infos = new ArrayList<String>();
        for (int i = 0; i < maxMonth; i+=split) {
            infos.add(i+"~"+(i+3)+"个月");
        }
        return infos;
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String title = (String) adapter.getItem(position);
            Intent intent = new Intent(getContext(), BabyKnowledegDetailsActivity.class);
            intent.putExtra(BabyKnowledegDetailsActivity.TITLE,title);
            startActivity(intent);
        }
    };

}

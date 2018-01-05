package com.doumengmengandroidbady.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.doumengmengandroidbady.R;

/**
 * Created by Administrator on 2018/1/4.
 */

public class BehaviorAdapter extends RecyclerView.Adapter<BehaviorAdapter.ViewHolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_developmental_behavior,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_hehavior_title;
        private CheckBox cb_hebavior;
        private TextView tv_behavior;
        private ImageView iv_behavior;
        private View view_underline;
        private android.support.v4.widget.Space space_behavior;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_hehavior_title = itemView.findViewById(R.id.tv_hehavior_title);
            cb_hebavior = itemView.findViewById(R.id.cb_hebavior);
            tv_behavior = itemView.findViewById(R.id.tv_behavior);
            iv_behavior = itemView.findViewById(R.id.iv_behavior);
            view_underline = itemView.findViewById(R.id.view_underline);
            space_behavior = itemView.findViewById(R.id.space_behavior);
        }

        private void initData(){
            //TODO
            boolean isTitle = false;
            if ( isTitle ) {
                tv_hehavior_title.setVisibility(View.VISIBLE);
                space_behavior.setVisibility(View.VISIBLE);
            } else {
                tv_hehavior_title.setVisibility(View.GONE);
                space_behavior.setVisibility(View.GONE);
            }
        }

    }

}

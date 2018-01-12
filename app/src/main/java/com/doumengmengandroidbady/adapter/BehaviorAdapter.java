package com.doumengmengandroidbady.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.response.Feature;
import com.doumengmengandroidbady.view.MyGifPlayer;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class BehaviorAdapter extends RecyclerView.Adapter<BehaviorAdapter.ViewHolder> {

    private List<String> selection;
    private List<Feature> features;

    public BehaviorAdapter(List<Feature> features,List<String> selection) {
        this.features = features;
        this.selection = selection;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_developmental_behavior,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Feature feature = features.get(position);
        holder.initData(feature,selection);
    }

    @Override
    public int getItemCount() {
        return features.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_hehavior_title;
        private CheckBox cb_hebavior;
        private TextView tv_behavior;
        private MyGifPlayer player;
        private View view_underline;
        private android.support.v4.widget.Space space_behavior;
        private Feature feature;
        private List<String> selection;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_hehavior_title = itemView.findViewById(R.id.tv_hehavior_title);
            cb_hebavior = itemView.findViewById(R.id.cb_hebavior);
            tv_behavior = itemView.findViewById(R.id.tv_behavior);
            player = itemView.findViewById(R.id.player);
            view_underline = itemView.findViewById(R.id.view_underline);
            space_behavior = itemView.findViewById(R.id.space_behavior);
        }

        private void initData(Feature feature,List<String> selection){
            this.feature = feature;
            this.selection = selection;
            if ( feature.isTitle() ) {
                tv_hehavior_title.setVisibility(View.VISIBLE);
                space_behavior.setVisibility(View.VISIBLE);
            } else {
                tv_hehavior_title.setVisibility(View.GONE);
                space_behavior.setVisibility(View.GONE);
            }

            tv_hehavior_title.setText(feature.getFeaturetype());
            tv_behavior.setText(feature.getDetaildesc());
            cb_hebavior.setChecked(selection.contains( feature.getId()));
            cb_hebavior.setOnCheckedChangeListener(changeListener);
            if (TextUtils.isEmpty(feature.getExampleimgurl())) {
                player.clearDrawable();
                player.setVisibility(View.GONE);
            } else {
                player.setVisibility(View.VISIBLE);
                player.setDrawable(UrlAddressList.BASE_URL + feature.getExampleimgurl());
                player.setOnClickListener(listener);
            }
        }

        private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( b ){
                    selection.add(feature.getId());
                } else {
                    selection.remove(feature.getId());
                }
            }
        };

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };

    }

}

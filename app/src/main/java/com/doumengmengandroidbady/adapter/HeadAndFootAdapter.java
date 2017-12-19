package com.doumengmengandroidbady.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/12/19.
 */

public class HeadAndFootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int HEAD_TYPE = 0x01;
    private final static int FOOT_TYPE = 0x02;

    private View headView,footView;
    private RecyclerView.Adapter baseAdapter;
    private LoadMoreCallBack callBack;

    public HeadAndFootAdapter(RecyclerView.Adapter baseAdapter) {
        this.baseAdapter = baseAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        if ( headView != null && position == 0 ){
            return HEAD_TYPE;
        }

        if ( footView != null && position == (getItemCount()-1) ){
            return FOOT_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( HEAD_TYPE == viewType ){
            return new HeadViewHolder(headView);
        }
        if ( FOOT_TYPE == viewType ){
            return new FootViewHolder(footView);
        }
        return baseAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (null != headView && 0 == position ){
            return;
        }

        if ( null != footView && getItemCount()-1 == position ){
            return;
        }

        baseAdapter.onBindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        if ( headView != null && footView != null ){
            return baseAdapter.getItemCount() + 2;
        } else if ( headView != null || footView != null ){
            return baseAdapter.getItemCount() + 1;
        }
        return baseAdapter.getItemCount();
    }

    private static class HeadViewHolder extends RecyclerView.ViewHolder{

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }
    private static class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnLoadMoreListener(RecyclerView recyclerView,LoadMoreCallBack loadMoreCallBack){
        this.callBack = loadMoreCallBack;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if ( isBottom(recyclerView) ){
                    callBack.loadMore();
                }
            }
        });
    }

    public void addHeadView(View headView){
        this.headView = headView;
    }

    public void addFootView(View footView){
        this.footView = footView;
    }

    protected boolean isBottom(RecyclerView view){
        if ( view != null ){
            if ( view.computeVerticalScrollExtent() + view.computeVerticalScrollOffset() >= view.computeVerticalScrollRange() ){
                return true;
            }
        }
        return false;
    }

    public interface LoadMoreCallBack{
        public void loadMore();
    }

}

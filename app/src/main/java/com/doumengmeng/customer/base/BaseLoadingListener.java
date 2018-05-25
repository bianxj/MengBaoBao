package com.doumengmeng.customer.base;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class BaseLoadingListener implements XRecyclerView.LoadingListener {

    private XRecyclerView recyclerView;

    public BaseLoadingListener(XRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        recyclerView.setNoMore(true);
    }
}

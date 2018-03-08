package com.doumengmeng.doctor.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.jcodecraeer.xrecyclerview.BaseLoadMoreFooter;

/**
 * Created by Administrator on 2017/12/20.
 */

public class XLoadMoreFooter extends BaseLoadMoreFooter {

    private TextView tv_loading_text;
    private View line1 , line2;

    public XLoadMoreFooter(Context context) {
        super(context);
        initView(context);
    }

    public XLoadMoreFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.footer_load_more,null);
        addView(view);
        findView(view);
    }

    private void findView(View view){
        tv_loading_text = view.findViewById(R.id.tv_loading_text);
        line1 = view.findViewById(R.id.line1);
        line2 = view.findViewById(R.id.line2);
        line1.setVisibility(GONE);
        line2.setVisibility(GONE);
    }

    @Override
    protected void loading() {
        line1.setVisibility(GONE);
        line2.setVisibility(GONE);
        tv_loading_text.setText("……正在载入");
    }

    @Override
    protected void loadingComplete() {

    }

    @Override
    protected void noMoreLoading() {
        line1.setVisibility(VISIBLE);
        line2.setVisibility(VISIBLE);
        tv_loading_text.setText("没有更多内容了");
    }
}

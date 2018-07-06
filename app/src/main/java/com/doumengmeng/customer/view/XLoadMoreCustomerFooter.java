package com.doumengmeng.customer.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.jcodecraeer.xrecyclerview.BaseLoadMoreFooter;

public class XLoadMoreCustomerFooter extends BaseLoadMoreFooter {

    private TextView tv_loading_text;
    private View line1 , line2;
    private ImageView loading;

    public XLoadMoreCustomerFooter(Context context) {
        super(context);
        initView(context);
    }

    public XLoadMoreCustomerFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.footer_customer_load_more,null);
        addView(view);
        findView(view);
    }

    private void findView(View view){
        tv_loading_text = view.findViewById(R.id.tv_loading_text);
        line1 = view.findViewById(R.id.line1);
        line2 = view.findViewById(R.id.line2);
        loading = view.findViewById(R.id.loading);
        line1.setVisibility(GONE);
        line2.setVisibility(GONE);
        loading.setVisibility(GONE);
    }

    @Override
    protected void loading() {
        line1.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
        startLoading();
        tv_loading_text.setText("加载中...");
    }

    @Override
    protected void loadingComplete() {
        stopLoading();
    }

    @Override
    protected void noMoreLoading() {
        stopLoading();
        line1.setVisibility(VISIBLE);
        line2.setVisibility(VISIBLE);
        tv_loading_text.setText("· 这已经是底部了 ·");
    }

    @Override
    protected void loadMoreFailed() {
        stopLoading();
        line1.setVisibility(VISIBLE);
        line2.setVisibility(VISIBLE);
        tv_loading_text.setText("· 加载失败 ·");
    }

    RotateAnimation animation;
    private void startLoading(){
        loading.setVisibility(View.VISIBLE);
        if ( animation == null ) {
            animation = new RotateAnimation(0, 36000, RotateAnimation.RELATIVE_TO_SELF, 0.5F, RotateAnimation.RELATIVE_TO_SELF, 0.5F);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(100000);
        }
        loading.startAnimation(animation);
    }

    private void stopLoading(){
        if ( animation != null ) {
            animation.cancel();
        }
        loading.clearAnimation();
        loading.setVisibility(View.INVISIBLE);
        loading.invalidate();
    }

}

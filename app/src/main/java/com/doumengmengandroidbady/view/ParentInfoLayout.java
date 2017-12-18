package com.doumengmengandroidbady.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.entity.ParentInfo;

/**
 * Created by Administrator on 2017/12/13.
 */

public class ParentInfoLayout extends LinearLayout {

    private Context context;

    public ParentInfoLayout(Context context) {
        super(context);
        initInfoView(context);
    }

    public ParentInfoLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInfoView(context);
    }

    public ParentInfoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInfoView(context);
    }

    private void initInfoView(Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_parent_info,null);
        addView(view);
        findView();
        initView();
    }


    private void findView(){

    }

    private void initView(){

    }

    private ParentInfo parentInfo;

    public boolean checkParentInfo(){
        return true;
    }

    public ParentInfo getParentInfo() {
        return parentInfo;
    }

    public void setParentInfo(ParentInfo parentInfo) {
        this.parentInfo = parentInfo;
    }
}

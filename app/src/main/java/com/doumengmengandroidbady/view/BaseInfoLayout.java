package com.doumengmengandroidbady.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.doumengmengandroidbady.R;

/**
 * Created by Administrator on 2017/12/13.
 */

public class BaseInfoLayout extends LinearLayout {

    private Context context;

    public BaseInfoLayout(Context context) {
        super(context);
        initInfoView(context);
    }

    public BaseInfoLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInfoView(context);
    }

    public BaseInfoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInfoView(context);
    }

    private void initInfoView(Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_base_info,null);
        addView(view);
    }

}

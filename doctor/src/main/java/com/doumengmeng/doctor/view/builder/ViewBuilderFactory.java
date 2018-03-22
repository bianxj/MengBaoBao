package com.doumengmeng.doctor.view.builder;

import android.content.Context;

import com.doumengmeng.doctor.view.param.ViewParam;

/**
 * Created by Administrator on 2018/3/19.
 */

public class ViewBuilderFactory {

    public static TextViewBuilder buildTextView(Context context, int layout, ViewParam[] params){
        return new TextViewBuilder(context,layout,params);
    }

}

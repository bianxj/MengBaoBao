package com.doumengmeng.doctor.view.builder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.doumengmeng.doctor.view.param.ViewParam;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/3/19.
 */

public class TextViewBuilder implements ViewBuilder {

    private WeakReference<Context> weakReference;
    private int layout;
    private View view;
    private ViewParam[] params;

    public TextViewBuilder(Context context, int layout, ViewParam[] params) {
        this.weakReference = new WeakReference<Context>(context);
        this.layout = layout;
        this.params = params;
    }

    @Override
    public View createView() {
        if ( view == null ){
            view = LayoutInflater.from(weakReference.get()).inflate(layout,null);
            for (ViewParam param:params){
                param.refreshTitle(view);
            }
        }
        return view;
    }

    @Override
    public void refreshValue() {
        if (view != null){
            for (ViewParam param:params){
                param.refreshValue(view);
            }
        }
    }
}

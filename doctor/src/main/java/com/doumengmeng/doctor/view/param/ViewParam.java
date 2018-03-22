package com.doumengmeng.doctor.view.param;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/19.
 */

public class ViewParam {
    protected int titleId;
    protected int valueId;
    protected String title;
    protected Object value;

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getValueId() {
        return valueId;
    }

    public void setValueId(int valueId) {
        this.valueId = valueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void refreshTitle(View view){
        TextView title = view.findViewById(getTitleId());
        title.setText(getTitle());
    }
    public void refreshValue(View view){
        if ( getValue() != null ){
            TextView value = view.findViewById(getValueId());
            value.setText((String) getValue());
        }
    }

}

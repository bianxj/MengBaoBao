package com.doumengmeng.doctor.view.param;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/19.
 */

public class TextViewParam extends ViewParam {
    private int format;

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    @Override
    public void refreshValue(View view) {
        if ( getValue() != null ){
            TextView value = view.findViewById(getValueId());
            if ( format != 0 ){
//                value.setText(String.format(String.format(getFormat(),)));
            } else {
                value.setText((String) getValue());
            }
        }
    }
}

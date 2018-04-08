package com.doumengmeng.customer.entity;

import android.app.Activity;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SideMenuItem {

    private final int drawable;
    private final int content;
    private final Class<? extends Activity> switchActivity;

    public SideMenuItem(int drawable, int content, Class<? extends Activity> switchActivity) {
        this.drawable = drawable;
        this.content = content;
        this.switchActivity = switchActivity;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getContent() {
        return content;
    }

    public Class<? extends Activity> getSwitchActivity() {
        return switchActivity;
    }
}

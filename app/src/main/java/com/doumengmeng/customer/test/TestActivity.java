package com.doumengmeng.customer.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.doumengmeng.customer.R;

/**
 * Created by Administrator on 2018/1/24.
 */
//just test
public class TestActivity extends Activity {

    private TestView test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}

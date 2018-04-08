package com.doumengmeng.customer.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

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
        test = findViewById(R.id.test);
    }

    public void onFirstImage(View view){
        test.setBackGround(R.drawable.bg_boy_height_weight_0_2);
    }

    public void onSecondImage(View view){
        test.setBackGround(R.drawable.bg_boy_weight_data);
    }
}

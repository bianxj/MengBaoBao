package com.doumengmengandroidbady.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.response.entity.DayList;

/**
 * Created by Administrator on 2018/1/18.
 */

public class BaseInputDataActivity extends BaseActivity {


    protected int month;
    protected boolean isBoy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData(){
        DayList dayList = BaseApplication.getInstance().getDayList();
        month = Integer.parseInt(dayList.getCurrentMonth());
        isBoy = BaseApplication.getInstance().getUserData().isMale();
    }

    protected void generateListView(ViewGroup container ,String[] contents){
        if ( contents == null || contents.length <= 0 ){
            container.setVisibility(View.GONE);
            return;
        }
        for (String content:contents){
            TextView tv = new TextView(this);
            tv.setTextColor(getResources().getColor(R.color.second_black));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y26px));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y28px);
            tv.setLayoutParams(params);
            tv.setText(content);
            container.addView(tv);
        }
        container.setVisibility(View.VISIBLE);
    }

}
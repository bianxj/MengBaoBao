package com.doumengmeng.customer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.response.entity.DayList;
import com.doumengmeng.customer.util.MyDialog;

/**
 * Created by Administrator on 2018/1/18.
 */

public abstract class BaseInputDataActivity extends BaseSwipeActivity {


    protected int month;
    protected boolean isBoy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initChildData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyDialog.dismissPromptTopDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initData(){
        DayList dayList = BaseApplication.getInstance().getDayList();
        month = Integer.parseInt(dayList.getCorrentMonth());
        month = month<0?0:month;
        month = month>36?36:month;
        isBoy = BaseApplication.getInstance().getUserData().isMale();
    }

    private void initView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);
        rl_close = findViewById(R.id.rl_close);

        tv_title.setText(getTitleName());
        rl_complete.setVisibility(View.VISIBLE);
        rl_close.setOnClickListener(listener);
        rl_complete.setOnClickListener(listener);
        rl_back.setOnClickListener(listener);
    }

    protected void generateListView(ViewGroup container ,String[] contents,boolean isMark){
        if ( contents == null || contents.length <= 0 ){
            container.setVisibility(View.GONE);
            return;
        }
        int index = 0;
        for (String content:contents){
            TextView tv = new TextView(this);
            tv.setTextColor(getResources().getColor(R.color.second_black));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y26px));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y28px);
            tv.setLayoutParams(params);
            if ( isMark && index == 0 ){
                String value = "*建议："+content;
                SpannableString style = new SpannableString(value);
                style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.first_pink)), 0, 4,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(style);
            } else {
                tv.setText(content);
            }
            container.addView(tv);
            index++;
        }
        container.setVisibility(View.VISIBLE);
    }

    protected void generateListView(ViewGroup container ,String[] contents){
        generateListView(container ,contents,false);
    }

    protected RelativeLayout rl_back,rl_complete,rl_close;
    protected TextView tv_title,tv_complete;
    protected ImageView iv_close;

    protected void clearPromptTitle(){
        tv_title.setText(getTitleName());
        rl_complete.setVisibility(View.VISIBLE);
        rl_back.setVisibility(View.VISIBLE);
        rl_close.setVisibility(View.GONE);
    }

    protected void showPromptTitle(String message){
        MyDialog.showPromptTopDialog(this,getWindow().getDecorView(),message);
//        tv_title.setText(message);
//        rl_back.setVisibility(View.GONE);
//        rl_complete.setVisibility(View.GONE);
//        rl_close.setVisibility(View.VISIBLE);
    }

    protected View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.rl_close:
                    clearPromptTitle();
                    break;
                case R.id.rl_complete:
                    complete();
                    break;
                case R.id.rl_back:
                    back();
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( rl_close.getVisibility() == View.VISIBLE ){
            if ( keyCode == KeyEvent.KEYCODE_BACK ){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract int getTitleName();

    protected abstract void initChildData();

    protected abstract void complete();

    protected abstract void back();

}

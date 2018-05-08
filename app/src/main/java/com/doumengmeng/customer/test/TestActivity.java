package com.doumengmeng.customer.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.doumengmeng.customer.R;

/**
 * Created by Administrator on 2018/1/24.
 */
//just test
public class TestActivity extends Activity {

    private RelativeLayout rl_window;
    private Button bt_start;
    private ImageView iv_line;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView(){
        rl_window = findViewById(R.id.rl_window);
        bt_start = findViewById(R.id.bt_start);
        iv_line = findViewById(R.id.iv_line);
        bt_start.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startAnime();
        }
    };

    private void startAnime(){

        Animation animation = new TranslateAnimation(0,0,0,700);
        animation.setDuration(1500); // 动画持续时间
        animation.setRepeatCount(Animation.INFINITE); // 无限循环

        iv_line.clearAnimation();
        iv_line.startAnimation(animation);
    }

}

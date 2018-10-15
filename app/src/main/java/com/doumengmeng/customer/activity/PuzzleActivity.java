package com.doumengmeng.customer.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseActivity;
import com.doumengmeng.customer.util.MyDialog;
import com.mcxtzhang.captchalib.SwipeCaptchaView;

/**
 * 作者: 边贤君
 * 描述: 拼图页面
 */
public class PuzzleActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        initView();
    }

    private void initView(){
        initTitle();
        initPuzzle();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;

    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_back.setOnClickListener(listener);
//        tv_title.setText("拼图验证");
    }

    private SwipeCaptchaView mSwipeCaptchaView;
    private SeekBar mSeekBar;
    private void initPuzzle(){
        mSwipeCaptchaView = findViewById(R.id.swipeCaptchaView);
        mSeekBar = findViewById(R.id.dragBar);

        mSwipeCaptchaView.setOnCaptchaMatchCallback(callback);
        mSeekBar.setOnSeekBarChangeListener(changeListener);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.bg_puzzle);
        mSwipeCaptchaView.setImageBitmap(bmp);
        mSwipeCaptchaView.createCaptcha();
//        //测试从网络加载图片是否ok
//        Glide.with(this).asBitmap()
//                .load("http://www.investide.cn/data/edata/image/20151201/20151201180507_281.jpg")
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        mSwipeCaptchaView.setImageBitmap(resource);
//                        mSwipeCaptchaView.createCaptcha();
//                    }
//                });
    }

    private SwipeCaptchaView.OnCaptchaMatchCallback callback = new SwipeCaptchaView.OnCaptchaMatchCallback(){

        @Override
        public void matchSuccess(SwipeCaptchaView swipeCaptchaView) {
            MyDialog.showPromptTopDialog(PuzzleActivity.this,getWindow().getDecorView(),"验证通过");
            setResult(Activity.RESULT_OK);
            mSeekBar.setEnabled(false);
            mSeekBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    back();
                }
            },1000);
        }

        @Override
        public void matchFailed(SwipeCaptchaView swipeCaptchaView) {
            MyDialog.showPromptTopDialog(PuzzleActivity.this,getWindow().getDecorView(),"你有80%的可能是机器人，现在走还来得及");
            swipeCaptchaView.resetCaptcha();
            mSeekBar.setProgress(0);
        }
    };

    private SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mSwipeCaptchaView.setCurrentSwipeValue(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mSeekBar.setMax(mSwipeCaptchaView.getMaxSwipeValue());
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mSwipeCaptchaView.matchCaptcha();
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_back:
                    setResult(Activity.RESULT_CANCELED);
                    back();
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                setResult(Activity.RESULT_CANCELED);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back(){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

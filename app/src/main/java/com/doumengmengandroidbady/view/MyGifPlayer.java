package com.doumengmengandroidbady.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.doumengmengandroidbady.R;


public class MyGifPlayer extends FrameLayout {

    private Context context;
    private ImageView image;
    private ImageView start;
    private GifDrawable gif;

    public MyGifPlayer(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MyGifPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        this.context = context;
        image = new ImageView(context);
        image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(image);

        start = new ImageView(context);
        int side = context.getResources().getDimensionPixelOffset(R.dimen.x95px);
        LayoutParams params = new LayoutParams(side, side);
        params.gravity = Gravity.CENTER;
        start.setLayoutParams(params);
        start.setOnClickListener(listener);
        start.setImageResource(R.drawable.btn_play);
        addView(start);
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            startPlay();
        }
    };


    public void setNetWorkUrl(String url){
        Glide.with(this).asGif().load(url).load(target);
    }

    public void setDrawable(int drawable){
        Glide.with(this).asGif().load(drawable).into(target);
    }

    private SimpleTarget<GifDrawable> target = new SimpleTarget<GifDrawable>() {
        @Override
        public void onResourceReady(@NonNull GifDrawable gifDrawable, @Nullable Transition<? super GifDrawable> transition) {
            gif = gifDrawable;
            image.setImageDrawable(gifDrawable);
        }
    };

    private void startPlay(){
        start.setVisibility(View.GONE);
        gif.setLoopCount(1);
        gif.startFromFirstFrame();
        keepTime();
    }

    private void stopPlay(){
        start.setVisibility(View.VISIBLE);
        gif.stop();
    }

    private void keepTime(){
        image.postDelayed(stopRunnable,200);
    }

    private Runnable stopRunnable = new Runnable() {
        @Override
        public void run() {
            if ( !gif.isRunning() ) {
                stopPlay();
            } else {
                keepTime();
            }
        }
    };

}

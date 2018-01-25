package com.doumengmengandroidbady.view;

import android.content.Context;
import android.content.res.TypedArray;
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

    private View share;
    private ImageView image;
    private ImageView start;
    private GifDrawable gif;
    private StopCallBack callBack;
    private OnClickListener customerListener;
    private boolean isAutoPlay = false;
    private boolean isGif = true;

    public MyGifPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyGifPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.MyGifPlayer,defStyleAttr,0);
        int startId = a.getResourceId(R.styleable.MyGifPlayer_start_drawable,0);
        int side = a.getDimensionPixelOffset(R.styleable.MyGifPlayer_start_side,0);
        boolean needShare = a.getBoolean(R.styleable.MyGifPlayer_need_share,false);
        initView(context,startId,side,needShare);
    }

    private void initView(Context context,int startId , int side , boolean needShare){
        image = new ImageView(context);
        image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(image);

        share = new View(context);
        share.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        share.setBackgroundColor(context.getResources().getColor(R.color.translucence));
        if ( needShare ) {
            addView(share);
        }
        share.setOnClickListener(listener);

        start = new ImageView(context);
        LayoutParams params = new LayoutParams(side, side);
        params.gravity = Gravity.CENTER;
        start.setLayoutParams(params);
        start.setOnClickListener(listener);
        start.setImageResource(startId);
        addView(start);
        start.setVisibility(View.GONE);
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if ( customerListener != null ){
                customerListener.onClick(view);
            } else {
                if (isGif) {
                    startPlay();
                }
            }
        }
    };

    public void setCustomerListener(OnClickListener customerListener){
        this.customerListener = customerListener;
    }

    public void setDrawable(String url){
        isGif =false;
        start.setVisibility(View.VISIBLE);
        share.setVisibility(View.VISIBLE);
        Glide.with(this).asBitmap().load(url).into(image);
    }

    public void setGif(String url,StopCallBack callBack){
        prepare();
        isGif = true;
        isAutoPlay = true;
        this.callBack = callBack;
        Glide.with(this).asGif().load(url).into(target);
    }

    public void setGif(int drawable){
        prepare();
        isGif = true;
        Glide.with(this).asGif().load(drawable).into(target);
    }

    public void clearDrawable(){
        prepare();
    }

    private void prepare(){
        if ( gif != null ){
            gif.stop();
        }
        image.setImageDrawable(null);
        start.setVisibility(View.GONE);
        share.setVisibility(View.GONE);
    }

    private SimpleTarget<GifDrawable> target = new SimpleTarget<GifDrawable>() {
        @Override
        public void onResourceReady(@NonNull GifDrawable gifDrawable, @Nullable Transition<? super GifDrawable> transition) {
            gif = gifDrawable;
            image.setImageDrawable(gifDrawable);
            if ( isAutoPlay ){
                startPlay();
            } else {
                start.setVisibility(View.VISIBLE);
                share.setVisibility(View.VISIBLE);
            }
        }
    };

    private void startPlay(){
        share.setVisibility(View.GONE);
        start.setVisibility(View.GONE);
        gif.setLoopCount(1);
        gif.startFromFirstFrame();
        keepTime();
    }

    private void stopPlay(){
        if ( !isAutoPlay ) {
            share.setVisibility(View.VISIBLE);
            start.setVisibility(View.VISIBLE);
        }
        gif.stop();
        if ( callBack != null ){
            callBack.stoped();
        }
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

    public interface StopCallBack {
        void stoped();
    }



}

package com.doumengmengandroidbady.view;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.doumengmengandroidbady.R;

import java.io.IOException;

/**
 * 作者: 边贤君
 * 描述: 简单的视屏播放器
 * 创建日期: 2018/1/8 14:31
 */
public class MyMediaPlayer extends FrameLayout {

    private Context context;
    private SurfaceView surfaceView;
    private MediaPlayer player;
    private ImageView background;
    private ImageView start;

    public MyMediaPlayer(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MyMediaPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        player = new MediaPlayer();

        this.context = context;
        surfaceView = new SurfaceView(context);
        surfaceView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        surfaceView.setVisibility(View.GONE);
        addView(surfaceView);

        background = new ImageView(context);
        background.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        background.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(background);

        start = new ImageView(context);
        int side = context.getResources().getDimensionPixelOffset(R.dimen.x95px);
        LayoutParams params = new LayoutParams(side, side);
        params.gravity = Gravity.CENTER;
        start.setLayoutParams(params);
        start.setOnClickListener(listener);
        start.setImageResource(R.drawable.btn_play);
        addView(start);
    }

    public void initMediaPlayer(String fileName){
        try {
            AssetFileDescriptor descriptor = context.getResources().getAssets().openFd(fileName);


            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
            Bitmap bitmap = retriever.getFrameAtTime();
            background.setImageBitmap(bitmap);

            player.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
            surfaceView.getHolder().addCallback(callback);
            player.setOnCompletionListener(completionListener);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            startPlay();
        }
    };

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            player.setDisplay(surfaceHolder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            completePlay();
        }
    };

    private void startPlay(){
        surfaceView.setVisibility(View.VISIBLE);
        background.setVisibility(View.GONE);
        start.setVisibility(View.GONE);
            player.start();
    }

    private void completePlay(){
        surfaceView.setVisibility(View.GONE);
        background.setVisibility(View.VISIBLE);
        start.setVisibility(View.VISIBLE);
    }

    public void destory(){
        if ( player != null ){
            player.stop();
            player.release();
        }
    }

}

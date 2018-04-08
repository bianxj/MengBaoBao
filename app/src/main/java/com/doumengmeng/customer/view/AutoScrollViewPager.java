package com.doumengmeng.customer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Scroller;

import com.doumengmeng.customer.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/12/27.
 */

public class AutoScrollViewPager extends FrameLayout {

    private int dotImage;
    private int dotSize;
    private int dotGap;
    private int dotMarginBottom;
    private int pageHeight;
    private int pageMarginTop;
    private boolean canLoop = false;

    private final Context context;
    private CustomViewPager viewPager;
    private RadioGroup dotLayout;

    private int index = 0;
    private ScrollPagerAdapter adapter;
    private Timer timer;
    private OnClickCallBack callBack;

    private ScrollViewHandler handler;

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initParam(context,attrs);
        initViewPager();
        initDotLayout();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoop();
    }

    private void initParam(Context context , AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.AutoScrollViewPager,0,0);
        dotImage = array.getResourceId(R.styleable.AutoScrollViewPager_dot_src,0);
        dotSize = array.getDimensionPixelSize(R.styleable.AutoScrollViewPager_dot_size,0);
        dotGap = array.getDimensionPixelSize(R.styleable.AutoScrollViewPager_dot_gap,0);
        dotMarginBottom = array.getDimensionPixelSize(R.styleable.AutoScrollViewPager_dot_margin_bottom,0);
        pageHeight = array.getDimensionPixelSize(R.styleable.AutoScrollViewPager_page_height,0);
        pageMarginTop = array.getDimensionPixelSize(R.styleable.AutoScrollViewPager_page_margin_top,0);
        canLoop = array.getBoolean(R.styleable.AutoScrollViewPager_canLoop,false);
        array.recycle();
    }

    private void initViewPager(){
        viewPager = new CustomViewPager(context);
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,pageHeight);
        params.topMargin = pageMarginTop;
        viewPager.setLayoutParams(params);
        addView(viewPager);

        if ( canLoop() ) {
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(),
                        new AccelerateInterpolator());
                field.set(viewPager, scroller);
                scroller.setmDuration(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        handler = new ScrollViewHandler(viewPager);
    }

    private void initDotLayout(){
        dotLayout = new RadioGroup(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        params.bottomMargin = dotMarginBottom;
        dotLayout.setOrientation(RadioGroup.HORIZONTAL);
        dotLayout.setLayoutParams(params);
        addView(dotLayout);
    }

    public void setOnClickCallBack(OnClickCallBack onClickCallBack){
        this.callBack = onClickCallBack;
    }

    public void setImageList(int[] imageList){
        if ( canLoop() ){
            List<ImageView> imageViews = new ArrayList<>();
            for (int i = 0; i < imageList.length+2; i++) {
                ImageView imageView;
                if ( i == 0 ) {
                    imageView = createImageView(imageList[imageList.length-1]);
                } else if ( i == imageList.length+1 ){
                    imageView = createImageView(imageList[0]);
                } else {
                    imageView = createImageView(imageList[i-1]);
                }
                imageViews.add(imageView);
            }
            index = imageList.length;
            initDotList(index);
            this.adapter = new ScrollPagerAdapter(imageViews);
            viewPager.setAdapter(this.adapter);
            viewPager.addOnPageChangeListener(pageChangeListener);
            viewPager.setCustomOnTouchListener(onTouchListener);
            this.adapter.notifyDataSetChanged();
            viewPager.setCurrentItem(1);
            startLoop();
        } else {
            List<ImageView> imageViews = new ArrayList<>();
            for (int anImageList : imageList) {
                ImageView imageView = createImageView(anImageList);
                imageViews.add(imageView);
            }
            index = imageList.length;
            initDotList(index);
            this.adapter = new ScrollPagerAdapter(imageViews);
            viewPager.setAdapter(this.adapter);
            viewPager.addOnPageChangeListener(pageChangeListener);
            this.adapter.notifyDataSetChanged();
        }
    }

    private boolean canLoop(){
        return canLoop;
    }

    private ImageView createImageView(int imageId){
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        imageView.setImageResource(imageId);
        return imageView;
    }

    private void initDotList(int dotCount){
        dotLayout.removeAllViews();
        for (int i = 0;i<dotCount;i++){
            RadioButton radioButton = createDot();
            if ( 0 == i ){
                RadioGroup.LayoutParams layoutParams = (RadioGroup.LayoutParams) radioButton.getLayoutParams();
                layoutParams.leftMargin = 0;
                radioButton.setLayoutParams(layoutParams);
            }
            dotLayout.addView(radioButton);
        }
        RadioButton button = (RadioButton) dotLayout.getChildAt(0);
        button.setChecked(true);
    }

    private RadioButton createDot(){
        RadioButton radioButton = new RadioButton(context);
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(dotSize,dotSize);
        layoutParams.leftMargin = dotGap;
        radioButton.setLayoutParams(layoutParams);
        radioButton.setButtonDrawable(null);
        radioButton.setBackgroundResource(dotImage);
        radioButton.setChecked(false);
        return radioButton;
    }

    private final OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if ( motionEvent.getAction() == MotionEvent.ACTION_DOWN ){
                pauseLoop();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                startLoop();
            }
            return false;
        }
    };

    private TimerTask task;
    private void startLoop(){
        if ( timer == null ) {
            timer = new Timer();
        }
        if ( task == null ) {
            task = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(ScrollViewHandler.SCROLL_NEXT);
                }

            };
        }
        timer.schedule(task, 3000, 3000);
    }

    private void pauseLoop(){
        if ( timer != null && task != null ){
            task.cancel();
            task = null;
        }
    }

    private void stopLoop(){
        if ( timer != null ){
            timer.purge();
            timer.cancel();
            timer = null;
        }
    }

    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            if ( canLoop() ) {
                RadioButton botton = null;
                if ( position == 0 ){
                    botton = (RadioButton) dotLayout.getChildAt(index -1);
                } else if ( position == index + 1 ){
                    botton = (RadioButton) dotLayout.getChildAt(0);
                } else {
                    botton = (RadioButton) dotLayout.getChildAt(position-1);
                }
                botton.setChecked(true);
            } else {
                RadioButton botton = (RadioButton) dotLayout.getChildAt(position);
                botton.setChecked(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if ( canLoop() ) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    Message message = handler.obtainMessage();
                    message.what = ScrollViewHandler.SCROLL_NEXT_NOSCROLL;
                    message.arg1 = index;
                    handler.sendMessage(message);
                }
            }
        }
    };

    private class ScrollPagerAdapter extends PagerAdapter{
        private final List<ImageView> imageViews;

        public ScrollPagerAdapter(List<ImageView> imageViews) {
            this.imageViews = imageViews;
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(imageViews.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            ImageView view = imageViews.get(position);
            view.setTag(position);
            view.setOnClickListener(listener);
            return view;
        }
    }

    private final OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            if ( callBack != null ){
                if ( position == 0 ) {
                    callBack.onClick(index - 1);
                } else if ( position == index +1 ) {
                    callBack.onClick(0);
                } else {
                    callBack.onClick(position);
                }
            }
        }
    };

    public interface OnClickCallBack{

        void onClick(int position);

    }

    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 1500;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }
    }

    private static class ScrollViewHandler extends Handler{
        public final static int SCROLL_NEXT = 0x31;
        public final static int SCROLL_NEXT_NOSCROLL = 0x32;

        private final ViewPager viewPager;

        public ScrollViewHandler(ViewPager viewPager) {
            this.viewPager = viewPager;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == SCROLL_NEXT ){
                int currentItem = viewPager.getCurrentItem();
                if(currentItem == viewPager.getAdapter().getCount() - 1){
                    currentItem = 0 ;
                }else {
                    currentItem++ ;
                }
                viewPager.setCurrentItem(currentItem);
            } else {
                int position = viewPager.getCurrentItem();
                if ( position == 0 ){
                    viewPager.setCurrentItem(msg.arg1,false);
                } else if ( position == msg.arg1 + 1 ){
                    viewPager.setCurrentItem(1,false);
                }
            }
        }
    }

    private static class CustomViewPager extends ViewPager{

        private OnTouchListener listener;

        public CustomViewPager(@NonNull Context context) {
            super(context);
        }

        public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:
                    if (listener != null) {
                        listener.onTouch(this,ev);
                    }
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }

        public void setCustomOnTouchListener(OnTouchListener listener){
            this.listener = listener;
        }
    }

}

package com.doumengmengandroidbady.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/12/27.
 */

public class AutoScrollViewPager extends FrameLayout {

    private Context context;
    private ViewPager viewPager;
    private LinearLayout dotLayout;

    private int pageCount = 0;
    private ScrollPagerAdapter adapter;

    private Timer timer;

    public AutoScrollViewPager(Context context) {
        super(context);
        this.context = context;
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViewPager();
        initDotLayout();
    }

    private void initViewPager(){
        viewPager = new ViewPager(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(params);
        addView(viewPager);
    }

    private void initDotLayout(){
        dotLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        dotLayout.setLayoutParams(params);
        addView(dotLayout);
    }

    public void setAdapter(PagerAdapter adapter){
        pageCount = adapter.getCount();
        this.adapter = new ScrollPagerAdapter(adapter);
        viewPager.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }

    public void startScroll(){
        if ( timer == null ){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                if(currentItem == viewPager.getAdapter().getCount() - 1){
                    currentItem = 0 ;
                }else {
                    currentItem++ ;
                }
                viewPager.setCurrentItem(currentItem);
            }
        },300);
    }

    public void stopScroll(){
        if ( timer != null ){
            timer.cancel();
        }
    }

    private void updateDotLayout(){
        
    }

    private class ScrollPagerAdapter extends PagerAdapter{

        private PagerAdapter adapter;

        public ScrollPagerAdapter(PagerAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getCount() {
            return adapter.getCount();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return adapter.isViewFromObject(view,object);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            adapter.destroyItem(container,position,object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return adapter.instantiateItem(container,position);
        }
    }

}

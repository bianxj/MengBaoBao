package com.doumengmeng.doctor.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018/3/15.
 */

public class SwipeLayout extends FrameLayout {

    private ViewDragHelper mDragHelper;
    private View mBackView;
    private View mFrontView;

    private int mWidth;
    private int mHeight;
    private int mRange;
    private boolean isOpen = false;
    private OnSwipeLayoutClick onSwipeLayoutClick;

    public SwipeLayout(@NonNull Context context) {
        this(context,null);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackView = getChildAt(0);
        mFrontView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mFrontView.getMeasuredWidth();
        mHeight = mFrontView.getMeasuredHeight();
        mRange = mBackView.getMeasuredWidth();
    }

    private void init(){
        mDragHelper = ViewDragHelper.create(this,mCallback);
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            if ( child == mBackView ) {
                return false;
            }
            return  true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if ( child == mFrontView ){
                if ( left > 0 ){
                    left = 0;
                } else if ( left < -mRange ){
                    left = -mRange;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
           invalidate();
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if ( xvel < 0 ){
                open();
            } else if (xvel == 0 && mFrontView.getLeft() < (-mRange /2.0f)){
                open();
            } else {
                close();
            }
        }
    };

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onSwipeLayoutClick.onTouch(getContext());
        dispatchOnTouchEvent(event);
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private int x;
    private boolean isClick;
    private final int DX = 10;
    private void dispatchOnTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = (int) event.getX();
            isClick = true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int movex = (int) event.getX();
            if (Math.abs(movex - x) > DX) {//防止点击事件，会稍微手指抖动
                isClick = false;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isClick) {
                dispatchClickListener((int) event.getX());
            }
        }
    }

    public void dispatchClickListener(int x) {
        if ( isOpen ) {
            if ( x > 0 && x < (mWidth-mRange) ){
                onSwipeLayoutClick.onFrontClick(getContext(),true);
            } else {
                onSwipeLayoutClick.onBackClick(getContext());
            }
        } else {
            onSwipeLayoutClick.onFrontClick(getContext(),false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutChildView(false);
    }

    private void layoutChildView(boolean isOpen){
        Rect rect = computerFontViewRect(isOpen);
        mFrontView.layout(rect.left,rect.top,rect.right,rect.bottom);
        mBackView.layout(mFrontView.getWidth()-mRange,0,mFrontView.getWidth(),mHeight);
    }

    private Rect computerFontViewRect(boolean isOpen){
        int left = isOpen ? -mRange : 0;
        return new Rect(left,0,left+mWidth,mHeight);
    }

    public void open(){
        open(true);
    }

    public void open(boolean isSmooth){
        onSwipeLayoutClick.onStatusChanged(true);
        isOpen = true;
        if ( isSmooth ){
            if ( mDragHelper.smoothSlideViewTo(mFrontView,-mRange,0) ){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutChildView(true);
        }
    }

    public void close(){
        close(true);
    }

    public void close(boolean isSmooth){
        onSwipeLayoutClick.onStatusChanged(false);
        isOpen = false;
        if ( isSmooth ){
            if ( mDragHelper.smoothSlideViewTo(mFrontView,0,0) ){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutChildView(false);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if ( mDragHelper.continueSettling(true) ){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setOnSwipeLayoutClick(OnSwipeLayoutClick onSwipeLayoutClick){
        this.onSwipeLayoutClick = onSwipeLayoutClick;
    }

    public interface OnSwipeLayoutClick{
        public void onFrontClick(Context context,boolean isOpen);
        public void onBackClick(Context context);
        public void onStatusChanged(boolean isOpen);
        public void onTouch(Context context);
    }

    public boolean isOpen(){
        return isOpen;
    }

}

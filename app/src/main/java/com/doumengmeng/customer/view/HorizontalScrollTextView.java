package com.doumengmeng.customer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.doumengmeng.customer.R;

@SuppressLint("AppCompatCustomView")
public class HorizontalScrollTextView extends TextView implements View.OnClickListener {
    private float textLength = 0f;// 文本长度
    private float viewWidth = 0f;//文本控件的长度
    private float step = 0f;// 文本的横坐标
    private float increase = 0f;
    private float y = 0f;// 文本的纵坐标
    public boolean isStarting = false;// 是否开始滚动
    private Paint paint = null;
    private String text = "";// 文本内容
    private OnScrollCompleteListener onScrollCompleteListener;//滚动结束监听

    public HorizontalScrollTextView(Context context) {
        super(context);
        initView();
    }

    public HorizontalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HorizontalScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public OnScrollCompleteListener getOnSrollCompleteListener() {
        return onScrollCompleteListener;
    }

    public void setOnScrollCompleteListener(OnScrollCompleteListener  onScrollCompleteListener){
        this.onScrollCompleteListener = onScrollCompleteListener;
    }

    private void initView() {
        setOnClickListener(this);
    }

    public void init() {
        paint = getPaint();
        //设置滚动字体颜色
        paint.setColor(getCurrentTextColor());
        text = getText().toString();
        textLength = paint.measureText(text);
        viewWidth = getWidth();
        step = 0;
        increase = getResources().getDimension(R.dimen.x1px);
        y = getTextSize() + getPaddingTop();
    }
    //开启滚动
    public void startScroll() {
        isStarting = true;
        invalidate();
    }
    //停止滚动
    public void stopScroll() {
        isStarting = false;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = getWidth();
        if ( textLength > width ) {
            float space = getResources().getDimension(R.dimen.x100px);
            float widthSpace = textLength + space;

            if ( widthSpace - step <= 0 ){
                step = 0;
            }

            canvas.drawText(text, -step, y, paint);
            canvas.drawText(text, widthSpace - step, y, paint);
            if (!isStarting) {
                return;
            }
            step += increase;// 2.0为文字的滚动速度
            invalidate();
        } else {
            canvas.drawText(text, -step, y, paint);
        }
        //判断是否滚动结束
//        if (step > textLength){
//            step = 0;
//            onScrollCompleteListener.onScrollComplete();
//        }
    }

    //控制点击停止或者继续运行
    @Override
    public void onClick(View v) {
        if (isStarting)
            stopScroll();
        else
            startScroll();
    }

    public interface OnScrollCompleteListener {
        void onScrollComplete();
    }
}

package com.doumengmengandroidbady.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/2/1.
 */

public class TestView extends View {

    private Paint paint;
    private int background = -1;

    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackGround(canvas);
    }

    private void drawBackGround(Canvas canvas){
        if ( background != -1 ){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),background);
            Rect src = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
            Rect dest = new Rect(0,0,canvas.getWidth(),canvas.getHeight());
            canvas.drawBitmap(bitmap,src,dest,paint);
        }
    }

    public void setBackGround(int background){
        this.background = background;
        postInvalidate();
    }

}

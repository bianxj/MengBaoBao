package com.doumengmengandroidbady.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.doumengmengandroidbady.R;

/**
 * 作者: 边贤君
 * 描述  二维码扫描遮罩层
 * 创建日期: 2017/12/6 10:36
 */
public class ViewfinderView extends View {

    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    private static final long ANIMATION_DELAY = 80L;
    private static final int DEFAULT_MASK_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_LASER_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;

    private static final int DEFAULT_LASER_SIZE = 0;
    private static final int DEFAULT_BORDER_SIZE = 0;
    private static final int DEFAULT_SIDE_SIZE = 0;
    private static final int DEFAULT_CENT_POINT_X = -1;
    private static final int DEFAULT_CENT_POINT_Y = -1;

    private final int maskColor;
    private final int laserMove;
    private final int laserColor;
    private final int laserSize;
    private final int borderColor;
    private final int borderSize;
    private final Rect rect;
    private final int laserBorder;
//    private final int centPointX;
//    private final int centPointY;
    private final int sideSize;

    private Paint paint;

    public ViewfinderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public ViewfinderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewfinderView,defStyleAttr,0);

        maskColor = a.getColor(R.styleable.ViewfinderView_mask_color,DEFAULT_MASK_COLOR);
        laserColor = a.getColor(R.styleable.ViewfinderView_laser_color,DEFAULT_LASER_COLOR);
        borderColor = a.getColor(R.styleable.ViewfinderView_border_color,DEFAULT_BORDER_COLOR);

        laserSize = a.getDimensionPixelSize(R.styleable.ViewfinderView_laser_size,DEFAULT_LASER_SIZE);
        borderSize = a.getDimensionPixelSize(R.styleable.ViewfinderView_border_size,DEFAULT_BORDER_SIZE);
        sideSize = a.getDimensionPixelSize(R.styleable.ViewfinderView_side_size,DEFAULT_SIDE_SIZE);

        int x = a.getDimensionPixelSize(R.styleable.ViewfinderView_cent_x,DEFAULT_CENT_POINT_X);
        int y = a.getDimensionPixelSize(R.styleable.ViewfinderView_cent_y,DEFAULT_CENT_POINT_Y);
        int halfSideSize = sideSize/2;
        laserBorder = sideSize/50;
        laserMove = sideSize/100>1?sideSize/100:1;

        rect = new Rect();
        rect.top = y - halfSideSize;
        rect.left = x - halfSideSize;
        rect.right = x + halfSideSize;
        rect.bottom = y + halfSideSize;
        //反锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMask(canvas);
        drawBorder(canvas);
        postInvalidateDelayed(ANIMATION_DELAY,rect.left,rect.top,rect.right,rect.bottom);
    }

    private void drawMask(Canvas canvas){
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if ( maskColor != DEFAULT_MASK_COLOR ){
            paint.setColor(maskColor);
//            canvas.drawRect(rect.left,rect.top,rect.right,rect.bottom,paint);
            canvas.drawRect(0,0,width,rect.top,paint);
            canvas.drawRect(0,rect.bottom,width,height,paint);
            canvas.drawRect(0,rect.top,rect.left,rect.bottom,paint);
            canvas.drawRect(rect.right,rect.top,width,rect.bottom,paint);
        }
    }

    private void drawBorder(Canvas canvas){
        if ( borderColor != DEFAULT_BORDER_COLOR ) {
            int borderLen = sideSize/15;
            paint.setColor(borderColor);
            //左上
            canvas.drawRect(rect.left,rect.top,rect.left+borderLen,rect.top+borderSize,paint);
            canvas.drawRect(rect.left,rect.top,rect.left+borderSize,rect.top+borderLen,paint);
            //右上
            canvas.drawRect(rect.right-borderLen,rect.top,rect.right,rect.top+borderSize,paint);
            canvas.drawRect(rect.right-borderSize,rect.top,rect.right,rect.top+borderLen,paint);
            //左下
            canvas.drawRect(rect.left,rect.bottom-borderLen,rect.left+borderSize,rect.bottom,paint);
            canvas.drawRect(rect.left,rect.bottom-borderSize,rect.left+borderLen,rect.bottom,paint);
            //右下
            canvas.drawRect(rect.right-borderSize,rect.bottom-borderLen,rect.right,rect.bottom,paint);
            canvas.drawRect(rect.right-borderLen,rect.bottom-borderSize,rect.right,rect.bottom,paint);
        }
    }

    private int laserLocation = 0;
    private void drawLaser(Canvas canvas){
        //TODO
//        if ( laserColor != DEFAULT_LASER_COLOR ){
//            paint.setColor(laserColor);
//            if ( laserLocation == 0 || laserLocation > rect.bottom-laserBorder  ){
//                laserLocation = rect.top+laserBorder;
//            } else {
//                laserLocation += laserMove;
//            }
//
//
//
//        }
    }

    public Rect getScanRect(){
        return rect;
    }

}

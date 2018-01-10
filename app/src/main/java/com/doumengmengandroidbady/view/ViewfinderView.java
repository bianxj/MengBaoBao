package com.doumengmengandroidbady.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private static final long ANIMATION_DELAY = 30L;
    private static final int DEFAULT_MASK_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_LASER_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;

    private static final int DEFAULT_LASER_SIZE = 0;
    private static final int DEFAULT_BORDER_SIZE = 0;
    private static final int DEFAULT_SIDE_SIZE = 0;
    private static final int DEFAULT_CENT_POINT_X = -1;
    private static final int DEFAULT_CENT_POINT_Y = -1;

    private final int maskColor;
    private final int borderColor;
    private final int borderSize;
    private final Rect rect;
    private Rect scanRect;
//    private final int laserBorder;
//    private final int centPointX;
//    private final int centPointY;
    private final int sideSize;

    private static final int LASER_LANDSCAPE_BORDER = 40;

    private Bitmap lazer;
    private final int laserMove;
    private final int laserTopBorder;
    private final int laserBottomBorder;
    private final int laserHeight;
    private Rect laserSrcRect;
    private Rect laserDesRect;

    private Paint paint;

    public ViewfinderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public ViewfinderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewfinderView,defStyleAttr,0);

        maskColor = a.getColor(R.styleable.ViewfinderView_mask_color,DEFAULT_MASK_COLOR);
//        laserColor = a.getColor(R.styleable.ViewfinderView_laser_color,DEFAULT_LASER_COLOR);
        borderColor = a.getColor(R.styleable.ViewfinderView_border_color,DEFAULT_BORDER_COLOR);

//        laserSize = a.getDimensionPixelSize(R.styleable.ViewfinderView_laser_size,DEFAULT_LASER_SIZE);
        borderSize = a.getDimensionPixelSize(R.styleable.ViewfinderView_border_size,DEFAULT_BORDER_SIZE);
        sideSize = a.getDimensionPixelSize(R.styleable.ViewfinderView_side_size,DEFAULT_SIDE_SIZE);

        int x = a.getDimensionPixelSize(R.styleable.ViewfinderView_cent_x,DEFAULT_CENT_POINT_X);
        int y = a.getDimensionPixelSize(R.styleable.ViewfinderView_cent_y,DEFAULT_CENT_POINT_Y);
        int halfSideSize = sideSize/2;
//        laserBorder = sideSize/50;


        rect = new Rect();
        rect.top = y - halfSideSize;
        rect.left = x - halfSideSize;
        rect.right = x + halfSideSize;
        rect.bottom = y + halfSideSize;

        //反锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        lazer = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_lazer);
        laserHeight = getResources().getDimensionPixelSize(R.dimen.y10px);
        laserMove = sideSize/100>1?sideSize/100:1;
        laserTopBorder = rect.top+laserMove*3;
        laserBottomBorder = rect.bottom-laserMove*3;
        laserSrcRect = new Rect(0,0,lazer.getWidth(),lazer.getHeight());
        laserDesRect = new Rect(rect.left+LASER_LANDSCAPE_BORDER,laserTopBorder,rect.right-LASER_LANDSCAPE_BORDER,laserTopBorder+ laserHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMask(canvas);
        drawBorder(canvas);
        drawLaser(canvas);
        postInvalidateDelayed(ANIMATION_DELAY,rect.left+borderSize,rect.top+borderSize,rect.right-borderSize,rect.bottom-borderSize);
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
            int borderLen = sideSize/10;
            paint.setColor(borderColor);
            //左上
            canvas.drawRect(rect.left-borderSize,rect.top-borderSize,rect.left+borderLen-borderSize,rect.top,paint);
            canvas.drawRect(rect.left-borderSize,rect.top-borderSize,rect.left,rect.top+borderLen-borderSize,paint);
            //右上
            canvas.drawRect(rect.right-borderLen+borderSize,rect.top-borderSize,rect.right+borderSize,rect.top+borderSize-borderSize,paint);
            canvas.drawRect(rect.right-borderSize+borderSize,rect.top-borderSize,rect.right+borderSize,rect.top+borderLen-borderSize,paint);
            //左下
            canvas.drawRect(rect.left-borderSize,rect.bottom-borderLen+borderSize,rect.left+borderSize-borderSize,rect.bottom+borderSize,paint);
            canvas.drawRect(rect.left-borderSize,rect.bottom,rect.left+borderLen-borderSize,rect.bottom+borderSize,paint);
            //右下
            canvas.drawRect(rect.right-borderSize+borderSize,rect.bottom-borderLen+borderSize,rect.right+borderSize,rect.bottom+borderSize,paint);
            canvas.drawRect(rect.right-borderLen+borderSize,rect.bottom,rect.right+borderSize,rect.bottom+borderSize,paint);
        }
    }

    private void drawLaser(Canvas canvas){
        int bottom = laserDesRect.bottom + laserMove;
        if ( bottom > laserBottomBorder  ){
            laserDesRect.top = laserTopBorder;
            laserDesRect.bottom = laserTopBorder+laserHeight;
        } else {
            laserDesRect.top = laserDesRect.top+laserMove;
            laserDesRect.bottom = laserDesRect.bottom+laserMove;
        }
        canvas.drawBitmap(lazer, laserSrcRect, laserDesRect,paint);
    }

    public Rect getScanRect(int top,int left){
        if ( scanRect == null ) {
            scanRect = new Rect();
            scanRect.top = rect.top + top;
            scanRect.bottom = rect.bottom + top;
            scanRect.left = rect.left + left;
            scanRect.right = rect.right + left;
        }
        return scanRect;
    }

}

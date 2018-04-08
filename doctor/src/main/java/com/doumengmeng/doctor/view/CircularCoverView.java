package com.doumengmeng.doctor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.doumengmeng.doctor.R;


@SuppressLint("AppCompatCustomView")
public class CircularCoverView extends ImageView {

    private int radius;
    private int coverColor;


    public CircularCoverView(Context context) {
        super(context);
    }

    public CircularCoverView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircularCoverView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularCoverView,defStyleAttr,0);
        radius = array.getDimensionPixelOffset(R.styleable.CircularCoverView_radius,30);
        coverColor = array.getColor(R.styleable.CircularCoverView_cover_color,0xFFFFFFFF);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setStyle(Paint.Style.FILL);

        int sc=canvas.saveLayer(0,0,width,height,paint,Canvas.ALL_SAVE_FLAG);

        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);


        canvas.drawBitmap(createDestBitmap(width,height),0,0,paint);
        paint.setXfermode(xfermode);

        canvas.drawBitmap(createSrcBitmap(width,height),0,0,paint);
        paint.setXfermode(null);

        canvas.restoreToCount(sc);
    }

    private Bitmap createDestBitmap(int width,int height){
        Bitmap dest = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(dest);

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFCC44);

        c.drawArc(new RectF(0,0,radius*2,radius*2),180,90,true,p);
        c.drawArc(new RectF(width-radius*2,height-radius*2,width,height),0,90,true,p);

        c.drawArc(new RectF(0,height-radius*2,radius*2,height),90,90,true,p);
        c.drawArc(new RectF(width-radius*2,0, width,radius*2),-90,90,true,p);
        return dest;
    }

    private Bitmap createSrcBitmap(int width,int height){
        Bitmap src = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(src);

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(coverColor);

        c.drawRect(0,0,radius,radius,p);
        c.drawRect(width-radius,0,width,radius,p);
        c.drawRect(width-radius,height-radius,width,height,p);
        c.drawRect(0,height-radius,radius,height,p);

        return src;
    }

}

package com.doumengmeng.customer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public class ScaleplateView extends View {

    private final static int TOTLE_WEIGHT = 267;
    private final static int TOTLE_HEIGHT = 27;
    private final static int MARGIN_SIDE = 7;
    public final static int HORIZONTAL_LENGHT = 253;
    private final static int TRIANGLE_WIDTH = 14;
    private final static int SIDE_SPITE_LINE = 18;
    private final static int MIDDLE_SPITE_LINE = 16;
    private final static int LINE_WIDTH = 2;
    private final static String COLOR = "#77D2F7";

    private final List<Float> splitX = new ArrayList<>();
    private final Paint paint;


    public ScaleplateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScaleplateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentWeight = canvas.getWidth();
        int currentHeight = canvas.getHeight();


        float unitWeight = ((float)currentWeight)/TOTLE_WEIGHT;
        float unitHeight = ((float)currentHeight)/TOTLE_HEIGHT;

        paint.setStrokeWidth(unitHeight*2);
        paint.setColor(Color.parseColor(COLOR));

        canvas.drawLine(MARGIN_SIDE*unitWeight,getBottom()-(SIDE_SPITE_LINE/2*unitHeight),getWidth()-MARGIN_SIDE*unitWeight,getBottom()-(SIDE_SPITE_LINE/2*unitHeight),paint);

        calculateSplitX(unitWeight);
        drawSplitLine(canvas,paint,unitHeight);
        drawTriangle(canvas,paint);
    }

    private void calculateSplitX(float unitWeight){
        splitX.clear();
        splitX.add((LINE_WIDTH/2+MARGIN_SIDE)*unitWeight);
        splitX.add(splitX.get(splitX.size()-1)+21*unitWeight);
        splitX.add(splitX.get(splitX.size()-1)+52*unitWeight);
        splitX.add(splitX.get(splitX.size()-1)+49*unitWeight);
        splitX.add(splitX.get(splitX.size()-1)+51*unitWeight);
        splitX.add(splitX.get(splitX.size()-1)+55*unitWeight);
        splitX.add(splitX.get(splitX.size()-1)+23*unitWeight);
    }

    private void drawSplitLine(Canvas canvas, Paint paint, float unitHeight){
        for (int i = 0;i<splitX.size();i++){
            if ( i == 0 || i == splitX.size()-1 ){
                canvas.drawLine(splitX.get(i),getBottom()-(SIDE_SPITE_LINE*unitHeight),splitX.get(i),getBottom(),paint);
            } else {
                canvas.drawLine(splitX.get(i),getBottom()-((SIDE_SPITE_LINE+MIDDLE_SPITE_LINE)/2*unitHeight),splitX.get(i),getBottom()-((SIDE_SPITE_LINE-MIDDLE_SPITE_LINE)/2*unitHeight),paint);
            }
        }
    }

    private void drawTriangle(Canvas canvas, Paint paint){
        if ( paragraph == -1 ){
            return;
        }

        float unitWeight = ((float)canvas.getWidth())/TOTLE_WEIGHT;
        float unitHeight = ((float)canvas.getHeight())/TOTLE_HEIGHT;
        float y = ( (splitX.get(paragraph+1)-splitX.get(paragraph))/(upper-lower) * (value-lower) );
        float x = splitX.get(paragraph) + y;

        Path path = new Path();
        path.moveTo(x-(TRIANGLE_WIDTH/2*unitWeight),0);
        path.lineTo(x,getBottom()-(SIDE_SPITE_LINE/2*unitHeight));
        path.lineTo(x+(TRIANGLE_WIDTH/2*unitWeight),0);
        path.close();

        canvas.drawPath(path,paint);
    }

    private int paragraph = -1;
    private float lower;
    private float upper;
    private float value;
    public void setValue(int paragraph,float lower , float upper , float value){
        this.paragraph = paragraph;
        this.lower = lower;
        this.upper = upper;
        this.value = value;
        postInvalidate();
    }

}

package com.doumengmengandroidbady.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.doumengmengandroidbady.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */

public class DiagramView extends View {

    private Context context;
    private DiagramBaseInfo baseInfo;
    private DiagramParam param;
    private Paint paint;
    private Paint whitePaint;

    private float innerRadius;
    private float radius;
    private float strokeWidth;
    private final float originX;
    private final float originY;
    private float xUnit;
    private float yUnit;

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DiagramView,defStyleAttr,0);
        originX = a.getDimension(R.styleable.DiagramView_origin_x,0);
        originY = a.getDimension(R.styleable.DiagramView_origin_y,0);
    }

    private void init(){
        paint = new Paint();
        paint.setAntiAlias(true);

        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setAntiAlias(true);

        Resources resources = context.getResources();
        radius = resources.getDimension(R.dimen.x10px);
        innerRadius = resources.getDimension(R.dimen.x8px);
        strokeWidth = resources.getDimension(R.dimen.x5px);
//        paddingLeft = resources.getDimension()
//        paddingRight =
    }

    public void setParam(DiagramBaseInfo baseInfo,DiagramParam param){
        this.baseInfo = baseInfo;
        this.param = param;
        xUnit = baseInfo.getxLength()/baseInfo.getxCount();
        yUnit = baseInfo.getyLength()/baseInfo.getyCount();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ( param != null ){
            drawRedLine(canvas);
            drawBlueLine(canvas);
        }
    }

    private void drawRedLine(Canvas canvas){
        if (  param.getRedLine() == null ){
            return;
        }
        paint.setColor(Color.RED);
        drawLineList(canvas,param.getRedLine(),paint);
        drawPoints(canvas,param.getRedLine(),paint);
    }

    private void drawBlueLine(Canvas canvas){
        if (  param.getBlueLine() == null ){
            return;
        }
        paint.setColor(Color.BLUE);
        drawLineList(canvas,param.getBlueLine(),paint);
        drawPoints(canvas,param.getBlueLine(),paint);
    }

    private void drawPoints(Canvas canvas,List<DiagramPoint> points,Paint paint){
        for (int i = 0; i < points.size(); i++) {
            drawPoint(canvas,points.get(i),paint);
        }
    }

    private void drawLineList(Canvas canvas ,List<DiagramPoint> points,Paint paint){
        paint.setStrokeWidth(strokeWidth);
        if ( points.size() >= 2 ) {
            for (int i = 0; i < points.size() - 1; i++) {
                drawLine(canvas,points.get(i),points.get(i+1),paint);
            }
        }
    }

    private void drawLine(Canvas canvas, DiagramPoint p1 , DiagramPoint p2 , Paint paint){
        float startX = (p1.x-baseInfo.getLowerLimitX())*xUnit+originX;
        float startY = getHeight()-(p1.y-baseInfo.getLowerLimitY())*yUnit-originY;
        float stopX = (p2.x-baseInfo.getLowerLimitX())*xUnit+originX;
        float stopY = getHeight()-(p2.y-baseInfo.getLowerLimitY())*yUnit-originY;
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }

    private void drawPoint(Canvas canvas,DiagramPoint p,Paint paint){
        float cx = (p.getX()-baseInfo.getLowerLimitX())*xUnit+originX;
        float cy = getHeight()-(p.getY()-baseInfo.getLowerLimitY())*yUnit-originY;
//        canvas.drawCircle(cx,cy,outerRadius,whitePaint);
        if ( p.getType() == 0 ){
            canvas.drawCircle(cx,cy,radius,paint);
            canvas.drawCircle(cx, cy, innerRadius, whitePaint);
        } else {
            canvas.drawCircle(cx,cy,radius,whitePaint);
            canvas.drawCircle(cx, cy, innerRadius, paint);
        }
    }

    public static class DiagramParam{
        private List<DiagramPoint> blueLine;
        private List<DiagramPoint> redLine;

        public List<DiagramPoint> getBlueLine() {
            return blueLine;
        }

        public void setBlueLine(List<DiagramPoint> blueLine) {
            this.blueLine = blueLine;
        }

        public List<DiagramPoint> getRedLine() {
            return redLine;
        }

        public void setRedLine(List<DiagramPoint> redLine) {
            this.redLine = redLine;
        }
    }

    public static class DiagramBaseInfo{
        private int lowerLimitX;
        private int lowerLimitY;
        private int upperLimitX;
        private int upperLimitY;
        private float xLength;
        private float yLength;

        public int getLowerLimitX() {
            return lowerLimitX;
        }

        public void setLowerLimitX(int lowerLimitX) {
            this.lowerLimitX = lowerLimitX;
        }

        public int getLowerLimitY() {
            return lowerLimitY;
        }

        public void setLowerLimitY(int lowerLimitY) {
            this.lowerLimitY = lowerLimitY;
        }

        public float getxLength() {
            return xLength;
        }

        public void setxLength(float xLength) {
            this.xLength = xLength;
        }

        public float getyLength() {
            return yLength;
        }

        public void setyLength(float yLength) {
            this.yLength = yLength;
        }

        public int getUpperLimitX() {
            return upperLimitX;
        }

        public void setUpperLimitX(int upperLimitX) {
            this.upperLimitX = upperLimitX;
        }

        public int getUpperLimitY() {
            return upperLimitY;
        }

        public void setUpperLimitY(int upperLimitY) {
            this.upperLimitY = upperLimitY;
        }

        public int getxCount() {
            return upperLimitX-lowerLimitX;
        }

        public int getyCount() {
            return upperLimitY-lowerLimitY;
        }

    }

    public static class DiagramPoint{
        private int x;
        private int y;
        private int type;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}

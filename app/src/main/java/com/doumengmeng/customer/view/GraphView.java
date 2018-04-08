package com.doumengmeng.customer.view;

import android.content.Context;
import android.content.res.Resources;
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

import com.doumengmeng.customer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 曲线图控件
 * 创建日期: 2018/1/19 9:31
 */
public class GraphView extends View {

    private final Context context;
    private GraphBaseInfo baseInfo;
    private GraphLine param;
    private Paint paint;
    private Paint whitePaint;

    private float innerRadius;
    private float radius;
    private float strokeWidth;
    private final float originX;
    private final float originY;
    private float xUnit;
    private float yUnit;

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GraphView,defStyleAttr,0);
        originX = a.getDimension(R.styleable.GraphView_origin_x,0);
        originY = a.getDimension(R.styleable.GraphView_origin_y,0);
        a.recycle();
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

    public void setParam(GraphBaseInfo baseInfo, GraphLine param){
        this.baseInfo = baseInfo;
        this.param = param;
        xUnit = baseInfo.getxLength()/baseInfo.getxCount();
        yUnit = baseInfo.getyLength()/baseInfo.getyCount();

        updateLowerPoint(param.getRedLine(),baseInfo);
        updateLowerPoint(param.getBlueLine(),baseInfo);
        postInvalidate();
    }

    private void updateLowerPoint(List<GraphPoint> points , GraphBaseInfo baseInfo){
        for ( GraphPoint point:points ){
            if ( point.getX() < baseInfo.getLowerLimitX() ){
                point.setX(baseInfo.getLowerLimitX());
            }
            if ( point.getY() < baseInfo.getLowerLimitY() ){
                point.setY(baseInfo.getLowerLimitY());
            }
        }
    }

    public boolean hasBlueLine(){
        return hasPointInDiagram(param.getBlueLine());
    }

    public boolean hasRedLine(){
        if ( hasPointInDiagram(param.getRedLine()) ){
            List<GraphPoint> bluePoints = param.getBlueLine();
            List<GraphPoint> redPoints = param.getRedLine();
            for (int i = 0 ; i< bluePoints.size();i++) {
                if (!isSamePoint(bluePoints.get(i), redPoints.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPointInDiagram(List<GraphPoint> points){
        return !(points == null || points.size() <= 0);
    }

    private boolean isPointInRange(GraphPoint point){
        return !(point.getX() < baseInfo.getLowerLimitX()
                || point.getY() < baseInfo.getLowerLimitY());
    }

    private boolean isSamePoint(GraphPoint p1 , GraphPoint p2){
        return p1.getY() == p2.getY() && p1.getX() == p2.getX();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ( baseInfo != null && baseInfo.getBackground() != -1 ){
            drawBackground(canvas);
        }
        if ( param != null ){
            drawRedLine(canvas);
            drawBlueLine(canvas);
        }
    }

    private void drawBackground(Canvas canvas){
        canvas.save();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),baseInfo.background);
        Rect src = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        Rect dest = new Rect(0,0,canvas.getWidth(),canvas.getHeight());
        canvas.drawBitmap(bitmap,src,dest,paint);
        bitmap.recycle();
        canvas.restore();
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

    private void drawPoints(Canvas canvas, List<GraphPoint> points, Paint paint){
        for (int i = 0; i < points.size(); i++) {
            drawPoint(canvas,points.get(i),paint);
        }
    }

    private void drawLineList(Canvas canvas , List<GraphPoint> points, Paint paint){
        paint.setStrokeWidth(strokeWidth);
        if ( points.size() >= 2 ) {
            for (int i = 0; i < points.size() - 1; i++) {
                drawLine(canvas,points.get(i),points.get(i+1),paint);
            }
        }
    }

    private void drawLine(Canvas canvas, GraphPoint p1 , GraphPoint p2 , Paint paint){
        float startX = (p1.x-baseInfo.getLowerLimitX())*xUnit+originX;
        float startY = getHeight()-(p1.y-baseInfo.getLowerLimitY())*yUnit-originY;
        float stopX = (p2.x-baseInfo.getLowerLimitX())*xUnit+originX;
        float stopY = getHeight()-(p2.y-baseInfo.getLowerLimitY())*yUnit-originY;
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }

    private void drawPoint(Canvas canvas, GraphPoint p, Paint paint){
        float cx = (p.getX()-baseInfo.getLowerLimitX())*xUnit+originX;
        float cy = getHeight()-(p.getY()-baseInfo.getLowerLimitY())*yUnit-originY;
//        canvas.drawCircle(cx,cy,outerRadius,whitePaint);
        if ( p.getType() != 2 ){
            canvas.drawCircle(cx,cy,radius,whitePaint);
            canvas.drawCircle(cx, cy, innerRadius, paint);
        } else {
            canvas.drawCircle(cx,cy,radius,paint);
            canvas.drawCircle(cx, cy, innerRadius, whitePaint);
        }
    }

    public static class GraphLine {
        private List<GraphPoint> blueLine;
        private List<GraphPoint> redLine;

        public GraphLine() {
            blueLine = new ArrayList<>();
            redLine = new ArrayList<>();
        }

        public List<GraphPoint> getBlueLine() {
            return blueLine;
        }

        public void setBlueLine(List<GraphPoint> blueLine) {
            this.blueLine = blueLine;
        }

        public List<GraphPoint> getRedLine() {
            return redLine;
        }

        public void setRedLine(List<GraphPoint> redLine) {
            this.redLine = redLine;
        }
    }

    public static class GraphBaseInfo {
        private int lowerLimitX;
        private int lowerLimitY;
        private int upperLimitX;
        private int upperLimitY;
        private float xLength;
        private float yLength;
        private int background = -1;

        public int getBackground() {
            return background;
        }

        public void setBackground(int background) {
            this.background = background;
        }

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

    public static class GraphPoint {
        private int x;
        private int y;
        private int type;

        public GraphPoint(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

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

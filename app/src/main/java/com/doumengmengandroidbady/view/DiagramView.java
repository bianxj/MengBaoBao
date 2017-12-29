package com.doumengmengandroidbady.view;

import android.content.Context;
import android.content.res.Resources;
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
    private DiagramParam param;
    private Paint paint;
    private Paint whitePaint;

    private float innerRadius;
    private float radius;
    private float strokeWidth;
    private float paddingLeft;
    private float paddingRight;

    public DiagramView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setAntiAlias(true);

        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setAntiAlias(true);

        Resources resources = context.getResources();
        radius = resources.getDimension(R.dimen.x15px);
        innerRadius = resources.getDimension(R.dimen.x10px);
        strokeWidth = resources.getDimension(R.dimen.x7px);
//        paddingLeft = resources.getDimension()
//        paddingRight =
    }

    public void setParam(DiagramParam param){
        this.param = param;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ( param != null ){
            drawBlueLine(canvas);
            drawRedLine(canvas);
        } else {
            paint.setStrokeWidth(5);
            paint.setColor(Color.GRAY);
            canvas.drawLine(10,10,400,400,paint);
        }
    }

    private void drawRedLine(Canvas canvas){
        paint.setColor(Color.RED);
        drawLineList(canvas,param.getRedLine(),paint);
        drawPoints(canvas,param.getRedLine(),paint);
    }

    private void drawBlueLine(Canvas canvas){
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
        canvas.drawLine(p1.x*param.xUnit,getHeight()-p1.y*param.yUnit,p2.x*param.xUnit,getHeight()-p2.y*param.yUnit,paint);
    }

    private void drawPoint(Canvas canvas,DiagramPoint p,Paint paint){
        float cx = p.getX()*param.xUnit;
        float cy = getHeight()-p.getY()*param.yUnit;
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
        private int xUnit;
        private int yUnit;
        private List<DiagramPoint> blueLine;
        private List<DiagramPoint> redLine;

        public int getxUnit() {
            return xUnit;
        }

        public void setxUnit(int xUnit) {
            this.xUnit = xUnit;
        }

        public int getyUnit() {
            return yUnit;
        }

        public void setyUnit(int yUnit) {
            this.yUnit = yUnit;
        }

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

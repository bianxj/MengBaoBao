package com.doumengmeng.doctor.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.activity.DiagramDataActivity;
import com.doumengmeng.doctor.response.entity.ImageData;
import com.doumengmeng.doctor.util.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/1.
 */
public class GraphModule extends LinearLayout {

    private List<ImageData> imageDatas;
    private int monthAge;
    private boolean isMale;
    private boolean isShowDetails;

    public GraphModule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraphModule(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.view_graph_module,null);
        addView(view);

        bt_details = findViewById(R.id.bt_details);
        iv_details = findViewById(R.id.iv_details);
        iv_describe = findViewById(R.id.iv_describe);
        graph_view = findViewById(R.id.diagram_view);
        rl_data = findViewById(R.id.rl_data);
        rg_weight = findViewById(R.id.rg_weight);
        rg_height = findViewById(R.id.rg_height);
        rg_BMI = findViewById(R.id.rg_BMI);

        graphButtons.add(rg_weight);
        graphButtons.add(rg_height);
        graphButtons.add(rg_BMI);

        rg_BMI.setOnCheckedChangeListener(graphOnChangeListener);
        rg_height.setOnCheckedChangeListener(graphOnChangeListener);
        rg_weight.setOnCheckedChangeListener(graphOnChangeListener);
        bt_details.setOnClickListener(diagramListener);
    }

    public void setData(List<ImageData> imageDatas , int monthAge , boolean isMale , boolean isShowDetails){
        this.imageDatas = imageDatas;
        this.monthAge = monthAge;
        this.isMale = isMale;
        this.isShowDetails = isShowDetails;
        initGraphData();
    }

    private ImageView iv_details;
    private GraphView graph_view;
    private RelativeLayout rl_data;
    private Button bt_details;
    private ImageView iv_describe;
    private RadioButton rg_weight,rg_height,rg_BMI;
    private final List<RadioButton> graphButtons = new ArrayList<>();

    private enum GRAPH_TYPE{
        HEIGHT,
        WEIGHT,
        HW
    }

    private final Map<GRAPH_TYPE,GraphData> dataMap = new HashMap<>();

    private void initGraphData(){
        dataMap.put(GRAPH_TYPE.WEIGHT,buildGraphData(0,0,1080,3000,R.dimen.x600px,R.dimen.x622px));
        dataMap.put(GRAPH_TYPE.HEIGHT,buildGraphData(0,4500,1080,12000,R.dimen.x600px,R.dimen.x622px));
        if ( isLowerThanTwoYear() ){
            dataMap.put(GRAPH_TYPE.HW, buildGraphData(4500, 0, 11000, 3000, R.dimen.x590px, R.dimen.x622px));
        } else {
            dataMap.put(GRAPH_TYPE.HW, buildGraphData(6500, 0, 12000, 3000, R.dimen.x588px, R.dimen.x622px));
        }
        if ( isShowDetails ){
            bt_details.setVisibility(View.VISIBLE);
        } else {
            bt_details.setVisibility(View.GONE);
        }
        initLinesData();
        setDefaultPage();
    }

    private void initLinesData(){
        for (ImageData imageData:imageDatas){
            int currentDayInYear = getDayInYear(imageData.getMonthAge(),imageData.getMonthDay());
            int correntDayInYear = getDayInYear(imageData.getCorrectMonthAge(),imageData.getCorrectMonthDay());
            int height = (int) (Float.parseFloat(imageData.getHeight()) * 100);
            int weight = (int) (Float.parseFloat(imageData.getWeight()) * 100);
            int type = imageData.getType();

            dataMap.get(GRAPH_TYPE.WEIGHT).getLine().getBlueLine().add(new GraphView.GraphPoint(currentDayInYear,weight,type));
            if ( imageData.getMonthAge() >= 24 ){
                dataMap.get(GRAPH_TYPE.WEIGHT).getLine().getRedLine().add(new GraphView.GraphPoint(currentDayInYear, weight, type));
            } else {
                dataMap.get(GRAPH_TYPE.WEIGHT).getLine().getRedLine().add(new GraphView.GraphPoint(correntDayInYear, weight, type));
            }
//            dataMap.get(GRAPH_TYPE.WEIGHT).getLine().getRedLine().add(new GraphView.GraphPoint(correntDayInYear, weight, type));

            dataMap.get(GRAPH_TYPE.HEIGHT).getLine().getBlueLine().add(new GraphView.GraphPoint(currentDayInYear,height,type));
            dataMap.get(GRAPH_TYPE.HEIGHT).getLine().getRedLine().add(new GraphView.GraphPoint(correntDayInYear, height, type));

            dataMap.get(GRAPH_TYPE.HW).getLine().getBlueLine().add(new GraphView.GraphPoint(height,weight,type));
        }
    }

    private void setDefaultPage(){
        refreshWeight();
    }

    private GraphData buildGraphData(int lowerX , int lowerY , int upperX , int upperY,int dimenX , int dimenY){
        GraphData data = new GraphData();
        data.setBaseInfo(buildGraphBaseInfo(lowerX,lowerY,upperX,upperY,dimenX,dimenY));
        data.setLine(new GraphView.GraphLine());
        return data;
    }

    private GraphView.GraphBaseInfo buildGraphBaseInfo(int lowerX , int lowerY , int upperX , int upperY,int dimenX , int dimenY){
        GraphView.GraphBaseInfo baseInfo = new GraphView.GraphBaseInfo();
        baseInfo.setLowerLimitX(lowerX);
        baseInfo.setLowerLimitY(lowerY);
        baseInfo.setUpperLimitX(upperX);
        baseInfo.setUpperLimitY(upperY);
        baseInfo.setxLength(getResources().getDimension(dimenX));
        baseInfo.setyLength(getResources().getDimension(dimenY));
        return baseInfo;
    }

    private final CompoundButton.OnCheckedChangeListener graphOnChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if ( isChecked ){
                refreshDiagram(compoundButton.getId());
            }
        }
    };

    //曲线图详细界面跳转
    private final OnClickListener diagramListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = getContext();
            if ( imageDatas != null ) {
                String imageData = GsonUtil.getInstance().toJson(imageDatas);
                int type;
                if ( rg_height.isChecked() ){
                    type = DiagramDataActivity.DIAGRAM_TYPE.TYPE_HEIGHT.ordinal();
                } else if ( rg_weight.isChecked() ){
                    type = DiagramDataActivity.DIAGRAM_TYPE.TYPE_WEIGHT.ordinal();
                } else {
                    type = DiagramDataActivity.DIAGRAM_TYPE.TYPE_HEIGHT_WEIGHT.ordinal();
                }
                Intent intent = new Intent(context, DiagramDataActivity.class);
                intent.putExtra(DiagramDataActivity.IN_PARAM_IMAGE_DATA, imageData);
                intent.putExtra(DiagramDataActivity.IN_PARAM_TYPE,type);
                context.startActivity(intent);
            } else {
                Toast.makeText(context,"暂无数据",Toast.LENGTH_SHORT).show();
            }
        }
    };

    //刷新曲线图
    private void refreshDiagram(int id){
        for (RadioButton button: graphButtons){
            if ( button.getId() != id ){
                button.setChecked(false);
            }
        }

        switch (id){
            case R.id.rg_weight:
                refreshWeight();
                break;
            case R.id.rg_height:
                refreshHeight();
                break;
            case R.id.rg_BMI:
                refreshHeightWeight();
                break;
        }
    }

    private void refreshWeight(){
        if ( isMale ) {
            iv_details.setImageResource(R.drawable.bg_boy_weight_data);
        } else {
            iv_details.setImageResource(R.drawable.bg_girl_weight_data);
        }
        rl_data.setBackgroundColor(getResources().getColor(R.color.bgWeightColor));
        refreshGraph(GRAPH_TYPE.WEIGHT);
    }

    private void refreshHeight(){
        if ( isMale ) {
            iv_details.setImageResource(R.drawable.bg_boy_height_data);
        } else {
            iv_details.setImageResource(R.drawable.bg_girl_height_data);
        }
        rl_data.setBackgroundColor(getResources().getColor(R.color.bgHeightColor));
        refreshGraph(GRAPH_TYPE.HEIGHT);
    }

    private void refreshHeightWeight(){
        if ( !isLowerThanTwoYear() ){
            if ( isMale ) {
                iv_details.setImageResource(R.drawable.bg_boy_height_weight_2_5);
            } else {
                iv_details.setImageResource(R.drawable.bg_girl_height_weight_2_5);
            }
        } else {
            if ( isMale ) {
                iv_details.setImageResource(R.drawable.bg_boy_height_weight_0_2);
            } else {
                iv_details.setImageResource(R.drawable.bg_girl_height_weight_0_2);
            }
        }
        refreshGraph(GRAPH_TYPE.HW);
        rl_data.setBackgroundColor(getResources().getColor(R.color.bgBMIColor));
    }

    private void refreshGraph(GRAPH_TYPE type){
        GraphData data = dataMap.get(type);
        graph_view.setParam(data.getBaseInfo(),data.getLine());
        if ( graph_view.hasRedLine() && graph_view.hasBlueLine() ){
            iv_describe.setImageResource(R.drawable.icon_graph_describe);
        } else if ( graph_view.hasBlueLine() ){
            iv_describe.setImageResource(R.drawable.icon_graph_blue_describe);
        } else if ( graph_view.hasRedLine() ) {
            iv_describe.setImageResource(R.drawable.icon_graph_red_describe);
        } else {
            iv_describe.setImageBitmap(null);
        }
    }

    private boolean isLowerThanTwoYear(){
        return monthAge <= 24;
    }

    /**
     * 作者: 边贤君
     * 描述: 获取实际天数
     * 参数:
     * 返回:
     * 日期: 2018/1/16 15:54
     */
    private int getDayInYear(int month , int day){
        int dayInYear = month * 30;
        if ( day > 30 ){
            dayInYear += 30;
        } else {
            dayInYear += day;
        }
        return dayInYear;
    }

    private static class GraphData{
        private GraphView.GraphBaseInfo baseInfo;
        private GraphView.GraphLine line;

        public GraphView.GraphBaseInfo getBaseInfo() {
            return baseInfo;
        }

        public void setBaseInfo(GraphView.GraphBaseInfo baseInfo) {
            this.baseInfo = baseInfo;
        }

        public GraphView.GraphLine getLine() {
            return line;
        }

        public void setLine(GraphView.GraphLine line) {
            this.line = line;
        }
    }

}

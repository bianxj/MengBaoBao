package com.doumengmeng.doctor.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.response.entity.Feature;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/5/3.
 */

public class DevelopmentView extends LinearLayout {
    private TextView tv_title;
    private LinearLayout ll_content;
    private boolean isHospitalReport;

    public DevelopmentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DevelopmentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(LinearLayout.VERTICAL);
        tv_title = createTitleView();
        addView(tv_title);
        ll_content = createContentLayout();
        addView(ll_content);
    }

    private TextView createTitleView(){
        TextView view = new TextView(getContext());
        int height = getContext().getResources().getDimensionPixelOffset(R.dimen.y96px);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,height);
        view.setLayoutParams(params);
        float size = getContext().getResources().getDimension(R.dimen.y28px);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
        view.setTextColor(getContext().getResources().getColor(R.color.second_black));
        view.setGravity(Gravity.CENTER_VERTICAL);

        return view;
    }

    private LinearLayout createContentLayout(){
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        return layout;
    }

    public void initDevelopment(String correntMonth , String recordTime, List<String> selected,boolean isHospitalReport){
        this.isHospitalReport = isHospitalReport;
        if ( isHospitalReport ){
            LayoutParams params = (LayoutParams) tv_title.getLayoutParams();
            params.leftMargin = getContext().getResources().getDimensionPixelOffset(R.dimen.x20px);
            tv_title.setLayoutParams(params);
        }
        tv_title.setText(String.format(getResources().getString(R.string.assessment_develop_month), Integer.parseInt(correntMonth)));
        tv_title.getPaint().setFakeBoldText(true);
        initDevelopment(generateDevelopmentData(correntMonth , recordTime, selected));
    }

    //初始化发行为
    private void initDevelopment(Map<String,List<DevelopmentView.DevelopmentalItem>> maps){
        Set<String> keys = maps.keySet();
        for (String key:keys){
            ll_content.addView(createSubItem(key,maps.get(key)));
        }
    }

    private Map<String,List<DevelopmentView.DevelopmentalItem>> generateDevelopmentData(String correntMonth , String recordTime, List<String> selected){
        Map<String,List<DevelopmentView.DevelopmentalItem>> map = new LinkedHashMap<>();

        List<String> ages = new ArrayList<>();
        ages.add(correntMonth);
        List<Feature> features = DaoManager.getInstance().getFeatureDao().searchFeatureList(getContext(),ages,recordTime);

        for (Feature feature:features){
            List<DevelopmentView.DevelopmentalItem> developmentalItems;
            if ( !map.containsKey(feature.getFeaturetype()) ){
                developmentalItems = new ArrayList<>();
                map.put(feature.getFeaturetype(),developmentalItems);
            } else {
                developmentalItems = map.get(feature.getFeaturetype());
            }
            DevelopmentView.DevelopmentalItem item = new DevelopmentView.DevelopmentalItem();
            item.setValue(feature.getDetaildesc());
            if ( selected != null ) {
                item.setCheck(selected.contains(feature.getId()));
            } else {
                item.setCheck(false);
            }
            item.setMark(!"0".equals(feature.getPointtag()));
            developmentalItems.add(item);
        }
        return map;
    }

    /**
     * 作者: 边贤君
     * 描述: 创建发育行为条目
     * 日期: 2018/1/18 9:44
     */
    private View createSubItem(String title , List<DevelopmentView.DevelopmentalItem> contents){
        RelativeLayout layout = new RelativeLayout(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);

        View divider = new View(getContext());
        divider.setBackgroundColor(getResources().getColor(R.color.linePink));
        divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelOffset(R.dimen.y1px)));
        layout.addView(divider);

        TextView tv_title = new TextView(getContext());
        tv_title.setText(title);
        tv_title.setTextColor(getResources().getColor(R.color.second_black));
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y24px));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
        if ( isHospitalReport ) {
            params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.y20px);
        }
        tv_title.setLayoutParams(params);
        layout.addView(tv_title);

        View right = createCheckLine(contents);
        layout.addView(right);

        return layout;
    }

    /**
     * 作者: 边贤君
     * 描述: 创建发育行为条目中的选项
     * 日期: 2018/1/18 9:44
     */
    private View createCheckLine(List<DevelopmentView.DevelopmentalItem> contents){
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y24px);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP|RelativeLayout.ALIGN_PARENT_LEFT);
        params.topMargin = getResources().getDimensionPixelOffset(R.dimen.y35px);
        params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x159px);
        params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.x20px);
        layout.setLayoutParams(params);

        for (DevelopmentView.DevelopmentalItem content:contents){
            RelativeLayout subLayout = new RelativeLayout(getContext());
            LayoutParams layoutParams = (new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y10px);
            subLayout.setLayoutParams(layoutParams);

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setButtonDrawable(null);
            if ( isHospitalReport ) {
                checkBox.setBackgroundResource(R.drawable.cb_hospital_report);
            } else {
                checkBox.setBackgroundResource(R.drawable.cb_history_report);
            }
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x34px),getResources().getDimensionPixelOffset(R.dimen.x34px));
            p.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.x2px);
            checkBox.setLayoutParams(p);
            checkBox.setChecked(content.isCheck());
            checkBox.setEnabled(false);
            subLayout.addView(checkBox);

            TextView tv_content = new TextView(getContext());
            RelativeLayout.LayoutParams textParam = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textParam.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x44px);
            tv_content.setLayoutParams(textParam);
            tv_content.setGravity(Gravity.CENTER_VERTICAL);
            tv_content.setTextColor(getResources().getColor(R.color.fourth_gray));
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y24px));
            if ( content.isMark ) {
                String value = content.getValue()+" *";
                SpannableString style = new SpannableString(value);
                style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.first_pink)), value.length()-1, value.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new StyleSpan(Typeface.BOLD), value.length()-1, value.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new AbsoluteSizeSpan(((int)tv_content.getTextSize())+2), value.length()-1, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_content.setText(style);
            } else {
                tv_content.setText(content.getValue());
            }
            subLayout.addView(tv_content);

//            if ( content.isMark ){
//                TextView tv_mark = new TextView(getContext());
//                LinearLayout.LayoutParams markParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//                textParam.topMargin = -1*getResources().getDimensionPixelOffset(R.dimen.y5px);
//                tv_mark.setLayoutParams(markParam);
//                tv_mark.setText("*");
//                tv_mark.setTextColor(Color.RED);
//                tv_mark.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y24px));
//                subLayout.addView(tv_mark);
//            }

            layout.addView(subLayout);
        }

        return layout;
    }

    public static class DevelopmentalItem{
        private String value;
        private boolean isCheck;
        private boolean isMark;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public boolean isMark() {
            return isMark;
        }

        public void setMark(boolean mark) {
            isMark = mark;
        }
    }
    
}

package com.doumengmeng.doctor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.doumengmeng.doctor.R;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxLayout extends LinearLayout {


    private int defaultGrap;
    private int verticalGrap;
    private boolean isRadio;
    private List<CheckBox> checkBoxes;
    private List<String> contents;
    private boolean isDrawed = false;
    private OnItemSelectListener onItemSelectListener;

    public CheckBoxLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CheckBoxLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxLayout,0,0);
        defaultGrap = array.getDimensionPixelOffset(R.styleable.CheckBoxLayout_default_grap,getResources().getDimensionPixelOffset(R.dimen.x28px));
        verticalGrap = array.getDimensionPixelOffset(R.styleable.CheckBoxLayout_vertical_grap,getResources().getDimensionPixelOffset(R.dimen.y48px));
        array.recycle();
        setOrientation(LinearLayout.VERTICAL);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if ( !isDrawed ){
                    if ( contents != null ){
                        generate(contents);
                    }
                    isDrawed = true;
                }
            }
        });
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener){
        this.onItemSelectListener = onItemSelectListener;
    }

    public void setCheckBoxes(List<String> contents,boolean isRadio){
        if ( checkBoxes != null ) {
            removeAllViews();
            checkBoxes.clear();
        } else {
            checkBoxes = new ArrayList<>();
        }
        this.isRadio = isRadio;
        this.contents = contents;
        if ( isDrawed ) {
            generate(contents);
        }
    }

    private void generate(List<String> contents){
        int width = getWidth();
        int widthTemp = 0;
        List<CheckBox> temps = new ArrayList<>();
        for (String content:contents){
            CheckBox checkBox = generateCheckBox(content);
            checkBoxes.add(checkBox);
            if ( (widthTemp + checkBox.getLayoutParams().width) > width ){
                generateSubLayout(temps,(width-widthTemp)/(temps.size()-1));
                widthTemp = 0;
                temps.clear();
            }
            widthTemp += checkBox.getLayoutParams().width;
            temps.add(checkBox);
        }

        if ( temps.size() > 0 ){
            if ( (widthTemp+(defaultGrap*temps.size())) > width ){
                generateSubLayout(temps,(width-widthTemp)/(temps.size()-1));
            } else {
                generateSubLayout(temps,defaultGrap);
            }
        }
    }

    private void generateSubLayout(List<CheckBox> checkBoxes,int gap){
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        if ( getChildCount() > 0 ) {
            params.topMargin = verticalGrap;
        }
        layout.setLayoutParams(params);
        for (int i = 0; i< checkBoxes.size();i++){
            if ( i != 0 ){
                Space space = new Space(getContext());
                space.setLayoutParams(new LayoutParams(gap,LayoutParams.WRAP_CONTENT));
                layout.addView(space);
            }
            layout.addView(checkBoxes.get(i));
        }
        addView(layout);
    }

    private CheckBox generateCheckBox(String content){
        CheckBox checkBox = new CheckBox(getContext());

        Paint paint = checkBox.getPaint();
        paint.setTextSize(getResources().getDimension(R.dimen.y26px));
        int width = (int)paint.measureText(content) + getResources().getDimensionPixelOffset(R.dimen.x20px)*2;

        LayoutParams params = new LayoutParams(width,getResources().getDimensionPixelOffset(R.dimen.y56px));
        checkBox.setPadding(getResources().getDimensionPixelOffset(R.dimen.x20px),0,getResources().getDimensionPixelOffset(R.dimen.x20px),0);
        checkBox.setLayoutParams(params);
        checkBox.setText(content);
        checkBox.setButtonDrawable(null);
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.y26px));
        checkBox.setBackgroundResource(R.drawable.bg_circle);
        @SuppressLint("ResourceType") ColorStateList list = getResources().getColorStateList(R.drawable.bg_circle_text_color);
        checkBox.setTextColor(list);

        if ( isRadio ){
            checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        }
        return checkBox;
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if ( b ){
                if ( onItemSelectListener != null ){
                    onItemSelectListener.onItemClickListener(compoundButton);
                }
                selectCompoundButton(compoundButton);
            }
        }
    };

    private void selectCompoundButton(CompoundButton button){
        for (CheckBox checkBox:checkBoxes){
            checkBox.setChecked(checkBox.equals(button));
        }
    }

    public List<CheckBox> getCheckBoxes(){
        return checkBoxes;
    }

    public CheckBox getFirstCheckBox(){
        for (CheckBox checkBox:checkBoxes){
            if ( checkBox.isChecked() ){
                return checkBox;
            }
        }
        return null;
    }

    public interface OnItemSelectListener{
        public void onItemClickListener(CompoundButton compoundButton);
    }

}

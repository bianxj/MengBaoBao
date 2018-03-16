package com.doumengmeng.doctor.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/3/16.
 */

public class AssessmentDataView extends LinearLayout {

    public AssessmentDataView(Context context) {
        super(context);
    }

    public AssessmentDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AssessmentDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAssessmentFormat(AssessmentFormat format){
        removeAllViews();

        TYPE type = format.getType();
        if ( TYPE.SINGLE_VIEW == type ){

        }
    }

    public enum TYPE {
        SINGLE_VIEW,
        DOUBLE_VIEW,
        REFERENCE_VIEW
    }

    public static class AssessmentFormat{
        private TYPE type;
        private int[] formats;
        private int marginLeft;
        private String[] values;

        protected TYPE getType() {
            return type;
        }

        public AssessmentFormat setType(TYPE type) {
            this.type = type;
            return this;
        }

        protected int[] getFormats() {
            return formats;
        }

        public AssessmentFormat setFormats(int[] formats) {
            this.formats = formats;
            return this;
        }

        protected String[] getValues() {
            return values;
        }

        public AssessmentFormat setValues(String[] values) {
            this.values = values;
            return this;
        }

        public int getMarginLeft() {
            return marginLeft;
        }

        public void setMarginLeft(int marginLeft) {
            this.marginLeft = marginLeft;
        }
    }

}

package com.doumengmeng.customer.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */

public class PayItemLayout extends LinearLayout {

    public PayItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PayItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private List<CheckBox> checkBoxes = new ArrayList<>();
    public void addItems(List<PayData> datas){
        for (PayData data:datas){
            addView(createSubItem(data));
        }
    }

    private View createSubItem(PayData data){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_pay,null);
//        view.setBackgroundResource(R.drawable.bg_pay_selector);
        ImageView iv_icon = view.findViewById(R.id.iv_icon);
        TextView tv_name = view.findViewById(R.id.tv_name);
        CheckBox cb_choose = view.findViewById(R.id.cb_choose);

        iv_icon.setImageResource(data.getDrawableId());
        tv_name.setText(data.getName());
        cb_choose.setChecked(data.isCheck());
        cb_choose.setTag(view);
        if ( data.isCheck() ){
            view.setBackgroundColor(getResources().getColor(R.color.thirdGray));
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.first_white));
        }
        view.setOnClickListener(listener);
        checkBoxes.add(cb_choose);
        return view;
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            CheckBox cb_choose = view.findViewById(R.id.cb_choose);
            setSelectPosition(cb_choose);
        }
    };

    private void setSelectPosition(CompoundButton compoundButton){
        for (CompoundButton checkBox:checkBoxes){
            if ( checkBox.equals(compoundButton) ){
                checkBox.setChecked(true);
                ((View)checkBox.getTag()).setBackgroundColor(getResources().getColor(R.color.thirdGray));
            } else {
                checkBox.setChecked(false);
                ((View)checkBox.getTag()).setBackgroundColor(getResources().getColor(R.color.first_white));
            }
        }
    }

    public int getSelectPostion(){
        for (int i=0;i<checkBoxes.size();i++){
            if ( checkBoxes.get(i).isChecked() ){
                return i;
            }
        }
        return -1;
    }

    public static class PayData{
        private int drawableId;
        private String name;
        private boolean isCheck;

        public PayData(int drawableId, String name, boolean isCheck) {
            this.drawableId = drawableId;
            this.name = name;
            this.isCheck = isCheck;
        }

        public int getDrawableId() {
            return drawableId;
        }

        public void setDrawableId(int drawableId) {
            this.drawableId = drawableId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }

}

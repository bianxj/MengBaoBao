package com.doumengmeng.doctor.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseFragment;
import com.doumengmeng.doctor.response.entity.Nurture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class ParentingGuideFragment extends BaseFragment {

    private List<Nurture> nurtures;

    private LinearLayout ll_age,ll_nurture;
    private EditText et_custom_title,et_custom_content;

    private List<CheckBox> ages = new ArrayList<>();
    private List<View> contents = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parenting_guide,null);
        findView(view);
        return view;
    }

    private void findView(View view){
        ll_age = view.findViewById(R.id.ll_age);
        ll_nurture = view.findViewById(R.id.ll_nurture);
        et_custom_title = view.findViewById(R.id.et_custom_title);
        et_custom_content = view.findViewById(R.id.et_custom_content);
    }

    private void buildAgeTitle(){
        List<String> ageNames = new ArrayList<>();
        for (Nurture nurture:nurtures){
            if ( !ageNames.contains(nurture.getAge()) ){
                ageNames.add(nurture.getAge());
            }
        }

        for (String age:ageNames){
            CheckBox checkBox = buildCheckBox(age);
            ll_age.addView(checkBox);
            checkBox.setOnCheckedChangeListener(changeListener);
            ages.add(checkBox);
        }

    }

    private CheckBox buildCheckBox(String content){
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setButtonDrawable(null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x58px),getResources().getDimensionPixelOffset(R.dimen.x58px));
        checkBox.setLayoutParams(params);
        checkBox.setBackgroundResource(R.drawable.bg_round);
        ColorStateList list = ColorStateList.valueOf(R.drawable.bg_circle_text_color);
        checkBox.setTextColor(list);
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelOffset(R.dimen.y29px));
        checkBox.setGravity(Gravity.CENTER);
        checkBox.setText(content);
        if ( ll_age.getChildCount() > 0 ){
            params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x29px);
        }
        return checkBox;
    }

    private void refreshContent(String age){
        ll_nurture.removeAllViews();
        for (Nurture nurture:nurtures){
            if ( nurture.getAge().equals(age) ){
                ll_nurture.addView(buildNurtureContent(nurture));
            }
        }
    }

    private View buildNurtureContent(Nurture nurture){
        View view = getCacheView();
        if ( view == null ) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_nurture_content, null);
            contents.add(view);
        }
        LinearLayout ll_content = view.findViewById(R.id.ll_content);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        CheckBox cb = view.findViewById(R.id.cb);

        tv_title.setText(nurture.getNurtureTitle());
        tv_content.setText(nurture.getNurtureDesc());
        if ( nurture.isChoose() ){
            cb.setChecked(true);
            ll_content.setEnabled(true);
        } else {
            cb.setChecked(false);
            ll_content.setEnabled(false);
        }
        return view;
    }

    private View getCacheView(){
        for (View view:contents){
            if ( view.getParent() == null ){
                return view;
            }
        }
        return null;
    }

    public void setNurtures(List<Nurture> nurtures){
        this.nurtures = nurtures;
    }

    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if ( b ){
                refreshAges(compoundButton);
                refreshContent(compoundButton.getText().toString().trim());
            }
        }
    };

    private void refreshAges(CompoundButton button){
        for (CheckBox checkBox:ages){
            if ( !checkBox.getText().toString().equals(button.getText().toString()) ){
                checkBox.setChecked(false);
            }
        }
    }

}

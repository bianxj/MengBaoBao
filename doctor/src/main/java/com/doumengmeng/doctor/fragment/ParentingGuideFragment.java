package com.doumengmeng.doctor.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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

    private String age;
    private int position = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parenting_guide,null);
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if ( position == -1 ){
            for (int i=0;i<ages.size();i++){
                if ( age.equals(ages.get(i).getText()) ){
                    position = i;
                    ages.get(i).setChecked(true);
                    break;
                }
            }
            //TODO
            if ( position == -1 ){
                position = ages.size()/2;
                ages.get(ages.size()/2).setChecked(true);
            }
        } else {
            ages.get(position).setChecked(true);
        }
        loadCustomerNurture();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveCustomerNurture();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ages.clear();
        contents.clear();
    }

    private void findView(View view){
        ll_age = view.findViewById(R.id.ll_age);
        ll_nurture = view.findViewById(R.id.ll_nurture);
        et_custom_title = view.findViewById(R.id.et_custom_title);
        et_custom_content = view.findViewById(R.id.et_custom_content);

        buildAgeTitles();
        initCustomValue();
    }

    private void buildAgeTitles(){
        List<String> ageNames = new ArrayList<>();
        for (Nurture nurture:nurtures){
            if ( !ageNames.contains(nurture.getAge()) && !TextUtils.isEmpty(nurture.getAge())){
                ageNames.add(nurture.getAge());
            }
        }

        for (int i = 0;i<ageNames.size();i++){
            CheckBox checkBox = createAgeTitle(ageNames.get(i));
            checkBox.setTag(i);
            ll_age.addView(checkBox);
            checkBox.setOnCheckedChangeListener(ageSelectListener);
            ages.add(checkBox);
        }

    }

    private void initCustomValue(){
        for (Nurture nurture:nurtures){
            if ( nurture.isCustom() ){
                et_custom_title.setText(nurture.getNurtureTitle());
                et_custom_content.setText(nurture.getNurtureDesc());
                break;
            }
        }
    }

    private CheckBox createAgeTitle(String content){
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setButtonDrawable(null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.x58px),getResources().getDimensionPixelOffset(R.dimen.x58px));
        checkBox.setLayoutParams(params);
        checkBox.setBackgroundResource(R.drawable.bg_round);
        @SuppressLint("ResourceType") ColorStateList list = getResources().getColorStateList(R.drawable.bg_circle_text_color);
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
            if ( age.equals(nurture.getAge()) ){
                View view = buildNurtureContent(nurture);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                if ( params == null ){
                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    view.setLayoutParams(params);
                }
                params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.y39px);
                ll_nurture.addView(buildNurtureContent(nurture));
            }
        }
    }

    private View buildNurtureContent(Nurture nurture){
        View view = getCacheView();
        if ( view == null ) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_nurture_content, null);
            putCacheView(view);
        }
        LinearLayout ll_content = view.findViewById(R.id.ll_content);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        CheckBox cb = view.findViewById(R.id.cb);

        ll_content.setOnClickListener(nutureOnclicListener);

//        ll_content.setTag(nurture);
        cb.setTag(nurture);
        cb.setOnCheckedChangeListener(nurtureSelectListener);

        tv_title.setText(nurture.getNurtureTitle());
        tv_content.setText(nurture.getNurtureDesc());
        cb.setChecked(nurture.isChoose());
        return view;
    }

    public void putCacheView(View view){
        contents.add(view);
    }

    private View getCacheView(){
        for (View view:contents){
            if ( view.getParent() == null ){
                return view;
            }
        }
        return null;
    }

    public void setNurtures(String age,List<Nurture> nurtures){
        this.age = age;
        this.nurtures = nurtures;
    }

    private CompoundButton.OnCheckedChangeListener ageSelectListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if ( b ){
                position = (int) compoundButton.getTag();
                refreshAges(compoundButton);
                refreshContent(compoundButton.getText().toString().trim());
            }
        }
    };

    private View.OnClickListener nutureOnclicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CheckBox cb = ((ViewGroup)view.getParent()).findViewById(R.id.cb);
            cb.setChecked(!cb.isChecked());
        }
    };

    private CompoundButton.OnCheckedChangeListener nurtureSelectListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            LinearLayout ll_content = ((ViewGroup)compoundButton.getParent()).findViewById(R.id.ll_content);
            Nurture nurture = (Nurture) compoundButton.getTag();
            nurture.setChoose(b);
            ll_content.setBackgroundResource(b?R.drawable.bg_frame_check:R.drawable.bg_frame_uncheck);
//            ll_content.setEnabled(b);
        }
    };

    private void refreshAges(CompoundButton button){
        for (CheckBox checkBox:ages){
            if ( !checkBox.getText().toString().equals(button.getText().toString()) ){
                checkBox.setChecked(false);
            }
        }
    }

    public List<Nurture> getNurtureList(){
        List<Nurture> selectNurtures = new ArrayList<>();
        for (Nurture nurture:nurtures){
            if ( nurture.isChoose() ){
                selectNurtures.add(nurture);
            }
            if ( nurture.isCustom() ){
                if ( et_custom_title != null && et_custom_content != null ) {
                    nurture.setNurtureTitle(et_custom_title.getText().toString().trim());
                    nurture.setNurtureDesc(et_custom_content.getText().toString().trim());
                }
                if ( !TextUtils.isEmpty(nurture.getNurtureTitle()) && !TextUtils.isEmpty(nurture.getNurtureDesc()) ){
                    selectNurtures.add(nurture);
                }
            }
        }
        return selectNurtures;
    }

    private void loadCustomerNurture(){
        Nurture custom = getCustomNurture();
        if ( custom != null ){
            et_custom_title.setText(custom.getNurtureTitle());
            et_custom_content.setText(custom.getNurtureDesc());
        }
    }

    private void saveCustomerNurture(){
        if ( et_custom_title != null && et_custom_content != null ) {
            Nurture custom = getCustomNurture();

            if ( custom != null ){
                custom.setNurtureTitle(et_custom_title.getText().toString().trim());
                custom.setNurtureDesc(et_custom_content.getText().toString().trim());
            }
        }
    }

    private Nurture getCustomNurture(){
        for (Nurture nurture:nurtures){
            if ( nurture.isCustom() ){
                return nurture;
            }
        }
        return null;
    }

}

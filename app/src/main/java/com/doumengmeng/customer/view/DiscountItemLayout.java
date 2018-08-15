package com.doumengmeng.customer.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;

import java.util.ArrayList;
import java.util.List;

public class DiscountItemLayout extends LinearLayout {
    public final static int UNSELECT = -1;

    private List<DiscountData> datas;
    private List<View> items = new ArrayList<>();
    private int selected = UNSELECT;

    public DiscountItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DiscountItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(LinearLayout.VERTICAL);
    }

    public int getSelectIndex(){
        return selected;
    }

    public void addDiscounts(List<DiscountData> datas){
        this.datas = datas;
        initDiscounts();
    }

    private void initDiscounts(){
        int index = 0;
        for (DiscountData data:datas){
            addDiscount(data,index);
            index++;
        }
    }

    private RelativeLayout container;
    private void addDiscount(DiscountData data,int index){
        if ( index%2 == 0 ){
            container = createContainer(index);
            addView(container);
        }
        View child = createDiscountView(data,index);
        items.add(child);
        container.addView(child);
    }

    private RelativeLayout createContainer(int index){
        RelativeLayout layout = new RelativeLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        if ( index > 1 ){
            params.topMargin = getContext().getResources().getDimensionPixelSize(R.dimen.x49px);
        }
        layout.setLayoutParams(params);
        return layout;
    }

    private View createDiscountView(DiscountData data ,int index){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_discount,null);
        view.setOnClickListener(listener);
        TextView tv_spare = view.findViewById(R.id.tv_spare);
        TextView tv_cost = view.findViewById(R.id.tv_cost);
        TextView tv_count = view.findViewById(R.id.tv_count);

        float spare = data.getSingleCost()*data.getCount() - data.getAmountCost();
        tv_cost.setText(String.format(getContext().getString(R.string.pay_cost),(float)data.getAmountCost()));
        if ( spare*100%100 == 0 ) {
            tv_spare.setText(String.format(getContext().getString(R.string.pay_spare), (int)spare));
        } else {
            tv_spare.setText(String.format("已省%.2f元", spare));
        }
        tv_count.setText(String.format(getContext().getString(R.string.pay_count),data.getCount()));
        if ( index == 0 ){
            tv_spare.setVisibility(View.GONE);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        if ( index%2 == 1 ){
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        view.setLayoutParams(params);
        view.setTag(index);
        return view;
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            fresh((int) view.getTag());
        }
    };

    private void fresh(int index){
        selected = index;
        for (View view:items){
            CheckBox checkBox = view.findViewById(R.id.bg_check);
            if ( selected == (int)view.getTag() ){
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        }
    }

    public static class DiscountData{
        private int count;
        private float amountCost;
        private float singleCost;

        public DiscountData(String count, String amountCost, String singleCost) {
            this.count = Integer.parseInt(count);
            this.amountCost = Float.parseFloat(amountCost);
            this.singleCost = Float.parseFloat(singleCost);
        }

        public int getCount() {
            return count;
        }

        public float getAmountCost() {
            return amountCost;
        }

        public float getSingleCost() {
            return singleCost;
        }
    }

}

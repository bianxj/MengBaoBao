package com.doumengmeng.customer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.base.BaseSwipeActivity;
import com.doumengmeng.customer.response.ConfirmRefundResponse;
import com.doumengmeng.customer.util.GsonUtil;

import java.util.List;

/**
 * 作者: 边贤君
 * 描述: 退款结果页面
 */
public class RefundResultActivity extends BaseSwipeActivity {

    public final static String IN_PARAM_REFUND_DATA = "refund_data";

    private ConfirmRefundResponse.Result result;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_result);
        initView();
    }

    private void initView(){
        initTitle();
        initContent();
    }

    private RelativeLayout rl_back;
    private TextView tv_title;
    private void initTitle(){
        rl_back = findViewById(R.id.rl_back);
        tv_title = findViewById(R.id.tv_title);

        rl_back.setOnClickListener(listener);
        tv_title.setText(getString(R.string.refund_result_title));
    }

    private TextView tv_refund_cost;
    private LinearLayout ll_refund_details;
    private TextView tv_sure;
    private void initContent(){
        String data = getIntent().getStringExtra(IN_PARAM_REFUND_DATA);
        result = GsonUtil.getInstance().fromJson(data, ConfirmRefundResponse.Result.class);

        tv_refund_cost = findViewById(R.id.tv_refund_cost);
        ll_refund_details = findViewById(R.id.ll_refund_details);
        tv_sure = findViewById(R.id.tv_sure);

        tv_refund_cost.setText(result.getTotalAmount()+"");
        tv_sure.setOnClickListener(listener);
        initRefundDetails(ll_refund_details,result.getRefundResult());

        BaseApplication.getInstance().setRecordTimes(result.getRecordtimes());
    }

    private void initRefundDetails(LinearLayout ll_refund_details,List<ConfirmRefundResponse.RefundItem> items){
        View view = null;
        for (ConfirmRefundResponse.RefundItem item:items){
            view = LayoutInflater.from(this).inflate(R.layout.item_refund_result,null);
            ((TextView)view.findViewById(R.id.tv_doctor_cost)).setText("￥"+item.getRefundMoney());
            ((TextView)view.findViewById(R.id.tv_doctor_details)).setText(item.getDoctorDetail());
            ll_refund_details.addView(view);
        }
        view.findViewById(R.id.v_split_line).setVisibility(View.GONE);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.tv_sure:
                    back();
                    break;
            }
        }
    };

    protected void back(){
        finish();
    }

}

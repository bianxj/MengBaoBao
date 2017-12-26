package com.doumengmengandroidbady.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/25.
 */

public class RecordActivity extends BaseActivity {

    private final static int REQUEST_HEIGHT = 0x01;
    private final static int REQUEST_WEIGHT = 0x02;
    private final static int REQUEST_HEAD_CIRCUMFERENCE = 0x03;
    private final static int REQUEST_CHEST_CIRCUMFERENCE = 0x04;
    private final static int REQUEST_CACATION = 0x05;
    private final static int REQUEST_NIGHT_SLEEP = 0x06;
    private final static int REQUEST_DAY_SLEEP = 0x07;
    private final static int REQUEST_BREASTFEEDING = 0x08;
    private final static int REQUEST_MILK = 0x09;
    private final static int REQUEST_MICTURITION = 0x10;

    private RelativeLayout rl_back,rl_complete;
    private TextView tv_title,tv_complete;

    private TextView tv_really_month,tv_correct_month;
    private TextView tv_height,tv_weight,tv_micturition_count,
            tv_head_circumference,tv_chest_circumference,
            tv_cacation_day,tv_cacation_count,
            tv_night_sleep,tv_day_sleep,
            tv_breastfeeding,tv_breastfeeding_count,
            tv_formula_milk,tv_formula_milk_count;

    private EditText et_milk,et_milk_powder,
            et_flour,et_food,et_porridge,et_rice,
            et_meat,et_egg,et_bean,et_vegetables,
            et_fruit,et_water,et_vitamin_product,et_vitamin_dosage,
            et_calcium_product,et_calcium_dosage,
            et_other;

    private RadioGroup rg_appetite;

    private ImageView iv_develop_action;

    private EditText et_parent_message;

    private GridView gv_upload_report;

    private Map<Integer,InputActivityCallBack> requestMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        findView();
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        tv_title = findViewById(R.id.tv_title);
        tv_complete = findViewById(R.id.tv_complete);

        tv_really_month = findViewById(R.id.tv_really_month);
        tv_micturition_count = findViewById(R.id.tv_micturition_count);
        tv_correct_month = findViewById(R.id.tv_correct_month);
        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_head_circumference = findViewById(R.id.tv_head_circumference);
        tv_chest_circumference = findViewById(R.id.tv_chest_circumference);
        tv_cacation_day = findViewById(R.id.tv_cacation_day);
        tv_cacation_count = findViewById(R.id.tv_cacation_count);
        tv_night_sleep = findViewById(R.id.tv_night_sleep);
        tv_day_sleep = findViewById(R.id.tv_day_sleep);
        tv_breastfeeding = findViewById(R.id.tv_breastfeeding);
        tv_breastfeeding_count = findViewById(R.id.tv_breastfeeding_count);
        tv_formula_milk = findViewById(R.id.tv_formula_milk);
        tv_formula_milk_count = findViewById(R.id.tv_formula_milk_count);

        et_milk = findViewById(R.id.et_milk);
        et_milk_powder = findViewById(R.id.et_milk_powder);
        et_flour = findViewById(R.id.et_flour);
        et_food = findViewById(R.id.et_food);
        et_porridge = findViewById(R.id.et_porridge);
        et_rice = findViewById(R.id.et_rice);
        et_meat = findViewById(R.id.et_meat);
        et_egg = findViewById(R.id.et_egg);
        et_bean = findViewById(R.id.et_bean);
        et_vegetables = findViewById(R.id.et_vegetables);
        et_fruit = findViewById(R.id.et_fruit);
        et_water = findViewById(R.id.et_water);
        et_vitamin_product = findViewById(R.id.et_vitamin_product);
        et_vitamin_dosage = findViewById(R.id.et_vitamin_dosage);
        et_calcium_product = findViewById(R.id.et_calcium_product);
        et_calcium_dosage = findViewById(R.id.et_calcium_dosage);
        et_other = findViewById(R.id.et_other);

        rg_appetite = findViewById(R.id.rg_appetite);
        iv_develop_action = findViewById(R.id.iv_develop_action);
        et_parent_message = findViewById(R.id.et_parent_message);
        gv_upload_report = findViewById(R.id.gv_upload_report);
        initRequestMap();
        initView();
    }

    private void initRequestMap(){
        requestMap = new HashMap<>();
        requestMap.put(inputHeightCallBack.requestCode(),inputHeightCallBack);
        requestMap.put(inputBreastFeedingCallBack.requestCode(),inputBreastFeedingCallBack);
        requestMap.put(inputCacationCallBack.requestCode(),inputCacationCallBack);
        requestMap.put(inputChestCircumferenceCallBack.requestCode(),inputChestCircumferenceCallBack);
        requestMap.put(inputWeightCallBack.requestCode(),inputWeightCallBack);
        requestMap.put(inputNightSleepCallBack.requestCode(),inputNightSleepCallBack);
        requestMap.put(inputDaySleepCallBack.requestCode(),inputDaySleepCallBack);
        requestMap.put(inputMilkCallBack.requestCode(),inputMilkCallBack);
        requestMap.put(inputHeadCircumferenceCallBack.requestCode(),inputHeadCircumferenceCallBack);
        requestMap.put(inputMicturitionCallBack.requestCode(),inputMicturitionCallBack);
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        tv_complete.setText(R.string.prompt_bt_submit);
        rl_complete.setOnClickListener(listener);
        rl_complete.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.record);

        tv_height.setOnClickListener(listener);
        tv_weight.setOnClickListener(listener);
        tv_chest_circumference.setOnClickListener(listener);
        tv_head_circumference.setOnClickListener(listener);
        tv_cacation_day.setOnClickListener(listener);
        tv_cacation_count.setOnClickListener(listener);
        tv_night_sleep.setOnClickListener(listener);
        tv_day_sleep.setOnClickListener(listener);
        tv_breastfeeding.setOnClickListener(listener);
        tv_breastfeeding_count.setOnClickListener(listener);
        tv_formula_milk.setOnClickListener(listener);
        tv_formula_milk_count.setOnClickListener(listener);
        tv_micturition_count.setOnClickListener(listener);

        iv_develop_action.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rl_back:
                    back();
                    break;
                case R.id.rl_complete:
                    submit();
                    break;
                case R.id.tv_height:
                    inputHeightCallBack.request();
                    break;
                case R.id.tv_weight:
                    inputWeightCallBack.request();
                    break;
                case R.id.tv_head_circumference:
                    inputHeadCircumferenceCallBack.request();
                    break;
                case R.id.tv_chest_circumference:
                    inputChestCircumferenceCallBack.request();
                    break;
                case R.id.tv_cacation_day:
                case R.id.tv_cacation_count:
                    inputCacationCallBack.request();
                    break;
                case R.id.tv_night_sleep:
                    inputNightSleepCallBack.request();
                    break;
                case R.id.tv_day_sleep:
                    inputDaySleepCallBack.request();
                    break;
                case R.id.tv_breastfeeding:
                case R.id.tv_breastfeeding_count:
                    inputBreastFeedingCallBack.request();
                    break;
                case R.id.tv_formula_milk:
                case R.id.tv_formula_milk_count:
                    inputMilkCallBack.request();
                    break;
                case R.id.tv_micturition_count:
                    inputMicturitionCallBack.request();
                    break;
                case R.id.iv_develop_action:
                    //TODO
                    break;
            }
        }
    };

    private void back(){
        finish();
    }

    private void submit(){
        //TODO
        startActivity(AssessmentActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestMap.containsKey(requestCode) ){
            requestMap.get(requestCode).response(resultCode,data);
        }
    }

    private InputActivityCallBack inputHeightCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_HEIGHT;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputHeightActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputHeightActivity.RESULT_HEIGHT);
                tv_height.setText(value);
            }
        }
    };

    private InputActivityCallBack inputWeightCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_WEIGHT;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputWeightActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputWeightActivity.RESULT_WEIGHT);
                tv_weight.setText(value);
            }
        }
    };

    private InputActivityCallBack inputHeadCircumferenceCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_HEAD_CIRCUMFERENCE;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputHeadActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputHeadActivity.RESULT_HEAD);
                tv_head_circumference.setText(value);
            }
        }
    };

    private InputActivityCallBack inputChestCircumferenceCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_CHEST_CIRCUMFERENCE;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputChestActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputChestActivity.RESULT_CHEST);
                tv_chest_circumference.setText(value);
            }
        }
    };

    private InputActivityCallBack inputCacationCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_CACATION;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputCacationActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String day = data.getStringExtra(InputCacationActivity.RESULT_CACATION_DAY);
                String count = data.getStringExtra(InputCacationActivity.RESULT_CACATION_COUNT);
                tv_cacation_day.setText(day);
                tv_cacation_count.setText(count);
            }
        }
    };

    private InputActivityCallBack inputNightSleepCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_NIGHT_SLEEP;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputNightSleepActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputNightSleepActivity.RESULT_NIGHT_SLEEP);
                tv_night_sleep.setText(value);
            }
        }
    };

    private InputActivityCallBack inputDaySleepCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_DAY_SLEEP;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputDaySleepActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputDaySleepActivity.RESULT_DAY_SLEEP);
                tv_day_sleep.setText(value);
            }
        }
    };

    private InputActivityCallBack inputBreastFeedingCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_BREASTFEEDING;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputBreastFeedingActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String formulaMilk = data.getStringExtra(InputBreastFeedingActivity.RESULT_BREAST_FEEDING);
                String formulaMilkCount = data.getStringExtra(InputBreastFeedingActivity.RESULT_BREAST_FEEDING_COUNT);
                tv_breastfeeding.setText(formulaMilk);
                tv_breastfeeding_count.setText(formulaMilkCount);
            }
        }
    };

    private InputActivityCallBack inputMilkCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_MILK;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputMilkActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String formulaMilk = data.getStringExtra(InputMilkActivity.RESULT_FORMULA_MILK);
                String formulaMilkCount = data.getStringExtra(InputMilkActivity.RESULT_FORMULA_MILK_COUNT);
                tv_formula_milk.setText(formulaMilk);
                tv_formula_milk_count.setText(formulaMilkCount);
            }
        }
    };

    private InputActivityCallBack inputMicturitionCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_MICTURITION;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputMicturitionActivity.class);
            startActivityForResult(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputMicturitionActivity.RESULT_MICTURITION);
                tv_micturition_count.setText(value);
            }
        }
    };

    private interface InputActivityCallBack{
        public int requestCode();
        public void request();
        public void response(int resultCode,Intent data);
    }

}

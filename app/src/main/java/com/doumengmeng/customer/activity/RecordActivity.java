package com.doumengmeng.customer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.adapter.PictureAdapter;
import com.doumengmeng.customer.base.BaseActivity;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.net.HttpUtil;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.request.ResponseErrorCode;
import com.doumengmeng.customer.request.entity.SubmitRecord;
import com.doumengmeng.customer.response.CurrentRecordResponse;
import com.doumengmeng.customer.response.SubmitRecordResponse;
import com.doumengmeng.customer.response.entity.DayList;
import com.doumengmeng.customer.response.entity.Record;
import com.doumengmeng.customer.response.entity.RecordResult;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.AppUtil;
import com.doumengmeng.customer.util.EditTextUtil;
import com.doumengmeng.customer.util.FormulaUtil;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.util.MyDialog;
import com.doumengmeng.customer.util.PermissionUtil;
import com.doumengmeng.customer.util.PictureUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 记录
 * 创建日期: 2018/1/10 13:56
 */
public class RecordActivity extends BaseActivity {

    //--------------------------------------回调CODE------------------------------------------------
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
    private final static int REQUEST_DEVELOPMENTAL_ACTION = 0x11;

    //------------------------------------------控件-----------------------------------------------
    private RelativeLayout rl_back,rl_complete,rl_close;
    private TextView tv_title,tv_complete;
    private ImageView iv_close;

    private RelativeLayout rl_correct_month;
    private TextView tv_really_month,tv_correct_month;
    private TextView tv_height,tv_weight,tv_micturition_count,
            tv_head_circumference,tv_chest_circumference,
            tv_cacation_day,tv_cacation_count,
            tv_night_sleep,tv_day_sleep,
            tv_breastfeeding,tv_breastfeeding_count,
            tv_formula_milk,tv_formula_milk_count;

    private EditText et_milk,
            et_flour,et_food,et_porridge,et_rice,
            et_meat,et_egg,et_bean,et_vegetables,
            et_fruit,et_water,et_vitamin_dosage,
            et_calcium_dosage,
            et_other;

    private final List<EditText> editTexts = new ArrayList<>();

    private RadioGroup rg_appetite;

    private ImageView iv_develop_action;

    private EditText et_parent_message;

    private GridView gv_upload_report;
    private PictureAdapter adapter;

    //-----------------------------------------------------------------------------------------
    private SparseArray<InputActivityCallBack> requestArray;
//    private Map<Integer,InputActivityCallBack> requestMap;
    //用户信息
    private final UserData userData = BaseApplication.getInstance().getUserData();
    //发育行为
    private List<String> developments = new ArrayList<>();
    //月龄信息
    private DayList dayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        findView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(currentRecordTask);
        stopTask(submitRecordTask);
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        rl_complete = findViewById(R.id.rl_complete);
        rl_close = findViewById(R.id.rl_close);
        tv_title = findViewById(R.id.tv_title);
        tv_complete = findViewById(R.id.tv_complete);
        iv_close = findViewById(R.id.iv_close);

        rl_correct_month = findViewById(R.id.rl_correct_month);
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
        et_vitamin_dosage = findViewById(R.id.et_vitamin_dosage);
        et_calcium_dosage = findViewById(R.id.et_calcium_dosage);
        et_other = findViewById(R.id.et_other);

        rg_appetite = findViewById(R.id.rg_appetite);
        iv_develop_action = findViewById(R.id.iv_develop_action);
        et_parent_message = findViewById(R.id.et_parent_message);
        gv_upload_report = findViewById(R.id.gv_upload_report);

        editTexts.add(et_milk);
        editTexts.add(et_flour);
        editTexts.add(et_food);
        editTexts.add(et_porridge);
        editTexts.add(et_rice);
        editTexts.add(et_meat);
        editTexts.add(et_egg);
        editTexts.add(et_bean);
        editTexts.add(et_vegetables);
        editTexts.add(et_fruit);
        editTexts.add(et_water);
        editTexts.add(et_vitamin_dosage);
        editTexts.add(et_calcium_dosage);
        editTexts.add(et_other);
        editTexts.add(et_parent_message);

        initRequestMap();
        initView();
    }

    /**
     * 作者: 边贤君
     * 描述: 初始化各类测量页面回调
     * 日期: 2018/1/10 13:58
     */
    private void initRequestMap(){
        requestArray = new SparseArray<>();
        requestArray.put(inputHeightCallBack.requestCode(),inputHeightCallBack);
        requestArray.put(inputBreastFeedingCallBack.requestCode(),inputBreastFeedingCallBack);
        requestArray.put(inputCacationCallBack.requestCode(),inputCacationCallBack);
        requestArray.put(inputChestCircumferenceCallBack.requestCode(),inputChestCircumferenceCallBack);
        requestArray.put(inputWeightCallBack.requestCode(),inputWeightCallBack);
        requestArray.put(inputNightSleepCallBack.requestCode(),inputNightSleepCallBack);
        requestArray.put(inputDaySleepCallBack.requestCode(),inputDaySleepCallBack);
        requestArray.put(inputMilkCallBack.requestCode(),inputMilkCallBack);
        requestArray.put(inputHeadCircumferenceCallBack.requestCode(),inputHeadCircumferenceCallBack);
        requestArray.put(inputMicturitionCallBack.requestCode(),inputMicturitionCallBack);

//        requestMap = new HashMap<>();
//        requestMap.put(inputHeightCallBack.requestCode(),inputHeightCallBack);
//        requestMap.put(inputBreastFeedingCallBack.requestCode(),inputBreastFeedingCallBack);
//        requestMap.put(inputCacationCallBack.requestCode(),inputCacationCallBack);
//        requestMap.put(inputChestCircumferenceCallBack.requestCode(),inputChestCircumferenceCallBack);
//        requestMap.put(inputWeightCallBack.requestCode(),inputWeightCallBack);
//        requestMap.put(inputNightSleepCallBack.requestCode(),inputNightSleepCallBack);
//        requestMap.put(inputDaySleepCallBack.requestCode(),inputDaySleepCallBack);
//        requestMap.put(inputMilkCallBack.requestCode(),inputMilkCallBack);
//        requestMap.put(inputHeadCircumferenceCallBack.requestCode(),inputHeadCircumferenceCallBack);
//        requestMap.put(inputMicturitionCallBack.requestCode(),inputMicturitionCallBack);
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        tv_complete.setText(R.string.prompt_bt_submit);
        rl_complete.setOnClickListener(listener);
        rl_complete.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.record);

        rl_close.setOnClickListener(listener);
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

        et_parent_message.setOnTouchListener(onTouchListener);

        new EditTextUtil(et_egg);
        new EditTextUtil(et_fruit);

        adapter = new PictureAdapter(this,tackPictureCallBack);
        gv_upload_report.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        dayList = BaseApplication.getInstance().getDayList();
        if ( dayList == null ){
            //TODO 无月龄数据如何处理
            BaseApplication.getInstance().getMLog().error("无月龄数据");
        } else {
            tv_really_month.setText(String.format(getResources().getString(R.string.record_month_age),dayList.getCurrentMonth(),dayList.getCurrentDay()));
            tv_correct_month.setText(String.format(getResources().getString(R.string.record_month_age),dayList.getCorrentMonth(),dayList.getCorrentDay()));
            if ( tv_really_month.getText().toString().trim().equals(tv_correct_month.getText().toString().trim()) ){
                rl_correct_month.setVisibility(View.INVISIBLE);
            }
        }
    }

    private final View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if ((view.getId() == R.id.et_parent_message && canVerticalScroll(et_parent_message))) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
            }
            return false;
        }
    };

    /**
     * EditText竖直方向是否可以滚动
     * @param editText  需要判断的EditText
     * @return  true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( rl_close.getVisibility() == View.GONE ){
            if ( keyCode == KeyEvent.KEYCODE_BACK ){
                back();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
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
                    gotoDevelopment();
                    break;
                case R.id.rl_close:
                    clearPromptTitle();
                    break;
            }
        }
    };

    private void gotoDevelopment(){
        Intent intent = new Intent(RecordActivity.this, DevelopmentalBehaviorActivity.class);
        String development = GsonUtil.getInstance().toJson(developments);
        intent.putExtra(DevelopmentalBehaviorActivity.IN_PARAM_DEVELOPMENT, development);
        intent.putExtra(DevelopmentalBehaviorActivity.IN_PARAM_MONTH_AGE, Integer.parseInt(dayList.getCorrentMonth())<0?"0":dayList.getCorrentMonth());
        intent.putExtra(DevelopmentalBehaviorActivity.IN_PARAM_RECORD_TIME, FormulaUtil.getCurrentTime());
        startActivityForResult(intent, REQUEST_DEVELOPMENTAL_ACTION);
    }

    private void back(){
        Intent intent = new Intent(RecordActivity.this,MainActivity.class);
        intent.putExtra(MainActivity.SHOW_PAGE,MainActivity.PAGE_SPACIALIST_SERVICE);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //测量页面返回
        requestArray.get(requestCode);
        if ( requestArray.get(requestCode) != null ){
            clearEditTextFocus();
            requestArray.get(requestCode).response(resultCode,data);
        }

        //照片返回
        if ( REQUEST_IMAGE == requestCode && Activity.RESULT_OK == resultCode && null != data ) {
            Uri uri = data.getData();
            String target = BaseApplication.getInstance().getUploadPicture();

            PictureUtils.compressPicture(RecordActivity.this,uri,target);
            adapter.addPicture(target);
            setGridViewHeight(gv_upload_report);
        }

        //发育行为返回
        if ( REQUEST_DEVELOPMENTAL_ACTION == requestCode && Activity.RESULT_OK == resultCode && data != null ){
            String result = data.getStringExtra(DevelopmentalBehaviorActivity.OUT_PARAM_DEVELOPMENT);
            developments = GsonUtil.getInstance().fromJson(result,new TypeToken<List<String>>(){}.getType());
        }
    }

    private void startInputDataActivity(Intent intent,int requestCode){
        startActivityForResult(intent,requestCode);
    }

    /**
     * 作者: 边贤君
     * 描述: 测量身高回调
     * 创建日期: 2018/1/10 11:43
     */
    private final InputActivityCallBack inputHeightCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_HEIGHT;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputHeightActivity.class);
            intent.putExtra(InputHeightActivity.IN_PARAM_HEIGHT,tv_height.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputHeightActivity.OUT_PARAM_HEIGHT);
                tv_height.setText(value);
            }
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 测量体重回调
     * 创建日期: 2018/1/10 11:46
     */
    private final InputActivityCallBack inputWeightCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_WEIGHT;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputWeightActivity.class);
            intent.putExtra(InputWeightActivity.IN_PARAM_WEIGHT,tv_weight.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputWeightActivity.OUT_PARAM_WEIGHT);
                tv_weight.setText(value);
            }
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 测量头围回调
     * 创建日期: 2018/1/10 11:47
     */
    private final InputActivityCallBack inputHeadCircumferenceCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_HEAD_CIRCUMFERENCE;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputHeadActivity.class);
            intent.putExtra(InputHeadActivity.IN_PARAM_HEAD,tv_head_circumference.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputHeadActivity.OUT_PARAM_HEAD);
                tv_head_circumference.setText(value);
            }
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 测量胸围回调
     * 创建日期: 2018/1/10 13:20
     */
    private final InputActivityCallBack inputChestCircumferenceCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_CHEST_CIRCUMFERENCE;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputChestActivity.class);
            intent.putExtra(InputChestActivity.IN_PARAM_CHEST,tv_chest_circumference.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputChestActivity.OUT_PARAM_CHEST);
                tv_chest_circumference.setText(value);
            }
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 排便回调
     * 创建日期: 2018/1/10 13:21
     */
    private final InputActivityCallBack inputCacationCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_CACATION;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputCacationActivity.class);
            intent.putExtra(InputCacationActivity.IN_PARAM_CACATION_DAY,tv_cacation_day.getText().toString().trim());
            intent.putExtra(InputCacationActivity.IN_PARAM_CACATION_COUNT,tv_cacation_count.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String day = data.getStringExtra(InputCacationActivity.OUT_PARAM_CACATION_DAY);
                String count = data.getStringExtra(InputCacationActivity.OUT_PARAM_CACATION_COUNT);
                tv_cacation_day.setText(day);
                tv_cacation_count.setText(count);
            }
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 夜间睡眠回调
     * 创建日期: 2018/1/10 13:23
     */
    private final InputActivityCallBack inputNightSleepCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_NIGHT_SLEEP;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputNightSleepActivity.class);
            intent.putExtra(InputNightSleepActivity.IN_PARAM_NIGHT_SLEEP,tv_night_sleep.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputNightSleepActivity.OUT_PARAM_NIGHT_SLEEP);
                tv_night_sleep.setText(value);
            }
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 日间睡眠回调
     * 创建日期: 2018/1/10 13:23
     */
    private final InputActivityCallBack inputDaySleepCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_DAY_SLEEP;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputDaySleepActivity.class);
            intent.putExtra(InputDaySleepActivity.IN_PARAM_DAY_SLEEP,tv_day_sleep.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputDaySleepActivity.OUT_PARAM_DAY_SLEEP);
                tv_day_sleep.setText(value);
            }
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 母乳喂养回调
     * 创建日期: 2018/1/10 13:27
     */
    private final InputActivityCallBack inputBreastFeedingCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_BREASTFEEDING;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputBreastFeedingActivity.class);
            intent.putExtra(InputBreastFeedingActivity.IN_PARAM_BREAST_FEEDING,tv_breastfeeding.getText().toString().trim());
            intent.putExtra(InputBreastFeedingActivity.IN_PARAM_BREAST_FEEDING_COUNT,tv_breastfeeding_count.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String formulaMilk = data.getStringExtra(InputBreastFeedingActivity.OUT_PARAM_BREAST_FEEDING);
                String formulaMilkCount = data.getStringExtra(InputBreastFeedingActivity.OUT_PARAM_BREAST_FEEDING_COUNT);
                tv_breastfeeding.setText(formulaMilk);
                tv_breastfeeding_count.setText(formulaMilkCount);
            }
        }
    };

    /**
     * 作者: 边贤君
     * 描述: 配方奶喂养回调
     * 创建日期: 2018/1/10 13:31
     */
    private final InputActivityCallBack inputMilkCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_MILK;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputMilkActivity.class);
            intent.putExtra(InputMilkActivity.IN_PARAM_FORMULA_MILK,tv_formula_milk.getText().toString().trim());
            intent.putExtra(InputMilkActivity.IN_PARAM_FORMULA_MILK_COUNT,tv_formula_milk_count.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String formulaMilk = data.getStringExtra(InputMilkActivity.OUT_PARAM_FORMULA_MILK);
                String formulaMilkCount = data.getStringExtra(InputMilkActivity.OUT_PARAM_FORMULA_MILK_COUNT);
                tv_formula_milk.setText(formulaMilk);
                tv_formula_milk_count.setText(formulaMilkCount);
            }
        }
    };

    private final InputActivityCallBack inputMicturitionCallBack = new InputActivityCallBack() {
        @Override
        public int requestCode() {
            return REQUEST_MICTURITION;
        }

        @Override
        public void request() {
            Intent intent = new Intent(getBaseContext(),InputMicturitionActivity.class);
            intent.putExtra(InputMicturitionActivity.IN_PARAM_MICTURITION,tv_micturition_count.getText().toString().trim());
            startInputDataActivity(intent,requestCode());
        }

        @Override
        public void response(int resultCode, Intent data) {
            if (Activity.RESULT_OK == resultCode ){
                String value = data.getStringExtra(InputMicturitionActivity.OUT_PARAM_MICTURITION);
                tv_micturition_count.setText(value);
            }
        }
    };

    private interface InputActivityCallBack{
        int requestCode();
        void request();
        void response(int resultCode, Intent data);
    }
    //--------------------------------------调用相册-----------------------------------------------
    private final static int REQUEST_IMAGE = 0x99;
    private final PictureAdapter.TackPictureCallBack tackPictureCallBack = new PictureAdapter.TackPictureCallBack() {
        @Override
        public void tackPicture() {
            RecordActivity.this.tackPicture();
        }
    };

    private void tackPicture(){
        if (PermissionUtil.checkPermissionAndRequest(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Intent intent = new Intent(Intent.ACTION_PICK) ;
            intent.setType("image/*") ;
            startActivityForResult(intent , REQUEST_IMAGE) ;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this,requestCode,permissions,grantResults,new PermissionUtil.RequestPermissionSuccess(){

            @Override
            public void success(String permission) {
                tackPicture();
            }

            @Override
            public void denied(String permission) {

            }

            @Override
            public void alwaysDenied(String permission) {
                String prompt = getResources().getString(R.string.dialog_content_storage_permission);
                MyDialog.showPermissionDialog(RecordActivity.this, prompt, new MyDialog.ChooseDialogCallback() {
                    @Override
                    public void sure() {
                        AppUtil.openPrimession(RecordActivity.this);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        });
    }

    private void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int numColumns= gridview.getNumColumns(); //5
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        gridview.setLayoutParams(params);
    }

    //---------------------------------提交记录------------------------------------------------
    private RequestTask submitRecordTask;
    private void submit(){
        if ( checkData() ) {
            if ( developments.size() > 0 ) {
                submitRecord();
            } else {
                MyDialog.showChooseDialog(this, getString(R.string.dialog_content_development), R.string.dialog_btn_prompt_submit, R.color.first_black,R.string.dialog_btn_prompt_back_choose, R.color.second_pink,  new MyDialog.ChooseDialogCallback() {
                    @Override
                    public void sure() {
                        gotoDevelopment();
                    }

                    @Override
                    public void cancel() {
                        submitRecord();
                    }
                });
            }
        }
    }

    private void submitRecord(){
        try {
            submitRecordTask = new RequestTask.Builder(this,submitRecordCallBack)
                    .setUrl(UrlAddressList.URL_SUBMIT_RECORD)
                    .setType(RequestTask.DEFAULT)
                    .setContent(buildSubmitRecordContent())
                    .build();
            submitRecordTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, String> buildSubmitRecordContent() {
        Map<String,String> map = new HashMap<>();

        SubmitRecord record  = new SubmitRecord(userData.getUserid());
        record.setHeight(tv_height.getText().toString().trim());
        record.setWeight(tv_weight.getText().toString().trim());
        record.setHeadcircumference(tv_head_circumference.getText().toString().trim());
        record.setChestcircumference(tv_chest_circumference.getText().toString().trim());
        record.setCacation(tv_cacation_count.getText().toString().trim());
        record.setCacationdays(tv_cacation_day.getText().toString().trim());
        record.setUrinate(tv_micturition_count.getText().toString().trim());
        record.setNighttimeSleep(tv_night_sleep.getText().toString().trim());
        record.setDaytimesleep(tv_day_sleep.getText().toString().trim());
        record.setBreastfeedingml(tv_breastfeeding.getText().toString().trim());
        record.setBreastfeedingcount(tv_breastfeeding_count.getText().toString().trim());
        record.setMilkfeedingml(tv_formula_milk.getText().toString().trim());
        record.setMilkfeedingcount(tv_formula_milk_count.getText().toString().trim());

        record.setMilk(et_milk.getText().toString().trim());
        record.setRiceflour(et_flour.getText().toString().trim());
        record.setPasta(et_food.getText().toString().trim());
        record.setCongee(et_porridge.getText().toString().trim());
        record.setRice(et_rice.getText().toString().trim());
        record.setMeat(et_meat.getText().toString().trim());
        record.setEggs(et_egg.getText().toString().trim());
        record.setSoyproducts(et_bean.getText().toString().trim());
        record.setVegetables(et_vegetables.getText().toString().trim());
        record.setFruit(et_fruit.getText().toString().trim());
        record.setWater(et_water.getText().toString().trim());
        record.setVitaminaddose(et_vitamin_dosage.getText().toString().trim());
        record.setCalciumdose(et_calcium_dosage.getText().toString().trim());
        record.setOther(et_parent_message.getText().toString().trim());
        record.setOtherfood(et_other.getText().toString().trim());

        if ( -1 != rg_appetite.getCheckedRadioButtonId() ) {
            RadioButton button = findViewById(rg_appetite.getCheckedRadioButtonId());
            record.setOrexia(button.getText().toString().trim());
        } else {
            record.setOrexia("");
        }

        record.setFeature(developments);

        map.put(UrlAddressList.PARAM, GsonUtil.getInstance().toJson(record));
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());

        List<HttpUtil.UploadFile> uploadFiles = new ArrayList<>();
        List<PictureAdapter.UploadPicture> pictures = adapter.getPictures();
        for (PictureAdapter.UploadPicture picture:pictures){
            HttpUtil.UploadFile file = new HttpUtil.UploadFile();
            file.setFilePath(picture.getPicturePath());
            file.setFileName("reportImg");
            uploadFiles.add(file);
        }
        map.put(HttpUtil.TYPE_FILE,GsonUtil.getInstance().toJson(uploadFiles));
        return map;
    }

    private final RequestCallBack submitRecordCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(RecordActivity.this,ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            SubmitRecordResponse response = GsonUtil.getInstance().fromJson(result,SubmitRecordResponse.class);
            if ( 1 == response.getResult().getIsSuccess() ){
                getCurrentRecord();
            } else {
                MyDialog.showPromptDialog(RecordActivity.this,getString(R.string.dialog_content_submit_failed),null);
            }
        }
    };

    private boolean checkData(){
        if (isTextContentEmpty(tv_height)){showPromptTitle("请输入身长信息");return false;}
        if (isTextContentEmpty(tv_weight)){showPromptTitle("请输入体重信息");return false;}
        if (isTextContentEmpty(tv_head_circumference)){showPromptTitle("请输入头围信息");return false;}
        if (isTextContentEmpty(tv_chest_circumference)){showPromptTitle("请输入胸围信息");return false;}
        if (isTextContentEmpty(tv_cacation_day)){showPromptTitle("请输入排便信息");return false;}
        if (isTextContentEmpty(tv_cacation_count)){showPromptTitle("请输入排便信息");return false;}
        if (isTextContentEmpty(tv_micturition_count)){showPromptTitle("请输入排尿信息");return false;}
        if (isTextContentEmpty(tv_night_sleep)){showPromptTitle("请输入夜间睡眠信息");return false;}
        if (isTextContentEmpty(tv_day_sleep)){showPromptTitle("请输入日间睡眠信息");return false;}

        if ( isTextContentEmpty(tv_breastfeeding) && isTextContentEmpty(tv_formula_milk) ){
            showPromptTitle("请填写喂养信息");
            return false;
        }
//        if (isTextContentEmpty(tv_breastfeeding)){showPromptTitle("请输入人乳喂养信息");return false;}
//        if (isTextContentEmpty(tv_breastfeeding_count)){showPromptTitle("请输入人乳喂养信息");return false;}
//        if (isTextContentEmpty(tv_formula_milk)){showPromptTitle("请输入配方奶信息");return false;}
//        if (isTextContentEmpty(tv_formula_milk_count)){showPromptTitle("请输入配方奶信息");return false;}

        float milk = getTextContentToFloat(et_milk);
        if ( 0 > milk || milk > 2000 ){showPromptTitle("牛奶 范围为0~2000");return false;}

        float flour = getTextContentToFloat(et_flour);
        if ( 0 > flour || flour > 5000 ){showPromptTitle("米粉 范围为0~5000");return false;}

        float food = getTextContentToFloat(et_food);
        if ( 0 > food || food > 5000 ){showPromptTitle("面食 范围为0~5000");return false;}

        float porridge = getTextContentToFloat(et_porridge);
        if ( 0 > porridge || porridge > 5000 ){showPromptTitle("粥 范围为0~5000");return false;}

        float rice = getTextContentToFloat(et_rice);
        if ( 0 > rice || rice > 5000 ){showPromptTitle("饭 范围为0~5000");return false;}

        float meat = getTextContentToFloat(et_meat);
        if ( 0 > meat || meat > 5000 ){showPromptTitle("荤菜（鱼肉肝） 范围为0~5000");return false;}

        float bean = getTextContentToFloat(et_bean);
        if ( 0 > bean || bean > 5000 ){showPromptTitle("豆制品 范围为0~5000");return false;}
        float vegetables = getTextContentToFloat(et_vegetables);
        if ( 0 > vegetables || vegetables > 5000 ){showPromptTitle("蔬菜 范围为0~5000");return false;}

        float water = getTextContentToFloat(et_water);
        if ( 0 > water || water > 2000 ){showPromptTitle("水 范围为0~2000");return false;}
        float vitamin_dosage = getTextContentToFloat(et_vitamin_dosage);
        if ( 0 > vitamin_dosage || vitamin_dosage > 2000 ){showPromptTitle("维生素 范围为0~2000");return false;}
        float calcium_dosage = getTextContentToFloat(et_calcium_dosage);
        if ( 0 > calcium_dosage || calcium_dosage > 2000 ){showPromptTitle("钙 范围为0~2000");return false;}
        return true;
    }

    private void clearPromptTitle(){
        tv_title.setText(R.string.record);
        rl_complete.setVisibility(View.VISIBLE);
        rl_back.setVisibility(View.VISIBLE);
        rl_close.setVisibility(View.GONE);
    }

    private void showPromptTitle(String message){
        tv_title.setText(message);
        rl_back.setVisibility(View.GONE);
        rl_complete.setVisibility(View.GONE);
        rl_close.setVisibility(View.VISIBLE);
    }

    private float getTextContentToFloat(TextView textView){
        if ( TextUtils.isEmpty(textView.getText().toString().trim()) ){
            return 0;
        }
        return Float.parseFloat(textView.getText().toString().trim());
    }

    private boolean isTextContentEmpty(TextView textView){
        if ( TextUtils.isEmpty(textView.getText()) ){
            return true;
        }
        return false;
    }

    //-------------------------------获取最新的评估记录--------------------------------------------
    private RequestTask currentRecordTask;

    private void getCurrentRecord(){
        try {
            currentRecordTask = new RequestTask.Builder(this,currentRequestCallBack)
                    .setUrl(UrlAddressList.URL_GET_CURRENT_RECORD)
                    .setType(RequestTask.DEFAULT)
                    .setContent(buildCurrentRecordContent())
                    .build();
            currentRecordTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, String> buildCurrentRecordContent() {
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,userData.getUserid());
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        return map;
    }

    private final RequestCallBack currentRequestCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
            CurrentRecordResponse response = GsonUtil.getInstance().fromJson(result, CurrentRecordResponse.class);
            RecordResult result1 = response.getResult();

            List<Record> list = result1.getRecordList();

            for (Record record : list) {
                record.setImageData(result1.getImgList());
            }

            BaseApplication.getInstance().minusRecordTimes();
            Intent intent = new Intent(RecordActivity.this, AssessmentActivity.class);
            intent.putExtra(AssessmentActivity.IN_PARAM_RECORD, GsonUtil.getInstance().toJson(list.get(0)));
            RecordActivity.this.startActivity(intent);
        }
    };

    //-------------------------------OTHERS--------------------------------------------------------

    private void clearEditTextFocus(){
        for (EditText editText:editTexts){
            editText.clearFocus();
        }
    }



}

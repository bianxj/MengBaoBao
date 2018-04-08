package com.doumengmeng.customer.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.view.MyGifPlayer;
import com.doumengmeng.customer.view.PayItemLayout;

import java.util.List;

/**
 * 作者: 边贤君
 * 描述: Dialog工具类
 * 创建日期: 2018/1/17 15:03
 */
public class MyDialog {

    private final static Object updateLock = new Object();
    private final static Object promptLock = new Object();
    private final static Object chooseLock = new Object();
    private final static Object loadingLock = new Object();
    private final static Object pictureLock = new Object();
    private final static Object notificationLock = new Object();
    private final static Object gifLock = new Object();
    private final static Object cityLock = new Object();
    private final static Object payLock = new Object();

    private static Dialog updateDialog;
    private static Dialog promptDialog;
    private static Dialog chooseDialog;
    private static Dialog loadingDialog;
    private static Dialog pictureDialog;
    private static Dialog notificationDialog;
    private static Dialog gifDialog;

    private static PopupWindow payDialog;
    private static PopupWindow cityDialog;

    public static void showPermissionDialog(Context context, String content , ChooseDialogCallback callback){
        showChooseDialog(context,content,R.string.dialog_btn_prompt_later,R.string.dialog_btn_prompt_go_setting,callback);
    }

    public static void showUpdateDialog(Context context,boolean isForce,String updateVersion,String content,final UpdateDialogCallback callback){
        synchronized (updateLock){
            if ( updateDialog != null ){
                updateDialog.dismiss();
                updateDialog = null;
            }
            updateDialog = new Dialog(context,R.style.MyDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_update,null);
            RelativeLayout rl_close = view.findViewById(R.id.rl_close);
            ImageView iv_update = view.findViewById(R.id.iv_update);
            TextView tv_update_content = view.findViewById(R.id.tv_update_content);
            tv_update_content.setMovementMethod(new ScrollingMovementMethod());
            TextView tv_use_percent = view.findViewById(R.id.tv_use_percent);
            TextView tv_update_version = view.findViewById(R.id.tv_update_version);

            tv_update_version.setText(updateVersion);
            tv_update_content.setText(content);
            if ( isForce ){
                rl_close.setVisibility(View.GONE);
            }

            rl_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissUpdateDialog();
                    if ( callback != null ) {
                        callback.cancel();
                    }
                }
            });
            iv_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( callback != null ) {
                        callback.update();
                    }
                }
            });

            updateDialog.setCancelable(false);
            updateDialog.setCanceledOnTouchOutside(false);
            updateDialog.setContentView(view);
            updateDialog.show();
        }
    }

    private static void dismissUpdateDialog(){
        synchronized (updateLock){
            if ( updateDialog != null ){
                updateDialog.dismiss();
                updateDialog = null;
            }
        }
    }

    public static void showPromptDialog(Context context, String content, final PromptDialogCallback callback){
        showPromptDialog(context,content,R.string.prompt_bt_sure,callback);
    }

    public static void showPromptDialog(Context context, String content, int sure, final PromptDialogCallback callback){
        synchronized (promptLock){
            if ( promptDialog != null ){
                promptDialog.dismiss();
                promptDialog = null;
            }
            promptDialog = new Dialog(context,R.style.MyDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_prompt,null);

            TextView tv_prompt = view.findViewById(R.id.tv_prompt);
            TextView tv_sure = view.findViewById(R.id.tv_sure);
            tv_sure.setText(sure);

            tv_prompt.setText(content);
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissPromptDialog();
                    if ( callback != null ){
                        callback.sure();
                    }
                }
            });
            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setContentView(view);
            promptDialog.show();
        }
    }

    private static void dismissPromptDialog(){
        synchronized (promptLock){
            if ( promptDialog != null ){
                promptDialog.dismiss();
                promptDialog = null;
            }
        }
    }

    public static void showChooseDialog(Context context,String content,ChooseDialogCallback callback){
        showChooseDialog(context,content,R.string.prompt_bt_cancel,R.string.prompt_bt_sure,callback);
    }

//    public static void showPermissionDialog(Context context,String content,ChooseDialogCallback callback){
//        showChooseDialog(context,content,R.string.dialog_btn_prompt_later,R.string.dialog_btn_prompt_go_setting,callback);
//    }

    public static void showChooseDialog(Context context,String content,int cancel,int sure,final ChooseDialogCallback callback){
        synchronized (chooseLock){
            if ( chooseDialog != null ){
                chooseDialog.dismiss();
                chooseDialog = null;
            }
            chooseDialog = new Dialog(context,R.style.MyDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_choose,null);

            TextView tv_prompt = view.findViewById(R.id.tv_prompt);
            TextView tv_sure = view.findViewById(R.id.tv_sure);
            tv_sure.setText(sure);
            TextView tv_cancel = view.findViewById(R.id.tv_cancel);
            tv_cancel.setText(cancel);

            tv_prompt.setText(content);
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissChooseDialog();
                    if ( callback != null ){
                        callback.sure();
                    }
                }
            });
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissChooseDialog();
                    if ( callback != null ){
                        callback.cancel();
                    }
                }
            });
            chooseDialog.setCancelable(false);
            chooseDialog.setCanceledOnTouchOutside(false);
            chooseDialog.setContentView(view);
            chooseDialog.show();
        }
    }

    public static void showChooseCityDialog(final Context context,View view,final ChooseCityCallback callback){
        synchronized (cityLock) {
            if ( cityDialog != null && cityDialog.isShowing() ) {
                cityDialog.dismiss();
                cityDialog = null;
            }
            final String[] citys = context.getResources().getStringArray(R.array.citys);
            cityDialog = new PopupWindow(context);
            cityDialog.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            cityDialog.setWidth(context.getResources().getDimensionPixelSize(R.dimen.x720px));
            cityDialog.setBackgroundDrawable(null);
            View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_choose_city, null);
            ListView lv_city = contentView.findViewById(R.id.lv_city);
            contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cityDialog.setContentView(contentView);
            cityDialog.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            lv_city.setAdapter(new CityAdapter(context, citys));
            lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    callback.choose(citys[i]);
                }
            });
            if (Build.VERSION.SDK_INT < 24) {
                cityDialog.showAsDropDown(view);
            } else {
                if ( "SMARTISAN".equals(AppUtil.getSystemOsName()) ){
                    cityDialog.showAsDropDown(view);
                } else {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    cityDialog.showAtLocation(view, Gravity.NO_GRAVITY, 0, y + view.getHeight());
                }
            }
        }
    }

    public static void showPayDialog(final Context context, View parent, final PayCallBack callBack, List<PayItemLayout.PayData> datas, String price, int timeOut){
        synchronized (payLock){
            if ( payDialog != null && payDialog.isShowing() ) {
                payDialog.dismiss();
                payDialog = null;
            }

            payDialog = new PopupWindow(context);
            payDialog.setWidth(context.getResources().getDimensionPixelSize(R.dimen.x720px));


            payDialog.setBackgroundDrawable(new BitmapDrawable());
            payDialog.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            payDialog.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_pay, null);
            contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            payDialog.setContentView(contentView);
            payDialog.setAnimationStyle(R.style.PayAnimation);

            RelativeLayout rl_close = contentView.findViewById(R.id.rl_close);
            /*ImageView iv_close = contentView.findViewById(R.id.iv_close);*/
            rl_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissPayDialog();
                }
            });

            final TextView tv_time = contentView.findViewById(R.id.tv_time);
            tv_time.setText(String.format(context.getString(R.string.dialog_minute_second),timeOut / 60,timeOut % 60));
            tv_time.setTag(timeOut);

            TextView tv_price = contentView.findViewById(R.id.tv_price);
            tv_price.setText(String.format(context.getResources().getString(R.string.dialog_price),price));

            final PayItemLayout pay_layout = contentView.findViewById(R.id.pay_layout);
            pay_layout.addItems(datas);
//            ListView lv = contentView.findViewById(R.id.lv);
//            final PayAdapter adapter = new PayAdapter(datas);
//            lv.setAdapter(adapter);
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    adapter.setClickPosition(i);
//                }
//            });

            TextView tv_pay = contentView.findViewById(R.id.tv_pay);
            tv_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int chooseId = pay_layout.getSelectPostion();
                    if ( chooseId != -1 ){
                        if ( chooseId == 0 ){
                            callBack.alipay();
                        } else {
                            callBack.iwxpay();
                        }
                        dismissPayDialog();
                    } else {
                        Toast.makeText(context,"请选择支付渠道",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            payDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
//                    tv_time.removeCallbacks()
                }
            });
            payDialog.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            payDialog.showAtLocation(parent,Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
            calcuteTimeOut(tv_time);
        }
    }

    private static void calcuteTimeOut(final TextView tv){
        tv.postDelayed(new Runnable() {
            @Override
            public void run() {
                int time = (int) tv.getTag();
                time--;
                if ( time <= 0 ){
                    dismissPayDialog();
                } else {
                    tv.setText(String.format(tv.getContext().getString(R.string.dialog_minute_second),time / 60,time % 60));
                    tv.setTag(time);
                    calcuteTimeOut(tv);
                }
            }
        },1000);
    }

    private static void dismissPayDialog(){
        synchronized (payLock){
            if ( payDialog != null && payDialog.isShowing() ) {
                payDialog.dismiss();
                payDialog = null;
            }
        }
    }

    public static void showPictureDialog(Context context, Bitmap bitmap){
        synchronized (pictureLock){
            pictureDialog = new Dialog(context,R.style.MyDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_picture,null);
            ImageView iv_picture = view.findViewById(R.id.iv_picture);
            iv_picture.setImageBitmap(bitmap);
            pictureDialog.setContentView(view);
            pictureDialog.show();
        }
    }

    public static void showGifDialog(Context context,String url){
        synchronized (gifLock){
            gifDialog = new Dialog(context,R.style.MyDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_gif,null);
            MyGifPlayer player = view.findViewById(R.id.player);
            gifDialog.setContentView(view);
            gifDialog.show();
            player.setGif(url, new MyGifPlayer.StopCallBack() {
                @Override
                public void stoped() {
                    gifDialog.dismiss();
                }
            });
        }
    }

    public static void showNotificationDialog(Context context, final NotificationCallback callback){
        synchronized (notificationLock){
            notificationDialog = new Dialog(context,R.style.MyDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_notification,null);

            ImageView iv_close = view.findViewById(R.id.iv_close);
            Button bt_sure = view.findViewById(R.id.bt_sure);

            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissNotificationDialog();
                    if ( callback != null ){
                        callback.cancel();
                    }
                }
            });

            bt_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissNotificationDialog();
                    if ( callback != null ){
                        callback.sure();
                    }
                }
            });

            notificationDialog.setCancelable(false);
            notificationDialog.setCanceledOnTouchOutside(false);
            notificationDialog.setContentView(view);
            notificationDialog.show();
        }
    }

    private static void dismissNotificationDialog(){
        synchronized (notificationLock){
            if ( notificationDialog != null){
                notificationDialog.dismiss();
                notificationDialog = null;
            }
        }
    }

    public static boolean isShowingChooseCityDialog(){
        synchronized (cityLock) {
            return cityDialog != null && cityDialog.isShowing();
        }
    }

    public static void dismissChooseCityDialog(){
        synchronized (cityLock){
            if ( cityDialog != null && cityDialog.isShowing() ) {
                cityDialog.dismiss();
                cityDialog = null;
            }
        }
    }

    private static void dismissPictureDialog(){
        synchronized (pictureLock){
            if ( pictureDialog != null && pictureDialog.isShowing() ){
                pictureDialog.dismiss();
                pictureDialog = null;
            }
        }
    }

    private static class CityAdapter extends BaseAdapter{

        private final Context context;
        private final String[] citys;

        public CityAdapter(Context context,String[] citys) {
            this.context = context;
            this.citys = citys;
        }

        @Override
        public int getCount() {
            return citys.length;
        }

        @Override
        public Object getItem(int i) {
            return citys[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if ( view == null ){
                view = LayoutInflater.from(context).inflate(R.layout.item_choose_city,null);
            }
            TextView tv_city_name = view.findViewById(R.id.tv_city_name);
            tv_city_name.setText(citys[i]);
            return view;
        }
    }

    private static void dismissChooseDialog(){
        synchronized (chooseLock){
            if ( chooseDialog != null ){
                chooseDialog.dismiss();
                chooseDialog = null;
            }
        }
    }

    public static void showLoadingDialog(Context context){
        synchronized (loadingLock){
            loadingDialog = new Dialog(context,R.style.LoadingDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading,null);

            RotateAnimation animation = new RotateAnimation(0,36000,RotateAnimation.RELATIVE_TO_SELF,0.5F,RotateAnimation.RELATIVE_TO_SELF,0.5F);
//            Animation animation = AnimationUtils.loadAnimation(context,R.anim.loading_rotate);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(100000);
            ImageView loading = view.findViewById(R.id.loading);
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setContentView(view);
            loadingDialog.show();
            loading.startAnimation(animation);
        }
    }

    public static void dismissLoadingDialog(){
        synchronized (loadingLock){
            if ( loadingDialog != null ){
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        }
    }

    public interface UpdateDialogCallback{
        void update();
        void cancel();
    }

    public interface PromptDialogCallback{
        void sure();
    }

    public interface ChooseDialogCallback{
        void sure();
        void cancel();
    }

    public interface ChooseCityCallback{
        void choose(String city);
    }

    public interface NotificationCallback{
        void sure();
        void cancel();
    }

    public interface PayCallBack{
        //支付宝
        void alipay();
        //微信
        void iwxpay();
    }

}

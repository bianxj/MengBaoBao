package com.doumengmengandroidbady.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;

/**
 * Created by Administrator on 2017/12/14.
 */
public class MyDialog {

    private static Object updateLock = new Object();
    private static Object promptLock = new Object();
    private static Object chooseLock = new Object();
    private static Object loadingLock = new Object();
    private static Object notificationLock = new Object();
    private static Object cityLock = new Object();
    private static Dialog updateDialog;
    private static Dialog promptDialog;
    private static Dialog chooseDialog;
    private static Dialog loadingDialog;
    private static Dialog notificationDialog;
    private static PopupWindow cityDialog;

    public static void showUpdateDialog(Context context,boolean isForce,String content,final UpdateDialogCallback callback){
        synchronized (updateLock){
            dismissUpdateDialog();
            updateDialog = new Dialog(context,R.style.MyDialog);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_update,null);
            RelativeLayout rl_close = view.findViewById(R.id.rl_close);
            ImageView iv_update = view.findViewById(R.id.iv_update);
            TextView tv_update_content = view.findViewById(R.id.tv_update_content);
            TextView tv_use_percent = view.findViewById(R.id.tv_use_percent);
            TextView tv_update_version = view.findViewById(R.id.tv_update_version);

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

    public static void dismissUpdateDialog(){
        synchronized (updateLock){
            if ( updateDialog != null ){
                updateDialog.dismiss();
                updateDialog = null;
            }
        }
    }

    public static void showPromptDialog(Context context, String content, final PromptDialogCallback callback){
        showPromptDialog(context,content,R.string.go_on,callback);
    }

    public static void showPromptDialog(Context context, String content, int sure, final PromptDialogCallback callback){
        synchronized (promptLock){
            dismissPromptDialog();
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

    public static void showChooseDialog(Context context,String content,int cancel,int sure,final ChooseDialogCallback callback){
        synchronized (chooseLock){
            dismissChooseDialog();
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
            dismissChooseCityDialog();
            final String[] citys = context.getResources().getStringArray(R.array.citys);
            cityDialog = new PopupWindow(context);
            cityDialog.setWidth(context.getResources().getDimensionPixelSize(R.dimen.x720px));
            cityDialog.setBackgroundDrawable(null);
            View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_choose_city, null);
            ListView lv_city = contentView.findViewById(R.id.lv_city);
            contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cityDialog.setContentView(contentView);

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
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                cityDialog.showAtLocation(view, Gravity.NO_GRAVITY, 0, y + view.getHeight());
            }
        }
    }

    private static void showNotificationDialog(Context context, final NotificationCallback callback){
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
        synchronized (cityLock){
            if ( cityDialog != null ){
                return cityDialog.isShowing();
            }
            return false;
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

    private static class CityAdapter extends BaseAdapter{

        private Context context;
        private String[] citys;

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
        public void update();
        public void cancel();
    }

    public interface PromptDialogCallback{
        public void sure();
    }

    public interface ChooseDialogCallback{
        public void sure();
        public void cancel();
    }

    public interface ChooseCityCallback{
        public void choose(String city);
    }

    public interface NotificationCallback{
        public void sure();
        public void cancel();
    }

}

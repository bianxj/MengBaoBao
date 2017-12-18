package com.doumengmengandroidbady.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
    private static Dialog updateDialog;
    private static Dialog promptDialog;
    private static Dialog chooseDialog;
    private static Dialog loadingDialog;

    public static void showUpdateDialog(Context context,boolean isForce,String content,final UpdateDialogCallback callback){
        synchronized (updateLock){
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

    private static void dismissChooseDialog(){
        synchronized (chooseLock){
            if ( chooseDialog != null ){
                chooseDialog.dismiss();
                chooseDialog = null;
            }
        }
    }

    public void showLoadingDialog(){
        synchronized (loadingLock){

        }
    }

    public void dismissLoadingDialog(){
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

}

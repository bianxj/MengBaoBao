package com.doumengmeng.doctor.request;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.doumengmeng.doctor.activity.LoadingActivity;
import com.doumengmeng.doctor.net.HttpUtil;
import com.doumengmeng.doctor.util.MyDialog;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 */

public class RequestTask extends AsyncTask<String,Void,String> {
    public final static int JSON = 0x001;
    public final static int FILE = 0x002;
    public final static int PROMPT = 0x010;
    public final static int LOADING = 0x100;

    public final static int DEFAULT = JSON | PROMPT | LOADING;
    public final static int NO_PROMPT = JSON | LOADING;
    public final static int NO_LOADING = JSON | PROMPT;
    public final static int NO_JSON = LOADING | PROMPT;

    private final static boolean isTest = false;

    private final Builder builder;
    private Handler handler;
    private Runnable runnable;

    private RequestTask(Builder builder) {
        this.builder = builder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if ( isSelectFlag(builder.getType(),LOADING) ) {
//            handler = new Handler();
//            runnable = new Runnable() {
//                @Override
//                public void run() {
                    MyDialog.showLoadingDialog(builder.getWeakReference().get());
//                }
//            };
//            handler.postDelayed(runnable, 2000);
        }
        builder.getCallBack().onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        if ( -1 != builder.getDelay() ){
            try {
                Thread.sleep(builder.getDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String result = null;
        String url = builder.getUrl();
        if ( isSelectFlag(builder.getType(),FILE) ){
            result = HttpUtil.getInstance().httpsRequestFile(url);
        } else {
            Map<String, String> map = builder.getContent();
            result = HttpUtil.getInstance().httpsRequestPost(url, map);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if ( handler != null ){
            handler.removeCallbacks(runnable);
        }
        if ( isSelectFlag(builder.getType(),LOADING) ) {
            MyDialog.dismissLoadingDialog();
        }
        if ( isTest ){
            builder.getCallBack().onPostExecute(s);
        } else {
            if ( !isSelectFlag(builder.getType(),JSON) ){
                builder.getCallBack().onPostExecute(s);
            } else {
                if (!isError(s)) {
                    builder.getCallBack().onPostExecute(s);
                } else {
                    if ( isLoginTimeOut(s) ){
                        skipToLoading();
                    } else {
                        builder.getCallBack().onError(s);
                    }
                }
            }
        }
    }

    private void skipToLoading(){
        Context context = builder.getWeakReference().get();
        Intent obj = new Intent(context, LoadingActivity.class);
        obj.putExtra(LoadingActivity.IN_PARAM_AUTO_LOGIN,true);
        context.startActivity(obj);
    }

    private boolean isLoginTimeOut(String json){
        return ResponseErrorCode.ERROR_LOGIN_ROLE_EXIST == ResponseErrorCode.getErrorCode(json);
    }

    private boolean isError(String json){
        String errorMsg;
        errorMsg = ResponseErrorCode.getErrorMsg(json);
        if ( null == errorMsg ){
            return false;
        } else {
            if ( !isLoginTimeOut(json) ) {
                if (isSelectFlag(builder.getType(), PROMPT)) {
                    errorDispose(errorMsg);
                }
            }
            return true;
        }
    }

    private void errorDispose(String errorMsg){
        Context context = builder.getWeakReference().get();
        if ( null != context ) {
            MyDialog.showPromptDialog(builder.getWeakReference().get(), errorMsg, null);
        }
    }

    private boolean isSelectFlag(int value,int flag){
        return (flag & value) == flag;
    }

    public static class Builder{

        private String url;
        private Map<String,String> content;
        private int type;
        private WeakReference<Context> weakReference;
        private RequestCallBack callBack;
        private int delay = -1;

        public Builder(Context context,@NonNull RequestCallBack callBack) {
            this.callBack = callBack;
            this.weakReference = new WeakReference<>(context);
        }

//        public void setCallBack(@NonNull RequestCallBack callBack) {
//            this.callBack = callBack;
//        }

        private RequestCallBack getCallBack() {
            return callBack;
        }

        public WeakReference<Context> getWeakReference() {
            return weakReference;
        }

        public void setWeakReference(Context context) {
            this.weakReference = new WeakReference<>(context);
        }

        public String getUrl() {
            return url;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Map<String, String> getContent() {
            return content;
        }

        public Builder setContent(Map<String, String> content) {
            this.content = content;
            return this;
        }

        public int getType() {
            return type;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public int getDelay() {
            return delay;
        }

        public Builder setDelay(int delay) {
            this.delay = delay;
            return this;
        }

        public RequestTask build() throws Throwable {
            if ( null == callBack ){
                throw new Throwable("Need Set RequestCallBack");
            }
            return new RequestTask(this);
        }

    }

}

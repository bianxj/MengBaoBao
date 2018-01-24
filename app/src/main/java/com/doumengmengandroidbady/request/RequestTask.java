package com.doumengmengandroidbady.request;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.doumengmengandroidbady.net.HttpUtil;
import com.doumengmengandroidbady.util.MyDialog;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 */

public class RequestTask extends AsyncTask<String,Void,String> {

    private final static boolean isTest = false;

    private Builder builder;
    private Handler handler;
    private Runnable runnable;

    private RequestTask(Builder builder) {
        this.builder = builder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if ( null != builder.getCallBack() ){
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    MyDialog.showLoadingDialog(builder.getWeakReference().get());
                }
            };
            handler.postDelayed(runnable,2000);
            builder.getCallBack().onPreExecute();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        if (!isTest) {
            String url = builder.getCallBack().getUrl();
            Map<String,String> map = builder.getCallBack().getContent();
            result = HttpUtil.getInstance().httpsRequestPost(url,map);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if ( handler != null ){
            handler.removeCallbacks(runnable);
        }
        MyDialog.dismissLoadingDialog();
        if ( isTest ){
            builder.getCallBack().onPostExecute(s);
        } else {
            if ( RequestCallBack.NOT_JSON == builder.getCallBack().type() ){
                builder.getCallBack().onPostExecute(s);
            } else {
                if (!isError(s)) {
                    builder.getCallBack().onPostExecute(s);
                } else {
                    builder.getCallBack().onError(s);
                }
            }
        }
    }



    private boolean isError(String json){
        String errorMsg = null;
        errorMsg = ResponseErrorCode.getErrorMsg(json);
        if ( null == errorMsg ){
            return false;
        } else {
            if ( RequestCallBack.JSON == builder.getCallBack().type() ) {
                errorDispose(errorMsg);
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

    public static class Builder{

        private WeakReference<Context> weakReference;
        private RequestCallBack callBack;

        public Builder(Context context,RequestCallBack callBack) {
            this.callBack = callBack;
            this.weakReference = new WeakReference<Context>(context);
        }

        public void setCallBack(RequestCallBack callBack) {
            this.callBack = callBack;
        }

        private RequestCallBack getCallBack() {
            return callBack;
        }

        public WeakReference<Context> getWeakReference() {
            return weakReference;
        }

        public void setWeakReference(Context context) {
            this.weakReference = new WeakReference<Context>(context);
        }

        public RequestTask build() throws Throwable {
            if ( null == callBack ){
                throw new Throwable("Need Set RequestCallBack");
            }
            return new RequestTask(this);
        }

    }

}

package com.doumengmengandroidbady.request;

import android.content.Context;
import android.os.AsyncTask;

import com.doumengmengandroidbady.net.HttpUtil;
import com.doumengmengandroidbady.util.MyDialog;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 */

public class RequestTask extends AsyncTask<String,Void,String> {

    private final static boolean isTest = false;

    private Builder builder;

    private RequestTask(Builder builder) {
        this.builder = builder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if ( null != builder.getCallBack() ){
            MyDialog.showLoadingDialog(builder.getCallBack().getContext());
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
        if ( isTest ){
            builder.getCallBack().onPostExecute(s);
        } else {
            MyDialog.dismissLoadingDialog();
            if (!isError(s)) {
                builder.getCallBack().onPostExecute(s);
            } else {
                builder.getCallBack().onError(s);
            }
        }
    }

    private boolean isError(String json){
        String errorMsg = null;

        if ( null == json ){
            errorMsg = "数据为空";
        } else {
            int errorCode = ResponseErrorCode.getErrorCode(json);
            errorMsg = ResponseErrorCode.getErrorMsg(errorCode);
        }
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
        Context context = builder.getCallBack().getContext();
        if ( null != context ) {
            MyDialog.showPromptDialog(builder.getCallBack().getContext(), errorMsg, null);
        }
    }

    public static class Builder{

        private RequestCallBack callBack;

        public Builder(RequestCallBack callBack) {
            this.callBack = callBack;
        }

        public void setCallBack(RequestCallBack callBack) {
            this.callBack = callBack;
        }

        private RequestCallBack getCallBack() {
            return callBack;
        }

        public RequestTask build() throws Throwable {
            if ( null == callBack ){
                throw new Throwable("Need Set RequestCallBack");
            }
            return new RequestTask(this);
        }

    }

}

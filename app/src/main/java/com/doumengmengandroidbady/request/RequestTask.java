package com.doumengmengandroidbady.request;

import android.content.Context;
import android.os.AsyncTask;

import com.doumengmengandroidbady.config.Config;
import com.doumengmengandroidbady.net.HttpUtil;
import com.doumengmengandroidbady.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/15.
 */

public class RequestTask extends AsyncTask<String,Void,String> {

    private Builder builder;

    private RequestTask(Builder builder) {
        this.builder = builder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if ( null != builder.getCallBack() ){
            builder.getCallBack().onPreExecute();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        if (!Config.isTest) {
            String url = builder.getCallBack().getUrl();
            result = HttpUtil.getInstance().httpsRequestPost(url);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if ( Config.isTest ){
            builder.getCallBack().onPostExecute(s);
        } else {
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
            try {
                JSONObject object = new JSONObject(json);
                int errorCode = object.getInt("errorId");
                errorMsg = ResponseErrorCode.getErrorMsg(errorCode);
            } catch (JSONException e) {
                e.printStackTrace();
                errorMsg = "数据解析出错";
            }
        }
        if ( null == errorMsg ){
            return false;
        } else {
            errorDispose(errorMsg);
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

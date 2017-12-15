package com.doumengmengandroidbady.request;

import android.os.AsyncTask;

import com.doumengmengandroidbady.net.HttpUtil;

/**
 * Created by Administrator on 2017/12/15.
 */

public class BaseAsyncTask extends AsyncTask<String,Void,String> {

    private RequestCallBack callBack;

    public BaseAsyncTask(RequestCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if ( null != callBack ){
            callBack.onPreExecute();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
//        if (!Config.isTest) {
            String url = callBack.getUrl();
            result = HttpUtil.getInstance().httpsRequestPost(url);
            callBack.disposeResponseInThread(result);
//        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if ( null != callBack ){
            callBack.onPostExecute(s);
        }
    }
}

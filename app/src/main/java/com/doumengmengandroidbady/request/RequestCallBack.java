package com.doumengmengandroidbady.request;

/**
 * Created by Administrator on 2017/12/15.
 */

public interface RequestCallBack {

    public void onPreExecute();
    public String getUrl();
    public void disposeResponseInThread(String result);
    public void onPostExecute(String result);

}

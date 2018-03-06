package com.doumengmeng.doctor.request;

/**
 * Created by Administrator on 2017/12/15.
 */

public interface RequestCallBack {
    void onPreExecute();

//    String getUrl();

    //    public WeakReference<Context> getContext();
//    Map<String, String> getContent();

    void onError(String result);

    void onPostExecute(String result);

//    int type();

}

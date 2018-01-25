package com.doumengmengandroidbady.request;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 */

public interface RequestCallBack {

    int JSON = 0x001;
    int PROMPT = 0x010;
    int LOADING = 0x100;

    int DEFAULT = JSON|PROMPT|LOADING;
    int NO_PROMPT = JSON|LOADING;
    int NO_LOADING = JSON|PROMPT;
    int NO_JSON = LOADING|PROMPT;

    void onPreExecute();
    String getUrl();
//    public WeakReference<Context> getContext();
Map<String,String> getContent();
    void onError(String result);
    void onPostExecute(String result);
    int type();

}

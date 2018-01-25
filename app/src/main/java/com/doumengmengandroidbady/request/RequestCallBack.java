package com.doumengmengandroidbady.request;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 */

public interface RequestCallBack {

    public final static int JSON = 0x001;
    public final static int PROMPT = 0x010;
    public final static int LOADING = 0x100;

    public final static int DEFAULT = JSON|PROMPT|LOADING;
    public final static int NO_PROMPT = JSON|LOADING;
    public final static int NO_LOADING = JSON|PROMPT;
    public final static int NO_JSON = LOADING|PROMPT;

    public void onPreExecute();
    public String getUrl();
//    public WeakReference<Context> getContext();
    public Map<String,String> getContent();
    public void onError(String result);
    public void onPostExecute(String result);
    public int type();

}

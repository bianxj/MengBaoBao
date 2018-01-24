package com.doumengmengandroidbady.request;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/15.
 */

public interface RequestCallBack {

    public final static String JSON = "json";
    public final static String JSON_NO_PROMPT = "json_no_prompt";
    public final static String NOT_JSON = "not_json";

    public void onPreExecute();
    public String getUrl();
//    public WeakReference<Context> getContext();
    public Map<String,String> getContent();
    public void onError(String result);
    public void onPostExecute(String result);
    public String type();

}

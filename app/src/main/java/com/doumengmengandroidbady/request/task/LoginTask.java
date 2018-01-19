package com.doumengmengandroidbady.request.task;

import android.content.Context;

import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.request.ResponseErrorCode;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/5.
 */

public class LoginTask {

    private RequestTask task;
    private LoginCallBack loginCallBack;
    private String accountMobile;
    private String loginPwd;

    public LoginTask(Context context,String accountMobile, String loginPwd , LoginCallBack loginCallBack) throws Throwable {
        this.accountMobile = accountMobile;
        this.loginPwd = loginPwd;
        this.loginCallBack = loginCallBack;
        task = new RequestTask.Builder(context,callBack).build();
    }

    public void execute(){
        task.execute();
    }

    public RequestTask getTask(){
        return task;
    }

    private RequestCallBack callBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            if ( loginCallBack != null ){
                loginCallBack.onPreExecute();
            }
        }

        @Override
        public String getUrl() {
            return UrlAddressList.URL_LOGIN;
        }


        @Override
        public Map<String, String> getContent() {
            JSONObject object = new JSONObject();
            try {
                object.put("accountMobile",accountMobile);
                object.put("loginPwd",loginPwd);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Map<String,String> map = new HashMap<>();
            map.put(UrlAddressList.PARAM,object.toString());
            return map;
        }

        @Override
        public void onError(String result) {
            if ( loginCallBack != null ){
                loginCallBack.onError(result);
            }
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                String sessionId = res.getString("SessionId");
                int isAbnormalExit = res.getInt("isAbnormalExit");
                BaseApplication.getInstance().saveAbnormalExit(isAbnormalExit != 0);

                JSONObject user = res.getJSONObject("User");
                UserData userData = GsonUtil.getInstance().getGson().fromJson(user.toString(),UserData.class);
                userData.setSessionId(sessionId);
                BaseApplication.getInstance().saveUserData(userData);
                if ( loginCallBack != null ){
                    loginCallBack.onPostExecute(result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if ( loginCallBack != null ){
                    loginCallBack.onError(ResponseErrorCode.getErrorMsg(ResponseErrorCode.ERROR_ANALYSIS_FAILED));
                }
            }
        }

        @Override
        public String type() {
            return JSON_NO_PROMPT;
        }
    };

    public interface LoginCallBack{
        public void onPreExecute();
        public void onError(String result);
        public void onPostExecute(String result);
    }

}

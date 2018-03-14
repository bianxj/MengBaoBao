package com.doumengmeng.doctor.request.task;

import android.content.Context;

import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.response.LoginResponse;
import com.doumengmeng.doctor.util.GsonUtil;
import com.umeng.commonsdk.utils.UMUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;


public class LoginTask {

    private final WeakReference<Context> weakReference;
    private RequestTask task;
    private final LoginCallBack loginCallBack;
    private final String accountMobile;
    private final String loginPwd;
    private int type = RequestTask.NO_PROMPT;

    public LoginTask(Context context, String accountMobile, String loginPwd , LoginCallBack loginCallBack, int type){
        this.accountMobile = accountMobile;
        this.loginPwd = loginPwd;
        this.loginCallBack = loginCallBack;
        this.type = type;
        this.weakReference = new WeakReference<Context>(context);
    }

    public LoginTask(Context context, String accountMobile, String loginPwd , LoginCallBack loginCallBack){
        this(context,accountMobile,loginPwd,loginCallBack,RequestTask.NO_PROMPT);
    }

    public void execute() throws Throwable{
        task = new RequestTask.Builder(weakReference.get(),callBack)
                .setUrl(UrlAddressList.URL_LOGIN)
                .setType(type)
                .setContent(buildLoginContent())
                .build();
        task.execute();
    }

    public RequestTask getTask(){
        return task;
    }

    private Map<String, String> buildLoginContent() {
        JSONObject object = new JSONObject();
        try {
            object.put("doctorPhone",accountMobile);
            object.put("loginPwd",loginPwd);
            object.put("deviceToken", UMUtils.getDeviceToken(weakReference.get()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,object.toString());
        return map;
    }

    private final RequestCallBack callBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            if ( loginCallBack != null ){
                loginCallBack.onPreExecute();
            }
        }

        @Override
        public void onError(String result) {
            if ( loginCallBack != null ){
                loginCallBack.onError(result);
            }
        }

        @Override
        public void onPostExecute(String result) {
            LoginResponse response = GsonUtil.getInstance().fromJson(result, LoginResponse.class);

            BaseApplication.getInstance().saveSessionId(response.getResult().getSessionId());
            BaseApplication.getInstance().saveUserData(response.getResult().getUser());

            BaseApplication.getInstance().saveLogin(accountMobile,loginPwd);
            BaseApplication.getInstance().saveAbnormalExit(response.getResult().getIsAbnormalExit() != 0);
            BaseApplication.getInstance().saveToExamine(response.getResult().getIsToExamine() != 0);
            if (loginCallBack != null) {
                loginCallBack.onPostExecute(result);
            }
        }
    };

    public interface LoginCallBack{
        void onPreExecute();
        void onError(String result);
        void onPostExecute(String result);
    }

}

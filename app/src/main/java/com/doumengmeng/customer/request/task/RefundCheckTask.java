package com.doumengmeng.customer.request.task;

import android.content.Context;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.net.UrlAddressList;
import com.doumengmeng.customer.request.RequestCallBack;
import com.doumengmeng.customer.request.RequestTask;
import com.doumengmeng.customer.request.ResponseErrorCode;
import com.doumengmeng.customer.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class RefundCheckTask {

    public enum RefundType{
        REFUND,
        ADD_RECORD,
        BUY
    }

    private RefundType type;
    private RefundCallBack callBack;
    private WeakReference<Context> weakReference;

    public RefundCheckTask(Context context, RefundType type, RefundCallBack callBack) {
        this.type = type;
        this.callBack = callBack;
        weakReference = new WeakReference<>(context);
    }

    public void startTask(){
        refundCheck();
    }

    public RequestTask getRequestTask(){
        return refundCheckTask;
    }

    private RequestTask refundCheckTask = null;
    private void refundCheck(){
        try {
            refundCheckTask = new RequestTask.Builder(weakReference.get(),refundCheckCallback)
                    .setContent(buildRefundCheckData())
                    .setType(RequestTask.LOADING|RequestTask.JSON)
                    .setUrl(UrlAddressList.URL_REFUND_CHECK).build();
            refundCheckTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String,String> buildRefundCheckData(){
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",BaseApplication.getInstance().getUserData().getUserid());
            map.put(UrlAddressList.PARAM,object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.SESSION_ID, BaseApplication.getInstance().getUserData().getSessionId());
        return map;
    }

    private RequestCallBack refundCheckCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {}

        @Override
        public void onError(String result) {}

        @Override
        public void onPostExecute(String result) {
            int errorId = ResponseErrorCode.getErrorCode(result);
            if ( type == RefundType.REFUND ){
                if ( ResponseErrorCode.ERROR_REFUND_NOTIMES == errorId ){
                    MyDialog.showPromptDialog(weakReference.get(),weakReference.get().getString(R.string.prompt_no_refund),null);
                    return;
                } else if ( ResponseErrorCode.ERROR_REFUND_APPLY == errorId ){
                    MyDialog.showPromptDialog(weakReference.get(),weakReference.get().getString(R.string.prompt_refund_again),null);
                    return;
                }
            } else {
                if ( ResponseErrorCode.ERROR_REFUND_APPLY == errorId ){
                    if ( RefundType.ADD_RECORD == type ){
                        MyDialog.showPromptDialog(weakReference.get(),weakReference.get().getString(R.string.prompt_refund_add_record),null);
                    } else {
                        MyDialog.showPromptDialog(weakReference.get(),weakReference.get().getString(R.string.prompt_refund_buy),null);
                    }
                    return;
                }
            }
            callBack.success();
        }
    };

    public interface RefundCallBack{
        public void success();
    }

}

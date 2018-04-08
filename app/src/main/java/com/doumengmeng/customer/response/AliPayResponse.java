package com.doumengmeng.customer.response;

/**
 * Created by Administrator on 2018/1/31.
 */

public class AliPayResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private String aLiPayBody;

        public String getaLiPayBody() {
            return aLiPayBody;
        }

        public void setaLiPayBody(String aLiPayBody) {
            this.aLiPayBody = aLiPayBody;
        }
    }

}

package com.doumengmeng.customer.response;

/**
 * Created by Administrator on 2018/1/31.
 */

public class VCForgotResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private String forgetSesId;

        public String getForgetSesId() {
            return forgetSesId;
        }

        public void setForgetSesId(String forgetSesId) {
            this.forgetSesId = forgetSesId;
        }
    }

}

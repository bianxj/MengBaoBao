package com.doumengmeng.customer.response;

/**
 * Created by Administrator on 2018/1/31.
 */

public class VCResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private String registerSesId;

        public String getRegisterSesId() {
            return registerSesId;
        }

        public void setRegisterSesId(String registerSesId) {
            this.registerSesId = registerSesId;
        }
    }

}

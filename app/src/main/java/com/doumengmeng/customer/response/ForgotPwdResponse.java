package com.doumengmeng.customer.response;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ForgotPwdResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private int isEditPwd;

        public int getIsEditPwd() {
            return isEditPwd;
        }

        public void setIsEditPwd(int isEditPwd) {
            this.isEditPwd = isEditPwd;
        }
    }

}

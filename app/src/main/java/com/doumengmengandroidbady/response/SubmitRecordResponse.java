package com.doumengmengandroidbady.response;

/**
 * Created by Administrator on 2018/1/31.
 */

public class SubmitRecordResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private int isSuccess;

        public int getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(int isSuccess) {
            this.isSuccess = isSuccess;
        }
    }

}

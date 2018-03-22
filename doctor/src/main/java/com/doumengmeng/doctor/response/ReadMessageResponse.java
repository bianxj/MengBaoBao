package com.doumengmeng.doctor.response;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ReadMessageResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private int isRead;

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }
    }

}

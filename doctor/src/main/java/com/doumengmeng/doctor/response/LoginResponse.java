package com.doumengmeng.doctor.response;


import com.doumengmeng.doctor.response.entity.UserData;

/**
 * Created by Administrator on 2018/1/31.
 */

public class LoginResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{

        private String SessionId;
        private int isToExamine;
        private int isAbnormalExit;
        private UserData Doctor;

        public String getSessionId() {
            return SessionId;
        }

        public void setSessionId(String sessionId) {
            SessionId = sessionId;
        }

        public int getIsAbnormalExit() {
            return isAbnormalExit;
        }

        public void setIsAbnormalExit(int isAbnormalExit) {
            this.isAbnormalExit = isAbnormalExit;
        }

        public UserData getUser() {
            return Doctor;
        }

        public void setUser(UserData user) {
            Doctor = user;
        }

        public int getIsToExamine() {
            return isToExamine;
        }

        public void setIsToExamine(int isToExamine) {
            this.isToExamine = isToExamine;
        }
    }

}

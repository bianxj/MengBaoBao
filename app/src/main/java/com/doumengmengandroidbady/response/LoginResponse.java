package com.doumengmengandroidbady.response;

import com.doumengmengandroidbady.response.entity.UserData;

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
        private int isAbnormalExit;
        private UserData User;

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
            return User;
        }

        public void setUser(UserData user) {
            User = user;
        }
    }

}

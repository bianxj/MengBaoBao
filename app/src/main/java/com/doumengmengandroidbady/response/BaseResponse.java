package com.doumengmengandroidbady.response;

/**
 * Created by Administrator on 2018/1/31.
 */

public class BaseResponse {

    private int errorId;
    private String serverTime;

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}

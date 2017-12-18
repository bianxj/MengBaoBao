package com.doumengmengandroidbady.entity;

/**
 * Created by Administrator on 2017/12/18.
 */

public class UserData {

    public static final String ACCOUNT = "account";
    public static final String PWD = "pwd";

    private String account;
    private String pwd;

    public UserData(String account, String pwd) {
        this.account = account;
        this.pwd = pwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}

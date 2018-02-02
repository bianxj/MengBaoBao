package com.doumengmengandroidbady.response.entity;


import android.text.TextUtils;

public class DayList {

    private String correntDay;
    private String currentDay;
    private String currentMonth;
    private String correntMonth;

    public String getCorrentDay() {
        return correntDay;
    }

    public void setCorrentDay(String correntDay) {
        this.correntDay = correntDay;
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getCorrentMonth() {
        return correntMonth;
    }

    public void setCorrentMonth(String correntMonth) {
        this.correntMonth = correntMonth;
    }

    public boolean notNull(){
        return !TextUtils.isEmpty(currentDay) && !TextUtils.isEmpty(currentMonth);
    }

}

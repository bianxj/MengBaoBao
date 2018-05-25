package com.doumengmeng.customer.response.entity;


import android.text.TextUtils;

public class DayList {

    private String correntDay;
    private String currentDay;
    private String currentMonth;
    private String correntMonth;

    public String getCorrentDay() {
        if ( Integer.parseInt(correntDay) > 0 && Integer.parseInt(correntDay) < 10 ){
            return "0"+correntDay;
        }
        return correntDay;
    }

    public void setCorrentDay(String correntDay) {
        this.correntDay = correntDay;
    }

    public String getCurrentDay() {
        if ( Integer.parseInt(currentDay) > 0 && Integer.parseInt(currentDay) < 10 ){
            return "0"+currentDay;
        }
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

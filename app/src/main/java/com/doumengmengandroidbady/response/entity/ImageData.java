package com.doumengmengandroidbady.response.entity;

/**
 * 作者: 边贤君
 * 描述: 曲线图数据
 * 创建日期: 2018/1/17 13:44
 */
public class ImageData {

    private int monthAge;
    private String weight;
    private String height;
    private int correctMonthAge;
    private int monthDay;
    private int correctMonthDay;
    private String recordtime;
    private int type;

    public int getMonthAge() {
        return monthAge;
    }

    public void setMonthAge(int monthAge) {
        this.monthAge = monthAge;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getCorrectMonthAge() {
        return correctMonthAge;
    }

    public void setCorrectMonthAge(int correctMonthAge) {
        this.correctMonthAge = correctMonthAge;
    }

    public int getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(int monthDay) {
        this.monthDay = monthDay;
    }

    public int getCorrectMonthDay() {
        return correctMonthDay;
    }

    public void setCorrectMonthDay(int correctMonthDay) {
        this.correctMonthDay = correctMonthDay;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }
}

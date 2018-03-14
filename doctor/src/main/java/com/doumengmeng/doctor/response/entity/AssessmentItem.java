package com.doumengmeng.doctor.response.entity;

import android.text.TextUtils;

import com.doumengmeng.doctor.net.UrlAddressList;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/3/13.
 */

public class AssessmentItem {

    @SerializedName("headimg")
    private String headImg;
    @SerializedName("monthage")
    private String monthAge;
    @SerializedName("monthday")
    private String monthDay;
    @SerializedName("recordid")
    private String recordId;
    @SerializedName("recordtime")
    private String recordTime;
    @SerializedName("userid")
    private String userId;
    @SerializedName("validitytime")
    private String validityTime;

    public String getHeadImgUrl(){
        if ( !TextUtils.isEmpty(headImg) ){
            return UrlAddressList.BASE_IMAGE_URL + headImg;
        }
        return headImg;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getMonthAge() {
        return monthAge;
    }

    public void setMonthAge(String monthAge) {
        this.monthAge = monthAge;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(String validityTime) {
        this.validityTime = validityTime;
    }
}

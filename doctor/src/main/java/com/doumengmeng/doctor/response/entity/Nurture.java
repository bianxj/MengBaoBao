package com.doumengmeng.doctor.response.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/3/12.
 */

public class Nurture {

    private String id;
    @SerializedName("nurturetype")
    private String nurtureType;
    @SerializedName("nurturetitle")
    private String nurtureTitle;
    @SerializedName("nurturedesc")
    private String nurtureDesc;
    private String age;
    @SerializedName("nurtureorder")
    private String nurtureOrder;
    @SerializedName("nurturetypeid")
    private String nurtureTypeId;
    @Expose
    private boolean isChoose;
    @Expose
    private boolean isCustom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNurtureType() {
        return nurtureType;
    }

    public void setNurtureType(String nurtureType) {
        this.nurtureType = nurtureType;
    }

    public String getNurtureTitle() {
        return nurtureTitle;
    }

    public void setNurtureTitle(String nurtureTitle) {
        this.nurtureTitle = nurtureTitle;
    }

    public String getNurtureDesc() {
        return nurtureDesc;
    }

    public void setNurtureDesc(String nurtureDesc) {
        this.nurtureDesc = nurtureDesc;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNurtureOrder() {
        return nurtureOrder;
    }

    public void setNurtureOrder(String nurtureOrder) {
        this.nurtureOrder = nurtureOrder;
    }

    public String getNurtureTypeId() {
        return nurtureTypeId;
    }

    public void setNurtureTypeId(String nurtureTypeId) {
        this.nurtureTypeId = nurtureTypeId;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }
}

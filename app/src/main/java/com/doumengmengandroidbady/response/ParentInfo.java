package com.doumengmengandroidbady.response;

import com.doumengmengandroidbady.util.FormatCheckUtil;
import com.doumengmengandroidbady.util.GsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/18.
 */

public class ParentInfo {

    @SerializedName(value = "dadweight")
    private String dadWeight;
    @SerializedName("dadname")
    private String dadName;
    @SerializedName("dadheight")
    private String dadHeight;
    @SerializedName("dadbmi")
    private String dadBMI;
    @SerializedName("dadeducation")
    private String dadEducation;

    @SerializedName("mumweight")
    private String mumWeight;
    @SerializedName("mumname")
    private String mumName;
    @SerializedName("mumheight")
    private String mumHeight;
    @SerializedName("mumbmi")
    private String mumBMI;
    @SerializedName("mumeducation")
    private String mumEducation;

    public String getDadWeight() {
        return dadWeight;
    }

    public void setDadWeight(String dadWeight) {
        this.dadWeight = dadWeight;
    }

    public String getDadName() {
        return dadName;
    }

    public void setDadName(String dadName) {
        this.dadName = dadName;
    }

    public String getDadHeight() {
        return dadHeight;
    }

    public void setDadHeight(String dadHeight) {
        this.dadHeight = dadHeight;
    }

    public String getDadBMI() {
        return dadBMI;
    }

    public void setDadBMI(String dadBMI) {
        this.dadBMI = dadBMI;
    }

    public String getDadEducation() {
        return dadEducation;
    }

    public void setDadEducation(String dadEducation) {
        this.dadEducation = dadEducation;
    }

    public String getMumWeight() {
        return mumWeight;
    }

    public void setMumWeight(String mumWeight) {
        this.mumWeight = mumWeight;
    }

    public String getMumName() {
        return mumName;
    }

    public void setMumName(String mumName) {
        this.mumName = mumName;
    }

    public String getMumHeight() {
        return mumHeight;
    }

    public void setMumHeight(String mumHeight) {
        this.mumHeight = mumHeight;
    }

    public String getMumBMI() {
        return mumBMI;
    }

    public void setMumBMI(String mumBMI) {
        this.mumBMI = mumBMI;
    }

    public String getMumEducation() {
        return mumEducation;
    }

    public void setMumEducation(String mumEducation) {
        this.mumEducation = mumEducation;
    }

    @Override
    public String toString() {
        String value = GsonUtil.getInstance().toJson(this);
        return value;
    }

    public static boolean isSamleValue(ParentInfo p1 , ParentInfo p2){
        return FormatCheckUtil.isSameString(p1.toString(), p2.toString());
    }

}

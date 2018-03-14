package com.doumengmeng.doctor.response.entity;

import android.text.TextUtils;

import com.doumengmeng.doctor.net.UrlAddressList;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/3/9.
 */

public class UserData {

    @SerializedName("doctorid")
    private String doctorId;
    @SerializedName("doctorphone")
    private String doctorPhone;
    @SerializedName("loginpwd")
    private String loginPwd;
    @SerializedName("doctorname")
    private String doctorName;
    @SerializedName("positionaltitles")
    private String positionalTitles;
    @SerializedName("speciality")
    private String speciality;
    @SerializedName("doctordesc")
    private String doctorDesc;
    @SerializedName("doctorimg")
    private String doctorImg;
    @SerializedName("doctorcode")
    private String doctorCode;
    @SerializedName("cost")
    private String cost;
    @SerializedName("hospitalid")
    private String hospitalId;
    @SerializedName("certificatea")
    private String certificateA;
    @SerializedName("certificateb")
    private String certificateB;
    @SerializedName("departmentname")
    private String departmentName;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPositionalTitles() {
        return positionalTitles;
    }

    public void setPositionalTitles(String positionalTitles) {
        this.positionalTitles = positionalTitles;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDoctorDesc() {
        return doctorDesc;
    }

    public void setDoctorDesc(String doctorDesc) {
        this.doctorDesc = doctorDesc;
    }

    public String getDoctorImg() {
        return doctorImg;
    }

    public void setDoctorImg(String doctorImg) {
        this.doctorImg = doctorImg;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getCertificateA() {
        return certificateA;
    }

    public void setCertificateA(String certificateA) {
        this.certificateA = certificateA;
    }

    public String getCertificateB() {
        return certificateB;
    }

    public void setCertificateB(String certificateB) {
        this.certificateB = certificateB;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHeadimg() {
        if (TextUtils.isEmpty(doctorImg)){
            return null;
        } else {
            return UrlAddressList.BASE_IMAGE_URL + doctorImg;
        }
    }

    public String getCertificateAUrl(){
        if (TextUtils.isEmpty(certificateA)){
            return null;
        } else {
            return UrlAddressList.BASE_IMAGE_URL + certificateA;
        }
    }

    public String getCertificateBUrl(){
        if (TextUtils.isEmpty(certificateB)){
            return null;
        } else {
            return UrlAddressList.BASE_IMAGE_URL + certificateB;
        }
    }

}

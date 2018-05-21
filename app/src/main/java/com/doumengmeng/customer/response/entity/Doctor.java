package com.doumengmeng.customer.response.entity;

import android.text.TextUtils;

import com.doumengmeng.customer.net.UrlAddressList;

/**
 * Created by Administrator on 2017/12/11.
 */

public class Doctor {

    private String doctorid;
    private String doctorcode;
    private String state;
    private String certificatea;
    private String certificateb;
    private String doctorphone;
    private String cost;
    private String loginpwd;
    private String doctororder;

    private String doctorimg;
    private String doctorname;
    private String positionaltitles;
    private String hospitalid;
    private String speciality;
    private String doctordesc;

    public String getDoctorImageUrl(){
        if (!TextUtils.isEmpty(doctorimg)){
            return UrlAddressList.IMAGE_URL + doctorimg;
        }
        return doctorimg;
    }

    public String getDoctorimg() {
        return doctorimg;
    }

    public void setDoctorimg(String doctorimg) {
        this.doctorimg = doctorimg;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getPositionaltitles() {
        return positionaltitles;
    }

    public void setPositionaltitles(String positionaltitles) {
        this.positionaltitles = positionaltitles;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDoctordesc() {
        return doctordesc;
    }

    public void setDoctordesc(String doctordesc) {
        this.doctordesc = doctordesc;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getDoctorcode() {
        return doctorcode;
    }

    public void setDoctorcode(String doctorcode) {
        this.doctorcode = doctorcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCertificatea() {
        return certificatea;
    }

    public void setCertificatea(String certificatea) {
        this.certificatea = certificatea;
    }

    public String getCertificateb() {
        return certificateb;
    }

    public void setCertificateb(String certificateb) {
        this.certificateb = certificateb;
    }

    public String getDoctorphone() {
        return doctorphone;
    }

    public void setDoctorphone(String doctorphone) {
        this.doctorphone = doctorphone;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getLoginpwd() {
        return loginpwd;
    }

    public void setLoginpwd(String loginpwd) {
        this.loginpwd = loginpwd;
    }

    public String getDoctororder() {
        return doctororder;
    }

    public void setDoctororder(String doctororder) {
        this.doctororder = doctororder;
    }

    public String getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(String hospitalid) {
        this.hospitalid = hospitalid;
    }
}

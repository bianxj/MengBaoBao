package com.doumengmeng.customer.entity;

import android.text.TextUtils;

import com.doumengmeng.customer.net.UrlAddressList;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DoctorEntity {

    private String doctorid;
    private String doctorimg;
    private String doctorname;
    private String positionaltitles;
    private String speciality;
    private String doctordesc;
    private String hospitalid;
    private String hospital;

    public String getDoctorimg() {
        if (!TextUtils.isEmpty(doctorimg)){
            return UrlAddressList.IMAGE_URL + doctorimg;
        }
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

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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

    public String getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(String hospitalid) {
        this.hospitalid = hospitalid;
    }
}

package com.doumengmengandroidbady.entity;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DoctorEntity {

//    private String doctorcode;
//    private String state;
//    private String certificatea;
//    private String certificateb;
//    private String doctorphone;
//    private String cost;
//    private String loginpwd;
//    private String doctororder;

    private String doctorid;
    private String doctorimg;
    private String doctorname;
    private String positionaltitles;
    private String hospitalid;
    private String hospital;
    private String speciality;
    private String doctordesc;

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

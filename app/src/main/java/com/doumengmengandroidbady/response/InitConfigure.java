package com.doumengmengandroidbady.response;


import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class InitConfigure {

    private ParentInfo ParentInfo;
    private List<Doctor> DoctorList;
    private List<Hospital> HospitalList;
    private List<MengClass> MengClassList;
    private List<Growth> GrowthList;
    private List<Feature> FeatureList;
    private DayList DayList;

    public void setDoctorList(List<Doctor> doctorList) {
        DoctorList = doctorList;
    }

    public void setHospitalList(List<Hospital> hospitalList) {
        HospitalList = hospitalList;
    }

    public void setMengClassList(List<MengClass> mengClassList) {
        MengClassList = mengClassList;
    }

    public void setGrowthList(List<Growth> growthList) {
        GrowthList = growthList;
    }

    public List<Doctor> getDoctorList() {
        return DoctorList;
    }

    public List<Hospital> getHospitalList() {
        return HospitalList;
    }

    public List<MengClass> getMengClassList() {
        return MengClassList;
    }

    public List<Growth> getGrowthList() {
        return GrowthList;
    }

    public List<Feature> getFeatureList() {
        return FeatureList;
    }

    public com.doumengmengandroidbady.response.DayList getDayList() {
        return DayList;
    }

    public void setDayList(com.doumengmengandroidbady.response.DayList dayList) {
        DayList = dayList;
    }

    public void setFeatureList(List<Feature> featureList) {
        FeatureList = featureList;
    }

    public com.doumengmengandroidbady.response.ParentInfo getParentInfo() {
        return ParentInfo;
    }

    public void setParentInfo(com.doumengmengandroidbady.response.ParentInfo parentInfo) {
        ParentInfo = parentInfo;
    }
}

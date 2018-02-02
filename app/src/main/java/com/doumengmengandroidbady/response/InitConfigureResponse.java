package com.doumengmengandroidbady.response;

import com.doumengmengandroidbady.response.entity.Doctor;
import com.doumengmengandroidbady.response.entity.Feature;
import com.doumengmengandroidbady.response.entity.Growth;
import com.doumengmengandroidbady.response.entity.Hospital;
import com.doumengmengandroidbady.response.entity.MengClass;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class InitConfigureResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private com.doumengmengandroidbady.response.entity.ParentInfo ParentInfo;
        private List<Doctor> DoctorList;
        private List<Hospital> HospitalList;
        private List<MengClass> MengClassList;
        private List<Growth> GrowthList;
        private List<Feature> FeatureList;
        private com.doumengmengandroidbady.response.entity.DayList DayList;

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

        public com.doumengmengandroidbady.response.entity.DayList getDayList() {
            return DayList;
        }

        public void setDayList(com.doumengmengandroidbady.response.entity.DayList dayList) {
            DayList = dayList;
        }

        public void setFeatureList(List<Feature> featureList) {
            FeatureList = featureList;
        }

        public com.doumengmengandroidbady.response.entity.ParentInfo getParentInfo() {
            return ParentInfo;
        }

        public void setParentInfo(com.doumengmengandroidbady.response.entity.ParentInfo parentInfo) {
            ParentInfo = parentInfo;
        }
    }

}

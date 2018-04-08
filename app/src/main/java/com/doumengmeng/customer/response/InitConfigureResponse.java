package com.doumengmeng.customer.response;

import com.doumengmeng.customer.response.entity.Doctor;
import com.doumengmeng.customer.response.entity.Feature;
import com.doumengmeng.customer.response.entity.Growth;
import com.doumengmeng.customer.response.entity.Hospital;
import com.doumengmeng.customer.response.entity.MengClass;

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
        private com.doumengmeng.customer.response.entity.ParentInfo ParentInfo;
        private List<Doctor> DoctorList;
        private List<Hospital> HospitalList;
        private List<MengClass> MengClassList;
        private List<Growth> GrowthList;
        private List<Feature> FeatureList;
        private com.doumengmeng.customer.response.entity.DayList DayList;

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

        public com.doumengmeng.customer.response.entity.DayList getDayList() {
            return DayList;
        }

        public void setDayList(com.doumengmeng.customer.response.entity.DayList dayList) {
            DayList = dayList;
        }

        public void setFeatureList(List<Feature> featureList) {
            FeatureList = featureList;
        }

        public com.doumengmeng.customer.response.entity.ParentInfo getParentInfo() {
            return ParentInfo;
        }

        public void setParentInfo(com.doumengmeng.customer.response.entity.ParentInfo parentInfo) {
            ParentInfo = parentInfo;
        }
    }

}

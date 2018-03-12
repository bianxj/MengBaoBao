package com.doumengmeng.doctor.response;


import com.doumengmeng.doctor.response.entity.Feature;
import com.doumengmeng.doctor.response.entity.Hospital;
import com.doumengmeng.doctor.response.entity.Nurture;

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
        private List<Hospital> HospitalList;
        private List<Feature> FeatureList;
        private List<Nurture> NurtureGuideInfoList;

        public void setHospitalList(List<Hospital> hospitalList) {
            HospitalList = hospitalList;
        }

        public List<Hospital> getHospitalList() {
            return HospitalList;
        }

        public List<Feature> getFeatureList() {
            return FeatureList;
        }

        public void setFeatureList(List<Feature> featureList) {
            FeatureList = featureList;
        }

        public List<Nurture> getNurtureGuideInfoList() {
            return NurtureGuideInfoList;
        }

        public void setNurtureGuideInfoList(List<Nurture> nurtureGuideInfoList) {
            NurtureGuideInfoList = nurtureGuideInfoList;
        }
    }

}

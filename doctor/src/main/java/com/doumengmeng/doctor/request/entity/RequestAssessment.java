package com.doumengmeng.doctor.request.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */

public class RequestAssessment {

    private String doctorId;
    private String recordId;
    private EvaluationInfo evaluationInfo;
    private List<NurtureDesc> nurtureInfoList;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public EvaluationInfo getEvaluationInfo() {
        return evaluationInfo;
    }

    public void setEvaluationInfo(EvaluationInfo evaluationInfo) {
        this.evaluationInfo = evaluationInfo;
    }

    public List<NurtureDesc> getNurtureInfoList() {
        return nurtureInfoList;
    }

    public void setNurtureInfoList(List<NurtureDesc> nurtureInfoList) {
        this.nurtureInfoList = nurtureInfoList;
    }

    public static class EvaluationInfo{
        private String doctorAdvice;
        private String featureAppraisal;
        private String growthLevelAppraisal;
        private String growthTendencyAppraisal;

        public String getDoctorAdvice() {
            return doctorAdvice;
        }

        public void setDoctorAdvice(String doctorAdvice) {
            this.doctorAdvice = doctorAdvice;
        }

        public String getFeatureAppraisal() {
            return featureAppraisal;
        }

        public void setFeatureAppraisal(String featureAppraisal) {
            this.featureAppraisal = featureAppraisal;
        }

        public String getGrowthLevelAppraisal() {
            return growthLevelAppraisal;
        }

        public void setGrowthLevelAppraisal(String growthLevelAppraisal) {
            this.growthLevelAppraisal = growthLevelAppraisal;
        }

        public String getGrowthTendencyAppraisal() {
            return growthTendencyAppraisal;
        }

        public void setGrowthTendencyAppraisal(String growthTendencyAppraisal) {
            this.growthTendencyAppraisal = growthTendencyAppraisal;
        }
    }

    public static class NurtureDesc{
        private String customNurtureDesc;
        private String customNurtureTitle;
        private String nurtureTypeId;
        private String nurtureId;

        public String getCustomNurtureDesc() {
            return customNurtureDesc;
        }

        public void setCustomNurtureDesc(String customNurtureDesc) {
            this.customNurtureDesc = customNurtureDesc;
        }

        public String getCustomNurtureTitle() {
            return customNurtureTitle;
        }

        public void setCustomNurtureTitle(String customNurtureTitle) {
            this.customNurtureTitle = customNurtureTitle;
        }

        public String getNurtureTypeId() {
            return nurtureTypeId;
        }

        public void setNurtureTypeId(String nurtureTypeId) {
            this.nurtureTypeId = nurtureTypeId;
        }

        public String getNurtureId() {
            return nurtureId;
        }

        public void setNurtureId(String nurtureId) {
            this.nurtureId = nurtureId;
        }
    }

}

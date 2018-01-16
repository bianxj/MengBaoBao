package com.doumengmengandroidbady.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Record {

    @SerializedName("recordid")
    private String recordId;
    @SerializedName("userid")
    private String userId;
    private String doctorid;

    private String height;
    private String weight;

    @SerializedName("heightevaluation")
    private String heightEvaluation;
    @SerializedName("weightevaluation")
    private String weightEvaluation;
    @SerializedName("featureevaluation")
    private String featureEvaluation;
    @SerializedName("hwevaluation")
    private String hwEvaluation;

    @SerializedName("monthage")
    private String monthAge;
    @SerializedName("monthday")
    private String monthDay;
    @SerializedName("correctmonthage")
    private String correctMonthAge;
    @SerializedName("correctmonthday")
    private String correctMonthDay;

    @SerializedName("recordstatus")
    private String recordStatus;
    @SerializedName("recordtime")
    private String recordTime;

    @SerializedName("heightresult")
    private String heightResult;
    @SerializedName("hwresult")
    private String hwResult;
    @SerializedName("weightresult")
    private String weightResult;
    @SerializedName("featureresult")
    private String featureResult;
    @SerializedName("feature")
    private List<String> featureList;

    private List<ImageData> imageData;

    @SerializedName("GrowthTendencyAppraisal")
    private String growthTendencyAppraisal;
    @SerializedName("GrowthLevelAppraisal")
    private String growthLevelAppraisal;
    @SerializedName("FeatureAppraisal")
    private String featureAppraisal;
    @SerializedName("DoctorAdvice")
    private String doctorAdvice;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getHeightEvaluation() {
        return heightEvaluation;
    }

    public void setHeightEvaluation(String heightEvaluation) {
        this.heightEvaluation = heightEvaluation;
    }

    public String getWeightEvaluation() {
        return weightEvaluation;
    }

    public void setWeightEvaluation(String weightEvaluation) {
        this.weightEvaluation = weightEvaluation;
    }

    public String getFeatureEvaluation() {
        return featureEvaluation;
    }

    public void setFeatureEvaluation(String featureEvaluation) {
        this.featureEvaluation = featureEvaluation;
    }

    public String getHwEvaluation() {
        return hwEvaluation;
    }

    public void setHwEvaluation(String hwEvaluation) {
        this.hwEvaluation = hwEvaluation;
    }

    public String getMonthAge() {
        return monthAge;
    }

    public void setMonthAge(String monthAge) {
        this.monthAge = monthAge;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(String monthDay) {
        this.monthDay = monthDay;
    }

    public String getCorrectMonthAge() {
        return correctMonthAge;
    }

    public void setCorrectMonthAge(String correctMonthAge) {
        this.correctMonthAge = correctMonthAge;
    }

    public String getCorrectMonthDay() {
        return correctMonthDay;
    }

    public void setCorrectMonthDay(String correctMonthDay) {
        this.correctMonthDay = correctMonthDay;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordDay(){
        String[] recordTimes = recordTime.split(" ");
        return recordTimes[0].replace("-",".");
    }

    public String getHeightResultString(){
        if ( "1".equals(heightResult) ){
            return "生长迟缓";
        } else {
            return "正常";
        }
    }

    public String getHeightResult() {
        return heightResult;
    }

    public void setHeightResult(String heightResult) {
        this.heightResult = heightResult;
    }

    public String getHwResultString(){
        if ( "-1".equals(hwResult) ){
            return "消瘦";
        } else if ( "1".equals(hwResult) ){
            return "超重";
        } else if ( "2".equals(hwResult) ){
            return "肥胖";
        } else if ( "3".equals(hwResult) ){
            return "重度肥胖";
        } else {
            return "正常";
        }

    }

    public String getHwResult() {
        return hwResult;
    }

    public void setHwResult(String hwResult) {
        this.hwResult = hwResult;
    }

    public String getWeightResultString(){
        if ( "1".equals(weightResult) ){
            return "低体重";
        }
        return "正常";
    }

    public String getWeightResult() {
        return weightResult;
    }

    public void setWeightResult(String weightResult) {
        this.weightResult = weightResult;
    }

    public String getFeatureResultString(){
        if ( "1".equals(featureResult) ){
            return "可疑";
        } else if ( "-1".equals(featureResult) ){
            return "异常";
        }
        return "正常";
    }

    public String getFeatureResult() {
        return featureResult;
    }

    public void setFeatureResult(String featureResult) {
        this.featureResult = featureResult;
    }

    public String getBabyMonth(){
        return monthAge + "月" + monthDay + "日";
    }

    public boolean isShowRecordState(){
        if ( "3".equals(recordStatus) ){
            return true;
        }
        return false;
    }

    public List<String> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<String> featureList) {
        this.featureList = featureList;
    }

    public List<ImageData> getImageData() {
        return imageData;
    }

    public void setImageData(List<ImageData> imageData) {
        this.imageData = imageData;
    }

    public String getCurrentMonthAgeString(){
        return monthAge + "月" + monthDay +"日";
    }

    public String getCorrectMonthAgeString(){
        return correctMonthAge + "月" + correctMonthDay +"日";
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGrowthTendencyAppraisal() {
        return growthTendencyAppraisal;
    }

    public void setGrowthTendencyAppraisal(String growthTendencyAppraisal) {
        this.growthTendencyAppraisal = growthTendencyAppraisal;
    }

    public String getGrowthLevelAppraisal() {
        return growthLevelAppraisal;
    }

    public void setGrowthLevelAppraisal(String growthLevelAppraisal) {
        this.growthLevelAppraisal = growthLevelAppraisal;
    }

    public String getFeatureAppraisal() {
        return featureAppraisal;
    }

    public void setFeatureAppraisal(String featureAppraisal) {
        this.featureAppraisal = featureAppraisal;
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }
}

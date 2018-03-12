package com.doumengmeng.doctor.response.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class HospitalReport {

    @SerializedName("userid")
    private String userId;
    @SerializedName("truename")
    private String trueName;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("sex")
    private String sex;
    @SerializedName("weight")
    private String weight;
    @SerializedName("height")
    private String height;
    @SerializedName("bronheight")
    private String bronHeight;
    @SerializedName("bornweight")
    private String bornWeight;
    @SerializedName("headcircumference")
    private String headCircumference;
    @SerializedName("chestcircumference")
    private String chestCircumference;
    @SerializedName("bmi")
    private String bmi;

    @SerializedName("doctorid")
    private String doctorId;
    @SerializedName("doctorname")
    private String doctorName;
    @SerializedName("doctoradvice")
    private String doctorAdvice;
    @SerializedName("departmentid")
    private String departmentId;
    @SerializedName("departmentname")
    private String departmentName;
    @SerializedName("hospitalname")
    private String hospitalName;
    @SerializedName("curedoctor")
    private String cureDoctor;

    @SerializedName("recordid")
    private String recordId;
    @SerializedName("recordtime")
    private String recordTime;
    @SerializedName("filecode")
    private String fileCode;

    @SerializedName("hwresult")
    private String hwResult;
    @SerializedName("hwresultsd")
    private String hwResultSD;
    @SerializedName("heightresult")
    private String heightResult;
    @SerializedName("heightresultsd")
    private String heightResultSD;
    @SerializedName("weightresult")
    private String weightResult;
    @SerializedName("weightresultsd")
    private String weightResultSD;
    @SerializedName("headcircumferenceresult")
    private String headCircumferenceResult;
    @SerializedName("featureresult")
    private String featureResult;
    @SerializedName("chestcircumferenceresultsd")
    private String chestCircumferenceResultSD;

    @SerializedName("heightevaluation")
    private String heightEvaluation;
    @SerializedName("weightevaluation")
    private String weightEvaluation;
    @SerializedName("resultevaluation")
    private String resultEvaluation;
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

    @SerializedName("presentillnesshistory")
    private String presentIllnessHistory;
    @SerializedName("otherpresentillnesshistory")
    private String otherPresentIllnessHistory;

    @SerializedName("featureage")
    private String featureAge;
    @SerializedName("features")
    private List<String> features;

    @SerializedName("feedingML")
    private String feedingML;
    @SerializedName("nexttime")
    private String nextTime;
    @SerializedName("foodtype")
    private String foodType;

    private List<ImageData> imageData;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getBirthday() {
        if (!TextUtils.isEmpty(birthday)){
            return birthday.split(" ")[0];
        }
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBronHeight() {
        return bronHeight;
    }

    public void setBronHeight(String bronHeight) {
        this.bronHeight = bronHeight;
    }

    public String getBornWeight() {
        return bornWeight;
    }

    public void setBornWeight(String bornWeight) {
        this.bornWeight = bornWeight;
    }

    public String getHeadCircumference() {
        return headCircumference;
    }

    public void setHeadCircumference(String headCircumference) {
        this.headCircumference = headCircumference;
    }

    public String getChestCircumference() {
        return chestCircumference;
    }

    public void setChestCircumference(String chestCircumference) {
        this.chestCircumference = chestCircumference;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getCureDoctor() {
        return cureDoctor;
    }

    public void setCureDoctor(String cureDoctor) {
        this.cureDoctor = cureDoctor;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getHwResult() {
        return hwResult;
    }

    public void setHwResult(String hwResult) {
        this.hwResult = hwResult;
    }

    public String getHwResultSD() {
        return hwResultSD;
    }

    public void setHwResultSD(String hwResultSD) {
        this.hwResultSD = hwResultSD;
    }

    public String getHeightResult() {
        return heightResult;
    }

    public void setHeightResult(String heightResult) {
        this.heightResult = heightResult;
    }

    public String getHeightResultSD() {
        return heightResultSD;
    }

    public void setHeightResultSD(String heightResultSD) {
        this.heightResultSD = heightResultSD;
    }

    public String getWeightResult() {
        return weightResult;
    }

    public void setWeightResult(String weightResult) {
        this.weightResult = weightResult;
    }

    public String getWeightResultSD() {
        return weightResultSD;
    }

    public void setWeightResultSD(String weightResultSD) {
        this.weightResultSD = weightResultSD;
    }

    public String getHeadCircumferenceResult() {
        return headCircumferenceResult;
    }

    public void setHeadCircumferenceResult(String headCircumferenceResult) {
        this.headCircumferenceResult = headCircumferenceResult;
    }

    public String getFeatureResult() {
        return featureResult;
    }

    public void setFeatureResult(String featureResult) {
        this.featureResult = featureResult;
    }

    public String getChestCircumferenceResultSD() {
        return chestCircumferenceResultSD;
    }

    public void setChestCircumferenceResultSD(String chestCircumferenceResultSD) {
        this.chestCircumferenceResultSD = chestCircumferenceResultSD;
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

    public String getResultEvaluation() {
        return resultEvaluation;
    }

    public void setResultEvaluation(String resultEvaluation) {
        this.resultEvaluation = resultEvaluation;
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

    public String getPresentIllnessHistory() {
        return presentIllnessHistory;
    }

    public void setPresentIllnessHistory(String presentIllnessHistory) {
        this.presentIllnessHistory = presentIllnessHistory;
    }

    public String getOtherPresentIllnessHistory() {
        return otherPresentIllnessHistory;
    }

    public void setOtherPresentIllnessHistory(String otherPresentIllnessHistory) {
        this.otherPresentIllnessHistory = otherPresentIllnessHistory;
    }

    public String getFeatureAge() {
        return featureAge;
    }

    public void setFeatureAge(String featureAge) {
        this.featureAge = featureAge;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getFeedingML() {
        return feedingML;
    }

    public void setFeedingML(String feedingML) {
        this.feedingML = feedingML;
    }

    public String getNextTime() {
        return nextTime;
    }

    public void setNextTime(String nextTime) {
        this.nextTime = nextTime;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getHeightResultString(){
        if ( "1".equals(heightResult) ){
            return "生长迟缓";
        } else {
            return "正常";
        }
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

    public String getWeightResultString(){
        if ( "1".equals(weightResult) ){
            return "低体重";
        }
        return "正常";
    }

    public String getFeatureResultString(){
        if ( "1".equals(featureResult) ){
            return "可疑";
        } else if ( "-1".equals(featureResult) ){
            return "异常";
        }
        return "正常";
    }

    public String getBabyMonth(){
        return monthAge + "月" + monthDay + "日";
    }

    public String getCurrentMonthAgeString(){
        return monthAge + "个月" + monthDay +"天";
    }

    public String getCorrectMonthAgeString(){
        return correctMonthAge + "个月" + correctMonthDay +"天";
    }

    public String getRecordDay(){
        String[] recordTimes = recordTime.split(" ");
        return recordTimes[0].replace("-",".");
    }

    public boolean isMale(){
        return "1".equals(sex);
    }

    public String getGender(){
        if ( "1".equals(sex) ){
            return "男";
        } else {
            return "女";
        }
    }

    public List<ImageData> getImageData() {
        return imageData;
    }

    public void setImageData(List<ImageData> imageData) {
        this.imageData = imageData;
    }
}

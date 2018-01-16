package com.doumengmengandroidbady.request.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class InputUserInfo {

    public String userId;
    public BabyInfo babyInfo;
    public ParentInfo parentInfo;

    public InputUserInfo() {
        babyInfo = new BabyInfo();
        parentInfo = new ParentInfo();
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BabyInfo getBabyInfo() {
        return babyInfo;
    }

    public ParentInfo getParentInfo() {
        return parentInfo;
    }

    public static class ParentInfo{
        //父亲姓名
        public String DadName;
        //父亲文化程度
        public String DadEducation;
        //父亲身高
        public String DadHeight;
        //父亲体重
        public String DadWeight;
        //父亲BMI
        public String DadBMI;
        //母亲姓名
        public String MumName;
        //母亲文化程度
        public String MumEducation;
        //母亲身高
        public String MumHeight;
        //母亲体重
        public String MumWeight;
        //母亲BMI
        public String MumBMI;

        public void clearData(){
            DadName = "";
            DadHeight = "";
            DadWeight = "";
            MumBMI = "";
            MumName = "";
            MumHeight = "";
            MumWeight = "";
            MumBMI = "";
        }

        public void setDadName(String dadName) {
            DadName = dadName;
        }

        public void setDadEducation(String dadEducation) {
            DadEducation = dadEducation;
        }

        public void setDadHeight(String dadHeight) {
            DadHeight = dadHeight;
        }

        public void setDadWeight(String dadWeight) {
            DadWeight = dadWeight;
        }

        public void setDadBMI(String dadBMI) {
            DadBMI = dadBMI;
        }

        public void setMumName(String mumName) {
            MumName = mumName;
        }

        public void setMumEducation(String mumEducation) {
            MumEducation = mumEducation;
        }

        public void setMumHeight(String mumHeight) {
            MumHeight = mumHeight;
        }

        public void setMumWeight(String mumWeight) {
            MumWeight = mumWeight;
        }

        public void setMumBMI(String mumBMI) {
            MumBMI = mumBMI;
        }

        public String getDadName() {
            return DadName;
        }

        public String getDadEducation() {
            return DadEducation;
        }

        public String getDadHeight() {
            return DadHeight;
        }

        public String getDadWeight() {
            return DadWeight;
        }

        public String getDadBMI() {
            return DadBMI;
        }

        public String getMumName() {
            return MumName;
        }

        public String getMumEducation() {
            return MumEducation;
        }

        public String getMumHeight() {
            return MumHeight;
        }

        public String getMumWeight() {
            return MumWeight;
        }

        public String getMumBMI() {
            return MumBMI;
        }
    }

    public static class BabyInfo {
        //真实姓名
        public String TrueName;
        //性别 0 女 1 男
        public String Sex;
        //联系手机
        public String AccountMobile;
        //生日 format '1970-01-01 00:00:00'
        public String Birthday;
        //孕周(周)
        public String PregnancyWeeks;
        //孕周(日)
        public String PregnancyDays;
        //出生体重
        public String BornWeight;
        //出生身长
        public String BornHeight;
        //胎次
        public String Pregnancies;
        //产次
        public String BirthTimes;
        //母亲生育年龄
        public String MumBearAge;
        //出生状况 1单胎 2 多胎
        public String BornType;
        //分娩方式
        public List<String> DeliveryMethods;
        //辅助生育 0无 1人工授精 2试管婴儿
        public String AssistedReproductive;
        //产伤  0无 1有
        public String BirthInjury;
        //新生儿窒息 0无 1有
        public String NeonatalAsphyxia;
        //颅内出血 0无 1有
        public String IntracranialHemorrhage;
        //家族遗传史 0无 1有
        public String HereditaryHistory;
        //家族遗传史内容
        public String HereditaryHistoryDesc;
        //家族过敏史 0无 1有
        public String AllergicHistory;
        //家族过敏史内容
        public String AllergicHistoryDesc;
        //既往史
        public List<String> PastHistory;
        //既往史其他内容
        public String PastHistoryOther;

        public void clearData(){
            HereditaryHistoryDesc = "";
            AllergicHistoryDesc = "";
            PastHistoryOther = "";
        }

        public String getTrueName() {
            return TrueName;
        }

        public void setTrueName(String trueName) {
            TrueName = trueName;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public String getAccountMobile() {
            return AccountMobile;
        }

        public void setAccountMobile(String accountMobile) {
            AccountMobile = accountMobile;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String birthday) {
            Birthday = birthday;
        }

        public String getPregnancyWeeks() {
            return PregnancyWeeks;
        }

        public void setPregnancyWeeks(String pregnancyWeeks) {
            PregnancyWeeks = pregnancyWeeks;
        }

        public String getPregnancyDays() {
            return PregnancyDays;
        }

        public void setPregnancyDays(String pregnancyDays) {
            PregnancyDays = pregnancyDays;
        }

        public String getBornWeight() {
            return BornWeight;
        }

        public void setBornWeight(String bornWeight) {
            BornWeight = bornWeight;
        }

        public String getBornHeight() {
            return BornHeight;
        }

        public void setBornHeight(String bornHeight) {
            BornHeight = bornHeight;
        }

        public String getPregnancies() {
            return Pregnancies;
        }

        public void setPregnancies(String pregnancies) {
            Pregnancies = pregnancies;
        }

        public String getBirthTimes() {
            return BirthTimes;
        }

        public void setBirthTimes(String birthTimes) {
            BirthTimes = birthTimes;
        }

        public String getMumBearAge() {
            return MumBearAge;
        }

        public void setMumBearAge(String mumBearAge) {
            MumBearAge = mumBearAge;
        }

        public String getBornType() {
            return BornType;
        }

        public void setBornType(String bornType) {
            BornType = bornType;
        }

        public List<String> getDeliveryMethods() {
            return DeliveryMethods;
        }

        public void setDeliveryMethods(List<String> deliveryMethods) {
            DeliveryMethods = deliveryMethods;
        }

        public String getAssistedReproductive() {
            return AssistedReproductive;
        }

        public void setAssistedReproductive(String assistedReproductive) {
            AssistedReproductive = assistedReproductive;
        }

        public String getBirthInjury() {
            return BirthInjury;
        }

        public void setBirthInjury(String birthInjury) {
            BirthInjury = birthInjury;
        }

        public String getNeonatalAsphyxia() {
            return NeonatalAsphyxia;
        }

        public void setNeonatalAsphyxia(String neonatalAsphyxia) {
            NeonatalAsphyxia = neonatalAsphyxia;
        }

        public String getIntracranialHemorrhage() {
            return IntracranialHemorrhage;
        }

        public void setIntracranialHemorrhage(String intracranialHemorrhage) {
            IntracranialHemorrhage = intracranialHemorrhage;
        }

        public String getHereditaryHistory() {
            return HereditaryHistory;
        }

        public void setHereditaryHistory(String hereditaryHistory) {
            HereditaryHistory = hereditaryHistory;
        }

        public String getHereditaryHistoryDesc() {
            return HereditaryHistoryDesc;
        }

        public void setHereditaryHistoryDesc(String hereditaryHistoryDesc) {
            HereditaryHistoryDesc = hereditaryHistoryDesc;
        }

        public String getAllergicHistory() {
            return AllergicHistory;
        }

        public void setAllergicHistory(String allergicHistory) {
            AllergicHistory = allergicHistory;
        }

        public String getAllergicHistoryDesc() {
            return AllergicHistoryDesc;
        }

        public void setAllergicHistoryDesc(String allergicHistoryDesc) {
            AllergicHistoryDesc = allergicHistoryDesc;
        }

        public List<String> getPastHistory() {
            return PastHistory;
        }

        public void setPastHistory(List<String> pastHistory) {
            PastHistory = pastHistory;
        }

        public String getPastHistoryOther() {
            return PastHistoryOther;
        }

        public void setPastHistoryOther(String pastHistoryOther) {
            PastHistoryOther = pastHistoryOther;
        }
    }

    public void clearData(){
        babyInfo.clearData();
        parentInfo.clearData();
    }

}

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
    }

    public void clearData(){
        babyInfo.clearData();
        parentInfo.clearData();
    }

}

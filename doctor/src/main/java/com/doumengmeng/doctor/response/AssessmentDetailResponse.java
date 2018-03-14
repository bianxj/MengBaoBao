package com.doumengmeng.doctor.response;

import android.text.TextUtils;

import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.response.entity.ImageData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

public class AssessmentDetailResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{

        private ParentData parentList;
        private List<ImageData> imgList;
        private SupplementFood supplementaryFoodList;
        private Record recordList;
        private User userList;
        private List<ReportImage> reportImgList;

        public ParentData getParentList() {
            return parentList;
        }

        public void setParentList(ParentData parentList) {
            this.parentList = parentList;
        }

        public List<ImageData> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImageData> imgList) {
            this.imgList = imgList;
        }

        public SupplementFood getSupplementaryFoodList() {
            return supplementaryFoodList;
        }

        public void setSupplementaryFoodList(SupplementFood supplementaryFoodList) {
            this.supplementaryFoodList = supplementaryFoodList;
        }

        public Record getRecordList() {
            return recordList;
        }

        public void setRecordList(Record recordList) {
            this.recordList = recordList;
        }

        public User getUserList() {
            return userList;
        }

        public void setUserList(User userList) {
            this.userList = userList;
        }

        public List<ReportImage> getReportImgList() {
            return reportImgList;
        }

        public void setReportImgList(List<ReportImage> reportImgList) {
            this.reportImgList = reportImgList;
        }
    }

    public static class ParentData{
        private String dadbmi;
        private String dadeducation;
        private String dadheight;
        private String dadname;
        private String dadweight;
        private String mumbmi;
        private String mumeducation;
        private String mumheight;
        private String mumname;
        private String mumweight;
        private String userid;

        public String getDadBmi() {
            return dadbmi;
        }

        public void setDadBmi(String dadbmi) {
            this.dadbmi = dadbmi;
        }

        public String getDadeducation() {
            return dadeducation;
        }

        public void setDadeducation(String dadeducation) {
            this.dadeducation = dadeducation;
        }

        public String getDadheight() {
            return dadheight;
        }

        public void setDadheight(String dadheight) {
            this.dadheight = dadheight;
        }

        public String getDadname() {
            return dadname;
        }

        public void setDadname(String dadname) {
            this.dadname = dadname;
        }

        public String getDadweight() {
            return dadweight;
        }

        public void setDadweight(String dadweight) {
            this.dadweight = dadweight;
        }

        public String getMumbmi() {
            return mumbmi;
        }

        public void setMumbmi(String mumbmi) {
            this.mumbmi = mumbmi;
        }

        public String getMumeducation() {
            return mumeducation;
        }

        public void setMumeducation(String mumeducation) {
            this.mumeducation = mumeducation;
        }

        public String getMumheight() {
            return mumheight;
        }

        public void setMumheight(String mumheight) {
            this.mumheight = mumheight;
        }

        public String getMumname() {
            return mumname;
        }

        public void setMumname(String mumname) {
            this.mumname = mumname;
        }

        public String getMumweight() {
            return mumweight;
        }

        public void setMumweight(String mumweight) {
            this.mumweight = mumweight;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }

    public static class SupplementFood{
        private String calciumdose;
        private String calciumname;
        private String congee;
        private String eggs;
        private String fruit;
        private String meat;
        private String milk;
        private String orexia;
        private String otherfood;
        private String pasta;
        private String recordid;
        private String rice;
        private String riceflour;
        private String soyproducts;
        private String vegetables;
        private String vitaminaddose;
        private String vitaminadname;
        private String water;

        public String getCalciumdose() {
            return calciumdose;
        }

        public void setCalciumdose(String calciumdose) {
            this.calciumdose = calciumdose;
        }

        public String getCalciumname() {
            return calciumname;
        }

        public void setCalciumname(String calciumname) {
            this.calciumname = calciumname;
        }

        public String getCongee() {
            return congee;
        }

        public void setCongee(String congee) {
            this.congee = congee;
        }

        public String getEggs() {
            return eggs;
        }

        public void setEggs(String eggs) {
            this.eggs = eggs;
        }

        public String getFruit() {
            return fruit;
        }

        public void setFruit(String fruit) {
            this.fruit = fruit;
        }

        public String getMeat() {
            return meat;
        }

        public void setMeat(String meat) {
            this.meat = meat;
        }

        public String getMilk() {
            return milk;
        }

        public void setMilk(String milk) {
            this.milk = milk;
        }

        public String getOrexia() {
            return orexia;
        }

        public void setOrexia(String orexia) {
            this.orexia = orexia;
        }

        public String getOtherfood() {
            return otherfood;
        }

        public void setOtherfood(String otherfood) {
            this.otherfood = otherfood;
        }

        public String getPasta() {
            return pasta;
        }

        public void setPasta(String pasta) {
            this.pasta = pasta;
        }

        public String getRecordid() {
            return recordid;
        }

        public void setRecordid(String recordid) {
            this.recordid = recordid;
        }

        public String getRice() {
            return rice;
        }

        public void setRice(String rice) {
            this.rice = rice;
        }

        public String getRiceflour() {
            return riceflour;
        }

        public void setRiceflour(String riceflour) {
            this.riceflour = riceflour;
        }

        public String getSoyproducts() {
            return soyproducts;
        }

        public void setSoyproducts(String soyproducts) {
            this.soyproducts = soyproducts;
        }

        public String getVegetables() {
            return vegetables;
        }

        public void setVegetables(String vegetables) {
            this.vegetables = vegetables;
        }

        public String getVitaminaddose() {
            return vitaminaddose;
        }

        public void setVitaminaddose(String vitaminaddose) {
            this.vitaminaddose = vitaminaddose;
        }

        public String getVitaminadname() {
            return vitaminadname;
        }

        public void setVitaminadname(String vitaminadname) {
            this.vitaminadname = vitaminadname;
        }

        public String getWater() {
            return water;
        }

        public void setWater(String water) {
            this.water = water;
        }
    }

    public static class Record {

        private String breastfeedingcount;
        private String breastfeedingml;
        private String cacation;
        private String cacationdays;
        private String chestcircumference;
        private String costid;
        private String daytimesleep;
        private String headcircumference;
        private String headcircumferenceevaluate;
        private String headcircumferenceresult;
        private String milkfeedingcount;
        private String milkfeedingml;
        private String nighttimesleep;
        private String other;
        private String reportimgidx;
        private String urinate;
        private String validitytime;

        @SerializedName("recordid")
        private String recordid;
        @SerializedName("userid")
        private String userid;
        private String doctorid;

        private String height;
        private String weight;

        @SerializedName("heightevaluation")
        private String heightevaluation;
        @SerializedName("weightevaluation")
        private String weightevaluation;
        @SerializedName("featureevaluation")
        private String featureevaluation;
        @SerializedName("hwevaluation")
        private String hwevaluation;

        @SerializedName("monthage")
        private int monthage;
        @SerializedName("monthday")
        private int monthday;
        @SerializedName("correctmonthage")
        private int correctmonthage;
        @SerializedName("correctmonthday")
        private int correctmonthday;

        @SerializedName("recordstatus")
        private String recordstatus;
        @SerializedName("recordtime")
        private String recordtime;

        @SerializedName("heightresult")
        private String heightresult;
        @SerializedName("hwresult")
        private String hwresult;
        @SerializedName("weightresult")
        private String weightresult;
        @SerializedName("featureresult")
        private String featureresult;
        @SerializedName("feature")
        private List<String> feature;

        public String getNighttimesleep() {
            return nighttimesleep;
        }

        public void setNighttimesleep(String nighttimesleep) {
            this.nighttimesleep = nighttimesleep;
        }

        public String getRecordid() {
            return recordid;
        }

        public void setRecordid(String recordid) {
            this.recordid = recordid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getDoctorid() {
            return doctorid;
        }

        public void setDoctorid(String doctorid) {
            this.doctorid = doctorid;
        }

        public String getHeightevaluation() {
            return heightevaluation;
        }

        public void setHeightevaluation(String heightevaluation) {
            this.heightevaluation = heightevaluation;
        }

        public String getWeightevaluation() {
            return weightevaluation;
        }

        public void setWeightevaluation(String weightevaluation) {
            this.weightevaluation = weightevaluation;
        }

        public String getFeatureevaluation() {
            return featureevaluation;
        }

        public void setFeatureevaluation(String featureevaluation) {
            this.featureevaluation = featureevaluation;
        }

        public String getHwevaluation() {
            return hwevaluation;
        }

        public void setHwevaluation(String hwevaluation) {
            this.hwevaluation = hwevaluation;
        }

        public int getMonthage() {
            return monthage;
        }

        public void setMonthage(int monthage) {
            this.monthage = monthage;
        }

        public int getMonthday() {
            return monthday;
        }

        public void setMonthday(int monthday) {
            this.monthday = monthday;
        }

        public int getCorrectmonthage() {
            return correctmonthage;
        }

        public void setCorrectmonthage(int correctmonthage) {
            this.correctmonthage = correctmonthage;
        }

        public int getCorrectmonthday() {
            return correctmonthday;
        }

        public void setCorrectmonthday(int correctmonthday) {
            this.correctmonthday = correctmonthday;
        }

        public String getRecordstatus() {
            return recordstatus;
        }

        public void setRecordstatus(String recordstatus) {
            this.recordstatus = recordstatus;
        }

        public String getRecordtime() {
            return recordtime;
        }

        public void setRecordtime(String recordtime) {
            this.recordtime = recordtime;
        }

        public String getRecordDay(){
            String[] recordTimes = recordtime.split(" ");
            return recordTimes[0].replace("-",".");
        }

        public String getHeightResultString(){
            if ( "1".equals(heightresult) ){
                return "生长迟缓";
            } else {
                return "正常";
            }
        }

        public String getHeightresult() {
            return heightresult;
        }

        public void setHeightresult(String heightresult) {
            this.heightresult = heightresult;
        }

        public String getHwResultString(){
            if ( "-1".equals(hwresult) ){
                return "消瘦";
            } else if ( "1".equals(hwresult) ){
                return "超重";
            } else if ( "2".equals(hwresult) ){
                return "肥胖";
            } else if ( "3".equals(hwresult) ){
                return "重度肥胖";
            } else {
                return "正常";
            }

        }

        public String getHwresult() {
            return hwresult;
        }

        public void setHwresult(String hwresult) {
            this.hwresult = hwresult;
        }

        public String getWeightResultString(){
            if ( "1".equals(weightresult) ){
                return "低体重";
            }
            return "正常";
        }

        public String getWeightresult() {
            return weightresult;
        }

        public void setWeightresult(String weightresult) {
            this.weightresult = weightresult;
        }

        public String getFeatureResultString(){
            if ( "1".equals(featureresult) ){
                return "可疑";
            } else if ( "-1".equals(featureresult) ){
                return "异常";
            }
            return "正常";
        }

        public String getFeatureresult() {
            return featureresult;
        }

        public void setFeatureresult(String featureresult) {
            this.featureresult = featureresult;
        }

        public String getBabyMonth(){
            return monthage + "月" + monthday + "日";
        }

        public boolean isShowRecordState(){
            return "3".equals(recordstatus);
        }

        public List<String> getFeature() {
            return feature;
        }

        public void setFeature(List<String> feature) {
            this.feature = feature;
        }

        public String getCurrentMonthAgeString(){
            return monthage + "月" + monthday +"日";
        }

        public String getCorrectMonthAgeString(){
            return correctmonthage + "月" + correctmonthday +"日";
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

        public String getBreastfeedingcount() {
            return breastfeedingcount;
        }

        public void setBreastfeedingcount(String breastfeedingcount) {
            this.breastfeedingcount = breastfeedingcount;
        }

        public String getBreastfeedingml() {
            return breastfeedingml;
        }

        public void setBreastfeedingml(String breastfeedingml) {
            this.breastfeedingml = breastfeedingml;
        }

        public String getCacation() {
            return cacation;
        }

        public void setCacation(String cacation) {
            this.cacation = cacation;
        }

        public String getCacationdays() {
            return cacationdays;
        }

        public void setCacationdays(String cacationdays) {
            this.cacationdays = cacationdays;
        }

        public String getChestcircumference() {
            return chestcircumference;
        }

        public void setChestcircumference(String chestcircumference) {
            this.chestcircumference = chestcircumference;
        }

        public String getCostid() {
            return costid;
        }

        public void setCostid(String costid) {
            this.costid = costid;
        }

        public String getDaytimesleep() {
            return daytimesleep;
        }

        public void setDaytimesleep(String daytimesleep) {
            this.daytimesleep = daytimesleep;
        }

        public String getHeadcircumference() {
            return headcircumference;
        }

        public void setHeadcircumference(String headcircumference) {
            this.headcircumference = headcircumference;
        }

        public String getHeadcircumferenceevaluate() {
            return headcircumferenceevaluate;
        }

        public void setHeadcircumferenceevaluate(String headcircumferenceevaluate) {
            this.headcircumferenceevaluate = headcircumferenceevaluate;
        }

        public String getHeadcircumferenceresult() {
            return headcircumferenceresult;
        }

        public void setHeadcircumferenceresult(String headcircumferenceresult) {
            this.headcircumferenceresult = headcircumferenceresult;
        }

        public String getMilkfeedingcount() {
            return milkfeedingcount;
        }

        public void setMilkfeedingcount(String milkfeedingcount) {
            this.milkfeedingcount = milkfeedingcount;
        }

        public String getMilkfeedingml() {
            return milkfeedingml;
        }

        public void setMilkfeedingml(String milkfeedingml) {
            this.milkfeedingml = milkfeedingml;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getReportimgidx() {
            return reportimgidx;
        }

        public void setReportimgidx(String reportimgidx) {
            this.reportimgidx = reportimgidx;
        }

        public String getUrinate() {
            return urinate;
        }

        public void setUrinate(String urinate) {
            this.urinate = urinate;
        }

        public String getValiditytime() {
            return validitytime;
        }

        public void setValiditytime(String validitytime) {
            this.validitytime = validitytime;
        }
    }

    public static class User{

        private final static String MALE = "1";
        private final static String FEMALE = "0";

        private String sessionId;
        private String userid;
        private String headimg;                     //头像
        private String birthday;
        private String sex;                         //性别(0女,1男)
        private String weekdvalue;                  //矫正周差
        private String daydvalue;                   //矫正天差
        private String truename;

        private String accountmobile;               //手机账号
        private String loginpwd;                    //登录密码

        private String childuserid;                 //?
        private String assistedreproductive;        //辅助生育
        private String mumbearage;                  //妈妈生育年龄
        private String intracranialhemorrhage;      //颅内出血
        private String pregnancyweeks;              //孕周
        private String pregnancydays;               //孕周天
        private String birthtimes;                  //产次
        private String isstop;                      //是否禁用?
        private String deliverymethods;       //分娩方式
        private String pregnancies;                 //胎次
        private String bornweight;                  //出生体重
        private String borntype;                    //出生状况(1单胎,2多胎)
        private String neonatalasphyxia;            //新生儿窒息
        private String bornheight;                  //出生身高
        private String allergichistory;             //家族过敏史
        private String allergichistorydesc;         //家族过敏疾病内容
        private String roletype;                    //用户角色 0免费,1互联网用户,2医院
        private String hereditaryhistory;           //家族遗传史
        private String hereditaryhistorydesc;       //家族遗传史内容
        private String birthinjury;                 //产伤
        private String pasthistory;                 //既往史
        private String pasthistoryother;            //既往史其他
        private String recordtimes;                 //剩余测评次数

        private String babyAge;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public boolean isMale(){
            return MALE.equals(sex);
        }

        public String getSexString(){
            if ( MALE.equals(sex) ){
                return "男";
            } else {
                return "女";
            }
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getChilduserid() {
            return childuserid;
        }

        public void setChilduserid(String childuserid) {
            this.childuserid = childuserid;
        }

        public String getAssistedreproductive() {
            return assistedreproductive;
        }

        public String getAssistedreproductiveString(){
            if ( "0".equals(assistedreproductive) ){
                return "无";
            } else if ( "1".equals(assistedreproductive) ){
                return "人工授精";
            } else {
                return "试管婴儿";
            }
        }

        public void setAssistedreproductive(String assistedreproductive) {
            this.assistedreproductive = assistedreproductive;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getPregnancydays() {
            return pregnancydays;
        }

        public void setPregnancydays(String pregnancydays) {
            this.pregnancydays = pregnancydays;
        }

        public String getPregnancyweeks() {
            return pregnancyweeks;
        }

        public void setPregnancyweeks(String pregnancyweeks) {
            this.pregnancyweeks = pregnancyweeks;
        }

        public String getMumbearage() {
            return mumbearage;
        }

        public void setMumbearage(String mumbearage) {
            this.mumbearage = mumbearage;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getIntracranialhemorrhage() {
            return intracranialhemorrhage;
        }

        public String getIntracranialhemorrhageString(){
            if ( "0".equals(intracranialhemorrhage) ){
                return "无";
            } else {
                return "有";
            }
        }

        public void setIntracranialhemorrhage(String intracranialhemorrhage) {
            this.intracranialhemorrhage = intracranialhemorrhage;
        }

        public String getAccountmobile() {
            return accountmobile;
        }

        public void setAccountmobile(String accountmobile) {
            this.accountmobile = accountmobile;
        }

        public String getWeekdvalue() {
            return weekdvalue;
        }

        public void setWeekdvalue(String weekdvalue) {
            this.weekdvalue = weekdvalue;
        }

        public String getBirthtimes() {
            return birthtimes;
        }

        public void setBirthtimes(String birthtimes) {
            this.birthtimes = birthtimes;
        }

        public String getIsstop() {
            return isstop;
        }

        public void setIsstop(String isstop) {
            this.isstop = isstop;
        }

        public String getPregnancies() {
            return pregnancies;
        }

        public void setPregnancies(String pregnancies) {
            this.pregnancies = pregnancies;
        }

        public String getLoginpwd() {
            return loginpwd;
        }

        public void setLoginpwd(String loginpwd) {
            this.loginpwd = loginpwd;
        }

        public String getBornweight() {
            return bornweight;
        }

        public void setBornweight(String bornweight) {
            this.bornweight = bornweight;
        }

        public String getPasthistoryother() {
            return pasthistoryother;
        }

        public void setPasthistoryother(String pasthistoryother) {
            this.pasthistoryother = pasthistoryother;
        }

        public String getBorntype() {
            return borntype;
        }
        public String getBorntypeString() {
            if ( "1".equals(borntype) ){
                return "单胎";
            } else {
                return "多胎";
            }
        }

        public void setBorntype(String borntype) {
            this.borntype = borntype;
        }

        public String getNeonatalasphyxia() {
            return neonatalasphyxia;
        }

        public String getNeonatalasphyxiaString(){
            if ( "0".equals(neonatalasphyxia) ){
                return "无";
            } else {
                return "有";
            }
        }

        public void setNeonatalasphyxia(String neonatalasphyxia) {
            this.neonatalasphyxia = neonatalasphyxia;
        }

        public String getBornheight() {
            return bornheight;
        }

        public void setBornheight(String bornheight) {
            this.bornheight = bornheight;
        }

        public String getAllergichistorydesc() {
            return allergichistorydesc;
        }

        public void setAllergichistorydesc(String allergichistorydesc) {
            this.allergichistorydesc = allergichistorydesc;
        }

        public String getRoletype() {
            return roletype;
        }

        public void setRoletype(String roletype) {
            this.roletype = roletype;
        }

        public String getHereditaryhistorydesc() {
            return hereditaryhistorydesc;
        }

        public void setHereditaryhistorydesc(String hereditaryhistorydesc) {
            this.hereditaryhistorydesc = hereditaryhistorydesc;
        }

        public String getHeadimg() {
            if (TextUtils.isEmpty(headimg)){
                return null;
            } else {
                return UrlAddressList.BASE_IMAGE_URL + headimg;
            }
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getAllergichistory() {
            return allergichistory;
        }

        public String getAllergichistoryString(){
            if ( "0".equals(allergichistory) ){
                return "无";
            } else {
                return allergichistorydesc;
            }
        }

        public void setAllergichistory(String allergichistory) {
            this.allergichistory = allergichistory;
        }

        public String getBirthinjury() {
            return birthinjury;
        }

        public void setBirthinjury(String birthinjury) {
            this.birthinjury = birthinjury;
        }

        public String getBirthinjuryString(){
            if ( "0".equals(birthday) ){
                return "无";
            } else {
                return "有";
            }
        }

        public String getPasthistory() {
            return pasthistory;
        }

//        public String getPasthistoryString(){
//            if ( pasthistory == null ){
//                return "无";
//            }
//            StringBuffer buffer = new StringBuffer();
//            for (String string:pasthistory){
//                if ( buffer.length() > 0 ){
//                    buffer.append("、");
//                }
//                buffer.append(string);
//            }
//            return buffer.toString();
//        }

        public void setPasthistory(String pasthistory) {
            this.pasthistory = pasthistory;
        }

        public String getDaydvalue() {
            return daydvalue;
        }

        public void setDaydvalue(String daydvalue) {
            this.daydvalue = daydvalue;
        }

        public String getHereditaryhistory() {
            return hereditaryhistory;
        }

        public String getHereditaryhistoryString(){
            if ( "0".equals(hereditaryhistory) ){
                return "无";
            } else {
                return hereditaryhistorydesc;
            }
        }

        public void setHereditaryhistory(String hereditaryhistory) {
            this.hereditaryhistory = hereditaryhistory;
        }

        public String getRecordtimes() {
            return recordtimes;
        }

        public void setRecordtimes(String recordtimes) {
            this.recordtimes = recordtimes;
        }

        public String getDeliverymethods() {
            return deliverymethods;
        }

//        public String getDelivermethodsString(){
//            if ( deliverymethods == null ){
//                return "";
//            }
//            StringBuffer buffer = new StringBuffer();
//            for (String string:deliverymethods){
//                buffer.append(string);
//            }
//            return buffer.toString();
//        }

        public void setDeliverymethods(String deliverymethods) {
            this.deliverymethods = deliverymethods;
        }

        public String getBabyAge(){
            if ( TextUtils.isEmpty(weekdvalue) && TextUtils.isEmpty(daydvalue) ){
                return "";
            }
            if ( TextUtils.isEmpty(weekdvalue) ){
                weekdvalue = "0";
            }
            if ( TextUtils.isEmpty(daydvalue) ){
                daydvalue = "0";
            }
            return weekdvalue+"周"+daydvalue+"日";
        }
    }

    public static class ReportImage{
        private String id;
        private String imgurl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgurl() {
            if ( !TextUtils.isEmpty(imgurl) ){
                return UrlAddressList.BASE_IMAGE_URL + imgurl;
            }
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }

}

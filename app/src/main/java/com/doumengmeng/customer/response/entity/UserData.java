package com.doumengmeng.customer.response.entity;

import android.text.TextUtils;

import com.doumengmeng.customer.entity.RoleType;
import com.doumengmeng.customer.net.UrlAddressList;

import java.util.List;

/**
 * Created by Administrator on 2017/12/18.
 */

public class UserData {

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
//    private String userAccount;                 //登录账号
//    private String passwd;                      //密码

    private String childuserid;                 //?
    private String assistedreproductive;        //辅助生育
    private String mumbearage;                  //妈妈生育年龄
    private String intracranialhemorrhage;      //颅内出血
    private String pregnancyweeks;              //孕周
    private String pregnancydays;               //孕周天
    private String birthtimes;                  //产次
    private String isstop;                      //是否禁用?
    private List<String> deliverymethods;       //分娩方式
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
    private List<String> pasthistory;            //既往史
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
        if ( !getRoleType().equals(RoleType.PAY_HOSPITAL_USER) && !getRoleType().equals(RoleType.PAY_NET_USER) ){
            return true;
        }
        return MALE.equals(sex);
    }

    private RoleType getRoleType(){
        if ( "3".equals(roletype) ) {
            return RoleType.FREE_HOSPITAL_USER;
        } else if ( "1".equals(roletype) ){
            return RoleType.PAY_NET_USER;
        } else if ( "2".equals(roletype) ){
            return RoleType.PAY_HOSPITAL_USER;
        } else {
            return RoleType.FREE_NET_USER;
        }
//        return RoleType.FREE_NET_USER;
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

    public void setBorntype(String borntype) {
        this.borntype = borntype;
    }

    public String getNeonatalasphyxia() {
        return neonatalasphyxia;
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
            return UrlAddressList.IMAGE_URL + headimg;
        }
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getAllergichistory() {
        return allergichistory;
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

    public List<String> getPasthistory() {
        return pasthistory;
    }

    public void setPasthistory(List<String> pasthistory) {
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

    public void setHereditaryhistory(String hereditaryhistory) {
        this.hereditaryhistory = hereditaryhistory;
    }

    public String getRecordtimes() {
        return recordtimes;
    }

    public void setRecordtimes(String recordtimes) {
        this.recordtimes = recordtimes;
    }

    public List<String> getDeliverymethods() {
        return deliverymethods;
    }

    public void setDeliverymethods(List<String> deliverymethods) {
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

//    public String getPasswd() {
//        return passwd;
//    }

//    public void setPasswd(String passwd) {
//        this.passwd = passwd;
//    }
}

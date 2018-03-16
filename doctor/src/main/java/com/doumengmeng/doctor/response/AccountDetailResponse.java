package com.doumengmeng.doctor.response;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */

public class AccountDetailResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private List<Detail> detailedList;

        public List<Detail> getDetailedList() {
            return detailedList;
        }

        public void setDetailedList(List<Detail> detailedList) {
            this.detailedList = detailedList;
        }
    }

    public static class Detail{
        private String buyprice;
        private String buytime;
        private String costid;
        private String doctorid;
        private String evaluationtime;
        private String isuse;
        private String truename;
        private String userid;

        public String getBuyprice() {
            return buyprice;
        }

        public void setBuyprice(String buyprice) {
            this.buyprice = buyprice;
        }

        public String getBuytime() {
            return buytime;
        }

        public void setBuytime(String buytime) {
            this.buytime = buytime;
        }

        public String getCostid() {
            return costid;
        }

        public void setCostid(String costid) {
            this.costid = costid;
        }

        public String getDoctorid() {
            return doctorid;
        }

        public void setDoctorid(String doctorid) {
            this.doctorid = doctorid;
        }

        public String getEvaluationtime() {
            return evaluationtime;
        }

        public void setEvaluationtime(String evaluationtime) {
            this.evaluationtime = evaluationtime;
        }

        public String getIsuse() {
            return isuse;
        }

        public void setIsuse(String isuse) {
            this.isuse = isuse;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }

}

package com.doumengmeng.customer.response;

import com.doumengmeng.customer.response.entity.DoctorTrans;
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
        private List<Banner> bannerList;
        private List<DoctorTrans> DoctorList;
        private List<Hospital> HospitalList;
        private List<MengClass> MengClassList;
        private List<Growth> GrowthList;
        private List<Feature> FeatureList;
        private com.doumengmeng.customer.response.entity.DayList DayList;
        private NotificationData nextTimeList;
        private String featureVersion;

        public NotificationData getNextTimeList() {
            return nextTimeList;
        }

        public List<Banner> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<Banner> bannerList) {
            this.bannerList = bannerList;
        }

        public void setNextTimeList(NotificationData nextTimeList) {
            this.nextTimeList = nextTimeList;
        }

        public List<DoctorTrans> getDoctorList() {
            return DoctorList;
        }

        public void setDoctorList(List<DoctorTrans> doctorList) {
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

        public String getFeatureVersion() {
            return featureVersion;
        }

        public void setFeatureVersion(String featureVersion) {
            this.featureVersion = featureVersion;
        }
    }

    public static class Banner{
        private String id;
        private String doctorid;
        private String imgurl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDoctorid() {
            return doctorid;
        }

        public void setDoctorid(String doctorid) {
            this.doctorid = doctorid;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }

    public static class NotificationData{
        private String nexttime;
        private String recordid;
        private String doctorid;
        private String noticeContent;
        private String noticeTitle;

        public String getNexttime() {
            return nexttime;
        }

        public void setNexttime(String nexttime) {
            this.nexttime = nexttime;
        }

        public String getRecordid() {
            return recordid;
        }

        public void setRecordid(String recordid) {
            this.recordid = recordid;
        }

        public String getDoctorid() {
            return doctorid;
        }

        public void setDoctorid(String doctorid) {
            this.doctorid = doctorid;
        }

        public String getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(String noticeContent) {
            this.noticeContent = noticeContent;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        @Override
        public boolean equals(Object obj) {
            if ( obj != null && obj instanceof NotificationData ){
                NotificationData dest = (NotificationData) obj;
                if ( nexttime.equals(dest.getNexttime())
                        && recordid.equals(dest.getRecordid())
                        && doctorid.equals(dest.getDoctorid())
                        && noticeContent.equals(dest.getNoticeContent())
                        && noticeTitle.equals(dest.getNoticeTitle())
                        ){
                    return true;
                }
            }
            return false;
        }
    }

}

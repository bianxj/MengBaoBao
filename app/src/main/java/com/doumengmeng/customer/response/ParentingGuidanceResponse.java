package com.doumengmeng.customer.response;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ParentingGuidanceResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private List<ParentingGuideClass> parentingGuidanceList;

        public static class ParentingGuideClass{
            private String guidanceType;
            private List<ParentingGuideItem> guidanceList;

            public String getGuidanceType() {
                return guidanceType;
            }

            public void setGuidanceType(String guidanceType) {
                this.guidanceType = guidanceType;
            }

            public List<ParentingGuideItem> getGuidanceList() {
                return guidanceList;
            }

            public void setGuidanceList(List<ParentingGuideItem> guidanceList) {
                this.guidanceList = guidanceList;
            }
        }

        public static class ParentingGuideItem{
            private String id;
            private String userid;
            private String nutureid;
            private String recordid;
            private String nurturetypeid;
            private String customnurturelorder;
            private String customnurturetitle;
            private String customnurturedesc;
            private String nurturetitle;
            private String nurturedesc;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getNutureid() {
                return nutureid;
            }

            public void setNutureid(String nutureid) {
                this.nutureid = nutureid;
            }

            public String getRecordid() {
                return recordid;
            }

            public void setRecordid(String recordid) {
                this.recordid = recordid;
            }

            public String getNurturetypeid() {
                return nurturetypeid;
            }

            public void setNurturetypeid(String nurturetypeid) {
                this.nurturetypeid = nurturetypeid;
            }

            public String getCustomnurturelorder() {
                return customnurturelorder;
            }

            public void setCustomnurturelorder(String customnurturelorder) {
                this.customnurturelorder = customnurturelorder;
            }

            public String getCustomnurturetitle() {
                return customnurturetitle;
            }

            public void setCustomnurturetitle(String customnurturetitle) {
                this.customnurturetitle = customnurturetitle;
            }

            public String getCustomnurturedesc() {
                return customnurturedesc;
            }

            public void setCustomnurturedesc(String customnurturedesc) {
                this.customnurturedesc = customnurturedesc;
            }

            public String getNurturetitle() {
                return nurturetitle;
            }

            public void setNurturetitle(String nurturetitle) {
                this.nurturetitle = nurturetitle;
            }

            public String getNurturedesc() {
                return nurturedesc;
            }

            public void setNurturedesc(String nurturedesc) {
                this.nurturedesc = nurturedesc;
            }
        }

        public List<ParentingGuideClass> getParentingGuidanceList() {
            return parentingGuidanceList;
        }

        public void setParentingGuidanceList(List<ParentingGuideClass> parentingGuidanceList) {
            this.parentingGuidanceList = parentingGuidanceList;
        }
    }

}

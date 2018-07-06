package com.doumengmeng.customer.response;

import java.util.List;

public class SearchLessonResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private List<LessonItem> mengClassList;

        public List<LessonItem> getMengClassList() {
            return mengClassList;
        }

        public void setMengClassList(List<LessonItem> mengClassList) {
            this.mengClassList = mengClassList;
        }
    }

    public static class LessonItem{
        private String classurl;
        private String classid;
        private String classtag;
        private String classtitle;
        private String classdesc;
        private String classimg;
        private String classtime;

        public String getClassurl() {
            return classurl;
        }

        public void setClassurl(String classurl) {
            this.classurl = classurl;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getClasstag() {
            return classtag;
        }

        public void setClasstag(String classtag) {
            this.classtag = classtag;
        }

        public String getClasstitle() {
            return classtitle;
        }

        public void setClasstitle(String classtitle) {
            this.classtitle = classtitle;
        }

        public String getClassdesc() {
            return classdesc;
        }

        public void setClassdesc(String classdesc) {
            this.classdesc = classdesc;
        }

        public String getClassimg() {
            return classimg;
        }

        public void setClassimg(String classimg) {
            this.classimg = classimg;
        }

        public String getClasstime() {
            return classtime;
        }

        public void setClasstime(String classtime) {
            this.classtime = classtime;
        }
    }

}

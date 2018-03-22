package com.doumengmeng.doctor.response;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class MessageResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        List<News> doctorNews;

        public List<News> getDoctorNews() {
            return doctorNews;
        }

        public void setDoctorNews(List<News> doctorNews) {
            this.doctorNews = doctorNews;
        }
    }

    public static class News{
        private String doctorid;
        private String newsdate;
        private String newsid;
        private String newsname;
        private String newsstate;
        private String newstype;
        private String newscontent;

        public String getDoctorid() {
            return doctorid;
        }

        public void setDoctorid(String doctorid) {
            this.doctorid = doctorid;
        }

        public String getNewsdate() {
            return newsdate;
        }

        public void setNewsdate(String newsdate) {
            this.newsdate = newsdate;
        }

        public String getNewsid() {
            return newsid;
        }

        public void setNewsid(String newsid) {
            this.newsid = newsid;
        }

        public String getNewsname() {
            return newsname;
        }

        public void setNewsname(String newsname) {
            this.newsname = newsname;
        }

        public String getNewsstate() {
            return newsstate;
        }

        public void setNewsstate(String newsstate) {
            this.newsstate = newsstate;
        }

        public String getNewstype() {
            return newstype;
        }

        public void setNewstype(String newstype) {
            this.newstype = newstype;
        }

        public String getNewscontent() {
            return newscontent;
        }

        public void setNewscontent(String newscontent) {
            this.newscontent = newscontent;
        }
    }

}

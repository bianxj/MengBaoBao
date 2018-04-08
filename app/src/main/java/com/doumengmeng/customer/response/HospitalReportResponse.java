package com.doumengmeng.customer.response;

import com.doumengmeng.customer.response.entity.HospitalReport;
import com.doumengmeng.customer.response.entity.ImageData;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class HospitalReportResponse extends BaseResponse {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private List<ImageData> imgList;
        private List<HospitalReport> childRecordList;

        public List<ImageData> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImageData> imgList) {
            this.imgList = imgList;
        }

        public List<HospitalReport> getChildRecordList() {
            return childRecordList;
        }

        public void setChildRecordList(List<HospitalReport> childRecordList) {
            this.childRecordList = childRecordList;
        }
    }

}
